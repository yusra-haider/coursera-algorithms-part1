    // IMPORTANT
    // 0 for blocked, 1 for open
    // ALL the methods call checkIfIndicesAreValid(int i, int j). So WHENEVER you give i, j, use values from 1 to N
    // also, the id[] in site (WeightedQuickUnionUF object) CANNOT be accessed directly. Always have to use the grid, and xyTo1D() for union(), connected() etc
    // whenever referring to the index (either grid's or the site object), I have used N and not gridSize

    public class Percolation {

        private int [][] grid;                  // modelling the pecolation system using an N-by-N grid of sites
        private int gridSize;                   // stores value of N
        private WeightedQuickUnionUF site;       // the WeightedQuickUnionUF object that we'll use to use the Dynmaic Connectivity problem solution to solve our problem
       
        
     public Percolation(int N){
         
         grid = new int [N][N];                 // creating an N-by-N grid of N^2 sites, with all the sites initialized with 0 (grid is an instance variable), representing blocked sites
         gridSize = N;
         site = new WeightedQuickUnionUF((int)(Math.pow(N,2)) + 2);  // N^2 sites representing the actual sites, the remaining two are the virtual top site (index: N^2), and virtual bottom site (index: N^2 + 1) respectively
         
         // CONNECT THE TOP AND BOTTOM VIRTUAL SITES WITH TOP AND BOTTOM ROWS!!
        for (int j = 1; j <= gridSize; j++){
             site.union((int)(Math.pow(N,2)),xyTo1D(1,j));       // connecting all the sites in the top row to the virtual top site
             site.union((int)(Math.pow(N,2)) + 1,xyTo1D(N,j) );  // connecting all the sites in the bottom row to the virtual bottom site
         }
         
       }
       
     // checks whether or not i and j are between 1 and N (inclusive). If not, an IndexOutOfBoundsException with the appropriate message is thrown
     private void checkIfIndicesAreValid(int i, int j){
         
         if(i < 1 || i > gridSize){
             throw new IndexOutOfBoundsException ("index i is out of bounds");
         }
         
         if(j < 1 || j > gridSize){
             throw new IndexOutOfBoundsException ("index j is out of bounds");
         }
     }
     
     // converts the indices i, j to the corresponding index of site's instance variable id
     private int xyTo1D(int i, int j){
      
         checkIfIndicesAreValid(i,j);
         
         i = i - 1;
         j = j -1;
         
         return (i*gridSize + j);
     }
     
     public void open(int i, int j){
         
         checkIfIndicesAreValid(i,j);
         
         if(isOpen(i,j)){
             return;
         }
         
         grid[i-1][j-1] = 1;       // opening the site at i-1,j-1 (subtracting 1 to get back ti the 0 to N-1 range) by setting its value to 1
                                   // note that we're still in the 1 to N range.
         
         if(i!=1 && isOpen(i-1,j)){                     // If we aren't in the top row of the grid(i=1), and the site above is open, then connect them both
             site.union(xyTo1D(i,j), xyTo1D(i-1,j));
         }
         
         if(i!=gridSize && isOpen(i+1,j)){              // If we aren't in the bottom row of the grid(i=N), and the site below is open, then connect them both
             site.union(xyTo1D(i,j),xyTo1D(i+1,j));     
         }
         
         if(j!=1 && isOpen(i,j-1)){                     // If we aren't in the left most column of the grid(j=1), and the site to the left is open, then connect them both
             site.union(xyTo1D(i,j),xyTo1D(i,j-1));
         }
         
         if(j!=gridSize && isOpen(i,j+1)){              // If we aren't in the right most column of the grid(j=N), and the site to the right is open, then connect them both
             site.union(xyTo1D(i,j),xyTo1D(i,j+1));
         }
         
         
     }
     
     public boolean isOpen(int i, int j){
         
          checkIfIndicesAreValid(i,j);
          
          i = i - 1;
          j = j - 1;
          
          if(grid[i][j] == 1){
              return true;
          }
          else{
         return false;
     }
          
     }
      
     public boolean isFull(int i, int j){
         
         checkIfIndicesAreValid(i,j);
         
         
         if(isOpen(i,j) && site.connected((int)(Math.pow(gridSize,2)), xyTo1D(i,j))){
            
             return true;
             
             }
               
             return false;
         
     }
     
     public boolean percolates(){
         
         if(site.connected((int)(Math.pow(gridSize,2)), (int)(Math.pow(gridSize,2)) + 1)){
             return true;
         }
         else{
             return false;
         }
     }
    }
