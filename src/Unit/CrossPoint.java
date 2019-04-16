import classes.*;

public class CrossPoint{

  Line P;
  Line Q;
  public double s,t;



  public void ask_for_a(Line P,Line Q)
  {
   int A;
    A=(P.q.x-P.p.x)*(Q.p.y-Q.q.y)+(Q.Q.x-Q.p.x)*(P.Q.y-P.p.y);
   ask_for_st(A);
  }

  public void ask_for_st(int A)
  {
    s={(Q.p.x-Q.q.y)*(Q.p.x-P.p.x)+(Q.q.x-Q.p.x)*(Q.p.y-P.p.y)}/A;
    t={(P.p.y-P.q.y)*(Q.p.x-P.p.x)+(P.q.x-P.p.x)*(Q.p.y-P.p.y)}/A;


  }


  public double judje_cross(double s, double t){
      if(0<=s && s<=1){
        if((0<=t && t<=1){
          point_x_calc(s,P.p.x,P.q.x);
          point_y_calc(s,P.p.y,P.q.y);
        }
        else return -1;
      }
      else return -1;
  }

  public double point_x_calc(double s, double x_p, double x_q){
      return x_p+(x_q-x_p)*s;
  }

  public double point_y_calc(double s, double y_p, double y_q){
      return y_p+(y_q-y-p)*s;
  }


}
