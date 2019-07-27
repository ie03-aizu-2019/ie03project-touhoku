package classes;

public class PointInfo {
	public enum e_PointType{
		Point,CrossPoint,
	}

	int Id;
	e_PointType type;

	public PointInfo(int getId, e_PointType getType) {
		this.Id = getId;
		this.type = getType;
	}
}
