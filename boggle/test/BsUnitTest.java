import javax.lang.model.SourceVersion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BsUnitTest {

    @Test
    public void testTrue() {
        Assertions.assertTrue(true);
    }

    @Test
    public void test4x4() {
        In dictIn = new In("data/dictionary-algs4.txt");

        String[] dictionary = dictIn.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("data/board4x4.txt");
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        Assertions.assertEquals(33, score);

    }
    @Test
    public void testQ() {
        In dictIn = new In("data/dictionary-algs4.txt");

        String[] dictionary = dictIn.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("data/board-q.txt");
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        Assertions.assertEquals(84, score);

    }
    
}
