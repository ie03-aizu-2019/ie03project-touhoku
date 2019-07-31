
//上のクラスはテストケース用に作成
import classes.*;

import java.util.Scanner;
public class Main{
  public static void main(String args[]){
    Scanner scanner = new Scanner(System.in);
    int pointNum = scanner.nextInt();
    int lineNum = scanner.nextInt();
    int newpointsnum = scanner.nextInt();
    float q = scanner.nextFloat();
    NewPoint[] newpoints =new NewPoint[newpointsnum];
    Point[] points=new Point[pointNum];
    Line[] lines=new Line[lineNum];
    int i;
    int k=0; //配列の代入よう
    int j;
    int tmp=0;//どの線が選ばれたかわかる
    double min=0;
    double x1,y1;
    //入力
    for(i=0;i<pointNum;i++){
      x1=scanner.nextDouble();
       y1=scanner.nextDouble();
      points[i] = new Point(x1, y1);
    }
    for(i=0;i<lineNum;i++){
     lines[i]=new Line(points[scanner.nextInt()-1],points[scanner.nextInt()-1]);
     method_road(lines[i]);
    }
    for(i=0;i<newpointsnum;i++){
     newpoints[i]=new NewPoint(scanner.nextDouble(),scanner.nextDouble());
    }


/*
    for(i=0;i<=newpoints.length;i++){
      for(j=i+1;j<=;j++){
        lines[k]=new Line(points[i],points[j]);
        method_road(lines[k]);//道の関数まで調べてる
        k++;
        }
      }*/
    for(i=0;i<newpoints.length;i++){
      for(j=0;j<lines.length;j++){
        //System.out.println(dis(lines[j],newpoints[i])+ " "+lines[j].tag);
        if(min==0) min=dis(lines[j],newpoints[i]);
        lines[j].min=dis(lines[j],newpoints[i]);
        if(min>lines[j].min){
          min=lines[j].min;
          tmp=j;
        }
      }
      //System.out.println(tmp);
      //System.out.println(lines[tmp].tag);//タグ番号でどこと繋いだか識別　0..新点と道　1...新点とP1 2...新点とP2
      //System.out.println(lines[tmp].min);
    if(lines[tmp].tag==0){
        double r_b;//法線のb r_aは出してる
        double cx,cy;//交差地点検出
         r_b=newpoints[i].y-(lines[tmp].r_a*newpoints[i].x);
         //System.out.println(lines[tmp].a+" "+lines[tmp].b +" "+r_b);//a,b,r_a,r_b ok
         //法線と元の式を使って道をつなぐ交点をだす
         cx=(r_b-lines[tmp].b)/(lines[tmp].a-lines[tmp].r_a);
         cy=lines[tmp].a*cx+lines[tmp].b;

        System.out.println(lines[tmp].tag+" "+cx + " "+cy);

      }
      if (lines[tmp].tag==1){

      System.out.println(lines[tmp].tag+" "+lines[tmp].P1.x+" "+lines[tmp].P1.y);

      }
     if (lines[tmp].tag==2){
        System.out.println(lines[tmp].tag+" "+lines[tmp].P2.x+" "+lines[tmp].P2.y);


      }
      tmp=0;
      min=0;

    }
  }


//まず道の関数を求める(テスト用)



public static void method_road(Line L1){
  L1.a=(L1.P2.y-L1.P1.y)/(L1.P2.x-L1.P1.x);
  L1.b=L1.P2.y-(L1.P2.x*L1.a);
// System.out.println(String.format("L.a="+"%.2f",L1.a)+" L.b"+String.format("%.2f",L1.b));
//System.out.println("y="+String.format("%.2f",L1.a)+"x+"+String.format("%.2f",L1.b));

 //System.out.println("\n");
}


//点と直線の距離 ついでに　点と点の距離

public static double dis(Line L1,NewPoint A){
  double dis_road,dis_p1,dis_p2;
  if(L1.a==0) L1.r_a=0;
  else L1.r_a=-1*(1/L1.a);

  double r_b_P1,r_b_P2;
  double judge_y1,judge_y2;
  //法線を求める
  r_b_P1=-L1.r_a*L1.P1.x+L1.P1.y;
  r_b_P2=-L1.r_a*L1.P2.x+L1.P2.y;
  //System.out.println("y="+r_a+"x"+"+"+r_b_P1+"\n");
  //System.out.println("y="+r_a+"x"+"+"+r_b_P2+"\n");
  judge_y1=L1.r_a*A.x+r_b_P1;
  judge_y2=L1.r_a*A.x+r_b_P2;

  if(L1.r_a>0){
    if(A.y>=judge_y1){
      dis_p1=Math.sqrt((L1.P1.x-A.x)*(L1.P1.x-A.x)+(L1.P1.y-A.y)*(L1.P1.y-A.y));
      L1.tag=1;
      return dis_p1;
    }
    else if(A.y<=judge_y2){
      dis_p2=Math.sqrt((L1.P2.x-A.x)*(L1.P2.x-A.x)+(L1.P2.y-A.y)*(L1.P2.y-A.y));
      L1.tag=2;

      return dis_p2;
    }
    else{
      dis_road=Math.abs((L1.a*A.x-A.y+L1.b))/Math.sqrt(L1.a*L1.a+1);
      L1.tag=0;
      return dis_road;
    }
  }
  else if(L1.r_a<0){
    if(A.y<=judge_y1){
      dis_p1=Math.sqrt((L1.P1.x-A.x)*(L1.P1.x-A.x)+(L1.P1.y-A.y)*(L1.P1.y-A.y));
      L1.tag=1;
      return dis_p1;
    }
    else if(A.y>=judge_y2){
      dis_p2=Math.sqrt((L1.P2.x-A.x)*(L1.P2.x-A.x)+(L1.P2.y-A.y)*(L1.P2.y-A.y));
      L1.tag=2;


      return dis_p2;
    }
    else{
      dis_road=Math.abs((L1.a*A.x-A.y+L1.b))/Math.sqrt(L1.a*L1.a+1);
      L1.tag=0;
      return dis_road;
    }
  }
  return 500;
}
}
