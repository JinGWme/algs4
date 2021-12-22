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

    @Test
    void testGraph3Length() {
        In in = new In("data/digraph3.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Assertions.assertEquals(3, sap.length(10, 7));
    }
    @Test
    void testGraph4Length() {
        In in = new In("data/digraph4.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Assertions.assertEquals(3, sap.length(1, 4));
    }

    @Test 
    void testGraph5Length() {
        In in = new In("data/digraph5.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Assertions.assertEquals(5, sap.length(17, 21));
    }
    @Test 
    void testGraph6Length() {
        In in = new In("data/digraph6.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Assertions.assertEquals(4, sap.length(5, 1));
    }
    @Test
    void testGraph9Length() {
        In in = new In("data/digraph9.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Assertions.assertEquals(3, sap.length(0, 4));
    }
    @Test
    void testGraph1InvalidInput() {
        In in = new In("data/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sap.length(13, 0);
            sap.ancestor(13, 0);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sap.length(0, 13);
            sap.ancestor(0, 13);
        });
    }

    @Test
    void testGraph1GroupInvalidInput() {

        In in = new In("data/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Integer[] v, w;
            v = new Integer[] { 13, 0, 7, 9, 12 };
            w = new Integer[] { 1, 2, 4, 5, 10 };
            sap.ancestor(Arrays.asList(v), Arrays.asList(w));
            sap.length(Arrays.asList(v), Arrays.asList(w));
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Integer[] v, w;
            v = new Integer[] { 0, null, 7, 9, 12 };
            w = new Integer[] { 1, 2, 4, 5, 10 };
            sap.ancestor(Arrays.asList(v), Arrays.asList(w));
            sap.length(Arrays.asList(v), Arrays.asList(w));
        });
    }
}
