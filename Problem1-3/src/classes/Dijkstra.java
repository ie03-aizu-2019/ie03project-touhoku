package classes;

import java.util.Vector;

import classes.PointType.e_PointType;

public class Dijkstra {
	Vector<DijkstraNode> nodes;
	Vector<Double> resultMatrix;
	Vector<Double> D;
	Vector<Point> points;
	Vector<Line> lines;
	Vector<CrossPoint> cPoints;
	PointType startPoint, goalPoint;

	public Dijkstra(PointType startPoint, PointType goalPoint, Vector<Point> points, Vector<Line> lines,
			Vector<CrossPoint> cPoints) {
		D = new Vector<Double>();
		nodes = new Vector<DijkstraNode>();
		resultMatrix = new Vector<Double>();
		this.points = points;
		this.lines = lines;
		this.cPoints = cPoints;
		this.startPoint = startPoint;
		this.goalPoint = goalPoint;

		//create D
		for (int i = 0; i < Math.pow((points.size() + cPoints.size()), 2); i++) {
			D.add(Double.MAX_VALUE);
			resultMatrix.add(Double.MAX_VALUE);
		}

		//set 0. in start point
		D.set(startPoint.type == e_PointType.Point ? startPoint.Id : points.size() + startPoint.Id, 0.);
		//done node
		var searchedNode = new Vector<Dijkstra_Search>();
		var startPath = new Vector<PointType>();
		startPath.add(startPoint);
		var test = new Dijkstra_Search(startPoint, 0, startPath);
		searchedNode.add(new Dijkstra_Search(startPoint, 0, startPath));
		var getMovablePoint = MovablePoint(searchedNode.get(0).location);
		for (int n = 0; n < getMovablePoint.size(); n++) {
			System.out.printf("%s ", getMovablePoint.get(n).type == e_PointType.Point ? "Point " : "CrossPoint");
			System.out.printf("%d\n", getMovablePoint.get(n).Id);
		}

		for (int n = 0; n < searchedNode.size(); n++) {
			var moveablePoint = MovablePoint(searchedNode.get(n).location);
			for (int m=0;m<moveablePoint.size();m++) {
				System.out.printf("%d ", moveablePoint.get(m).Id);
			}
			System.out.printf("\n");
		}
		PrintD();
	}

	Vector<PointType> MovablePoint(PointType getPoint) {
		var result = new Vector<PointType>();
		var searchedLine = new Vector<Line>();

		for (int p = 0; p < cPoints.size(); p++) {
			if (getPoint.type == e_PointType.Point) {
				//point -> cpoint
				if (cPoints.get(p).line1.P1.Id.Id == getPoint.Id || cPoints.get(p).line1.P2.Id.Id == getPoint.Id) {
					result.add(cPoints.get(p).id);
					searchedLine.add(cPoints.get(p).line1);
				}
				if (cPoints.get(p).line2.P1.Id.Id == getPoint.Id || cPoints.get(p).line2.P2.Id.Id == getPoint.Id) {
					result.add(cPoints.get(p).id);
					searchedLine.add(cPoints.get(p).line2);
				}
			}
			if (getPoint.type == e_PointType.CrossPoint) {
				if (cPoints.get(p).id.Id == getPoint.Id) {
					result.add(cPoints.get(p).line1.P1.Id);
					result.add(cPoints.get(p).line1.P2.Id);
					result.add(cPoints.get(p).line2.P1.Id);
					result.add(cPoints.get(p).line2.P2.Id);
					searchedLine.add(cPoints.get(p).line1);
					searchedLine.add(cPoints.get(p).line2);
				}
			}
		}
		for (int p = 0; p < lines.size(); p++) {
			if (!searchedLine.contains(lines.get(p))) {
				if (lines.get(p).P1.Id.equals(getPoint)) {
					result.add(lines.get(p).P2.Id);
				} else if (lines.get(p).P2.Id.equals(getPoint)) {
					result.add(lines.get(p).P1.Id);
				}
			}
		}

		return result;
	}

	void PrintD() {
		for (int y = 0; y < points.size() + cPoints.size(); y++) {
			for (int x = 0; x < points.size() + cPoints.size(); x++) {
				double getData = D.get(y * (points.size() + cPoints.size()) + x);
				if (getData == Double.MAX_VALUE) {
					System.out.printf("INF ");
				} else {
					System.out.printf("%.1f ", getData);
				}
			}
			System.out.printf("\n");
		}
	}
}

class Dijkstra_Search {
	int totalCost;
	PointType location;
	Vector<PointType> path;

	public Dijkstra_Search(PointType getLocation, int getCost, Vector<PointType> getPoint) {
		this.location = getLocation;
		this.totalCost = getCost;
		this.path = (Vector<PointType>) getPoint.clone();
	}
}