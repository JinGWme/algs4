import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.xml.transform.sax.TemplatesHandler;

import edu.princeton.cs.algs4.In;

public class BaseballElimination {
    private final String[] names;
    private final Map<String, Integer> nameMap;
    private final int[] wins;
    private final int[] losses;
    private final int[] remains;
    private final int[][] against;

    private int id(String team) {
        try {
            return nameMap.get(team);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Team " + team + " not part of league");
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
        final int teamNum = numberOfTeams();
        final int GAME_VERTEX_NUM = (teamNum-2)*(teamNum-1)/2;
        final int TEAM_VERTEX_NUM = (teamNum-1);
        int networkSize = 2 + GAME_VERTEX_NUM + TEAM_VERTEX_NUM;
        FlowNetwork network = new FlowNetwork(networkSize);

        // Add edges between vritual source and game vertices
        int gameVertexId = 1;
        for (int i = 0; i < teamNum; i++) {
            if (team.equals(names[i])) continue;
            for (int j = i+1; j < teamNum; j++) {
                if (team.equals(names[j])) continue;
                FlowEdge edge = new FlowEdge(0, gameVertexId++, against(names[i], names[j]));
                network.addEdge(edge);
            }
        }

        // Add edge Between game vertices and team vertices
        int teamVertexId = 1 + GAME_VERTEX_NUM;
        gameVertexId = 1;
        for (int i = 0; i < teamNum; i++) {
            if (team.equals(names[i])) continue;
            for (int j = i+1; j < teamNum; j++) {
                if (team.equals(names[j])) continue;
                FlowEdge edge = new FlowEdge(gameVertexId++, teamVertexId++, Integer.MAX_VALUE);
                network.addEdge(edge);
            }
        }
        teamVertexId = 1 + GAME_VERTEX_NUM;
        gameVertexId = 1;
        for (int i = 0; i < teamNum-1; i++) {
            if (team.equals(names[i])) continue;
            for (int j = i+1; j < teamNum-1; j++) {
                if (team.equals(names[j])) continue;
                FlowEdge edge = new FlowEdge(gameVertexId++, teamVertexId++, Integer.MAX_VALUE);
                network.addEdge(edge);
            }
        }

        // Add edges between  team vertices and virtual sink
        teamVertexId = 1 + GAME_VERTEX_NUM;
        for (int i = 0; i < (teamNum-1); i++) { 
            if (team.equals(names[i])) continue;
            FlowEdge edge = new FlowEdge(teamVertexId++, networkSize-1, wins(team)+remaining(team)-wins(names[i]));
            network.addEdge(edge);
        }

        return network;
    }

    private boolean isNontrivialEliminated(String team) {
        FlowNetwork network = buildNetwork(team);
        return false;

    }
    // is given team elimitated?
    public boolean isEliminated(String team) {
        if (isSimpleElimated(team)) return true;
        return isNontrivialEliminated(team);
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return null;
    }
}
