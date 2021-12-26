import java.awt.Color;
import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class EnergyUnitTest {

    @Test
    public void testLoadPicture() {
        Picture p = new Picture("data/3x4.png");
        Assertions.assertEquals(3, p.width());
        Assertions.assertEquals(4, p.height());
        Assertions.assertEquals(-13159, p.getRGB(1, 2));
    }

    @Test
    public void testLoadRGB() {
        Picture p = new Picture("data/3x4.png");
        Color c = p.get(1, 2);
        Assertions.assertEquals(255, c.getRed());
        Assertions.assertEquals(204, c.getGreen());
        Assertions.assertEquals(153, c.getBlue());
        c = p.get(2, 3);
        Assertions.assertEquals(255, c.getRed());
        Assertions.assertEquals(255, c.getGreen());
        Assertions.assertEquals(255, c.getBlue());
    }

    @Test 
    public void testEdgeEnergy() {
        Picture p = new Picture("data/3x4.png");
        SeamCarver sc = new SeamCarver(p);
        Assertions.assertEquals(1000, sc.energy(0, 0));
        Assertions.assertEquals(1000, sc.energy(1, 3));
        Assertions.assertEquals(1000, sc.energy(0, 3));
        
        p = new Picture("data/6x5.png");
        sc = new SeamCarver(p);
        Assertions.assertEquals(1000, sc.energy(0, 0));
        Assertions.assertEquals(1000, sc.energy(0, 3));
        Assertions.assertEquals(1000, sc.energy(5, 4));
        Assertions.assertEquals(1000, sc.energy(4, 4));


    }

    @Test
    public void testCenterEnergy() {
        Picture p = new Picture("data/3x4.png");
        SeamCarver sc = new SeamCarver(p);
        Assertions.assertEquals(228.08770243044668, sc.energy(1, 2));
        Assertions.assertEquals(228.52789764052878, sc.energy(1, 1));

        p = new Picture("data/6x5.png");
        sc = new SeamCarver(p);
        Assertions.assertEquals(151.02, Math.round(sc.energy(2, 1)*100.0)/100.0);
        Assertions.assertEquals(133.07, Math.round(sc.energy(3, 2)*100.0)/100.0);
        Assertions.assertEquals(194.50, Math.round(sc.energy(4, 3)*100.0)/100.0);
    }
    
}
