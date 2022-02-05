import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamUniTest {

    @Test
    public void test6x5Vertical() {
        Picture p = new Picture("data/6x5.png");
        SeamCarver sc = new SeamCarver(p);
        int[] vs = sc.findVerticalSeam();
        Assertions.assertEquals(4, vs[1]);
        Assertions.assertEquals(3, vs[2]);
        Assertions.assertEquals(2, vs[3]);
    }

    @Test
    public void test6x5Horizontal() {
        Picture p = new Picture("data/6x5.png");
        SeamCarver sc = new SeamCarver(p);
        int[] vs = sc.findHorizontalSeam();
        Assertions.assertEquals(2, vs[1]);
        Assertions.assertEquals(1, vs[2]);
        Assertions.assertEquals(2, vs[3]);
        Assertions.assertEquals(1, vs[4]);
    }
    
    @Test
    public void testRemoveHorizontal() {
        Picture p = new Picture("data/6x5.png");
        SeamCarver sc = new SeamCarver(p);
        int[] vs = sc.findHorizontalSeam();
        sc.removeHorizontalSeam(vs);
        Assertions.assertEquals(6, sc.width());
        Assertions.assertEquals(4, sc.height());
    }

    @Test
    public void testRemoveVertical() {
        Picture p = new Picture("data/6x5.png");
        SeamCarver sc = new SeamCarver(p);
        int[] vs = sc.findVerticalSeam();
        sc.removeVerticalSeam(vs);
        Assertions.assertEquals(5, sc.width());
        Assertions.assertEquals(5, sc.height());
    }

    @Test
    public void testRemoveVerticalDetail() {
        Picture p = new Picture("data/6x5.png");
        for (int row = 0; row < p.height(); row++) {
            for (int col = 0; col < p.width(); col++) {
                StdOut.printf("%04X\t", p.getRGB(col, row));
            }
            StdOut.println();
        }

    }
}
