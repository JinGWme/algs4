import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NontrivialEliminationTest {
    @Test
    public void testTeam4Elimination() {
        BaseballElimination elimination = new BaseballElimination("/home/wangj/works/algs4/baseball/data/teams4.txt");
        Assertions.assertEquals(true, elimination.isEliminated("Philadelphia"));
        Assertions.assertEquals(true, elimination.isEliminated("Montreal"));
        
        Assertions.assertEquals(false, elimination.isEliminated("New_York"));
        Assertions.assertEquals(false, elimination.isEliminated("Atlanta"));

    }

    @Test
    public void testTeam4CutP() {
        BaseballElimination elimination = new BaseballElimination("/home/wangj/works/algs4/baseball/data/teams4.txt");
        Assertions.assertEquals(true, elimination.isEliminated("Philadelphia"));
        Iterable<String> inCut = elimination.certificateOfElimination("Philadelphia");
        for (String string : inCut) {
            boolean correct = string.equals("Atlanta") || string.equals("New_York");
            Assertions.assertTrue(correct);
        }
    }
    @Test
    public void testTeam4CutM() {
        BaseballElimination elimination = new BaseballElimination("/home/wangj/works/algs4/baseball/data/teams4.txt");
        Assertions.assertEquals(true, elimination.isEliminated("Montreal"));
        Iterable<String> inCut = elimination.certificateOfElimination("Montreal");
        for (String string : inCut) {
            boolean correct = string.equals("Atlanta");
            Assertions.assertTrue(correct);
        }
    }
    @Test
    public void testTeam4aElimination() {
        BaseballElimination elimination = new BaseballElimination("/home/wangj/works/algs4/baseball/data/teams4a.txt");
        Assertions.assertEquals(true, elimination.isEliminated("Ghaddafi"));
        Assertions.assertEquals(false, elimination.isEliminated("Obama"));
        // elimination.isEliminated("New_York");

    }

    @Test
    public void testTeam5Elimination() {
        BaseballElimination elimination = new BaseballElimination("/home/wangj/works/algs4/baseball/data/teams5.txt");
        Assertions.assertEquals(true, elimination.isEliminated("Detroit"));
        Assertions.assertEquals(false, elimination.isEliminated("New_York"));
        Assertions.assertEquals(false, elimination.isEliminated("Baltimore"));
        Assertions.assertEquals(false, elimination.isEliminated("Boston"));
        Assertions.assertEquals(false, elimination.isEliminated("Toronto"));
    }
    
    @Test
    public void testTeam5CutD() {
        BaseballElimination elimination = new BaseballElimination("/home/wangj/works/algs4/baseball/data/teams5.txt");
        // Assertions.assertEquals(true, elimination.isEliminated("Montreal"));
        Iterable<String> inCut = elimination.certificateOfElimination("Detroit");
        for (String string : inCut) {
            boolean correct = string.equals("New_York") || string.equals("Baltimore") || string.equals("Boston") || string.equals("Toronto");
            Assertions.assertTrue(correct);
        }
    }
}
