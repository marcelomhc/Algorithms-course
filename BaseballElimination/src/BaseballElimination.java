import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseballElimination {
    private List<String> teams;
    private int[] wins;
    private int[] losses;
    private int[] left;
    private int[][] games;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int numOfTeams = in.readInt();

        teams = new ArrayList<>(numOfTeams);
        wins = new int[numOfTeams];
        losses = new int[numOfTeams];
        left = new int[numOfTeams];
        games = new int[numOfTeams][numOfTeams];

        for(int i = 0; i < numOfTeams; i++) {
            teams.add(in.readString());
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            left[i] = in.readInt();
            for(int j = 0; j < numOfTeams; j++) {
                games[i][j] = in.readInt();
            }
        }

//        int numOfEdges = (numOfTeams*(numOfTeams+1))/2 + 2;
//        FlowNetwork flowNetwork = new FlowNetwork(numOfEdges);
    }

    // number of teams
    public int numberOfTeams() {
        return teams.size();
    }

    // all teams
    public Iterable<String> teams() {
        return teams;
    }

    // number of wins for given team
    public int wins(String team) {
        checkTeam(team);
        return wins[getTeamIndex(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        checkTeam(team);
        return losses[getTeamIndex(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        checkTeam(team);
        return left[getTeamIndex(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        checkTeam(team1);
        checkTeam(team2);
        return games[getTeamIndex(team1)][getTeamIndex(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        checkTeam(team);

        if(isTriviallyEliminated(team)) {
            return true;
        }

        return false;
    }

    private boolean isTriviallyEliminated(String team) {
        int maxWins = wins[getTeamIndex(team)] + left[getTeamIndex(team)];

        for (int i = 0; i < wins.length; i++) {
            if(maxWins < wins[i]) {
                return true;
            }
        }
        return false;
    }

    private Set<String> getTrivialEliminations(String team) {
        int maxWins = wins[getTeamIndex(team)] + left[getTeamIndex(team)];

        Set<String> certificate = new HashSet<>();
        for (int i = 0; i < wins.length; i++) {
            if(maxWins < wins[i]) {
                certificate.add(teams.get(i));
            }
        }
        return certificate;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        checkTeam(team);

        return getTrivialEliminations(team);
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
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

    private int getTeamIndex(String team) {
        return teams.indexOf(team);
    }

    private void checkTeam(String team) {
        if(!teams.contains(team)) {
            throw new IllegalArgumentException("Team is invalid!");
        }
    }
}
