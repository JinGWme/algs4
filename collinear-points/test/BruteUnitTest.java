import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import edu.princeton.cs.algs4.In;

public class BruteUnitTest {
    @Test
    public void testInput8() {
        In in = new In("/home/wangj/works/algs4/collinear-points/data/input8.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        BruteCollinearPoints bc = new BruteCollinearPoints(points);
        LineSegment[] ls = bc.segments();
        Assertions.assertEquals(2, ls.length);

        Point a = new Point(10000, 0);
        Point b = new Point(0, 10000);
        LineSegment l1 = new LineSegment(a, b);
        Point c = new Point(3000, 4000);
        Point d = new Point(20000, 21000);
        LineSegment l2 = new LineSegment(c, d);

        // boolean hasAny = Arrays.stream(ls).anyMatch(s -> s.equals(l1));
        // Assertions.assertTrue(hasAny);;
        // hasAny = Arrays.stream(ls).anyMatch(s -> s.equals(l2));
        // Assertions.assertTrue(hasAny);;
    }

}
