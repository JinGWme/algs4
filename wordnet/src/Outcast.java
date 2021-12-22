public class Outcast {

    private final WordNet wordnet;

    // constructor taks a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of Wordnet nouns, return an outcast
    public String outcast(String[] nouns) {
        String outcast = nouns[0];
        int maxDistance = 0;

        for (String nounA : nouns) {
            int distance = 0;
            for (String nounB : nouns) {
                distance += wordnet.distance(nounA, nounB);
            }
            if (distance > maxDistance) {
                maxDistance = distance;
                outcast = nounA;
            }
        }
        return outcast;
    }

    // see test client below
    public static void main(String[] args) {
        // Test will be done by JUnit

    }

}
