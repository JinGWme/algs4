import java.nio.file.Files;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.In;

public class PercolationTest {
    @Test
    public void testJunit() {
        Assertions.assertEquals(true, true);
    }

    @Test
    public void testReadInput() {
        In in  = new In("/home/wangj/works/algs4/percolation/data/input20.txt");
        Percolation p = new Percolation(in.readInt());
        while(in.hasNextLine()) {
            int row = in.readInt();
            int col = in.readInt();
            p.open(row, col);
            // StdOut.println("Opening row " + row + " col " + col);
        }
        Assertions.assertEquals(false, p.isFull(18, 1));
    }


    
}
