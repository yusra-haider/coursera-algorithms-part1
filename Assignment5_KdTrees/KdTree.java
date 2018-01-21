public class KdTree {
    
  private Node root;
  private int treeSize;
  private Point2D nearest;
    
   private static class Node{
   private Point2D p;      // the point
   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
   private Node lb;        // the left/bottom subtree
   private Node rt;        // the right/top subtree
   private boolean isVertical;
   
   public Node(){
       
   }
   public Node(Point2D p, RectHV rect, boolean isVertical){
       this.p = p;
       this.rect = rect;
       this.isVertical = isVertical;
   }
      
   }
   
    public KdTree(){
        root = null;
        treeSize = 0;
    }
    
    public boolean isEmpty(){
        return root == null;
    }
    
    public int size(){
        return treeSize;
    }
       
    public void insert(Point2D p){
        if(p == null)
            return;
                    
     if(!contains(p)) { root = insert(root,true,new RectHV(0,0,1,1),p); treeSize++;}
         
    }
    
    private Node insert(Node x, boolean isVertical,RectHV rect,Point2D p){
        final double pX = p.x();
        final double pY = p.y();
        if(x == null)                         return new Node(p,rect,isVertical);
        if(isVertical && pX < x.p.x())        x.lb = insert(x.lb,false,new RectHV(x.rect.xmin(),x.rect.ymin(),x.p.x(),x.rect.ymax()),p);
        else if(isVertical && pX >= x.p.x())  x.rt = insert(x.rt,false,new RectHV(x.p.x(),x.rect.ymin(),x.rect.xmax(),x.rect.ymax()),p);
        else if(!isVertical && pY < x.p.y())  x.lb = insert(x.lb,true,new RectHV(x.rect.xmin(),x.rect.ymin(),x.rect.xmax(),x.p.y()),p);
        else if(!isVertical && pY >= x.p.y()) x.rt = insert(x.rt,true,new RectHV(x.rect.xmin(),x.p.y(),x.rect.xmax(),x.rect.ymax()),p);
        return x;
   
    }
    
   public boolean contains(Point2D p){
        if(p == null) return false;   
        return contains(root,true,p);
    }
    
    
   private boolean contains(Node x,boolean isVertical, Point2D p){
      final double pX = p.x();
      final double pY = p.y();
       boolean flag= false;
       if(x == null)                            return false;
       if(p.equals(x.p))                        return true;
       if(isVertical && pX < x.p.x())           flag = contains(x.lb,false,p);
       else if(isVertical && pX >= x.p.x())     flag = contains(x.rt,false,p);
       else if(!isVertical && pY < x.p.y())     flag = contains(x.lb,true,p);
       else if(!isVertical && pY >= x.p.y())    flag = contains(x.rt,true,p);   
         
       return flag;                           
       
   }
    
   public void draw(){
       root.rect.draw();
       draw(root);       
   }
   
   private void draw(Node x){   
       if(x == null) return;
       draw(x.lb);
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.setPenRadius(0.01);
        x.p.draw();
       StdDraw.setPenRadius();
       if(x.isVertical)  {
           StdDraw.setPenColor(StdDraw.RED);
           StdDraw.line(x.p.x(),x.rect.ymin(),x.p.x(),x.rect.ymax());
       }
       else{
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(x.rect.xmin(),x.p.y(),x.rect.xmax(),x.p.y());
       }
      
       draw(x.rt);
           
   }
   
   public Iterable<Point2D> range(RectHV rect){
       Queue<Point2D> queue = new Queue();
       double xMin = rect.xmin();
       double xMax = rect.xmax();
       double yMin = rect.ymin();
       double yMax = rect.ymax();
       addToRange(root,rect,xMin,xMax,yMin,yMax,queue);
       return queue;
   }
    
   private void addToRange(Node x, RectHV rect ,double xMin,double xMax,double yMin, double yMax,Queue q){
       if(x == null)   return;
       if(rect.contains(x.p))   q.enqueue(x.p);
       if(x.isVertical){
           double pX = x.p.x();
           
           if(rect.intersects(new RectHV(pX,x.rect.ymin(),pX,x.rect.ymax()))){ addToRange(x.lb,rect,xMin,xMax,yMin,yMax,q); addToRange(x.rt,rect,xMin,xMax,yMin,yMax,q);}
           else if(xMax < pX) addToRange(x.lb,rect,xMin,xMax,yMin,yMax,q);
           else addToRange(x.rt,rect,xMin,xMax,yMin,yMax,q);
               
       }
       else{
           double pY = x.p.y();
           if(rect.intersects(new RectHV(x.rect.xmin(),pY,x.rect.xmax(),pY))) { addToRange(x.lb,rect,xMin,xMax,yMin,yMax,q); addToRange(x.rt,rect,xMin,xMax,yMin,yMax,q);}
           else if(yMax < pY)  addToRange(x.lb,rect,xMin,xMax,yMin,yMax,q);
           else addToRange(x.rt,rect,xMin,xMax,yMin,yMax,q);
       }
   }
   
   public Point2D nearest(Point2D p){
       if(isEmpty()) return null;
       if(contains(p)) return p;
       nearest = root.p;
       nearest(root,p);
       return nearest;
   }
   
   
   private void nearest(Node x, Point2D qp){
       final double qpX = qp.x();
       final double qpY = qp.y();
       
      if(x == null) return;
      if(x.rect.distanceSquaredTo(qp) > qp.distanceSquaredTo(nearest))   return;
      if(qp.distanceSquaredTo(x.p) < qp.distanceSquaredTo(nearest))      {nearest = x.p;}
      
      Node firstNode = null;
      Node secondNode = null;
      if((x.isVertical && qpX < x.p.x()) || (!x.isVertical && qpY < x.p.y())){firstNode = x.lb; secondNode = x.rt;}
      else{firstNode = x.rt; secondNode = x.lb;}      
      nearest(firstNode,qp);
      nearest(secondNode,qp);
   }
    
}
