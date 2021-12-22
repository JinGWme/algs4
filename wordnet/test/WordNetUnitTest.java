

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.StdOut;

class WordNetUnitTest {

    // private final Calculator calculator = new Calculator();

    @Test
    void createWordNet() {
        WordNet net = new WordNet("data/synsets.txt", "data/hypernyms.txt");
    }

    @Test 
    void testWordNetNounNum() {
        WordNet net = new WordNet("data/synsets.txt", "data/hypernyms.txt");
        Assertions.assertEquals(119188, net.nouns().spliterator().getExactSizeIfKnown());
    }
    @Test
    void testWordNetHasNoun() {
        WordNet net = new WordNet("data/synsets.txt", "data/hypernyms.txt");
        Assertions.assertTrue(net.isNoun("hood"));
    }
    @Test
    void testWordNetHasNoNoun() {
        WordNet net = new WordNet("data/synsets.txt", "data/hypernyms.txt");
        Assertions.assertFalse(net.isNoun("aakdjflaksjdfoiwe"));
    }

    @Test
    void testWordNetLengthOK() {
        WordNet net = new WordNet("data/synsets.txt", "data/hypernyms.txt");
        Assertions.assertEquals(3, net.distance("9/11", "attack"));
    }

    @Test
    void testWordNetLengthNOK() {
        WordNet net = new WordNet("data/synsets.txt", "data/hypernyms.txt");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            net.distance("9/11", "aaaaaaaaaa");
        });
    }

    @Test
    void testWordNetSAPOK() {
        WordNet net = new WordNet("data/synsets.txt", "data/hypernyms.txt");
        Assertions.assertEquals("blood", net.sap("AB", "cord_blood"));
    }

    @Test
    void testWordNetSAPNOK() {
        WordNet net = new WordNet("data/synsets.txt", "data/hypernyms.txt");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            net.sap("AB", "adafafsfa");        
        });

    }

}
