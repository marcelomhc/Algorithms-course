import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WordNetTest {
    private static String SYNSETS = "/home/marcelo/Coding/Learning/Algorithms/WordNet/resources/synsets.txt";
    private static String HYPERNYMS = "/home/marcelo/Coding/Learning/Algorithms/WordNet/resources/hypernyms.txt";
    private static String SYNSETS15 = "/home/marcelo/Coding/Learning/Algorithms/WordNet/resources/synsets15.txt";
    private static String HYPERNYMS15 = "/home/marcelo/Coding/Learning/Algorithms/WordNet/resources/hypernyms15Path.txt";
    private static String SYNSETS_INVALID = "/home/marcelo/Coding/Learning/Algorithms/WordNet/resources/synsets3.txt";
    private static String HYPERNYMS_INVALID = "/home/marcelo/Coding/Learning/Algorithms/WordNet/resources/hypernyms3InvalidTwoRoots.txt";

    private static WordNet wordNet;

    @BeforeAll
    public static void setup() {
        wordNet = new WordNet(SYNSETS, HYPERNYMS);
    }

    @Test
    public void testFarDistance() {
        assertEquals(23, wordNet.distance("white_marlin", "mileage"));
        assertEquals(33, wordNet.distance("Black_Plague", "black_marlin"));
        assertEquals(27, wordNet.distance("American_water_spaniel", "histology"));
        assertEquals(29, wordNet.distance("Brown_Swiss", "barrel_roll"));
    }

    @Test
    public void testAncestor() {
        assertEquals("physical_entity", wordNet.sap("individual", "edible_fruit"));
        assertEquals(7, wordNet.distance("individual", "edible_fruit"));
    }

    @Test
    public void testSyntest15() {
        new WordNet(SYNSETS15, HYPERNYMS15);
    }

    @Test
    public void testSyntestInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new WordNet(SYNSETS_INVALID, HYPERNYMS_INVALID));
    }
}