import java.util.TreeSet;


public class PointSET {
    
    private TreeSet<Point2D> set;
    
    
    public PointSET(){
        set = new TreeSet();
        
    }
    
    public boolean isEmpty(){
        return set.isEmpty();
    }
    
    public int size(){
        return set.size();
    }
    
    public void insert(Point2D p){
        if(p == null)
            return;
        
        if(!contains(p))
            set.add(p);
    }
    
    public boolean contains(Point2D p){
        if(p == null || isEmpty())
            return false;
        
       return set.contains(p);
    }
    
    public void draw(){
       if(isEmpty()) return;
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.setPenRadius(0.01);
      for(Point2D p : set)
         p.draw();
    }
    
    public Iterable<Point2D> range(RectHV rect){
       Queue<Point2D> queue = new Queue();
       
       for(Point2D p : set)
           if(rect.contains(p))
               queue.enqueue(p);
       
       return queue;
    }
    
    
    public Point2D nearest(Point2D p){        
        if(isEmpty())       return null;
        if(contains(p))     return p;
        
        Point2D point = set.first();
        double min = p.distanceSquaredTo(point);
        
        for(Point2D p2 : set){
           if(p2.equals(point)) continue;
            if(p.distanceSquaredTo(p2) < min){
                min = p.distanceSquaredTo(p2);
                point = p2;
            }
        }
        return point;
        
}
}