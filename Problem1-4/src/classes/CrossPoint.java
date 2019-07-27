package classes;

import java.util.Vector;

public class CrossPoint extends Point {

	public Line line1;
	public Line line2;
	public double s, t;

	public Vector<CrossPoint> movableCrossPoint;

	public CrossPoint(Line line1, Line line2) {
		super(-1, -1);
		this.line1 = line1;
		this.line2 = line2;
		movableCrossPoint = new Vector<CrossPoint>();
	}

	public void Ask_for_a() {
		double A;
		A = (line1.P2.xyVec.x - line1.P1.xyVec.x) * (line2.P1.xyVec.y - line2.P2.xyVec.y)
				+ (line2.P2.xyVec.x - line2.P1.xyVec.x) * (line1.P2.xyVec.y - line1.P1.xyVec.y);
		Ask_for_st(A);
	}

	public void Ask_for_st(double A) {
		s = ((line2.P1.xyVec.y - line2.P2.xyVec.y) * (line2.P1.xyVec.x - line1.P1.xyVec.x)
				+ (line2.P2.xyVec.x - line2.P1.xyVec.x) * (line2.P1.xyVec.y - line1.P1.xyVec.y)) / A;
		t = ((line1.P1.xyVec.y - line1.P2.xyVec.y) * (line2.P1.xyVec.x - line1.P1.xyVec.x)
				+ (line1.P2.xyVec.x - line1.P1.xyVec.x) * (line2.P1.xyVec.y - line1.P1.xyVec.y)) / A;
	}

	public boolean Judje_cross() {
		//exception
		if (xyVec.Eq(xyVec, line1.P1.xyVec) || xyVec.Eq(xyVec, line1.P2.xyVec)
				|| xyVec.Eq(xyVec, line2.P1.xyVec) || xyVec.Eq(xyVec, line2.P2.xyVec)) {
			return false;
		}

		if (0 <= s && s <= 1) {
			if (0 <= t && t <= 1) {
				return true;
			}
		}
		return false;
	}

	public void Point_calc() {
		xyVec = new Vec(line1.P1.xyVec.x + (line1.P2.xyVec.x - line1.P1.xyVec.x) * s, line1.P1.xyVec.y + (line1.P2.xyVec.y - line1.P1.xyVec.y) * s);
	}
}
