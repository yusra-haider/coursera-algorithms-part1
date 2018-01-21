
import java.util.Arrays;
public class Brute {
   
    
   public static void main (String [] args){
    
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
        In in = new In(args[0]);
        int N = in.readInt();
        int count = 0;
        Point [] points = new Point [N];
       
        while(!in.isEmpty()){
        int p = in.readInt();
        int q = in.readInt();
        
        points[count] = new Point(p,q);
        points[count].draw();
        count++;
        }
        
        Arrays.sort(points);
        
        for(int i = 0; i < points.length; i++){
            
            for(int j = i+1; j < points.length; j++){
                
                for(int k = j+1; k < points.length; k++){
                    
                    if(points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]))
                        for(int l = k+1; l < points.length; l++){
                        if(points[i].slopeTo(points[k]) == points[i].slopeTo(points[l])){
                            points[i].drawTo(points[l]);
                            StdOut.print(Brute.subsetOfPointsToString(points[i], points[j], points[k], points[l])); 
                              StdOut.println();
                        }
                        }
                }
            }
        }
       StdDraw.show(0);
    }
   
   private static String subsetOfPointsToString(Point p1, Point p2, Point p3, Point p4){
       return p1.toString() + " -> " + p2.toString() + " -> " + p3.toString() + " -> " + p4.toString();
   }
}
