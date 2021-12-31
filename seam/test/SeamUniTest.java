import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.Picture;

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
    
}
