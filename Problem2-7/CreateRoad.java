
class NewPoint{
  double x;
  double y;
  public NewPoint(double x,double y){
    this.x=x;
    this.y=y;
  }
}

class Point{
  double x;
  double y;
  public Point(double x,double y){
    this.x=x;
    this.y=y;
  }
}

class Line{
  Point P1;
  Point P2;
  double a;
  double b;
  public Line(Point P1,Point P2){
    this.P1=P1;
    this.P2=P2;
  }
}


//上のクラスはテストケース用に作成

public class CreateRoad{
  public static void main(String args[]){

//元から存在する道をつなぐ点をP1,P2とする、
//A,Bを入れた時どう繋げるか考える
    NewPoint A= new NewPoint(5,1);
    NewPoint B= new NewPoint(11,5);
    Point P1=new Point(0,0);
    Point P2=new Point(2,5);
    Line L1=new Line(P1,P2);
    double distance=0;
    method_road(L1);
    road_dis(L1,A,distance);
    point_dis(P1,A,distance);

}

//まず道の関数を求める(テスト用)
public static void method_road(Line L1){
  double a,b;
  L1.a=(L1.P2.y-L1.P1.y)/(L1.P2.x-L1.P1.x);
  L1.b=L1.P2.y-(L1.P2.x*L1.a);
  System.out.println(L1.a+" "+L1.b);
  System.out.println("y="+L1.a+"x+"+L1.b);

}


//点と直線の距離

public static void road_dis(Line L1,NewPoint A,double distance){
  double dis;
 dis=Math.abs((L1.a*A.x-A.y+L1.b)/Math.sqrt(L1.a*L1.a+1));
 System.out.println(dis);
 if(distance==0) distance = dis;
 else{
   if(distance<dis) distance = dis;
 }
}
//点と点の距離

public static void point_dis(Point P1,NewPoint A,double distance){
  double dis;
  dis=Math.sqrt((P1.x-A.x)*(P1.x-A.x)+(P1.y-A.y)*(P1.y-A.y));
  System.out.println(dis);
}


}
