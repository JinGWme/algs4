import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class EnergyUnitTest {

    @Test
    public void testLoadPicture() {
        Picture p = new Picture("data/3-4.png");
        Assertions.assertEquals(3, p.width());
        Assertions.assertEquals(4, p.height());
        Assertions.assertEquals(-13159, p.getRGB(1, 2));
    }

    @Test
    public void testLoadRGB() {
        Picture p = new Picture("data/3-4.png");
    }
    
}
