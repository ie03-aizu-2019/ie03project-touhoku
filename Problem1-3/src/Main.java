import java.util.Scanner;
import java.util.Vector;

import classes.CrossPoint;
import classes.CrossPointGroup;
import classes.Dijkstra;
import classes.Line;
import classes.Point;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		var pointNum = scanner.nextInt();
		var lineNum = scanner.nextInt();
		var P = scanner.nextFloat();
		var dijkstra = scanner.nextFloat();

		var points = new Vector<Point>();
		for (var n = 0; n < pointNum; n++) {
			var x = scanner.nextFloat();
			var y = scanner.nextFloat();
			points.add(new Point(x, y, n));
		}

		var lines = new Vector<Line>();
		for (var n = 0; n < lineNum; n++) {
			var one = scanner.nextInt();
			var two = scanner.nextInt();
			lines.add(new Line(points.get(one - 1), points.get(two - 1)));
		}

		var crossPoints = new Vector<CrossPoint>();
		for (var n = 0; n < lineNum; n++) {
			for (var i = n + 1; i < lineNum; i++) {
				crossPoints.add(new CrossPoint(lines.get(n), lines.get(i)));
			}
		}

		for (var n = 0; n < crossPoints.size(); n++) {
			crossPoints.get(n).Ask_for_a();
			crossPoints.get(n).Point_calc();
		}
		for (int n = 0; n < crossPoints.size();) {
			if (!crossPoints.get(n).Judje_cross()) {
				crossPoints.remove(n);
			} else {
				n++;
			}
		}

		//Cross PointをLineに入れ込む
		for (int n = 0; n < crossPoints.size(); n++) {
			for (int l = 0; l < lines.size(); l++) {
				if (crossPoints.get(n).line1 == lines.get(l)) {
					lines.get(l).crossPoints.add(crossPoints.get(n));
				}
				if (crossPoints.get(n).line2 == lines.get(l)) {
					lines.get(l).crossPoints.add(crossPoints.get(n));
				}
			}
		}

		var crossPointGroup = new CrossPointGroup(crossPoints);
		crossPointGroup.Sort();
		crossPointGroup.AddMovableCrossPoint();

		for (int d = 0; d < dijkstra; d++) {
			var startString = scanner.next();
			var goalString = scanner.next();
			var number = scanner.nextInt();

			Point start;
			if (startString.charAt(0) == 'C') {
				var split = startString.split("C");
				var index = Integer.parseInt(split[1]) - 1;
				if (index < crossPoints.size()) {
					start = crossPoints.get(index);
				} else {
					start = null;
				}
			} else {
				start = points.get(Integer.parseInt(startString) - 1);
			}
			Point goal = null;
			if (goalString.charAt(0) == 'C') {
				var split = goalString.split("C");
				var index = Integer.parseInt(split[1]) - 1;
				if (index < crossPoints.size()) {
					goal = crossPoints.get(index);
				} else {
					goal = null;
				}
			} else {
				goal = points.get(Integer.parseInt(goalString) - 1);
			}


			if (start == null || goal == null) {
				System.out.printf("NaN\n");
			} else {
				var dij = new Dijkstra(start, goal, points, lines, crossPoints);

				//output
				var path = dij.GetPath();
				/*for (int i=0;i<path.size();i++) {
					if (path.get(i).pointInfo.type == e_PointType.Point) {
						System.out.printf("Point ");
					}else {
						System.out.printf("Cross Point ");
					}
					System.out.printf("%d ", path.get(i).pointInfo.Id);
					if (i < path.size()-1) {
						System.out.printf(" -> ");
					}
				}*/
				System.out.printf("%.5f\n",dij.GetCost());

			}
		}

		scanner.close();
	}
}