import classes.*;
import Unit.*;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class Main{

    static int[][] line_graph = new int[2000][2000];
    static int [] color = new int[2000];
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pointNum = scanner.nextInt();
        int lineNum = scanner.nextInt();
        float P = scanner.nextFloat();
        float q = scanner.nextFloat();
        int [][] adjacency_matrix;
        Vector<Vec> points = new Vector<Vec>();
        for (int n=0;n<pointNum;n++) {
            float x = scanner.nextFloat();
            float y = scanner.nextFloat();
            points.add(new Vec(x,y));
            points.get(n).Point_number = n;
        }

        Vector<Line> lines = new Vector<Line>();
        for (int n=0;n<lineNum;n++) {
            int one = scanner.nextInt();
            int two = scanner.nextInt();
            lines.add(new Line(points.get(one-1),points.get(two-1)));
            
        }

        Vector<CrossPoint> crossPoints = new Vector<CrossPoint>();
        for (int n=0;n<lineNum;n++){
            for (int i=n+1;i<lineNum;i++) {
                crossPoints.add(new CrossPoint(lines.get(n),lines.get(i)));
            }
        }
        for(int n=0;n<crossPoints.size();n++){
            crossPoints.get(n).Ask_for_a();
            crossPoints.get(n).Point_calc();
            if (crossPoints.get(n).Judje_cross()==1){
                for(int i=0;i<lines.size();i++){
                    if(lines.get(i).equals(crossPoints.get(n).line1)){
                        lines.add(new Line(lines.get(i).P1, crossPoints.get(n).CrossingVec));
                        lines.add(new Line(lines.get(i).P2, crossPoints.get(n).CrossingVec));
                        lines.get(i).linestate=false;
                    }
                    else if(lines.get(i).equals(crossPoints.get(n).line2)){
                        lines.add(new Line(lines.get(i).P1, crossPoints.get(n).CrossingVec));
                        lines.add(new Line(lines.get(i).P2, crossPoints.get(n).CrossingVec));
                        lines.get(i).linestate=false;                       
                    }      
                }
            }

            else if(crossPoints.get(n).Judje_cross()==2){
                if (crossPoints.get(n).CrossingVec.Eq(crossPoints.get(n).line1.P1, crossPoints.get(n).CrossingVec)){
                    lines.add(new Line(crossPoints.get(n).line1.P1, crossPoints.get(n).line2.P1));
                    lines.add(new Line(crossPoints.get(n).line1.P1, crossPoints.get(n).line2.P2));
                }
                else if (crossPoints.get(n).CrossingVec.Eq(crossPoints.get(n).line1.P2, crossPoints.get(n).CrossingVec)) {
                    lines.add(new Line(crossPoints.get(n).line1.P2, crossPoints.get(n).line2.P1));
                    lines.add(new Line(crossPoints.get(n).line1.P2, crossPoints.get(n).line2.P2));
                }
                else if (crossPoints.get(n).CrossingVec.Eq(crossPoints.get(n).line2.P1, crossPoints.get(n).CrossingVec)) {
                    lines.add(new Line(crossPoints.get(n).line2.P1, crossPoints.get(n).line1.P1));
                    lines.add(new Line(crossPoints.get(n).line2.P1, crossPoints.get(n).line1.P2));
                }
                else if(crossPoints.get(n).CrossingVec.Eq(crossPoints.get(n).line2.P1, crossPoints.get(n).CrossingVec)) {
                    lines.add(new Line(crossPoints.get(n).line2.P2, crossPoints.get(n).line1.P1));
                    lines.add(new Line(crossPoints.get(n).line2.P2, crossPoints.get(n).line1.P2));
                }
  
            }
        }

        for(int i=0;i<crossPoints.size();i++){
                for(int j=0;j<crossPoints.size();j++){
                   if (crossPoints.get(j).Judje_cross()==1){
                        if(i==j)continue;
                        if(crossPoints.get(i).line1.equals(crossPoints.get(j).line1)){
                            lines.add(new Line(crossPoints.get(i).CrossingVec, crossPoints.get(j).CrossingVec));
                        }
                        else if(crossPoints.get(i).line1.equals(crossPoints.get(j).line2)){
                            lines.add(new Line(crossPoints.get(i).CrossingVec, crossPoints.get(j).CrossingVec));                    
                        }
                        else if(crossPoints.get(i).line2.equals(crossPoints.get(j).line1)){
                            lines.add(new Line(crossPoints.get(i).CrossingVec, crossPoints.get(j).CrossingVec));
                        }
                        else if(crossPoints.get(i).line2.equals(crossPoints.get(j).line2)){
                            lines.add(new Line(crossPoints.get(i).CrossingVec, crossPoints.get(j).CrossingVec));
                        }
                    }
               }    
        }


        for(int n=0;n<crossPoints.size();n++){
            if(crossPoints.get(n).Judje_cross()==1){
               points.add(new Vec(crossPoints.get(n).CrossingVec.x, crossPoints.get(n).CrossingVec.y));
            }
        }
        
        for(int i=0;i<points.size();i++){
            for(int j=0;j<points.size();j++){
                Main.line_graph[i][j]=0;
            }
        }

        for(int i=0;i<points.size();i++){
            for(int j=0;j<lines.size();j++){
                if(lines.get(j).linestate==true){
                    if(points.get(i).x==lines.get(j).P1.x && points.get(i).y==lines.get(j).P1.y){
                        for(int k=0;k<points.size();k++){
                            if(i==k)continue;
                            if(points.get(k).x==lines.get(j).P2.x && points.get(k).y==lines.get(j).P2.y){
                             Main.line_graph[i][k]=1;
                            }
                        }
                    }   
                    else if(points.get(i).x==lines.get(j).P2.x && points.get(i).y==lines.get(j).P2.y){
                        for(int k=0;k<points.size();k++){
                            if(i==k)continue;
                            if(points.get(k).x==lines.get(j).P1.x && points.get(k).y==lines.get(j).P1.y){
                              Main.line_graph[i][k]=1;
                            }   
                        }
                    }
                }
            }
        }

        for(int i=0;i<points.size();i++){
            for(int j=0;j<points.size();j++){
                if(Main.line_graph[i][j]==1){
                    for(int k=j+1;k<points.size();k++){
                        if(Main.line_graph[i][k]==1 && Main.line_graph[j][k]==1 && Main.line_graph[k][j]==1){
                            if(line_a(points.get(i),points.get(j),points.get(k))==true && line_b(points.get(i),points.get(j),points.get(k))==true) {
                                if(dis(points.get(i),points.get(j)) < dis(points.get(i),points.get(k))){
                                    Main.line_graph[i][k]=0;
                                }

                                else if(dis(points.get(i),points.get(j)) > dis(points.get(i),points.get(k))){
                                    Main.line_graph[i][j]=0;
                                }
                            }
                        }
                    }
                }
            }
        }

        //Search
        int ci,cj;
        for(int i=0;i<points.size();i++){
            for(int j=i+1;j<points.size();j++){
                ci=0;
                cj=0;
                if(Main.line_graph[i][j]==1){
                    Main.line_graph[i][j]=0;
                    Main.line_graph[j][i]=0;
                    assignColor(points.size());
                    if(Main.color[i]!=Main.color[j]){
                        if(i+1>pointNum) {
                            ci=i+1-pointNum;
                            if(j+1>pointNum){
                                cj=j+1-pointNum;
                                System.out.printf("C%d-C%d line target line\n",ci,cj);
                            }
                            else {
                                System.out.printf("C%d-%d line target line\n",ci,j+1);
                            }
                        }
                        else{
                            if(j+1>pointNum){
                                cj=j+1-pointNum;
                                System.out.printf("%d-C%d line target line\n",i+1,cj);
                            }
                            else {
                                System.out.printf("%d-%d line target line\n",i+1,j+1);
                            }
                        }
                    }
                    Main.line_graph[i][j]=1;
                    Main.line_graph[j][i]=1;
                }
            }
        }

        for(int i=0;i<points.size();i++){
            for(int j=0;j<points.size();j++){
                System.out.printf("%d ", Main.line_graph[i][j]);
            }
            System.out.printf("\n");
        }

        scanner.close();
    }

    public static double dis(Vec start, Vec goal){
        return Math.sqrt((start.x-goal.x)*(start.x-goal.x)+(start.y-goal.y)*(start.y-goal.y)); 
    }

    public static boolean line_a(Vec start, Vec goal1, Vec goal2){
        double a1,a2;
        a1=(goal1.y-start.y)/(goal1.x-start.x);
        a2=(goal2.y-start.y)/(goal2.x-start.x);

        if(Math.abs(a1-a2)<0.00001) {
            return true;
        }

        else return false;
    }

    public static boolean line_b(Vec start, Vec goal1, Vec goal2){
        double b1,b2;
        b1=start.y-((goal1.y-start.y)/(goal1.x-start.x))*start.x;
        b2=start.y-((goal2.y-start.y)/(goal2.x-start.x))*start.x;

        if(Math.abs(b1-b2)<0.00001) return true;
        return false;
    }

    public static void assignColor(int n) {
        int id=1;
        for(int i=0;i<n;i++){
            Main.color[i]=-1;
        }   
        for(int u=0;u<n;u++){
            if(Main.color[u]==-1) dfs(u,id++,n);
        }
    }

    public static void dfs(int r, int c, int n){
        Vector<Integer> stack =new Vector<Integer>();
        stack.add(r);
        Main.color[r]=c;
        while(stack.isEmpty()!=true){
            int u=stack.get(stack.size()-1);
            stack.remove(stack.size()-1);
            for(int i=0;i<n;i++){
                if(Main.line_graph[u][i]==1){
                    int v=i;

                    if(Main.color[v] == -1){
                        Main.color[v]=c;
                        stack.add(v);
                    }
                }
            }

        }
    }
}