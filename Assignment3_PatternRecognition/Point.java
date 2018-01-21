import java.util.Comparator;

public class Point implements Comparable<Point>{

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
       
        // checking for degenerate line segment
        if ((that.x == x) && (that.y == y))
            return Double.NEGATIVE_INFINITY;
        
        // checking for horizontal line segment
        if (that.y == y)
            return 0.0;
        
        // checking for vertical line segment
        if (that.x == x)
          return Double.POSITIVE_INFINITY;
       
        // otherwise...
       return ((double)(that.y - y))/(that.x - x);
       
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        
        if (y < that.y)                    return -1;
        if (y > that.y)                    return +1;
        if ((y == that.y) && (x < that.x)) return -1;
        if ((y == that.y) && (x > that.x)) return +1;
        
        return 0;    
    }

    private class SlopeOrder implements Comparator <Point>{
        
        public int compare(Point p1, Point p2){
            
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            
            if (slope1 < slope2) return -1;
            if (slope1 > slope2) return +1;
            return 0;
        }
    }
    // return string representation of this point
    public String toString() {
        
        return "(" + x + ", " + y + ")";
    }

}