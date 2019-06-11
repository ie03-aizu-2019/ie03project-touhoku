package classes;

import java.util.Vector;

public class Line {
    public Vec P1;
    public Vec P2;
    public int Id_P1;
    public int Id_P2;

    private Vector<CrossPoint> m_crossPoints;

    public Vector<CrossPoint> crossPoints() {
        return m_crossPoints;
    }

    public Line(Vec P1, int Id_P1, Vec P2, int Id_P2) {
        this.P1 = P1;
        this.Id_P1 = Id_P1;
        this.P2 = P2;
        this.Id_P2 = Id_P2;
        m_crossPoints = new Vector<CrossPoint>();
    }
    public Line(Vec P1, Vec P2) {
        this.P1 = P1;
        this.Id_P1 = -1;
        this.P2 = P2;
        this.Id_P2 = -1;
        m_crossPoints = new Vector<CrossPoint>();
    }

    public void AddCrossPoint(CrossPoint cp) {
        m_crossPoints.add(cp);
    }

    public double CalDistance () {
    	var x = P1.x - P2.x;
    	var y = P1.y - P2.y;
    	return Math.pow(Math.pow(x,2)+Math.pow(y,2),0.5);
    }
}