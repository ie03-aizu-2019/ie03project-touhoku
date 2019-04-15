import classes.*;
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

        scanner.close();
    }
}