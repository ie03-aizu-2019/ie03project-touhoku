package classes;

public class PointInfo {
	public enum e_PointType{
		Point,CrossPoint,
	}

	public int Id;
	public e_PointType type;

	public PointInfo(int getId, e_PointType getType) {
		this.Id = getId;
		this.type = getType;
	}
}
