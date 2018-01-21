
    import java.util.Arrays;
    import java.util.Iterator;

    public class Board {
        
        private final char [] board;
        private final int N;               // as per N by N array of blocks
        private final int indexOfBlankTile;
        
        public Board(int[][] blocks){
            
            N = blocks.length;
            board = new char[N*N];
           int k = 0;
           for(int i = 0; i < N; i++){
               for(int j = 0; j < N; j++){
                board[i*N + j] = (char)blocks[i][j];
                
                if(blocks[i][j] == 0)
                    k = i*N + j;
               }
           }
           indexOfBlankTile = k;
        }
        
        public int dimension(){
            return N;
        }
        
        public int hamming(){
            
            int sum = 0;
            
            for(int i = 0; i < board.length;i++){
                if(board[i] == 0 || board[i] == i+1)
                    continue;
                else
                    sum = sum+1;
            }
            return sum;    //sum = numberOfBlocksOutPlace
        }
        
        public int manhattan(){
                
           int sum = 0;
            
            for(int i = 0; i < board.length; i++){
                if(board[i] == 0 || board[i] == i+1)  //skipping if the value is zero,or if the block is in place
                    continue;
                
                    sum = sum + Math.abs((i/N) - ((board[i]-1)/N));  // calculating verticle distance if any. i.e. diff btw target and current row
                    sum = sum + Math.abs((i%N) - ((board[i]-1)%N));  // calculating horizontal distance if any. i.e. diff btw target and current column
                
            }
            return sum;   //sum = sumOfManhattanDistances        
        }
        
        public boolean isGoal(){
            if(hamming() == 0){
                return true;
            }
            return false;
        }
        
        public Board twin(){
            
            int [][] blocks = new int [N][N];
            
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){
                    blocks[i][j] = board[i*N + j];
                }
            }
            
            int temp = 0;
            if(indexOfBlankTile/N != 0){       //if blank tile ISN'T in the top most row
                swap(0,0,0,1,blocks);          //swapping first two elements in top row
                
            }
            else{
                swap(1,0,1,1,blocks);
            }
            
            Board newBoard = new Board(blocks);
            return newBoard;
        }
        
        public boolean equals(Object y){
            
            if (y == this) return true;
            if (y == null) return false;
            if (y.getClass() != this.getClass()) return false;
            
            Board that = (Board)(y);
            return (Arrays.equals(this.board, that.board) && (this.N == that.N) && (this.indexOfBlankTile == that.indexOfBlankTile));
        }
        
        private void swap(int i, int j, int k, int l, int [][] a){
            int temp = a[i][j];
            a[i][j] = a[k][l];
            a[k][l] = temp;
        }
         
        public Iterable <Board> neighbors(){
            
            Queue <Board> queue = new Queue <Board>();
            int [][] temp = new int [N][N];
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){
                    temp [i][j] = board[i*N + j];
                }
            }
            int row = indexOfBlankTile/N;   //row = rowOfBlankTile   
            int column = indexOfBlankTile%N; //column = columnOfBlankTile
            
            if(row != 0){    //if blank tile isn't in the top row
                swap(row,column,row-1,column,temp);
                queue.enqueue(new Board(temp));
                swap(row,column,row-1,column,temp);
            }
                        
            if(row != N-1){
                swap(row,column,row+1,column,temp);
                queue.enqueue(new Board(temp));
                swap(row,column,row+1,column,temp);
            }            
            
            if(column!= 0){
                swap(row,column,row,column-1,temp);
                queue.enqueue(new Board(temp));
                swap(row,column,row,column-1,temp);
            }
                        
            if(column != N-1){
                swap(row,column,row,column+1,temp);
                queue.enqueue(new Board(temp));
                swap(row,column,row,column+1,temp);
            }
            
            return queue;
        }
       
        public String toString(){
            
            String s = new String();
            s= s + N + "\n";
            for(int i = 0; i < board.length; i++){
                s = s + (board[i] + 0) + " ";
               
               if(i%N == N-1)
                   s = s + "\n";
            }
            return s;           
            
        }
    }
