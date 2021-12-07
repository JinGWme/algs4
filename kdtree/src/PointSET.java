import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PointSET {
    private final SET<Point2D> points = new SET<>();

    // construct an empty set of points
    public PointSET() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        // StdDraw.setPenColor(Color.RED);
        for (Point2D point2d : points) {
            point2d.draw();
        }
        StdDraw.show();
        return;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        return new Iterable<Point2D>() {

            @Override
            public Iterator<Point2D> iterator() {
                List<Point2D> insiders = new ArrayList<>();
                for (Point2D p : points)
                    if (rect.contains(p))
                        insiders.add(p);
                return insiders.iterator();
            }

        };
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Point2D nearest = null;
        double distance = Double.POSITIVE_INFINITY;

        for (Point2D point2d : points) {
            if (point2d.distanceTo(p) < distance) {
                nearest = point2d;
                distance = point2d.distanceTo(p);
            }
        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET ps = new PointSET();
        for (int i = 0; i < 50; i++) {
            ps.insert(new Point2D(StdRandom.uniform(), StdRandom.uniform()));
        }
        for (Point2D p : ps.range(new RectHV(0.4, 0.4, 0.6, 0.6)))
            StdOut.println(p);
        StdOut.println(ps.nearest(new Point2D(0.2, 0.2)));
        ps.draw();
    }

}
