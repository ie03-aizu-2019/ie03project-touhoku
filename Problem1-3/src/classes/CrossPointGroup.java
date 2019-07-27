package classes;

import java.util.Vector;

import classes.PointInfo.e_PointType;

public class CrossPointGroup {
	public Vector<CrossPoint> cp;

	public CrossPointGroup(Vector<CrossPoint> getCP) {
		this.cp = getCP;

		for (int n=0;n<getCP.size();n++) {
			getCP.get(n).pointInfo = new PointInfo(n,e_PointType.CrossPoint);
		}
	}

	public void Sort() {
		for (int i = 0; i < cp.size() - 1; i++) {
			for (int j = cp.size() - 1; j > i; j--) {
				if ((cp.get(j - 1).xyVec.x > cp.get(j).xyVec.x)
						|| (cp.get(j - 1).xyVec.x == cp.get(j).xyVec.x &&
								cp.get(j - 1).xyVec.y > cp.get(j).xyVec.y)) {
					var tmp = cp.get(j - 1);
					cp.set(j - 1, cp.get(j));
					cp.set(j, tmp);
				}
			}
		}
	}

	//移動可能なクロスポイントを追加
	public void AddMovableCrossPoint() {
		for (int i = 0; i < cp.size(); i++) {
			for (int j = 0; j < cp.size(); j++) {
				if (i != j) {
					if (cp.get(i).line1 == cp.get(j).line1 || cp.get(i).line1 == cp.get(j).line2 || cp.get(i).line2 == cp.get(j).line2) {
						cp.get(i).movableCrossPoint.add(cp.get(j));
					}
				}
			}
		}
	}
}