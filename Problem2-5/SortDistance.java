public class SortDistance{

  public static void main(String args[]){
    double d[]=new double[5];
    d[0] = 5.1;
    d[1] = 4.2;
    d[2] = 6.7;
    d[3] = 3.0;
    d[4] = 8.0;

    for(int i=0;i<d.length;i++){
      System.out.println("d["+i+"]="+d[i]);
    }
    System.out.println("\n");

    sort(d);
    for(int i=0;i<d.length;i++){
      System.out.println("d["+i+"]="+d[i]);
    }
  }
  static void sort(double d[]){
    for(int i=0;i<d.length-1;i++){
      for(int j=d.length-1; j>i;j--){
        if(d[j-1]>d[j])
        {
          double tmp=d[j-1];
          d[j-1]=d[j];
          d[j]=tmp;
        }
      }
    }
  }
}
