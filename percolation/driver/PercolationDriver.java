import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationDriver {
    public static void main(String[] args) {
        int n = StdIn.readInt();
        StdOut.println("matrix size " + n);
        Percolation p = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            StdOut.println("opening " + row + "," + col);
            p.open(row, col);
            if (!p.isOpen(row, col)) {
                StdOut.println("ERROR");
                return;
            }
            if (p.percolates()) {
                StdOut.println("Percolate!");
            }
            StdOut.println("Number of Open Sites " + p.numberOfOpenSites());
            StdOut.println("is Full " + p.isFull(row, col));
        }
        if (p.percolates()) {
            StdOut.println("Percolate!");
        }
    }

}
