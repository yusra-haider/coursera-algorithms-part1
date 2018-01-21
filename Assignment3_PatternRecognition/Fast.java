
import java.util.Arrays;
import java.util.Comparator;

public class Fast {
    
  
    public static void main(String[]args){
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
        
        In in = new In(args[0]);
        int N = in.readInt();       // N is the number of points
        int count = 0;
        Point[] points = new Point[N];

        while (!in.isEmpty()) {
            int p = in.readInt();
            int q = in.readInt();

            points[count] = new Point(p, q);
            points[count].draw();
            count++;
        }
        
        Arrays.sort(points);
      
        for(int i = 0; i < N; i++){
            
            Point [] temp = Arrays.copyOfRange(points, 0, N);
            Arrays.sort(temp, points[i].SLOPE_ORDER);
            
            for(int j = 1; j < temp.length-2; j++){
                
                if((points[i].slopeTo(temp[j]) == points[i].slopeTo(temp[j+1])) && (points[i].slopeTo(temp[j]) == points[i].slopeTo(temp[j+2]))){
                String s = points[i] + " -> " + temp[j] + " -> " + temp[j+1] + " -> " + temp[j+2];
                    int k = j+3;
                
                if(points[i].compareTo(temp[j]) > 0)
                    while((k < temp.length) && (points[i].slopeTo(temp[k])==points[i].slopeTo(temp[j])))
                    k++;
                
                else if(points[i].compareTo(temp[j]) < 0){
                    while((k < temp.length) && (points[i].slopeTo(temp[k])==points[i].slopeTo(temp[j]))){
                    s = s + " -> " + temp[k];
                        k++;
                    }
                    points[i].drawTo(temp[k-1]);
                    StdOut.print(s);
                    StdOut.println();
                    }
                
                j = k-1;    
                
                }
            }
        
        }
             StdDraw.show();  
            
}
}
