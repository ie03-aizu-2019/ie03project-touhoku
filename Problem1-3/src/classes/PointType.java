package classes;

public class PointType {
	public enum e_PointType{
		Point,CrossPoint,
	}

	int Id;
	e_PointType type;

	public PointType(int getId, e_PointType getType) {
		this.Id = getId;
		this.type = getType;
	}
}
