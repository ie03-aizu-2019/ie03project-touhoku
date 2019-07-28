import classes.*;
import Unit.*;
import java.util.Scanner;
import java.util.Vector;

public class Main{
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
            if (crossPoints.get(n).Judje_cross()==true){
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
        }

        for(int i=0;i<crossPoints.size();i++){
            if (crossPoints.get(i).Judje_cross()==true){
                for(int j=0;j<crossPoints.size();j++){
                    if (crossPoints.get(j).Judje_cross()==true){
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
        }

        for(int n=0;n<crossPoints.size();n++){
            if(crossPoints.get(n).Judje_cross())
            points.add(new Vec(crossPoints.get(n).CrossingVec.x, crossPoints.get(n).CrossingVec.y));
        }
        
        int[][] line_graph = new int[points.size()][points.size()];

        for(int i=0;i<points.size();i++){
            for(int j=0;j<points.size();j++){
                line_graph[i][j]=0;
            }
        }

        for(int i=0;i<points.size();i++){
            for(int j=0;j<lines.size();j++){
                if(lines.get(j).linestate==true){
                    if(points.get(i).x==lines.get(j).P1.x && points.get(i).y==lines.get(j).P1.y){
                        for(int k=0;k<points.size();k++){
                            if(points.get(k).x==lines.get(j).P2.x && points.get(k).y==lines.get(j).P2.y){
                             line_graph[i][k]=1;
                            }
                        }
                    }   
                    else if(points.get(i).x==lines.get(j).P2.x && points.get(i).y==lines.get(j).P2.y){
                        for(int k=0;k<points.size();k++){
                            if(points.get(k).x==lines.get(j).P1.x && points.get(k).y==lines.get(j).P1.y){
                              line_graph[i][k]=1;
                            }   
                        }
                    }
                }
            }
        }

        for(int i=0;i<points.size();i++){
            for(int j=0;j<points.size();j++){
                if(line_graph[i][j]==1){
                    for(int k=j+1;k<points.size();k++){
                        if(line_graph[i][k]==1 && line_graph[j][k]==1 && line_graph[k][j]==1){
                            if(line_a(points.get(i),points.get(j),points.get(k))==true && line_b(points.get(i),points.get(j),points.get(k))==true) {
                                System.out.printf("i:%d,j%d:k:%d\n",i+1,j+1,k+1);
                                //continue;
                                if(dis(points.get(i),points.get(j)) < dis(points.get(i),points.get(k))){
                                    line_graph[i][k]=0;
                                }

                                else if(dis(points.get(i),points.get(j)) > dis(points.get(i),points.get(k))){
                                    line_graph[i][j]=0;
                                }
                            }
                        }
                    }
                }
            }
        }

        for(int i=0;i<points.size();i++){
            for(int j=0;j<points.size();j++){
                System.out.printf("%d ",line_graph[i][j]);
                //if(line_graph[i][j]==1){
                    //System.out.printf("line true:%d-%d 0\n",i+1,j+1);
                //}
                //else System.out.printf("line false:%d-%d x\n",i+1,j+1);
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
        System.out.printf("a1:%f, a2:%f\n", a1,a2);

        if(Double.compare(a1, a2)==0) {
            System.out.printf("true\n");
            return true;
        }

        else return false;
    }

    public static boolean line_b(Vec start, Vec goal1, Vec goal2){
        double b1,b2;
        b1=start.y-((goal1.y-start.y)/(goal1.x-start.x))*start.x;
        b2=start.y-((goal2.y-start.y)/(goal2.x-start.x))*start.x;
        System.out.printf("b1:%d, b2:%d\n", b1,b2);

        if(Double.compare(b1,b2)==0) return true;
        return false;
    }

}