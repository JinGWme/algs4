import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class PointUnitTest {
    @Test
    public void testCompareTo() {
        Point p0 = new Point(0, 0);
        Point p1 = new Point(1, 1);
        Assertions.assertEquals(true, p0.compareTo(p1)<0);

        Point p2 = new Point(1, 0);
        Assertions.assertEquals(true, p0.compareTo(p2)<0);

        Assertions.assertEquals(true, p0.compareTo(p0)==0);
    }
}
