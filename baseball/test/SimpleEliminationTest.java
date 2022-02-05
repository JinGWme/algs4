import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimpleEliminationTest {
    @Test
    public void testSimpleEliminated() {
        BaseballElimination elimination = new BaseballElimination("/home/wangj/works/algs4/baseball/data/teams4.txt");
        Assertions.assertEquals(true, elimination.isEliminated("Montreal"));
        Assertions.assertEquals(false, elimination.isEliminated("New_York"));
        Assertions.assertEquals(false, elimination.isEliminated("Atlanta"));
    }
    
}
