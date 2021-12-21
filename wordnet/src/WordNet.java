import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    private class Vector {
        public int id;
        public String[] nouns;
        public String gloss;
        public Bag<Integer> adj = new Bag<>();
    }

    private int V = 0;  // num of vertex
    private int E = 0;  // num of edge
    private final List<Vector> graph = new ArrayList<>();

    private Map<String, Integer> nounMap = new TreeMap<>();

    
    // constructor takes the name of the two input files
    public WordNet(String synset, String hypernyms) {
        if (synset == null) throw new IllegalArgumentException();
        if (hypernyms == null) throw new IllegalArgumentException();

        // read synset
        In synsetIn = new In(synset);
        while(synsetIn.hasNextLine()) {
            String[] line = synsetIn.readLine().split(",");
            Vector v = new Vector();
            v.id = Integer.parseInt(line[0]);
            if (v.id != this.V++) throw new InputMismatchException();
            v.nouns = line[1].split(" ");
            for (String noun : v.nouns) 
                nounMap.put(noun, v.id);
            v.gloss = line[2];
            graph.add(v);
        }

        // read hypernyms
        In hypernymIn = new In(hypernyms);
        while(hypernymIn.hasNextLine()) {
            String[] line = hypernymIn.readLine().split(",");
            Vector v = graph.get(Integer.parseInt(line[0]));
            for (int i = 1; i < line.length; i++) {
                v.adj.add(Integer.parseInt(line[i]));
                this.E++;
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        if (nounMap.keySet().contains(word)) return true;
        return false;
    }

    // idstance between nounA and nounB 
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        return 0;

    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path
    public String sap(String nounA, String nounB){
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        return null;
    }


    // do unit testing of this class
    public static void main(String[] args) {

        WordNet wn = new WordNet("data/synsets.txt", "data/hypernyms.txt");
    }
}