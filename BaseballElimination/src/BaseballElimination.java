import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseballElimination {
    private List<String> teams;
    private int[] wins;
    private int[] losses;
    private int[] left;
    private int[][] games;
    private Map<String, FlowNetwork> graphs;

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

        graphs = new HashMap<>();
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

        FlowNetwork flowNetwork = getFlowNetworkFor(team);
        new FordFulkerson(flowNetwork, 0, flowNetwork.V()-1);

        for (FlowEdge edge : flowNetwork.adj(0)) {
            if (edge.capacity() > edge.flow()) return true;
        }

        return false;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        checkTeam(team);
        Set<String> certificate = getTrivialEliminations(team);
        if(!certificate.isEmpty()) return certificate;

        int gameNodes = (wins.length*(wins.length-1))/2;
        FlowNetwork flow = graphs.get(team);
//        for(FlowEdge edge : flow.adj(0)) {
//            if (edge.capacity() > edge.flow()) {
//                for(FlowEdge e : flow.adj(edge.to())) {
//                    if (!e.equals(edge)) {
//                        certificate.add(teams.get(e.to() - gameNodes - 1));
//                    }
//                }
//            }
//        }

        double value = 0;
        for(FlowEdge e : flow.adj(0)) {
            value += e.flow();
        }
        FordFulkerson ff = new FordFulkerson(flow, 0, flow.V()-1);
        for(int i = 0; i < numberOfTeams(); i++) {
            if(ff.inCut(i+gameNodes+1)) {
                certificate.add(teams.get(i));
            }
        }

        if(!certificate.isEmpty()) return certificate;
        return null;
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

    private FlowNetwork getFlowNetworkFor(String team) {
        if (!graphs.containsKey(team)) {
            int numOfTeams = wins.length;
            int referenceIdx = getTeamIndex(team);

            int gameNodes = (numOfTeams*(numOfTeams-1))/2;
            int numOfEdges = gameNodes + numOfTeams + 2;
            FlowNetwork flowNetwork = new FlowNetwork(numOfEdges);
            int sourceVertex = 0;
            int sinkVertex = numOfEdges - 1;

            for(int i = 0; i < numOfTeams; i++) {
                if(i == referenceIdx) continue;
                for(int j = i+1; j < numOfTeams; j++) {
                    if (j == referenceIdx) continue;

                    int gameVertex = gameNodes - ((numOfTeams-i)*(numOfTeams-i-1))/2 + j - i;
                    flowNetwork.addEdge(new FlowEdge(sourceVertex, gameVertex, games[i][j]));
                    flowNetwork.addEdge(new FlowEdge(gameVertex, gameNodes + 1 + i, Double.POSITIVE_INFINITY));
                    flowNetwork.addEdge(new FlowEdge(gameVertex, gameNodes + 1 + j, Double.POSITIVE_INFINITY));
                }
                flowNetwork.addEdge(new FlowEdge(1 + gameNodes + i, sinkVertex, wins[referenceIdx] + left[referenceIdx] - wins[i]));
            }
            graphs.put(team, flowNetwork);
        }
        return graphs.get(team);
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

    private int getTeamIndex(String team) {
        return teams.indexOf(team);
    }

    private void checkTeam(String team) {
        if(!teams.contains(team)) {
            throw new IllegalArgumentException("Team is invalid!");
        }
    }
}
