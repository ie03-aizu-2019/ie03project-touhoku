import java.util.Scanner;
import java.util.Vector;

import classes.CrossPoint;
import classes.CrossPointGroup;
import classes.Line;
import classes.LineGroup;
import classes.Vec;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var pointNum = scanner.nextInt();
        var lineNum = scanner.nextInt();
        var P = scanner.nextFloat();
        var q = scanner.nextFloat();

        var points = new Vector<Vec>();
        for (var n = 0; n < pointNum; n++) {
            var x = scanner.nextFloat();
            var y = scanner.nextFloat();
            points.add(new Vec(x, y));
        }

        var lines = new Vector<Line>();
        for (var n = 0; n < lineNum; n++) {
            var one = scanner.nextInt();
            var two = scanner.nextInt();
            lines.add(new Line(points.get(one - 1),one-1, points.get(two - 1),two-1));
        }

        var crossPoints = new Vector<CrossPoint>();
        for (var n = 0; n < lineNum; n++) {
            for (var i = n + 1; i < lineNum; i++) {
                crossPoints.add(new CrossPoint(lines.get(n), lines.get(i)));
                //System.out.printf("CrossPoint : %d %d\n", n,i);
            }
        }

        for (var n = 0; n < crossPoints.size(); n++) {
            crossPoints.get(n).Judje_cross();
        }

        //var relationPoints = new Vector<RelationPoint>();


        var cpGroup = new CrossPointGroup(crossPoints);
        cpGroup.Sort();

        var lineGroup = new LineGroup(lines,crossPoints);

        //System.out.printf("%d (%f,%f)!!", lines.get(1).Id_P1,lines.get(1).P1.x,lines.get(1).P1.y);
        var walkLine = lineGroup.WalkLine(4, 5,0);
        walkLine.Sort();
        var minDis = 1000.0;
        var minId = -1;
        for (var i=0;i<walkLine.distance.size();i++) {
        	if (walkLine.distance.get(i) < minDis) {
        		minDis = walkLine.distance.get(i);
        		minId = i;
        	}
        }
        //walkLine.DeleteDouble();
        //walkLine.Sort();



        scanner.close();
    }
}