package classes;

import classes.PointType.e_PointType;

public class Point extends Vec{
	public PointType Id;

	public Point (double x, double y,int Id) {
		super(x,y);
		this.Id = new PointType(Id,e_PointType.Point);
	}
}
