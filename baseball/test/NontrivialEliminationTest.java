import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NontrivialEliminationTest {
    @Test
    public void testNontrivialElimination() {
        BaseballElimination elimination = new BaseballElimination("/home/wangj/works/algs4/baseball/data/teams4.txt");
        Assertions.assertEquals(true, elimination.isEliminated("Philadelphia"));
    }
    
}
