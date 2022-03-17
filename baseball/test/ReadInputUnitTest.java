import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReadInputUnitTest {

    @Test
    public void testReadTeam4() {
        BaseballElimination elimination = new BaseballElimination("data/teams4.txt");
        Assertions.assertEquals(4, elimination.numberOfTeams());
        Assertions.assertEquals(83, elimination.wins("Atlanta"));
        Assertions.assertEquals(79, elimination.losses("Philadelphia"));
        Assertions.assertEquals(6, elimination.remaining("New_York"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            elimination.remaining("team");
        });
    }

}
