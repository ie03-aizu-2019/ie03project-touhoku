package classes;

public class InvCP extends CrossPoint{
	PointInfo currentInfo;
	public InvCP(Line line1, Line line2) {
		super(line1, line2);

		CalCurrentInfo();
	}

	void CalCurrentInfo () {
		if ( Vec.Eq(line1.P1.xyVec, xyVec) ) {
			currentInfo = line1.P1.pointInfo;
		}
		if ( Vec.Eq(line1.P2.xyVec, xyVec) ) {
			currentInfo = line1.P2.pointInfo;
		}
		if ( Vec.Eq(line2.P1.xyVec, xyVec) ) {
			currentInfo = line2.P1.pointInfo;
		}
		if ( Vec.Eq(line2.P2.xyVec, xyVec) ) {
			currentInfo = line2.P2.pointInfo;
		}
	}
}
