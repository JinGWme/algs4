import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final List<LineSegment> lineSegmentList = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkInput(points);
        for (Point root : points) {
            checkAndMark(root, points);
        }
    }
    private void checkInput(Point[] points) {
        if (points == null) 
            throw new IllegalArgumentException();
        for (Point point : points) {
            if (point  == null)
                throw new IllegalArgumentException();
        }
        Point[] p = Arrays.copyOf(points, points.length);
        Arrays.sort(p);
        for (int i = 1; i < p.length; i++) {
            if (p[i].compareTo(p[i-1]) == 0)
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
            return Double.compare(arg0.slope, arg1.slope);
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

        Arrays.sort(siArray, new SlopeItemComparator());
        int count = 1;
        for (int i = 1; i < siArray.length; i++) {
            boolean same = siArray[i].slope == siArray[i-1].slope;
            if (same) {
                count++;
                if (i == siArray.length-1 && count > 2) markLineSegment(siArray, i-count+1, count, root);
            }
            else {
                if (count > 2) markLineSegment(siArray, i-count, count, root);
                count = 1;
            }
        }
    }

    private void markLineSegment(FastCollinearPoints.SlopeItem[] siArray, int start, int count, Point root) {
        double targetSlope = siArray[start].slope;
        Point[] pa = new Point[count+1];
        int idx = 0;
        pa[idx++] = root;
        // StdOut.print("Root" + root + " Checking line" );
        // for (int i = start; i < start+count; i++) {
        //     StdOut.print("\t" + siArray[i].p);
        // }
        // StdOut.println(" count " + count);
        for (SlopeItem slopeItem : siArray) {
            if (Double.compare(slopeItem.slope, targetSlope) == 0) pa[idx++] = slopeItem.p;
        }
        Arrays.sort(pa);
        if (root != pa[0]) return;

    //    StdOut.println("Adding line");
        lineSegmentList.add(new LineSegment(pa[0], pa[pa.length-1]));
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegmentList.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegmentList.toArray(new LineSegment[0]);
    }
}
