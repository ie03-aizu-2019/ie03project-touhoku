package classes;

public class Line {
    public Point P1;
    public Point P2;

    public Line (Point P1, Point P2) {
        this.P1 = P1;
        this.P2 = P2;
    }

    public double Distance () {
    	var calV = new Vec(P2.x-P1.x,P2.y-P1.y);
    	return Math.pow(Math.pow(calV.x, 2)+Math.pow(calV.y, 2), 0.5);
    }
}