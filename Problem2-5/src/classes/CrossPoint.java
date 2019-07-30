package classes;

import java.util.Vector;

import classes.PointInfo.e_PointType;

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
		this.pointInfo = new PointInfo(e_PointType.CrossPoint);
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
		boolean isCP = true;
		boolean isInvCP = false;

		//is cp
		if (Vec.Eq(xyVec, line1.P1.xyVec) || Vec.Eq(xyVec, line1.P2.xyVec)
				|| Vec.Eq(xyVec, line2.P1.xyVec) || Vec.Eq(xyVec, line2.P2.xyVec)) {
			isCP = false;
		}

		//is in cp
		int flag = 0;
		for (int x = 0; x < 4; x++) {
			Vec a;
			switch (x) {
			case 0:
				a = line1.P1.xyVec;
				break;
			case 1:
				a = line1.P2.xyVec;
				break;
			case 2:
				a = line2.P1.xyVec;
				break;
			case 3:
				a = line2.P2.xyVec;
				break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + x);
			}

			if (Vec.Eq(xyVec, a)) {
				flag++;
			}
		}

		//exception
		if (flag == 1) {
			if (0 <= s && s <= 1) {
				if (0 <= t && t <= 1) {
					isInvCP = true;
				}
			}
		}

		//chenge CP -> invCP
		if (isInvCP) {
			this.pointInfo.type = e_PointType.InvisibleCP;
		}

		if (isCP || isInvCP) {
			if (0 <= s && s <= 1) {
				if (0 <= t && t <= 1) {
					return true;
				}
			}
		}

		return false;
	}

	public void Point_calc() {
		xyVec = new Vec(line1.P1.xyVec.x + (line1.P2.xyVec.x - line1.P1.xyVec.x) * s,
				line1.P1.xyVec.y + (line1.P2.xyVec.y - line1.P1.xyVec.y) * s);
	}
}
