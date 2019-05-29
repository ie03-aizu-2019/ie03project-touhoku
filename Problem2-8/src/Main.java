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
        for (var n=0;n<lineNum;n++){
            for (var i=n+1;i<lineNum;i++) {
                crossPoints.add(new CrossPoint(lines.get(n),lines.get(i)));
                crossPoints.get(n).Ask_for_a();
                if (crossPoints.get(n).Judje_cross()){
                    crossPoints.get(n).Point_calc();
                    //points.add(new Vec(crossPoints.get(n).CrossingVec.x,crossPoints.get(n).CrossingVec.y));
                    lines.add(new Line(lines.get(n).P1, crossPoints.get(n).CrossingVec));
                    lines.add(new Line(lines.get(n).P2, crossPoints.get(n).CrossingVec));
                    lines.add(new Line(lines.get(i).P1, crossPoints.get(n).CrossingVec));
                    lines.add(new Line(lines.get(i).P2, crossPoints.get(n).CrossingVec));
                    lines.get(n).linestate=false;
                    lines.get(i).linestate=false;                    
                }
            }
        }
        
        for(var n=0;n<crossPoints.size();n++){
            
        }
        
        for (var n=0;n<lines.size();n++) {
            /*crossPoints.get(n).Ask_for_a();
            if (crossPoints.get(n).Judje_cross()) {
                crossPoints.get(n).Point_calc();
                points.add(new Vec(crossPoints.get(n).CrossingVec.x,crossPoints.get(n).CrossingVec.y));*/
                System.out.printf("%f,%f\n", crossPoints.get(n).CrossingVec.x,crossPoints.get(n).CrossingVec.y);
            }
        }   

        scanner.close();
    }
}