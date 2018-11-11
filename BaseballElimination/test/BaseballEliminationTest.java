import edu.princeton.cs.algs4.StdOut;
import org.junit.jupiter.api.Test;

class BaseballEliminationTest {
    private static final String teams4 = "/home/marcelo/Coding/Learning/Algorithms/BaseballElimination/resources/teams4.txt";

    @Test
    public void testTeams4() {
        BaseballElimination division = new BaseballElimination(teams4);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}