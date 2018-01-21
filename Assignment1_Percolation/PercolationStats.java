

    public class PercolationStats {
        private int numberOfExperiments;
        private Percolation percolation;
        private double[] fractionOfOpenSites;

        public PercolationStats(int N, int T) {

            if (N <= 0 || T <= 0) {
                throw new IllegalArgumentException("Either T or N is less than 0");
            }

            numberOfExperiments = T;
            fractionOfOpenSites = new double[T];

            for (int count = 0; count < T; count++) {

                percolation = new Percolation(N);
                int numberOfOpenSites = 0;

                while (!percolation.percolates()) {
                    int i = StdRandom.uniform(1, N + 1);
                    int j = StdRandom.uniform(1, N + 1);
                    
                    if (!percolation.isOpen(i, j)) {
                        percolation.open(i, j);
                        numberOfOpenSites++;
                    }
                    
                }
                
                fractionOfOpenSites[count] = numberOfOpenSites / Math.pow(N, 2);
            }
        }

        public double mean() {

            return StdStats.mean(fractionOfOpenSites);

        }

        public double stddev() {
           
            if(numberOfExperiments == 1){
                return Double.NaN;
            }
            
            return StdStats.stddev(fractionOfOpenSites);

        }

        public double confidenceLo() {

            return mean() - (1.96 * stddev() / Math.sqrt(numberOfExperiments));

        }

        public double confidenceHi() {

            return mean() + (1.96 * stddev() / Math.sqrt(numberOfExperiments));

        }
       
       public static void main(String[] args) {
           
           int N=Integer.parseInt(args[0]);
           int T=Integer.parseInt(args[1]);
           PercolationStats percolation1 = new PercolationStats(N,T);
            
           System.out.println("mean: " + percolation1.mean());
           System.out.println("stddev: " + percolation1.stddev());
           System.out.println("95% confidence interval: " + percolation1.confidenceLo() + " , " + percolation1.confidenceHi());      
       
       }
    }
