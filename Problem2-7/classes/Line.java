package classes;

public class Line{
 public Point P1;
  public Point P2;
public  double a; //y=ax+b;
  public double b;
  public double r_a;//法線の傾き
  public double min;
  public int tag;

  public Line(Point P1,Point P2){
    this.P1=P1;
    this.P2=P2;
  }
}
