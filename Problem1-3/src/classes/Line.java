package classes;

import java.util.Vector;

public class Line {
    public Point P1;
    public Point P2;
    public Vector<CrossPoint> crossPoints;

    public Line (Point P1, Point P2) {
        this.P1 = P1;
        this.P2 = P2;
        crossPoints = new Vector<CrossPoint>();
    }

    public double Distance () {
    	var calV = new Vec(P2.xyVec.x-P1.xyVec.x,P2.xyVec.y-P1.xyVec.y);
    	return Math.pow(Math.pow(calV.x, 2)+Math.pow(calV.y, 2), 0.5);
    }
}