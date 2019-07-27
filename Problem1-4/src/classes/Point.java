package classes;

import classes.PointInfo.e_PointType;

public class Point{
	public PointInfo pointInfo;
	public Vec xyVec;

	//for cross point
	public Point (double x, double y) {
		xyVec = new Vec(x, y);
	}

	//for point
	public Point (double x, double y,int Id) {
		xyVec = new Vec(x, y);
		this.pointInfo = new PointInfo(Id,e_PointType.Point);
	}
}
