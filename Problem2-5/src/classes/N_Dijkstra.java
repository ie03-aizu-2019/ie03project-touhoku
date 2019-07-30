package classes;

import java.util.Vector;

import classes.PointInfo.e_PointType;

public class N_Dijkstra {
	Vector<Dijkstra_Search> dijkstras;
	Vector<Point> points;
	Vector<CrossPoint> cPoints;
	Vector<Line> lines;

	public N_Dijkstra(Point startPoint, Point goalPoint, Vector<Point> points, Vector<Line> lines,
			Vector<CrossPoint> cPoints) {
		this.dijkstras = new Vector<Dijkstra_Search>();
		this.points = points;
		this.cPoints = cPoints;
		this.lines = lines;
		var A = new Vector<Dijkstra>();
		var B = new Vector<Dijkstra>();

		A.add(new Dijkstra(startPoint, goalPoint, points, lines, cPoints, null));

		int n_shortest = 2;
		for (int n = 1; n < n_shortest; n++) {
			for (int p = 0; p < A.get(n - 1).GetPath().size() - 1; p++) {
				var ex_path = A.get(n - 1).GetPath();

				var exception = new Vector<Node2Node>();
				for (int na = 0; na < A.size(); na++) {
					var node = A.get(na).GetPath();
					for (int ex = 0; ex < node.size() - 1; ex++) {
						if (ex_path.get(p).pointInfo == node.get(ex).pointInfo) {
							exception.add(new Node2Node(node.get(ex), node.get(ex + 1)));
						}
					}
				}

				startPoint = ex_path.get(p);

				var ex_dij = new Dijkstra(startPoint, goalPoint, points, lines, cPoints, exception);
				if (ex_dij.GetCost() != Double.MAX_VALUE) {
					double offset = 0;
					for (int af = 0; af < p; af++) {
						offset += Vec.Distance(ex_path.get(af).xyVec, ex_path.get(af + 1).xyVec);
						ex_dij.GetPath().insertElementAt(ex_path.get(af), af);
					}
					ex_dij.GetDijkstra_Search().totalCost += offset;

					boolean remove = false;
					//無駄遣いパスは消去
					for (int pt1 = 0; pt1 < ex_dij.GetPath().size() - 1; pt1++) {
						for (int pt2 = pt1 + 1; pt2 < ex_dij.GetPath().size(); pt2++) {
							if (ex_dij.GetPath().get(pt1).pointInfo == ex_dij.GetPath().get(pt2).pointInfo) {
								remove = true;
								break;
							}
						}
					}
					if (!remove) {
						B.add(ex_dij);

						var debugPath = ex_dij.GetPath();
						var debugCost = ex_dij.GetCost();
						for (int d = 0; d < debugPath.size(); d++) {
							if (debugPath.get(d).pointInfo.type != e_PointType.Point) {
								System.out.printf("C");
							}
							System.out.printf("%d ", debugPath.get(d).pointInfo.Id);
						}
						System.out.printf(" %f", debugCost);
						System.out.printf("\n");
					}
				}
			}

			//重なったパス消去
			for (int p = 0; p < B.size() - 1; p++) {
				for (int ps = p + 1; ps < B.size();) {

					boolean same = true;
					var path1 = B.get(p).GetPath();
					var path2 = B.get(ps).GetPath();
					if (path1.size() == path2.size()) {
						for (int pTrac = 0; pTrac < path1.size(); pTrac++) {
							if (path1.get(pTrac).pointInfo != path2.get(pTrac).pointInfo) {
								same = false;
							}
						}
					} else {
						same = false;
					}

					if (same) {
						B.remove(ps);
					} else {
						ps++;
					}

				}
			}

			//confirm most shortest
			double min_dis = Double.MAX_VALUE;
			int min_index = 0;
			for (int i = 0; i < B.size(); i++) {
				//System.out.printf("cost %f\n", B.get(i).GetCost());
				if (min_dis > B.get(i).GetCost()) {
					min_dis = B.get(i).GetCost();
					min_index = i;
				}
			}
			A.add(B.get(min_index));
			B.remove(min_index);

		}
		System.out.printf("%d is %f\n", n_shortest, A.get(n_shortest - 1).GetCost());
	}
}

class Node2Node {
	public Point p1, p2;

	public Node2Node(Point get1, Point get2) {

		if (get1.pointInfo.type != get2.pointInfo.type) {
			if (get1.pointInfo.type == e_PointType.Point) {
				p1 = get1;
				p2 = get2;
			} else {
				p1 = get2;
				p2 = get1;
			}
		} else {
			if (get1.pointInfo.Id < get2.pointInfo.Id) {
				p1 = get1;
				p2 = get2;
			} else {
				p1 = get2;
				p2 = get1;
			}
		}
	}
}