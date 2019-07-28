
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
class CrossPoint{
  double x;
  double y;
  public CrossPoint(double x,double y){
    this.x=x;
    this.y=y;
  }
}

class Line{
  Point P1;
  Point P2;
  double a; //y=ax+b;
  double b;
  double min;
  int tag;
  public Line(Point P1,Point P2){
    this.P1=P1;
    this.P2=P2;
  }
}


//上のクラスはテストケース用に作成

public class CreateRoad{
  public static void main(String args[]){

    NewPoint[] newpoints = {
          new NewPoint(-2,2),
          new NewPoint(2,-4),
        };
        //既存の点(テスト用に点と道を作成)
   Point[] points={
          new Point(0,0),
          new Point(2,2),
          new Point(4,6),
          new Point(6,-2),
        };


   Line[] lines=new Line[6];
    int i;
    int k=0; //配列の代入よう
    int j;
    int tmp=0;//どの線が選ばれたかわかる
    double min=0;


    for(i=0;i<=3;i++){
      for(j=i+1;j<=3;j++){
        lines[k]=new Line(points[i],points[j]);
        method_road(lines[k]);//道の関数まで調べてる
        k++;
        }
      }//全パターンの道の作成
      //交差点以外の距離計測
    for(i=0;i<newpoints.length;i++){
      for(j=0;j<lines.length;j++){
        if(min==0) min=dis(lines[j],newpoints[i]);
        lines[j].min=dis(lines[j],newpoints[i]);
      //  System.out.println(min+"\n");
        if(min>lines[j].min){
          min=lines[j].min;
          tmp=j;
        }

      }
      //System.out.println(tmp);
      //System.out.println(lines[tmp].tag);//タグ番号でどこと繋いだか識別　0..新点と道　1...新点とP1 2...新点とP2
      //System.out.println(lines[tmp].a+""+lines[tmp].b);

      //tag=0　つまり点と直線で結ぶ場合、道を作るのに交差地点の座標が必要

      if(lines[tmp].tag==0){
        double r_a,r_b;//法線のa,b
        double cx,cy;//交差地点検出ターイム
         r_a=-1/lines[tmp].a;
        // System.out.println(r_a);
         r_b=newpoints[i].y-(r_a*newpoints[i].x);
        // System.out.println(r_b);
         //法線と元の式を使って道をつなぐ交点をだす
         cx=(r_b-lines[tmp].b)/(lines[tmp].a-r_a);
         cy=lines[tmp].a*cx+lines[tmp].b;
        //System.out.println(cx+"   "+cy);

        tmp=0;
        min=0;
      }
    }
  }

//まず道の関数を求める(テスト用)



public static void method_road(Line L1){
  L1.a=(L1.P2.y-L1.P1.y)/(L1.P2.x-L1.P1.x);
  L1.b=L1.P2.y-(L1.P2.x*L1.a);
// System.out.println(String.format("L.a="+"%.2f",L1.a)+" L.b"+String.format("%.2f",L1.b));
 //System.out.println("y="+String.format("%.2f",L1.a)+"x+"+String.format("%.2f",L1.b));

 //System.out.println("\n");
//確認済み
}


//点と直線の距離 ついでに　点と点の距離

public static double dis(Line L1,NewPoint A){
  double dis_road,dis_p1,dis_p2;
  System.out.println("L1の座標"+L1.P1.x+" "+L1.P1.y+" もう片方"+L1.P2.x+" "+L1.P2.y+" A"+A.x+ " "+A.y+"\n");
  dis_road=Math.abs((L1.a*A.x-A.y+L1.b))/Math.sqrt(L1.a*L1.a+1);
  System.out.println("点と直線の距離"+dis_road);
  dis_p1=Math.sqrt((L1.P1.x-A.x)*(L1.P1.x-A.x)+(L1.P1.y-A.y)*(L1.P1.y-A.y));
  //System.out.println("新点とP1をつなぐ"+dis_p1);
  dis_p2=Math.sqrt((L1.P2.x-A.x)*(L1.P2.x-A.x)+(L1.P2.y-A.y)*(L1.P2.y-A.y));
  //System.out.println("P2をつなぐ"+dis_p2+"\n");

  //System.out.println("\n");
  return min(L1,dis_road,dis_p1,dis_p2);

  }

  public static double min(Line L1,double a,double b,double c){
   double min;
   int tag;
   min=0;
   if(a>b)
   {
     min=b;
     L1.tag=1;
   }
   else{
      min=a;
      L1.tag=0;
    }
   if(min>c){
     min=c;
     L1.tag=2;
   }
   //System.out.println(min);
   return min;
  }

}
