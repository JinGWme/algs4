import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

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

    private Stack<Board> solution;
    private boolean solvable;
    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        MinPQ<SearchNode> pqa = new MinPQ<SearchNode>(MANHATTANCOMP);
        MinPQ<SearchNode> pqb = new MinPQ<SearchNode>(MANHATTANCOMP);
        solution = new Stack<>();

        pqa.insert(new SearchNode(initial, 0, null));
        pqb.insert(new SearchNode(initial.twin(), 0, null));
        while (true) {
            if (pqa.min().board.isGoal()) {
                // this.pq = pqa;
                solvable = true;
                moves = pqa.min().move;
                break;
            }
            SearchNode nodeA = pqa.delMin();
            for (Board neighbor : nodeA.board.neighbors()) {
                if (nodeA.previous != null && neighbor.equals(nodeA.previous.board))
                    continue;
                pqa.insert(new SearchNode(neighbor, nodeA.move + 1, nodeA));
            }

            if (pqb.min().board.isGoal()) {
                // this.pq = null;
                solvable = false;
                moves = -1;
                break;
            }
            SearchNode nodeB = pqb.delMin();
            for (Board neighbor : nodeB.board.neighbors()) {
                if (nodeB.previous != null && neighbor.equals(nodeB.previous.board))
                    continue;
                pqb.insert(new SearchNode(neighbor, nodeB.move + 1, nodeB));
            }
        }
        if (solvable) {
            SearchNode node = pqa.min();
            while (node != null) {
                solution.push(node.board);
                node = node.previous;
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solvable) return solution;
        else return null;
    }
}
