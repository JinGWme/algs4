import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class SAPUnitTest {
    @Test
    void testGraph1Length() {
        In in = new In("data/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Assertions.assertEquals(0, sap.length(3, 3));
        Assertions.assertEquals(4, sap.length(3, 11));
        Assertions.assertEquals(3, sap.length(9, 12));
        Assertions.assertEquals(4, sap.length(7, 2));
        Assertions.assertEquals(-1, sap.length(1, 6));
    }
    
    @Test
    void testGraph1SAP() {
        In in = new In("data/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Assertions.assertEquals(3, sap.ancestor(3, 3));
        Assertions.assertEquals(1, sap.ancestor(3, 11));
        Assertions.assertEquals(5, sap.ancestor(9, 12));
        Assertions.assertEquals(0, sap.ancestor(7, 2));
        Assertions.assertEquals(-1, sap.ancestor(1, 6));
    }

    @Test 
    void testGraph1GroupLength() {
        In in = new In("data/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Integer[] v, w;
        v = new Integer[] {3};
        w = new Integer[] {3};
        Assertions.assertEquals(0, sap.length(Arrays.asList(v), Arrays.asList(w)));

        v = new Integer[] {3};
        w = new Integer[] {11};
        Assertions.assertEquals(4, sap.length(Arrays.asList(v), Arrays.asList(w)));

        v = new Integer[] {7,6,8};
        w = new Integer[] {11,12};
        Assertions.assertEquals(5, sap.length(Arrays.asList(v), Arrays.asList(w)));
    }

    @Test 
    void testGraph1GroupSAP() {
        In in = new In("data/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Integer[] v, w;
        v = new Integer[] {3};
        w = new Integer[] {3};
        Assertions.assertEquals(3, sap.ancestor(Arrays.asList(v), Arrays.asList(w)));

        v = new Integer[] {3};
        w = new Integer[] {11};
        Assertions.assertEquals(1, sap.ancestor(Arrays.asList(v), Arrays.asList(w)));

        v = new Integer[] {7,6,8};
        w = new Integer[] {11,12};
        Assertions.assertEquals(1, sap.ancestor(Arrays.asList(v), Arrays.asList(w)));
    }
}
