import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

  private final Digraph G;
  private static enum VisitType {
    N, // not visited
    V, // visited from v
    W  // visited from W
  };

  private int count(int[] ancestor, int start) {
    int length = 0;
    while (start != ancestor[start]) {
      length++;
      start = ancestor[start];
    }
    return length;
  }
  // contructor takes a digraph (not necessarily a DAG)
  public SAP(Digraph g) {
    this.G = g;
  }

  // length of shortest ancestral path between v and w, -1 if no such path
  public int length(int v, int w){
    // prepare visit map
    VisitType visited[]= new VisitType[G.V()];
    Arrays.fill(visited, VisitType.N);
    int ancestor[] = new int[G.V()];
    Arrays.fill(ancestor, -1);

    // prepare bfs queue 
    Deque<Integer> bfsQueue = new LinkedList<>();
    bfsQueue.add(v);
    visited[v] = VisitType.V;
    ancestor[v] = v;
    bfsQueue.add(w);
    visited[w] = VisitType.W;
    ancestor[w] = w;

    while (!bfsQueue.isEmpty()) {
      int n = bfsQueue.pop();
      for (Integer i : G.adj(n)) {
        if (visited[i] == VisitType.N) {
          // bfs add new node
          bfsQueue.add(i);
          visited[i] = visited[n];
          ancestor[i] = n;
          continue;
        } else if (visited[i] != visited[n]) {
          // find a merging point
          int length = count(ancestor, i);
          ancestor[i] = n;
          length += count(ancestor, i);
          return length;
        } else continue;
      }
    }
    return -1;
  }

  // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
  public int ancestor(int v, int w) {
    // prepare visit map
    VisitType visited[]= new VisitType[G.V()];
    Arrays.fill(visited, VisitType.N);
    int ancestor[] = new int[G.V()];
    Arrays.fill(ancestor, -1);

    // prepare bfs queue 
    Deque<Integer> bfsQueue = new LinkedList<>();
    bfsQueue.add(v);
    visited[v] = VisitType.V;
    ancestor[v] = v;
    bfsQueue.add(w);
    visited[w] = VisitType.W;
    ancestor[w] = w;

    while (!bfsQueue.isEmpty()) {
      int n = bfsQueue.pop();
      for (Integer i : G.adj(n)) {
        if (visited[i] == VisitType.N) {
          // bfs add new node
          bfsQueue.add(i);
          visited[i] = visited[n];
          ancestor[i] = n;
          continue;
        } else if (visited[i] != visited[n]) {
          // find a merging point
          return i;
        } else {
          continue;
        }
      }
    }
    return -1;
  }

  // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w){
    // prepare visit map
    VisitType visited[]= new VisitType[G.V()];
    Arrays.fill(visited, VisitType.N);
    int ancestor[] = new int[G.V()];
    Arrays.fill(ancestor, -1);

    // prepare bfs queue 
    Deque<Integer> bfsQueue = new LinkedList<>();
    for (Integer i : v) {
      visited[i] = VisitType.V;
      ancestor[i] = i;
      bfsQueue.add(i);
    }
    for (Integer i : w) {
      visited[i] = VisitType.W;
      ancestor[i] = i;
      bfsQueue.add(i);
    }

    while (!bfsQueue.isEmpty()) {
      int n = bfsQueue.pop();
      for (Integer i : G.adj(n)) {
        if (visited[i] == VisitType.N) {
          // bfs add new node
          visited[i] = visited[n];
          ancestor[i] = n;
          bfsQueue.add(i);
          continue;
        } else if (visited[i] != visited[n]) {
          // find a merging point
          // find a merging point
          int length = count(ancestor, i);
          ancestor[i] = n;
          length += count(ancestor, i);
          return length;
        } else {
          continue;
        }
      }
    }
    return -1;
  }

  // a common ancestor that participates in shortest ancestral path; -1 if no such path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    // prepare visit map
    VisitType visited[]= new VisitType[G.V()];
    Arrays.fill(visited, VisitType.N);
    int ancestor[] = new int[G.V()];
    Arrays.fill(ancestor, -1);

    // prepare bfs queue 
    Deque<Integer> bfsQueue = new LinkedList<>();
    for (Integer i : v) {
      visited[i] = VisitType.V;
      ancestor[i] = i;
      bfsQueue.add(i);
    }
    for (Integer i : w) {
      visited[i] = VisitType.W;
      ancestor[i] = i;
      bfsQueue.add(i);
    }

    while (!bfsQueue.isEmpty()) {
      int n = bfsQueue.pop();
      for (Integer i : G.adj(n)) {
        if (visited[i] == VisitType.N) {
          // bfs add new node
          visited[i] = visited[n];
          ancestor[i] = n;
          bfsQueue.add(i);
          continue;
        } else if (visited[i] != visited[n]) {
          // find a merging point
          return i;
        } else {
          continue;
        }
      }
    }
    return -1;
  }

  // do unit test of this class
  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    List<Integer> v = new ArrayList<>();
    v.add(13);
    v.add(14);
    List<Integer> w = new ArrayList<>();
    w.add(8);
    w.add(16);
    int a = sap.ancestor(v, w);
    int length = sap.length(v, w);
    StdOut.printf("ancestor %d, length %d\n", a, length);
  }
  
}
