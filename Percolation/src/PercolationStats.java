import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
  
  private double[] results;
  private int T;
  
  private int runTest(int n) {
    Percolation grid = new Percolation(n);
    while(!grid.percolates()) {
      grid.open(StdRandom.uniform(1,n+1), StdRandom.uniform(1,n+1)); 
    }
    return grid.numberOfOpenSites();
  }
  
	public PercolationStats(int n, int trials) {
		// perform trials independent experiments on an n-by-n grid
		if (n <= 0 || trials <= 0) {
			throw new java.lang.IllegalArgumentException();
		}

	  T = trials;
		results = new double[trials]; 
		for (int i = 0; i < trials; i++) {
		  results[i] = runTest(n)/ (double)(n*n);
		}
	}
   public double mean() {
	   return StdStats.mean(results);
   }
   public double stddev() {
     return StdStats.stddev(results);
   }
   public double confidenceLo() {
	   // low  endpoint of 95% confidence interval
     return mean() - (1.96 * stddev())/(java.lang.Math.sqrt(T));
   }
   public double confidenceHi() {
	   // high endpoint of 95% confidence interval
     return mean() + (1.96 * stddev())/(java.lang.Math.sqrt(T));
   }

   public static void main(String[] args) {
     PercolationStats experiment = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
     StdOut.printf("mean                    = %f\n", experiment.mean());
     StdOut.printf("stddev                  = %f\n", experiment.stddev());
     StdOut.printf("95%% confidence interval = [%f, %f]\n", experiment.confidenceLo(), experiment.confidenceHi());
   }
}
