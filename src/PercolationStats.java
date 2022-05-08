import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int rows;
    private int gridElements;
    private int totalTrials;
    private double[] results;
    private Percolation percolation;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("N and trials must be > 0");

        rows = n;
        gridElements = n * n;
        totalTrials = trials;
        percolation = new Percolation(n);
        results = new double[trials];

        for (int i = 0; i < totalTrials; i++) {
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, rows + 1);
                int col = StdRandom.uniform(1, rows + 1);
                percolation.open(row, col);
            }
            results[i] = (double) percolation.numberOfOpenSites() / gridElements;
            percolation = new Percolation(n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(totalTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(totalTrials));
    }

    // test client (see below)
    public static void main(String[] args) {

        int n = 1000;
        int t = 100;
        if (args.length >= 2) {
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
        }

        PercolationStats ps = new PercolationStats(n, t);

        StdOut.println("Mean is " + ps.mean() + " +- " + ps.stddev());
    }

}
