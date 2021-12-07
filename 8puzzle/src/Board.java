import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

  private final int N;
  private final int[][] tiles;

  // create a board from an n-by-n array of tiles,
  // where tiles[row][col] = tile at (row, col)
  public Board(int[][] tiles) {
    this.N = tiles.length;
    this.tiles = dup(tiles);
  }

  // Create identical but new tiles,
  // so that update to the new one will not impact current one
  private static int[][] dup(int[][] oldTiles) {
    int N = oldTiles.length;
    int[][] newTiles = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        newTiles[i][j] = oldTiles[i][j];
      }
    }
    return newTiles;
  }

  // return a new tile with [aX][aY] swapped with [bX][bY]
  private int[][] swap(int aX, int aY, int bX, int bY) {
    int[][] newTiles = dup(this.tiles);
    int tmp = newTiles[aX][aY];
    newTiles[aX][aY] = newTiles[bX][bY];
    newTiles[bX][bY] = tmp;
    return newTiles;
  }


  // string representation of this board
  public String toString() {
    StringBuilder out = new StringBuilder();
    out.append("" + N + "\n");
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        out.append(" " + tiles[i][j]);
      }
      out.append("\n");
    }
    return out.toString();
  }

  // board dimension n
  public int dimension() {
    return N;
  }

  // number of tiles out of place
  public int hamming() {
    int distance = 0;

    for (int i = 0; i < N*N-1; i++) {
      if (tiles[i / N][i % N] != i+1) distance++;
    }
    // Hamming distance doesn't count last 0
    // if (this.tiles[N-1][N-1] != 0) distance++;

    return distance;
  }

  // sum of Manhattan distances between tiles and goal
  // Manhattan distance doesn't count last 0
  public int manhattan() {
    int distance = 0;

    for (int i = 0; i < N*N; i++) {
      int num = tiles[i/N][i%N];
      if (num == 0) continue;
      int goalX = (num-1) / N;
      int goalY = (num-1) % N;
      distance += (Math.abs(goalX - (i/N)) + Math.abs(goalY - (i%N)));
    }
    return distance;
  }

  // is this board the goal board?
  public boolean isGoal() {
    for (int i = 0; i < N*N-1; i++) 
      if (tiles[i / N][i % N] != i+1) return false;
    if (tiles[N-1][N-1] != 0) return false;
    return true;
  }

  // does this board equal y?
  public boolean equals(Object y) {

    if (y == null)
      return false;
    if (this == y)
      return true;
    if (y.getClass() == this.getClass()) {
      Board other = (Board) y;
      if (N != other.N)
        return false;
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          if (tiles[i][j] != other.tiles[i][j])
            return false;
        }
      }
      return true;
    }
    return false;
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    return new Iterable<Board>() {

      @Override
      public Iterator<Board> iterator() {
        List<Board> neighborList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
          for (int j = 0; j < N; j++) {
            if (tiles[i][j] == 0) {
              if (i > 0)
                neighborList.add(new Board(swap(i, j, i - 1, j)));
              if (i < N - 1)
                neighborList.add(new Board(swap(i, j, i + 1, j)));
              if (j > 0)
                neighborList.add(new Board(swap(i, j, i, j - 1)));
              if (j < N - 1)
                neighborList.add(new Board(swap(i, j, i, j + 1)));
              return neighborList.iterator();
            }
          }
        }
        return null;
      }
    };
  }

  // a board that is obtained by exchanging any pair of tiles;
  public Board twin() {
    for (int i = 0; i < N; i++) {
      if (tiles[i][0] != 0 && tiles[i][1] != 0)
        return new Board(swap(i, 0, i, 1));
    }
    return null;
  }

  private static int[][] randomTiles(int dimension) {
    int[] source = new int[dimension * dimension];
    for (int i = 0; i < source.length; i++) {
      source[i] = i;
    }
    StdRandom.shuffle(source);
    int[][] target = new int[dimension][dimension];
    int idx = 0;
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        target[i][j] = source[idx++];
      }
    }
    return target;
  }

  // unit testing (not graded)
  public static void main(String[] args) {
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] t = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        t[i][j] = in.readInt();
      }
    }
    Board b = new Board(t);
    StdOut.println(b);
    StdOut.println(b.manhattan());
  }
}
