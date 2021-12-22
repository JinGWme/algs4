import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {

    private Digraph G;
    private Map<String, Set<Integer>> nounMap;
    private List<String[]> idMap;

    // constructor takes the name of the two input files
    public WordNet(String synset, String hypernyms) {
        if (synset == null)
            throw new IllegalArgumentException();
        if (hypernyms == null)
            throw new IllegalArgumentException();

        // Out out = new Out("data/output.txt");
        // read synset
        nounMap = new HashMap<>();
        idMap = new ArrayList<>();
        int v = 0;
        In synsetIn = new In(synset);
        while (synsetIn.hasNextLine()) {
            String[] line = synsetIn.readLine().split(",");
            if (Integer.parseInt(line[0]) != v)
                throw new InputMismatchException("Synset id not expected");
            String[] nouns = line[1].split(" ");
            for (String noun : nouns) {
                if (nounMap.containsKey(noun)) {
                    Set<Integer> ids = nounMap.get(noun);
                    ids.add(v);
                    // out.println("Adding noun map " + noun + " to " + v);
                } else {
                    Set<Integer> ids = new TreeSet<>();
                    ids.add(v);
                    nounMap.put(noun, ids);
                }
            }
            idMap.add(nouns);
            v++;
        }

        G = new Digraph(v);

        // read hypernyms
        In hypernymIn = new In(hypernyms);
        while (hypernymIn.hasNextLine()) {
            String[] line = hypernymIn.readLine().split(",");
            int hypo = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                G.addEdge(hypo, Integer.parseInt(line[i]));
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        if (nounMap.keySet().contains(word))
            return true;
        return false;
    }

    // idstance between nounA and nounB
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        SAP sap = new SAP(G);
        int length = sap.length(nounMap.get(nounA), nounMap.get(nounB));
        return length;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA
    // and nounB
    // in a shortest ancestral path
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        SAP sap = new SAP(G);
        int ancestor = sap.ancestor(nounMap.get(nounA), nounMap.get(nounB));
        return idMap.get(ancestor)[0];
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}