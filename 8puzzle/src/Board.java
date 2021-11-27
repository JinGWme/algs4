import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

  private int N;
  private int[][] tiles;
  private int[][] goalTiles;

  // Create identical but new tiles, 
  // so that update to the new one will not impact current one
  private static int[][] dup(int[][] oldTiles){
    int N  = oldTiles.length;
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
  // create a board from an n-by-n array of tiles,
  // where tiles[row][col] = tile at (row, col)
  public Board(int[][] tiles) {
    this.N = tiles.length;
    this.tiles = dup(tiles);
    this.goalTiles =  new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        this.goalTiles[i][j] = i*N+j+1;
      }
    }
    goalTiles[N-1][N-1] = 0;
  }

  // string representation of this board
  public String toString() {
    StringBuilder out = new StringBuilder();
    out.append(""+N+"\n");
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

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if(this.tiles[i][j] != goalTiles[i][j]) distance++;
      }
    }
    return distance;
  }

    // sum of Manhattan distances between tiles and goal
  public int manhattan() {
    int distance = 0;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        int goalX = tiles[i][j] == 0 ? N-1 : (tiles[i][j]-1)/N;
        int goalY = tiles[i][j] == 0 ? N-1 : (tiles[i][j]-1)%N;
        distance += (Math.abs(goalX-i) + Math.abs(goalY-j));
      }
    }
    return distance;
  }

    // is this board the goal board?
  public boolean isGoal() {
   for (int i = 0; i < N; i++) {
     for (int j = 0; j < N; j++) {
       if (tiles[i][j] != goalTiles[i][j]) return false;
     }
   } 
   return true;
  }

    // does this board equal y?
  public boolean equals(Object y){

    if (y == null) return false;
    if (this == y) return true;
    if (y instanceof Board) {
      Board other = (Board)y;
      if (N != other.N) return false;
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          if (tiles[i][j] != other.tiles[i][j]) return false;
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
              if (i > 0) neighborList.add(new Board(swap(i, j, i-1, j)));
              if (i < N-1) neighborList.add(new Board(swap(i, j, i+1, j)));
              if (j > 0) neighborList.add(new Board(swap(i, j, i, j-1)));
              if (j < N-1) neighborList.add(new Board(swap(i, j, i, j+1)));
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
    // TODO: this is not right
    return new Board(tiles);
  }

  public static int[][] randomTiles(int dimension) {
    int[] source = new int[dimension*dimension];
    for (int i = 0; i < source.length; i++) {
      source[i] = i;
    }
    StdRandom.shuffle(source);
    int[][] target = new int[dimension][dimension];
    for (int i = 0, idx = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        target[i][j] = source[idx++];
      }
    }
    return target;
  }
  // unit testing (not graded)
  public static void main(String[] args){
    Board b = new Board(randomTiles(3));
    StdOut.print(b);
    StdOut.println("Manhattan: " + b.manhattan());
    StdOut.println("Hamming: " + b.hamming());
    StdOut.println("Printing neighboring");
    for (Board nb : b.neighbors()) {
      StdOut.print(nb);
      StdOut.println("Manhattan: " + nb.manhattan());
      StdOut.println("Hamming: " + nb.hamming());
    }
  }
}
