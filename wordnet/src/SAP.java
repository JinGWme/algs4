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

  private enum VisitType {
    N, // not visited
    V, // visited from v
    W // visited from W
  };
  private class Visit {
    private final VisitType type;
    private final int fromId;
    private final int id;
    public Visit (VisitType type, int fromId, int id) {
      this.type = type;
      this.fromId = fromId;
      this.id = id;
    }
  }

  private int count(Visit[] visited, int i) {
    int length = 0;
    Visit v = visited[i];
    while (v.fromId != v.id) {
      length++;
      v = visited[v.fromId];
    }
    return length;
  }
  

  // contructor takes a digraph (not necessarily a DAG)
  public SAP(Digraph g) {
    this.G = g;
  }

  // length of shortest ancestral path between v and w, -1 if no such path
  public int length(int v, int w) {
    // prepare visit map
    Visit[] visited = new Visit[G.V()];
    Arrays.fill(visited, null);

    // prepare bfs queue
    Deque<Visit> bfsQueue = new LinkedList<>();
    bfsQueue.add(new Visit(VisitType.V, v, v));
    bfsQueue.add(new Visit(VisitType.W, w, w));

    while (!bfsQueue.isEmpty()) {
      Visit n = bfsQueue.pop();
      if (visited[n.id] == null) {
        visited[n.id] = n;
      } else if (visited[n.id].type != n.type) {
        // two trees reached the same point
        int length = count(visited, n.id);
        visited[n.id] = n;
        length += count(visited, n.id);
        return length;
      } else continue;
      for (Integer i : G.adj(n.id)) {
        // check every outgoing edges
        if (visited[i] == null || visited[i].type != n.type) {
          bfsQueue.add(new Visit(n.type, n.id, i));
        }
      }
    }
    return -1;
  }

  // a common ancestor of v and w that participates in a shortest ancestral path;
  // -1 if no such path
  public int ancestor(int v, int w) {
    // prepare visit map
    Visit[] visited = new Visit[G.V()];
    Arrays.fill(visited, null);

    // prepare bfs queue
    Deque<Visit> bfsQueue = new LinkedList<>();
    bfsQueue.add(new Visit(VisitType.V, v, v));
    bfsQueue.add(new Visit(VisitType.W, w, w));

    while (!bfsQueue.isEmpty()) {
      Visit n = bfsQueue.pop();
      if (visited[n.id] == null) {
        visited[n.id] = n;
      } else if (visited[n.id].type != n.type) {
        // two trees reached the same point
        return n.id;
      } else continue;
      for (Integer i : G.adj(n.id)) {
        // check every outgoing edges
        if (visited[i] == null || visited[i].type != n.type) {
          bfsQueue.add(new Visit(n.type, n.id, i));
        }
      }
    }
    return -1;
  }

  // length of shortest ancestral path between any vertex in v and any vertex in
  // w; -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    // prepare visit map
    Visit[] visited = new Visit[G.V()];
    Arrays.fill(visited, null);

    // prepare bfs queue
    Deque<Visit> bfsQueue = new LinkedList<>();
    for (Integer i : v) bfsQueue.add(new Visit(VisitType.V, i, i));
    for (Integer i : w) bfsQueue.add(new Visit(VisitType.W, i, i));

    while (!bfsQueue.isEmpty()) {
      Visit n = bfsQueue.pop();
      if (visited[n.id] == null) {
        visited[n.id] = n;
      } else if (visited[n.id].type != n.type) {
        // two trees reached the same point
        int length = count(visited, n.id);
        visited[n.id] = n;
        length += count(visited, n.id);
        return length;
      } else continue;
      for (Integer i : G.adj(n.id)) {
        // check every outgoing edges
        if (visited[i] == null || visited[i].type != n.type) {
          bfsQueue.add(new Visit(n.type, n.id, i));
        }
      }
    }
    return -1;
  }

  // a common ancestor that participates in shortest ancestral path; -1 if no such
  // path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    // prepare visit map
    Visit[] visited = new Visit[G.V()];
    Arrays.fill(visited, null);

    // prepare bfs queue
    Deque<Visit> bfsQueue = new LinkedList<>();
    for (Integer n : v) bfsQueue.add(new Visit(VisitType.V, n, n));  
    for (Integer n : w) bfsQueue.add(new Visit(VisitType.W, n, n));  

    while (!bfsQueue.isEmpty()) {
      Visit n = bfsQueue.pop();
      if (visited[n.id] == null) {
        visited[n.id] = n;
      } else if (visited[n.id].type != n.type) {
        // two trees reached the same point
        return n.id;
      } else continue;
      for (Integer i : G.adj(n.id)) {
        // check every outgoing edges
        if (visited[i] == null || visited[i].type != n.type) {
          bfsQueue.add(new Visit(n.type, n.id, i));
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
