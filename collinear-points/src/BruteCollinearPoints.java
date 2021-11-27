import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final List<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        Point[] workingPoints = new Point[points.length];
        for (int i = 0; i < workingPoints.length; i++) 
            workingPoints[i] = points[i];
        checkInput(workingPoints);
        for (int p = 0; p < workingPoints.length; p++)
            for (int q = p + 1; q < workingPoints.length; q++)
                for (int r = q + 1; r < workingPoints.length; r++)
                    for (int s = r + 1; s < workingPoints.length; s++)
                        measureAndMark(workingPoints[p], workingPoints[q], workingPoints[r], workingPoints[s]);
    }

    private void checkInput(Point[] points) {
        for (Point point : points) {
            if (point  == null)
                throw new IllegalArgumentException();
        }
        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i-1]) == 0)
                throw new IllegalArgumentException();
        }
    }

    private void measureAndMark(Point p, Point q, Point r, Point s) {
        Point[] points = new Point[4];
        points[0] = p;
        points[1] = q;
        points[2] = r;
        points[3] = s;
        Arrays.sort(points);
        // StdOut.print("Dots selected: ");
        // for (Point x : points)
        // StdOut.print(x + " ");
        // StdOut.println();
        // StdOut.println("least " + points[0] + " most " + points[3]);
        Comparator<Point> sc = points[0].slopeOrder();
        if (sc.compare(points[1], points[2]) == 0 && sc.compare(points[2], points[3]) == 0)
            segments.add(new LineSegment(points[0], points[3]));
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int nodeNum = in.readInt();
        Point[] points = new Point[nodeNum];
        for (int i = 0; i < nodeNum; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
        }
        BruteCollinearPoints p = new BruteCollinearPoints(points);
        for (LineSegment ls : p.segments)
            StdOut.println(ls);
    }
}
