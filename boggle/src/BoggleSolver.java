import java.util.stream.StreamSupport;

import edu.princeton.cs.algs4.TrieSET;
import edu.princeton.cs.algs4.TrieST;

public class BoggleSolver {
    private final TrieSET dictWords;
    private TrieST<Integer> wordScore;
    private boolean[][] visited;
    private final int[] scoreMap = { 0, 0, 0, 1, 1, 2, 3, 5 };

    // Initializes the data structure using the given array of strings as the
    // dictionary
    // (You can assume each word in the dictionary containsonly the upper case
    // letter A through Z)
    public BoggleSolver(String[] dictionary) {
        dictWords = new TrieSET();
        for (String word : dictionary) {
            dictWords.add(word);
        }
    }

    // Returns the set of all valid words in the given Boggle boards, as an Iterable
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        visited = new boolean[board.rows()][board.cols()];
        wordScore = new TrieST<Integer>();
        for (int sx = 0; sx < board.rows(); sx++) {
            for (int sy = 0; sy < board.cols(); sy++) {
                dfsOnBoard(board, sx, sy, new StringBuilder());
            }
        }
        return wordScore.keys();
    }

    // Returns the score of the given word if it is in the dictionary, zero
    // otherwise
    // (You can assuem the word contains only the upper case letter A through Z)
    public int scoreOf(String word) {
        if (wordScore == null)
            return 0;
        if (wordScore.contains(word))
            return wordScore.get(word);
        else
            return 0;
    }

    // visit a node in boggle board in dfs fashion
    // mark it as visited
    // if the word exist in dictionary, comput the score and save it
    private void dfsOnBoard(BoggleBoard b, int x, int y, StringBuilder word) {
        if (x >= b.rows() || x < 0)
            return;
        if (y >= b.cols() || y < 0)
            return;
        if (visited[x][y])
            return;

        // grow by one letter
        visited[x][y] = true;
        if (b.getLetter(x, y) == 'Q')
            word.append("QU");
        else
            word.append(b.getLetter(x, y));
        // calc score if match dictionary
        if (word.length() >= 3 && dictWords.contains(word.toString())) {
            int score = word.length() >= 8 ? 11 : scoreMap[word.length()];
            wordScore.put(word.toString(), score);
        }
        // dfs to find next char that is not visited
        if (word.length() < 3
                || StreamSupport
                        .stream(dictWords.keysWithPrefix(word.toString()).spliterator(), false)
                        .count() > 0) {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    dfsOnBoard(b, i, j, word);
                }
            }
        }
        if (b.getLetter(x, y) == 'Q')
            word.deleteCharAt(word.length() - 1);
        word.deleteCharAt(word.length() - 1);
        visited[x][y] = false;
    }

}
