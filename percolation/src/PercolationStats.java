import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] openSiteWhenPercolate;
    private final int size;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        openSiteWhenPercolate = new double[trials];
        size = trials;
        if (n <= 0 || size <= 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < size; i++) {
            int openSite = 0;
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    openSite++;
                }
            }
            openSiteWhenPercolate[i] = (double) openSite / (n * n);
            // StdOut.println("Size " + n*n + " Percolate @ " + openSite + " Percentage " +
            // openSiteWhenPercolate[i] );
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSiteWhenPercolate);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return Math.sqrt(StdStats.stddev(openSiteWhenPercolate));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(size));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(size));

    }

    // test client (see below)
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(size, trials);
        StdOut.println("mean\t\t\t= " + ps.mean());
        StdOut.println("stddev\t\t\t= " + ps.stddev());
        StdOut.println("95% confidence interval\t= " + "["+ps.confidenceLo()+", "+ps.confidenceHi()+"]");
    }
}
