package classes;

public class Vec {
    public double x;
    public double y;

    public Vec (double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double Magnitude () {
        return Math.pow(Math.pow(x, 2)+Math.pow(y, 2),0.5);
    }
    public Vec Add(Vec a, Vec b) {
        return  new Vec(a.x+b.x, a.y+b.y);
    }
    public static Vec Sub(Vec a, Vec b) {
        return  new Vec(a.x-b.x, a.y-b.y);
    }

    public static boolean Eq (Vec a, Vec b) {
        return a.x==b.x && a.y == b.y;
    }

	public static Double CalLength(Vec a, Vec b) {
		Double x = a.x/b.x;
		Double y = a.y/b.y;
		if (Math.abs(x-y) < 0.0000001) {
			return x;
		}else {
			return null;
		}
	}
}
