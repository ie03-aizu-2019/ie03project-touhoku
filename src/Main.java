import classes.*;
import Unit.*;
import java.util.Scanner;
import java.util.Vector;

public class Main{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var pointNum = scanner.nextInt();
        var lineNum = scanner.nextInt();
        var P = scanner.nextFloat();
        var q = scanner.nextFloat();
        
        var points = new Vector<Vec>();
        for (var n=0;n<pointNum;n++) {
            var x = scanner.nextFloat();
            var y = scanner.nextFloat();
            points.add(new Vec(x,y));
        }

        var lines = new Vector<Line>();
        for (var n=0;n<lineNum;n++) {
            var one = scanner.nextInt();
            var two = scanner.nextInt();
            lines.add(new Line(points.get(one-1),points.get(two-1)));
        }

        var crossPoints = new Vector<CrossPoint>();
        for (var n=0;n<(float)lineNum*0.5f;n++) {
            crossPoints.add(new CrossPoint(lines.get(n),lines.get(n+1)));
        }
        
        crossPoints.get(0).Ask_for_a();
        crossPoints.get(0).Point_calc();
        if (crossPoints.get(0).Judje_cross()) {
            System.out.printf("%f,%f\n", crossPoints.get(0).CrossingVec.x,crossPoints.get(0).CrossingVec.y);
        }else{
            System.out.printf("%s\n", "NA");
        }

        scanner.close();
    }
}