package Unit;
import classes.*;

public class CrossPoint{

  public Line line1;
  public Line line2;
  public double s,t;

  public Vec CrossingVec;


  public CrossPoint (Line line1, Line line2) {
    this.line1 = line1;
    this.line2 = line2;
  }

  public void Ask_for_a()
  {
   double A;
    A=(line1.P2.x-line1.P1.x)*(line2.P1.y-line2.P2.y)+(line2.P2.x-line2.P1.x)*(line1.P2.y-line1.P1.y);
    Ask_for_st(A);
  }

  public void Ask_for_st(double A)
  {
    s=((line2.P1.y-line2.P2.y)*(line2.P1.x-line1.P1.x)+(line2.P2.x-line2.P1.x)*(line2.P1.y-line1.P1.y))/A;
    t=((line1.P1.y-line1.P2.y)*(line2.P1.x-line1.P1.x)+(line1.P2.x-line1.P1.x)*(line2.P1.y-line1.P1.y))/A;
  }


  public int Judje_cross(){
    //exception
      if(0<=s && s<=1){
        if(0<=t && t<=1){
          if (CrossingVec.Eq(CrossingVec, line1.P1) || CrossingVec.Eq(CrossingVec, line1.P2) 
            || CrossingVec.Eq(CrossingVec, line2.P1) || CrossingVec.Eq(CrossingVec, line2.P2)) {
            return 2;
          }
          return 1;
        }
      }
      return 0;
  }

  public void Point_calc(){
      CrossingVec = new Vec(line1.P1.x+(line1.P2.x-line1.P1.x)*s,line1.P1.y+(line1.P2.y-line1.P1.y)*s);
  }
}
