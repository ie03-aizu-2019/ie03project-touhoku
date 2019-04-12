public class Vector {
    double X;
    double Y;
    public Vector (double X, double Y) {
        this.X = X;
        this.Y = Y;
    }
    public double Magnitude () {
        return Math.pow(Math.pow(X, 2)+Math.pow(Y, 2),0.5);
    }
}
