import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OutcastTest {

    private static String BASE = "/home/marcelo/Coding/Learning/Algorithms/WordNet/resources/";
    private static String SYNSETS = "/home/marcelo/Coding/Learning/Algorithms/WordNet/resources/synsets.txt";
    private static String HYPERNYMS = "/home/marcelo/Coding/Learning/Algorithms/WordNet/resources/hypernyms.txt";

    private static WordNet wordnet;
    private static Outcast outcast;

    @BeforeAll
    public static void setup() {
        wordnet = new WordNet(SYNSETS, HYPERNYMS);
        outcast = new Outcast(wordnet);
    }

    @Test
    public void testOutcast() {
        String[] words5 = testGeneral("outcast5.txt");
        String[] words8 = testGeneral("outcast8.txt");
        String[] words11 = testGeneral("outcast11.txt");

        assertEquals("table", outcast.outcast(words5));
        assertEquals("bed", outcast.outcast(words8));
        assertEquals("potato", outcast.outcast(words11));

    }

    private String[] testGeneral(String file) {
        In in = new In(BASE + file);
        return in.readAllStrings();
    }

}