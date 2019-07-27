package classes;

import java.util.Vector;

import classes.PointInfo.e_PointType;

public class Dijkstra {
	Vector<DijkstraNode> nodes;
	Vector<Double> resultMatrix;
	double[][] D;
	Vector<Line> lines;
	Vector<Point> points;
	Vector<CrossPoint> cPoints;
	PointInfo startPoint, goalPoint;

	public Dijkstra(PointInfo startPoint, PointInfo goalPoint, Vector<Point> points, Vector<Line> lines,
			Vector<CrossPoint> cPoints) {
		D = new double[points.size() + cPoints.size()][points.size() + cPoints.size()];
		nodes = new Vector<DijkstraNode>();
		resultMatrix = new Vector<Double>();
		this.lines = lines;
		this.points = points;
		this.cPoints = cPoints;
		this.startPoint = startPoint;
		this.goalPoint = goalPoint;

		//create D
		for (int y = 0; y < points.size() + cPoints.size(); y++) {
			for (int x = 0; x < points.size() + cPoints.size(); x++) {
				D[y][x] = Double.MAX_VALUE;
			}
		}
		//resultMatrix.add(Double.MAX_VALUE);

		//set 0. in start point
		int startIndex = startPoint.type == e_PointType.Point ? startPoint.Id : points.size() + startPoint.Id;
		D[startIndex][startIndex] = 0.;
		//done node
		var searchedNode = new Vector<Dijkstra_Search>();
		var startPath = new Vector<PointInfo>();
		startPath.add(startPoint);
		searchedNode.add(new Dijkstra_Search(startPoint, 0, startPath, null));

		//debug
		var debugPoint = cPoints.get(2);
		var getMovablePoint = MovablePoint(debugPoint);
		for (int n = 0; n < getMovablePoint.size(); n++) {
			System.out.printf("from %s ",
					debugPoint.pointInfo.type == e_PointType.Point ? "Point " : "CrossPoint");
			System.out.printf("%d ", debugPoint.pointInfo.Id);
			System.out.printf("to %s ",
					getMovablePoint.get(n).pointInfo.type == e_PointType.Point ? "Point " : "CrossPoint");
			System.out.printf("%d\n", getMovablePoint.get(n).pointInfo.Id);
		}

		/*while (searchedNode.size() <= 2) {
			for (int i = 0; i < searchedNode.size(); i++) {
				var getMovablePoint = MovablePoint(searchedNode.get(i).location);
				for (int n = 0; n < getMovablePoint.size(); n++) {

					System.out.printf("from %s ",
							searchedNode.get(i).location.type == e_PointType.Point ? "Point " : "CrossPoint");
					System.out.printf("%d ", searchedNode.get(i).location.Id);
					System.out.printf("to %s ",
							getMovablePoint.get(n).type == e_PointType.Point ? "Point " : "CrossPoint");
					System.out.printf("%d\n", getMovablePoint.get(n).Id);

					int setedIndex = getPointArrayIndex(searchedNode.get(i).location.type,
							searchedNode.get(i).location.Id);
					int setIndex = getPointArrayIndex(getMovablePoint.get(n).type, getMovablePoint.get(n).Id);
					PointInfo getPointInfo = getMovablePoint.get(n);
					Vec setVec;
					Vec setedVec;

					// setedVec -> setVec
					if (searchedNode.get(i).location.type == e_PointType.Point) {
						setedVec = points.get(searchedNode.get(i).location.Id);
					} else {
						setedVec = cPoints.get(searchedNode.get(i).location.Id).CrossingVec;
					}
					if (getMovablePoint.get(n).type == e_PointType.Point) {
						setVec = points.get(getMovablePoint.get(n).Id);
					} else {
						setVec = cPoints.get(getMovablePoint.get(n).Id).CrossingVec;
					}

					var tmpCal = Math.pow(Math.pow(setVec.x - setedVec.x, 2) + Math.pow(setVec.y - setedVec.y, 2), 0.5);

					if (setIndex < setedIndex) {
						int tmp = setIndex;
						setIndex = setedIndex;
						setedIndex = tmp;
					}

					if (D[setedIndex][setIndex] > searchedNode.get(i).totalCost + tmpCal) {
						D[setedIndex][setIndex] = searchedNode.get(i).totalCost + tmpCal;

						//new node
						Dijkstra_Search newNode = new Dijkstra_Search(getMovablePoint.get(n),
								searchedNode.get(i).totalCost + tmpCal, searchedNode.get(i).path,
								getMovablePoint.get(n));
						searchedNode.add(newNode);
					}
				}
				PrintD();
				System.out.printf("\n\n");
			}

		}*/
	}

	Vector<Point> MovablePoint(Point getPoint) {
		var result = new Vector<Point>();

		for (int l = 0; l < lines.size(); l++) {

			if (getPoint.pointInfo.type == e_PointType.Point) {
				double min_dis = Double.MAX_VALUE;
				Point min_Point = null;
				if (lines.get(l).P1 == getPoint) {
					double cal = Math.pow(lines.get(l).P2.xyVec.x - getPoint.xyVec.x, 2) + Math.pow(lines.get(l).P2.xyVec.y - getPoint.xyVec.y, 2);
					if (min_dis > cal) {
						min_dis = cal;
						min_Point = lines.get(l).P2;
					}

				}
				if (lines.get(l).P2 == getPoint) {
					double cal = Math.pow(lines.get(l).P1.xyVec.x - getPoint.xyVec.x, 2) + Math.pow(lines.get(l).P1.xyVec.y - getPoint.xyVec.y, 2);
					if (min_dis > cal) {
						min_dis = cal;
						min_Point = lines.get(l).P1;
					}

				}

				if (lines.get(l).P1 == getPoint || lines.get(l).P2 == getPoint) {
					for (int c = 0; c < lines.get(l).crossPoints.size(); c++) {
						double cal = Math.pow(lines.get(l).crossPoints.get(c).xyVec.x - getPoint.xyVec.x, 2) + Math.pow(lines.get(l).crossPoints.get(c).xyVec.y - getPoint.xyVec.y, 2);
						if (min_dis > cal) {
							min_dis = cal;
							min_Point = lines.get(l).crossPoints.get(c);
						}
					}
				}
				if (min_Point != null) {
					result.add(min_Point);
				}
			} else {
				for (int c = 0; c < lines.get(l).crossPoints.size(); c++) {
					if (lines.get(l).crossPoints.get(c) == getPoint) {

						double min_length = Double.MAX_VALUE;
						Point min_Point = lines.get(l).P1;

						for (int m = 0; m < lines.get(l).crossPoints.size(); m++) {
							if (c != m) {
								Double cal = Vec.CalLength(Vec.Sub(lines.get(l).P1.xyVec, lines.get(l).crossPoints.get(c).xyVec),
										Vec.Sub(lines.get(l).crossPoints.get(m).xyVec, lines.get(l).crossPoints.get(c).xyVec));
								if (cal != null && cal > 0 && min_length > cal) {
									min_length = cal;
									min_Point = lines.get(l).crossPoints.get(m);
								}
							}
						}
						result.add(min_Point);

						min_length = Double.MAX_VALUE;
						min_Point = lines.get(l).P2;
						for (int m = 0; m < lines.get(l).crossPoints.size(); m++) {
							if (c != m) {
								Double cal = Vec.CalLength(Vec.Sub(lines.get(l).P2.xyVec, lines.get(l).crossPoints.get(c).xyVec),
										Vec.Sub(lines.get(l).crossPoints.get(m).xyVec, lines.get(l).crossPoints.get(c).xyVec));
								if (cal != null && cal > 0 && min_length > cal) {
									min_length = cal;
									min_Point = lines.get(l).crossPoints.get(m);
								}
							}
						}
						result.add(min_Point);

					}
				}
			}

		}

		return result;
	}

	void PrintD() {
		for (int y = 0; y < points.size() + cPoints.size(); y++) {
			for (int x = 0; x < points.size() + cPoints.size(); x++) {
				double getData = D[y][x];
				if (y >= x) {
					System.out.printf("---- ");
				} else if (getData == Double.MAX_VALUE) {
					System.out.printf("INFY ");
				} else {
					System.out.printf("%04.1f ", getData);
				}
			}
			System.out.printf("\n");
		}
	}

	//同じ線上のCross Pointを getPosi から遠い方を消す
	// -> 隣り合ったポイントを求めるため
	Vector<CrossPoint> AntiDoubleLine(Vector<CrossPoint> getCPoint, Vec getPosi) {
		for (int i = 0; i < getCPoint.size(); i++) {
			for (int n = i + 1; n < getCPoint.size();) {
				if ((getCPoint.get(i).line1.P1 == getCPoint.get(n).line1.P1 && getCPoint.get(i).line1.P2 == getCPoint.get(n).line1.P2
						&& (getCPoint.get(i).line1.P1.xyVec == getPosi || getCPoint.get(i).line1.P2.xyVec == getPosi)) ||
						(getCPoint.get(i).line2.P1 == getCPoint.get(n).line2.P1 && getCPoint.get(i).line2.P2 == getCPoint.get(n).line2.P2
								&& (getCPoint.get(i).line2.P1.xyVec == getPosi || getCPoint.get(i).line2.P2.xyVec == getPosi))) {
					System.out.printf("%d and %d is same\n", i, n);
					double a = Math.pow(getCPoint.get(i).xyVec.x - getPosi.x, 2)
							+ Math.pow(getCPoint.get(i).xyVec.y - getPosi.y, 2);
					double b = Math.pow(getCPoint.get(n).xyVec.x - getPosi.x, 2)
							+ Math.pow(getCPoint.get(n).xyVec.y - getPosi.y, 2);

					if (a < b) {
						System.out.printf("remove at %d\n", i);
						getCPoint.remove(i);
					} else {
						System.out.printf("remove at %d\n", n);
						getCPoint.remove(n);
					}

					break;
				} else {
					n++;
				}
			}
		}
		return getCPoint;
	}

	//CrossPoint用にD配列をオフセットする
	int getPointArrayIndex(e_PointType getType, int index) {
		if (getType == e_PointType.Point) {
			return index;
		} else {
			return index + points.size();
		}
	}
}

class Dijkstra_Search {
	double totalCost;
	PointInfo location;
	Vector<Point> path;

	public Dijkstra_Search(PointInfo getLocation, double getCost, Vector<PointInfo> getPath, Point newPath) {
		this.location = getLocation;
		this.totalCost = getCost;
		this.path = (Vector<Point>) getPath.clone();
		if (newPath != null) {
			this.path.add(newPath);
		}
	}
}