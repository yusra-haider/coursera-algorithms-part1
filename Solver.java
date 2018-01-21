  import java.util.Iterator;

  public class Solver {
   
      private MinPQ <Node> priorityQueue;
      private Node initialNode;
      private Node goalNode;
      
      public Solver(Board initial){
          priorityQueue = new MinPQ();
          initialNode = new Node(null, initial,0);
          if(!isSolvable()) return;
          solving();
      }
      
      private void solving(){
          
          priorityQueue.insert(initialNode);
          Node node = new Node();
          Node deletedNode = priorityQueue.delMin();
          
          while(!deletedNode.board.isGoal()){
              for(Board board : deletedNode.board.neighbors()){
                   if(deletedNode.previousNode == null || !board.equals(deletedNode.previousNode.board)){
                       node = new Node(deletedNode,board,deletedNode.moves+1);
                       priorityQueue.insert(node);
                   }    
              }
              deletedNode = priorityQueue.delMin();
          }
          
          goalNode = deletedNode;
      }
      
      private static class Node implements Comparable <Node>{
          
          private Node previousNode;
          private Board board;
          private int manhattan;
          private int moves;
          private int priority;
          
         public Node(){
             
         }
         public Node(Node previousNode, Board board, int moves){
              this.previousNode = previousNode;
              this.board = board;
              this.moves = moves;
              manhattan = board.manhattan();
              priority = manhattan + this.moves;
          }
          
          @Override
          public int compareTo(Node that){
           
              if(this.board.equals(that.board))
                  return 0;
                         
          if(this.priority < that.priority) return -1;
                  
          if(this.priority > that.priority) return +1;
                  
          if(this.priority == that.priority){
              
              if(this.manhattan < that.manhattan) return -1;
              
              else return +1;
          }
          
          return 0;
          
          }
      }
      
      public boolean isSolvable(){
          Node originalNode = initialNode;
          Node twinNode = new Node (null, originalNode.board.twin(),0);
          boolean flag = true;
          
          MinPQ <Node> originalNodePQ = new MinPQ();
          MinPQ<Node> twinNodePQ = new MinPQ();
          
          originalNodePQ.insert(originalNode);
          twinNodePQ.insert(twinNode);
          
          Node originalDeletedNode = originalNodePQ.delMin();
          Node twinDeletedNode = twinNodePQ.delMin();
                  
          while(true){
              
             for(Board board : originalDeletedNode.board.neighbors()){
                   if(originalDeletedNode.previousNode == null || !board.equals(originalDeletedNode.previousNode.board)){
                     Node node = new Node(originalDeletedNode,board,originalDeletedNode.moves+1);
                      originalNodePQ.insert(node);
                     }
                    }
                    originalDeletedNode = originalNodePQ.delMin();
                     if(originalDeletedNode.board.isGoal()) break;
                  
                  for(Board board : twinDeletedNode.board.neighbors()){
                   if(twinDeletedNode.previousNode == null || !board.equals(twinDeletedNode.previousNode.board)){
                      Node node = new Node(twinDeletedNode,board,twinDeletedNode.moves+1);
                      twinNodePQ.insert(node);
                   }    
              }
              twinDeletedNode = twinNodePQ.delMin();
              if(twinDeletedNode.board.isGoal()){
              flag = false;
              break;
              }
                  
         } 
          return flag;
      }
          
          
     public int moves(){
         if(!isSolvable())
             return -1;
         
        return goalNode.moves;
     }
      public Iterable<Board> solution(){
          if(!isSolvable()) return null;
          
          Stack <Board> stack = new Stack();
          Node node = goalNode;
          
          while(node != null){
              stack.push(node.board);
              node = node.previousNode;
          }
          return stack;
      }
      
      public static void main(String[] args) {
      // create initial board from file
      In in = new In(args[0]);
      int N = in.readInt();
      int[][] blocks = new int[N][N];
      for (int i = 0; i < N; i++)
          for (int j = 0; j < N; j++)
              blocks[i][j] = in.readInt();
      Board initial = new Board(blocks);

      // solve the puzzle
      Solver solver = new Solver(initial);

      // print solution to standard output
      if (!solver.isSolvable())
          StdOut.println("No solution possible");
      else {
          StdOut.println("Minimum number of moves = " + solver.moves());
          for (Board board : solver.solution())
              StdOut.println(board);
      }
  }
      
      
  }


