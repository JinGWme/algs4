import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final List<LineSegment> lineSegmentList = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        Point[] workingPoints = new Point[points.length];
        for (int i = 0; i < workingPoints.length; i++) 
            workingPoints[i] = points[i];
        checkInput(workingPoints);
        for (int i = 0; i < workingPoints.length; i++) {
            checkAndMark(workingPoints[i], workingPoints);
        }
    }
    private void checkInput(Point[] points) {
        if(points == null) 
            throw new IllegalArgumentException();
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

    private class SlopeItem {
        double slope;
        Point p;
    }

    private static class SlopeItemComparator implements Comparator<SlopeItem> {

        @Override
        public int compare(FastCollinearPoints.SlopeItem arg0, FastCollinearPoints.SlopeItem arg1) {
            if (arg0.slope < arg1.slope) return -1;
            else if (arg0.slope > arg1.slope) return 1;
            else return 0;
        }

    }

    private void checkAndMark(Point root, Point[] points) {
        SlopeItem[] siArray = new SlopeItem[points.length];
        for (int i = 0; i < points.length; i++) {
            SlopeItem si = new SlopeItem();
            si.p = points[i];
            si.slope = root.slopeTo(points[i]);
            siArray[i] = si;
        }

        // Arrays.sort(siArray, new SlopeItemComparator());
        // StdOut.println("Slope Array for root " + root);
        // for (SlopeItem slopeItem : siArray) {
        //     StdOut.print(" " + slopeItem.slope);
        // }
        // StdOut.println();
        // StdOut.println("After sorting with "+root);
        // for (SlopeItem slopeItem : siArray) {
        // StdOut.print(slopeItem.slope+" ");
        // }
        // StdOut.println();
        boolean counting = false;
        int count = 1;
        for (int i = 1; i < siArray.length; i++) {
            counting = siArray[i].slope == siArray[i - 1].slope;
            if (counting) {
                count++;
            } else {
                if (count > 2) {
                    markLineSegment(siArray, i - count, count, root);
                }
                count = 1;
            }
        }
        if (counting && count >= 3) {
            markLineSegment(siArray, siArray.length - count, count, root);
        }
    }

    private void markLineSegment(FastCollinearPoints.SlopeItem[] siArray, int start, int count, Point root) {
        Point[] ps = new Point[count + 1];
        for (int i = 0; i < count; i++) {
            ps[i] = siArray[start + i].p;
        }
        ps[ps.length - 1] = root;
        Arrays.sort(ps);
        if (root == ps[0]) {
            lineSegmentList.add(new LineSegment(ps[0], ps[ps.length - 1]));
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegmentList.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegmentList.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        // for (Point p : points) {
        //     p.draw();
        // }
        // StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            // segment.draw();
        }
        // StdDraw.show();
    }
}
