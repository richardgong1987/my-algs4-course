import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double[] thresholds;
	private int trials;
	private int n;
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException("Both n and trials must be greater than 0.");
		}
		this.n = n;
		this.trials = trials;
		thresholds = new double[trials];
		for (int i = 0; i < trials; i++) {
			Percolation perc = new Percolation(n);
			int openSites = 0;
			while (!perc.percolates()) {
				int row = StdRandom.uniform(1, n + 1);
				int col = StdRandom.uniform(1, n + 1);
				if (!perc.isOpen(row, col)) {
					perc.open(row, col);
					openSites++;
				}
			}
			thresholds[i] = (double) openSites / (n * n);
		}
	}
	public double mean() {
		return StdStats.mean(thresholds);
	}


	// sample mean of percolation threshold

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(thresholds);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - (1.96 * stddev() / Math.sqrt(trials));
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + (1.96 * stddev() / Math.sqrt(trials));
	}

	// test client (see below)
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats stats = new PercolationStats(n, trials);
		StdOut.println("mean\t\t\t= " + stats.mean());
		StdOut.println("stddev\t\t\t= " + stats.stddev());
		StdOut.println("95% confidence interval\t= [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
	}
}
