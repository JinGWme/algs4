import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastUnitTest {

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
        FastCollinearPoints fc = new FastCollinearPoints(points);
        LineSegment[] ls = fc.segments();
        Assertions.assertEquals(2, ls.length);
        for (LineSegment lineSegment : ls) {
           StdOut.println(lineSegment); 
        }
    }

    @Test
    public void testInput40() {
        In in = new In("/home/wangj/works/algs4/collinear-points/data/input40.txt");

        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        FastCollinearPoints fc = new FastCollinearPoints(points);
        LineSegment[] ls = fc.segments();
        Assertions.assertEquals(4, ls.length);
        for (LineSegment lineSegment : ls) {
           StdOut.println(lineSegment); 
        }

    }
    
}
