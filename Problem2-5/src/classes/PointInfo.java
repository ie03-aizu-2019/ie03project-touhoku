package classes;

public class PointInfo {
	public enum e_PointType{
		Point,CrossPoint,InvisibleCP,
		//point -> ポイント
		//crossPoiont -> 交差地点
		//InvisibleCP -> ダイクストラで使うCross Point
	}

	public int Id;
	public e_PointType type;

	public PointInfo(int getId, e_PointType getType) {
		this.Id = getId;
		this.type = getType;
	}
	public PointInfo(e_PointType getType) {
		this.Id = -1;
		this.type = getType;
	}
}
