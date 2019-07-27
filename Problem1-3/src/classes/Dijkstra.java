package classes;

import java.util.Vector;

import classes.PointInfo.e_PointType;

public class Dijkstra {
	Vector<DijkstraNode> nodes;
	Vector<Double> resultMatrix;
	Dijkstra_Search[] D;
	Vector<Line> lines;
	Vector<Point> points;
	Vector<CrossPoint> cPoints;
	Point startPoint, goalPoint;

	public Dijkstra(Point startPoint, Point goalPoint, Vector<Point> points, Vector<Line> lines,
			Vector<CrossPoint> cPoints) {
		D = new Dijkstra_Search[points.size() + cPoints.size()];
		nodes = new Vector<DijkstraNode>();
		resultMatrix = new Vector<Double>();
		this.lines = lines;
		this.points = points;
		this.cPoints = cPoints;
		this.startPoint = startPoint;
		this.goalPoint = goalPoint;

		//create D
		for (int x = 0; x < points.size() + cPoints.size(); x++) {
			D[x] = new Dijkstra_Search();
		}

		var searchedNode = new Vector<Dijkstra_Search>();
		var startNode = D[getPointArrayIndex(startPoint.pointInfo.type,startPoint.pointInfo.Id)] = new Dijkstra_Search(startPoint, 0, null);
		searchedNode.add(startNode);

		Dijkstra_Search nextNode = startNode;
		while (true) {
			if (nextNode == null) {
				break;
			}
			var getMovablePoint = MovablePoint(nextNode.location);

			for (int n = 0; n < getMovablePoint.size(); n++) {

				/*
				System.out.printf("from %s ",
						nextNode.location.pointInfo.type == e_PointType.Point ? "Point " : "CrossPoint");
				System.out.printf("%d ", nextNode.location.pointInfo.Id);
				System.out.printf("to %s ",
						getMovablePoint.get(n).pointInfo.type == e_PointType.Point ? "Point " : "CrossPoint");
				System.out.printf("%d\n", getMovablePoint.get(n).pointInfo.Id);

				*/

				int setIndex = getPointArrayIndex(getMovablePoint.get(n).pointInfo.type, getMovablePoint.get(n).pointInfo.Id);
				Point getPointInfo = getMovablePoint.get(n);
				Vec setVec;
				Vec setedVec;

				// setedVec -> setVec
				if (nextNode.location.pointInfo.type == e_PointType.Point) {
					setedVec = points.get(nextNode.location.pointInfo.Id).xyVec;
				} else {
					setedVec = cPoints.get(nextNode.location.pointInfo.Id).xyVec;
				}
				if (getMovablePoint.get(n).pointInfo.type == e_PointType.Point) {
					setVec = points.get(getMovablePoint.get(n).pointInfo.Id).xyVec;
				} else {
					setVec = cPoints.get(getMovablePoint.get(n).pointInfo.Id).xyVec;
				}

				var tmpCal = Math.pow(Math.pow(setVec.x - setedVec.x, 2) + Math.pow(setVec.y - setedVec.y, 2), 0.5);

				if (D[setIndex].totalCost > nextNode.totalCost + tmpCal) {
					D[setIndex] = new Dijkstra_Search(getMovablePoint.get(n), nextNode.totalCost + tmpCal, nextNode.path);
				}
			}

			//PrintD();
			//System.out.printf("\n\n");

			nextNode = ConfirmNode(searchedNode);
			System.out.printf("");
		}

		var result = D[getPointArrayIndex(goalPoint.pointInfo.type,goalPoint.pointInfo.Id)];
		for (int i=0;i<result.path.size();i++) {
			if (result.path.get(i).pointInfo.type == e_PointType.Point) {
				System.out.printf("Point ");
			}else {
				System.out.printf("Cross Point ");
			}
			System.out.printf("%d ", result.path.get(i).pointInfo.Id);
			if (i < result.path.size()-1) {
				System.out.printf(" -> ");
			}
		}
		System.out.printf("\ntotal cost %f\n",result.totalCost);
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
		for (int x = 0; x < points.size() + cPoints.size(); x++) {
			double getData = D[x].totalCost;
			if (Math.abs(getData - Double.MAX_VALUE) < 0.000001) {
				System.out.printf("INFY ");
			} else {
				System.out.printf("%04.1f ", getData);
			}
		}
		System.out.printf("\n");
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

	// Searched Node には無い最小コストを検出
	Dijkstra_Search NextSearch(Vector<Dijkstra_Search> getSearchedNode) {
		double min_distance = Double.MAX_VALUE;
		Dijkstra_Search min_node = null;
		for (int x = 0; x < points.size() + cPoints.size(); x++) {
			if (min_distance > D[x].totalCost) {
				var existed = false;
				for (int i = 0; i < getSearchedNode.size(); i++) {
					if (D[x] == getSearchedNode.get(i)) {
						existed = true;
					}
				}
				if (existed == false) {
					min_node = D[x];
					min_distance = D[x].totalCost;
				}
			}
		}
		return min_node;
	}

	Dijkstra_Search ConfirmNode(Vector<Dijkstra_Search> getSearchedNode) {
		double min_distance = Double.MAX_VALUE;
		Dijkstra_Search min_node = null;
		for (int x = 0; x < points.size() + cPoints.size(); x++) {
			if (min_distance > D[x].totalCost) {
				var existed = false;
				for (int i = 0; i < getSearchedNode.size(); i++) {
					if (D[x].location.pointInfo == getSearchedNode.get(i).location.pointInfo) {
						existed = true;
					}
				}
				if (existed == false) {
					min_node = D[x];
					min_distance = D[x].totalCost;
				}
			}
		}
		getSearchedNode.add(min_node);
		return min_node;
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
	Point location;
	Vector<Point> path;

	public Dijkstra_Search() {
		this.location = null;
		this.totalCost = Double.MAX_VALUE;
		this.path = new Vector<Point>();
	}

	public Dijkstra_Search(Dijkstra_Search copy) {
		this.location = copy.location;
		this.totalCost = copy.totalCost;
		this.path = new Vector<Point>(copy.path);
	}

	public Dijkstra_Search(Point getLocation, double getCost, Vector<Point> getPath) {
		this.location = getLocation;
		this.totalCost = getCost;
		if (getPath == null) {
			this.path = new Vector<Point>();
		} else {
			this.path = (Vector<Point>) getPath.clone();
		}
		this.path.add(getLocation);
	}
}