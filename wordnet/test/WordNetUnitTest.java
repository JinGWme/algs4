

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WordNetUnitTest {

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

    @Test
    void testOutCast5() {
        WordNet net = new WordNet("data/synsets.txt", "data/hypernyms.txt");
        Outcast cast = new Outcast(net);
        String[] outcast5 = {"horse", "zebra", "cat", "bear", "table"};
        String out = cast.outcast(outcast5);
        Assertions.assertEquals("table", out);
    }

    @Test
    void testOutCast8() {
        WordNet net = new WordNet("data/synsets.txt", "data/hypernyms.txt");
        Outcast cast = new Outcast(net);
        String[] outcast8 = {"water", "soda", "bed", "orange_juice", "milk", "apple_juice", "tea", "coffee"};
        Assertions.assertEquals("bed", cast.outcast(outcast8));
    }

    @Test
    void testOutCast11() {
        WordNet net = new WordNet("data/synsets.txt", "data/hypernyms.txt");
        Outcast cast = new Outcast(net);
        String[] outcast11 = {"apple", "pear", "peach", "banana", "lime", "lemon", "blueberry",
                                "strawberry", "mango", "watermelon", "potato"};
        Assertions.assertEquals("potato", cast.outcast(outcast11));

    }

    @Test
    void testSAPMultiNoun() {
        WordNet net = new WordNet("data/synsets.txt", "data/hypernyms.txt");
        Assertions.assertEquals("whole unit", net.sap("Hippeastrum_puniceum", "ramequin"));
    }

    @Test
    void testInvalidCycle() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new WordNet("data/synsets3.txt", "data/hypernyms3InvalidCycle.txt");
        });
    }

    @Test
    void testTwoRoos() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new WordNet("data/synsets3.txt", "data/hypernyms3InvalidTwoRoots.txt");
        });
    }
}
