import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
    private final String[] names;
    private final Map<String, Integer> nameMap;
    private final int[] wins;
    private final int[] losses;
    private final int[] remains;
    private final int[][] against;
    private int networkSize;
    private int remainingGame;
    private FordFulkerson ff;
    private VertexType[] v;

    private int id(String team) {
        try {
            return nameMap.get(team);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Team " + team + " not part of division");
        }
    }
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for(String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.print("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
    // create a baseball division from given file name in format specified 
    public BaseballElimination(String filename){
        In in = new In(filename);
        int size = in.readInt();

        nameMap = new TreeMap<>();
        this.names = new String[size];
        this.wins = new int[size];
        this.losses = new int[size];
        this.remains = new int[size];
        this.against = new int[size][size];

        for (int i = 0; i < size; i++) {
            names[i] = in.readString();
            nameMap.put(names[i], i);
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remains[i] = in.readInt();
            for (int j = 0; j < size; j++) {
                against[i][j] = in.readInt();
            }
        }
    }

    // number of teams
    public int numberOfTeams() {
        return names.length;
    }

    // all teams
    public Iterable<String> teams() {
        return Arrays.asList(names);
    }

    // number of wins for given team
    public int wins(String team) {
        return wins[id(team)];
    }
    
    // number of losses for given team
    public int losses(String team) {
        return losses[id(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return remains[id(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return against[id(team1)][id(team2)];
    }

    // test for any win(x) + r(x) < w(i) where as i is any other team
    private boolean isSimpleElimated(String team) {
        for (String oppo : teams()) {
            if (team.equals(oppo)) continue;
            if (wins(oppo) > wins(team)+remaining(team)) return true;
        }
        return false;        
    }

    private FlowNetwork buildNetwork(String team) {
        // calculate network size to create flow network
        final int teamNum = numberOfTeams();
        final int GAME_VERTEX_NUM = (teamNum-2)*(teamNum-1)/2;
        final int TEAM_VERTEX_NUM = (teamNum-1);
        networkSize = 2 + GAME_VERTEX_NUM + TEAM_VERTEX_NUM;
        FlowNetwork network = new FlowNetwork(networkSize);
        remainingGame = 0;

        // starting point to help creat network
        final int S = 0;                        // source vertex id
        final int T = networkSize-1;            // sink vertex id
        final int G0 = 1;                       // starting index for game vertices
        final int T0 = G0+GAME_VERTEX_NUM;      // starting index for team vertices
        final int CTEAMID = nameMap.get(team);  // index for team that is being checked

        v = new VertexType[networkSize];

        // Add edges between virtual source and game vertices
        // int gameVertexId = G0;
        for (int i = 0, gameVertexId = G0; i < teamNum; i++) {
            if (i == CTEAMID) continue;
            for (int j = i+1; j < teamNum; j++) {
                if (j == CTEAMID) continue;
                FlowEdge edge = new FlowEdge(S, gameVertexId, against(names[i], names[j]));
                v[gameVertexId++] = new GameVertex(i, j);
                network.addEdge(edge);
                remainingGame += edge.capacity();
            }
        }

        // Add edge Between game vertices and team vertices
        for (int gameVertexId = G0; gameVertexId < T0; gameVertexId++) {
            GameVertex gv = (GameVertex)v[gameVertexId];
            // add edge to two team vertex
            int teamId = gv.a;
            int teamVertexId;
            if (teamId > CTEAMID)   teamVertexId = teamId-1;
            else                    teamVertexId = teamId;
            v[T0+teamVertexId] = new TeamVertex(teamId);
            FlowEdge edge = new FlowEdge(gameVertexId, T0+teamVertexId, Double.POSITIVE_INFINITY);
            network.addEdge(edge);
            teamId = gv.b;
            if (teamId > CTEAMID)   teamVertexId = teamId-1;
            else                    teamVertexId = teamId;
            v[T0+teamVertexId] = new TeamVertex(teamId);
            edge = new FlowEdge(gameVertexId, T0+teamVertexId, Double.POSITIVE_INFINITY);
            network.addEdge(edge);
        }
        
        for (int teamVertexId = T0; teamVertexId < T; teamVertexId++) {
            TeamVertex tv = (TeamVertex)v[teamVertexId];
            int capacity = wins[CTEAMID]+remains[CTEAMID]-wins[tv.t];
            FlowEdge edge = new FlowEdge(teamVertexId, T, capacity);
            network.addEdge(edge);
        }
        return network;
    }

    private boolean isNontrivialEliminated(String team) {
        FlowNetwork network = buildNetwork(team);
        ff = new FordFulkerson(network, 0, networkSize-1);
        if (ff.value() == remainingGame) return false;
        return true;

    }
    // is given team elimitated?
    public boolean isEliminated(String team) {
        if (isSimpleElimated(team)) return true;
        return isNontrivialEliminated(team);
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        List<String> inCut = new ArrayList<>();
        if (isSimpleElimated(team)) {
            for (String oppo : teams()) {
                if (oppo.equals(team)) continue;
                if (wins(oppo) > wins(team)+remaining(team)) inCut.add(oppo);
            }
            return inCut;
        }
        else if (isNontrivialEliminated(team)) {
            final int teamNum = numberOfTeams();
            final int GAME_VERTEX_NUM = (teamNum-2)*(teamNum-1)/2;
            final int T0 = 1 + GAME_VERTEX_NUM;      // starting index for team vertices
            
            for (int i = T0; i < T0+teamNum-1; i++) {
                // if (team.equals(names[i])) continue;
                if (ff.inCut(i))  {
                    inCut.add(names[((TeamVertex)v[i]).t]);
                }
            }
            return inCut;
        }
        return null;
    }

    private class VertexType {};
    private class GameVertex extends VertexType{
        final int a;
        final int b;
        public GameVertex(int a, int b) {this.a = a; this.b = b;}
    }
    private class TeamVertex extends VertexType {
        final int t;
        TeamVertex(int t) {this.t = t;}
    }
}
