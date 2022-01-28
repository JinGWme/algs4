import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF wqu;     // connects to virtual head only for test fullness
    private final WeightedQuickUnionUF wquP;    // connects to virutal head/tail for testing percolation 
    private final boolean[] openness;           // simulate the 2-dimension array to test openness
    private final int n;
    private int nOpen;
    private final int headNodeIndex;
    private final int tailNodeIndex;

    private int index(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("Index out of bound " + row + "," + col);
        return ((row - 1) * n) + (col - 1);
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Size error");
        this.n = n;
        this.nOpen = 0;
        int gridSize = n * n;
        // Two additional nodes for virtual head and tail node
        wqu = new WeightedQuickUnionUF(gridSize + 2);
        wquP = new WeightedQuickUnionUF(gridSize + 2);
        // Initialize to be all closed;
        openness = new boolean[gridSize];
        headNodeIndex = gridSize;
        tailNodeIndex = gridSize + 1;
        for (int i = 0; i < n; i++) {
            // Union the first row to a virtual head node at [gridSize]
            wqu.union(headNodeIndex, i);
            wquP.union(headNodeIndex, i);
            // Union the last row to a virtual tail node at [gridSize+1]
            wquP.union(tailNodeIndex, gridSize - 1 - i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col))
            return;

        openness[index(row, col)] = true;
        ++nOpen;
        if (row > 1 && isOpen(row - 1, col)) {
            wqu.union(index(row - 1, col), index(row, col));
            wquP.union(index(row - 1, col), index(row, col));
        }
        if (row < n && isOpen(row + 1, col)) {
            wqu.union(index(row + 1, col), index(row, col));
            wquP.union(index(row + 1, col), index(row, col));
        }

        if (col > 1 && isOpen(row, col - 1)) {
            wqu.union(index(row, col - 1), index(row, col));
            wquP.union(index(row, col - 1), index(row, col));
        }
        if (col < n && isOpen(row, col + 1)) {
            wqu.union(index(row, col + 1), index(row, col));
            wquP.union(index(row, col + 1), index(row, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openness[index(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // the first row is alway connected to head node
        // so we need this extra check about isOpen.

        return isOpen(row, col) && (wqu.find(index(row, col)) == wqu.find(headNodeIndex));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        if (n == 1)
            return openness[index(1, 1)];
        return wquP.find(headNodeIndex) == wquP.find(tailNodeIndex);
    }

}
