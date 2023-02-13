import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[][] sites;
	private int n;
	private WeightedQuickUnionUF uf;
	private int top;
	private int bottom;
	private int openSites;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		this.n = n;
		sites = new boolean[n][n];
		uf = new WeightedQuickUnionUF(n * n + 2);
		top = n * n;
		bottom = n * n + 1;
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		if (!isOpen(row, col)) {
			sites[row - 1][col - 1] = true;
			openSites++;
			if (row == 1) {
				uf.union(getIndex(row, col), top);
			}
			if (row == n) {
				uf.union(getIndex(row, col), bottom);
			}
			if (row > 1 && isOpen(row - 1, col)) {
				uf.union(getIndex(row, col), getIndex(row - 1, col));
			}
			if (row < n && isOpen(row + 1, col)) {
				uf.union(getIndex(row, col), getIndex(row + 1, col));
			}
			if (col > 1 && isOpen(row, col - 1)) {
				uf.union(getIndex(row, col), getIndex(row, col - 1));
			}
			if (col < n && isOpen(row, col + 1)) {
				uf.union(getIndex(row, col), getIndex(row, col + 1));
			}
		}
	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		return sites[row - 1][col - 1];
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col) {
		return uf.connected(top, getIndex(row, col));
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return openSites;
	}

	// does the system percolate?
	public boolean percolates() {
		return uf.connected(top, bottom);
	}

	private int getIndex(int row, int col) {
		return (row - 1) * n + col - 1;
	}

	// test client (optional)

	public static void main(String[] args) {
		int n = 20;
		Percolation percolation = new Percolation(n);

		// Open sites randomly
		while (!percolation.percolates()) {
			int row = StdRandom.uniform(1, n + 1);
			int col = StdRandom.uniform(1, n + 1);
			if (!percolation.isOpen(row, col)) {
				percolation.open(row, col);
			}
		}

		// Calculate and print the percolation threshold
		double threshold = (double) percolation.numberOfOpenSites() / (n * n);
		StdOut.println("Percolation threshold: " + threshold);
	}

}
