import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

  private final Digraph G;


  private class Visit {
    private final int fromId;
    private final int id;
    private final int length;

    public Visit(int id, int fromId, int length) {
      this.fromId = fromId;
      this.id = id;
      this.length = length;
    }
  }

  // contructor takes a digraph (not necessarily a DAG)
  public SAP(Digraph g) {
    this.G = g;
  }

  private Visit[] bfs(Iterable<Integer> from) {
    if (from == null) throw new IllegalArgumentException();
    Visit[] visited = new Visit[G.V()];
    Arrays.fill(visited, null);

    Deque<Visit> bfsQueue = new LinkedList<>();
    for (Integer f : from) {
      if (f == null || f < 0 || f >= G.V()) throw new IllegalArgumentException();
      bfsQueue.add(new Visit(f, f, 0));
    }

    while (!bfsQueue.isEmpty()) {
      Visit n = bfsQueue.pop();
      // StdOut.println("Visit " + n.id + " from " + n.fromId);
      if (visited[n.id] == null) {
        visited[n.id] = n;
        for (Integer i : G.adj(n.id)) {
          if (visited[i] == null) {
            bfsQueue.add(new Visit(i, n.id, n.length + 1));
          }
        }
      } else
        continue;
    }
    return visited;
  }

  // length of shortest ancestral path between v and w, -1 if no such path
  public int length(int v, int w) {
    return length(Collections.singletonList(v), Collections.singletonList(w));
  }

  // a common ancestor of v and w that participates in a shortest ancestral path;
  // -1 if no such path
  public int ancestor(int v, int w) {
    return ancestor(Collections.singletonList(v), Collections.singletonList(w));
  }

  // length of shortest ancestral path between any vertex in v and any vertex in
  // w; -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    Visit[] vVisited = bfs(v);
    Visit[] wVisited = bfs(w);

    int shortest = -1;
    int ancestor = -1;
    for (int i = 0; i < G.V(); i++) {
      if (vVisited[i] != null && wVisited[i] != null) {
        int length = vVisited[i].length + wVisited[i].length;
        if (shortest == -1 || length < shortest) {
          shortest = length;
          ancestor = i;
        }
      }
    }
    return shortest;
  }

  // a common ancestor that participates in shortest ancestral path; -1 if no such
  // path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    Visit[] vVisited = bfs(v);
    Visit[] wVisited = bfs(w);

    int shortest = -1;
    int ancestor = -1;
    for (int i = 0; i < G.V(); i++) {
      if (vVisited[i] != null && wVisited[i] != null) {
        int length = vVisited[i].length + wVisited[i].length;
        if (shortest == -1 || length < shortest) {
          shortest = length;
          ancestor = i;
        }
      }
    }
    return ancestor;
  }

  // do unit test of this class
  public static void main(String[] args) {
    // Unit test are done with JUnit5
  }

}
