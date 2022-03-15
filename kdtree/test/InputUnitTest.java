import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.sound.sampled.LineEvent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class InputUnitTest {
    @Test
    public void testInput0(){
        KdTree t = new KdTree();
        Assertions.assertNull(t.nearest(new Point2D(0, 0)));
    }

    @Test
    public void testInput1(){
        KdTree tree = new KdTree();
        In in = new In("/home/wangj/works/algs4/kdtree/data/input1.txt");

        // while (in.hasNextLine()) {
            double x = in.readDouble();
            double y = in.readDouble();
            tree.insert(new Point2D(x, y));
        // }

        assertEquals(new Point2D(0.5, 0.5), tree.nearest(new Point2D(StdRandom.uniform(), StdRandom.uniform())));
    }
}
