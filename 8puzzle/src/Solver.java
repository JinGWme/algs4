import java.util.Comparator;
import java.util.Iterator;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    // private static final Comparator<SearchNode> HAMMINGCOMP = new Comparator<SearchNode>() {

    //     @Override
    //     public int compare(Solver.SearchNode arg0, Solver.SearchNode arg1) {
    //         int weight0 = arg0.board.hamming() + arg0.move;
    //         int weight1 = arg1.board.hamming() + arg1.move;
    //         if (weight0 < weight1)
    //             return -1;
    //         else if (weight0 > weight1)
    //             return 1;
    //         else
    //             return 0;
    //     }
    // };

    private static final Comparator<SearchNode> MANHATTANCOMP = new Comparator<SearchNode>() {

        @Override
        public int compare(Solver.SearchNode arg0, Solver.SearchNode arg1) {
            int weight0 = arg0.board.manhattan() + arg0.move;
            int weight1 = arg1.board.manhattan() + arg1.move;
            if (weight0 < weight1)
                return -1;
            else if (weight0 > weight1)
                return 1;
            else
                return 0;
        }

    };
    private MinPQ<SearchNode> pq;

    private class SearchNode {
        private final Board board;
        private final int move;
        private final SearchNode previous;

        private SearchNode(Board board, int move, SearchNode previous) {
            this.board = board;
            this.move = move;
            this.previous = previous;
        }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        MinPQ<SearchNode> pqa = new MinPQ<SearchNode>(MANHATTANCOMP);
        MinPQ<SearchNode> pqb = new MinPQ<SearchNode>(MANHATTANCOMP);

        pqa.insert(new SearchNode(initial, 0, null));
        pqb.insert(new SearchNode(initial.twin(), 0, null));
        while (true) {
            if (pqa.min().board.isGoal()) {
                this.pq = pqa;
                break;
            }
            SearchNode nodeA = pqa.delMin();
            for (Board neighbor : nodeA.board.neighbors()) {
                if (nodeA.previous != null && neighbor.equals(nodeA.previous.board))
                    continue;
                pqa.insert(new SearchNode(neighbor, nodeA.move + 1, nodeA));
            }

            if (pqb.min().board.isGoal()) {
                this.pq = null;
                break;
            }
            SearchNode nodeB = pqb.delMin();
            for (Board neighbor : nodeB.board.neighbors()) {
                if (nodeB.previous != null && neighbor.equals(nodeB.previous.board))
                    continue;
                pqb.insert(new SearchNode(neighbor, nodeB.move + 1, nodeB));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (pq == null)
            return false;
        return pq.min().board.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        return pq.min().move;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        return new Iterable<Board>() {

            @Override
            public Iterator<Board> iterator() {
                SearchNode node = pq.min();
                Stack<Board> s = new Stack<>();

                while (node != null) {
                    s.push(node.board);
                    node = node.previous;
                }
                return s.iterator();
            }
        };
    }

    // test client (see below)
    public static void main(String[] args) {
        StdOut.println();
    }
}
