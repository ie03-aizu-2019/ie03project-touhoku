import classes.*;

public class CrossPoint{

  Vector P;
  Vevtor Q;

  public double jugje_cross(double s, double t){
      if(0<=s && s<=1){
        if((0<=t && t<=1){
          point_x_calc(s,x_p,x_q);
          point_y_calc(s,y_p,y_q);
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
