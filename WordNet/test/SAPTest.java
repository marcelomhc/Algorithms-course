import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SAPTest {

    @Test
    public void testDigrahp1() {
        SAP sap = getSAP("digraph1.txt");

        assertEquals(4, sap.length(3, 11));
        assertEquals(1, sap.ancestor(3, 11));

        assertEquals(3, sap.length(9, 12));
        assertEquals(5, sap.ancestor(9, 12));

        assertEquals(4, sap.length(7, 2));
        assertEquals(0, sap.ancestor(7, 2));

        assertEquals(-1, sap.length(1, 6));
        assertEquals(-1, sap.ancestor(1, 6));

        assertEquals(2, sap.length(4, 3));
        assertEquals(1, sap.ancestor(3, 4));

        assertEquals(1, sap.length(10, 11));
        assertEquals(10, sap.ancestor(10, 11));

        assertEquals(0, sap.length(1, 1));
        assertEquals(1, sap.ancestor(1, 1));

        assertEquals(0, sap.length(List.of(1, 2), List.of(1, 5)));
        assertEquals(1, sap.ancestor(List.of(1, 2), List.of(1, 5)));
    }

    @Test
    public void testDigrahp2() {
        SAP sap = getSAP("digraph2.txt");

        assertEquals(2, sap.length(1, 5));
        assertEquals(0, sap.ancestor(1, 5));

        assertEquals(2, sap.length(1, 3));
        assertEquals(3, sap.ancestor(1, 3));

    }

    @Test
    public void testDigrahp3() {
        SAP sap = getSAP("digraph3.txt");

        assertEquals(4, sap.length(0, 7));
    }

    @Test
    public void testDigrahp5() {
        SAP sap = getSAP("digraph5.txt");

        assertEquals(4, sap.length(11, 9));
    }

    @Test
    public void testDigrahp6() {
        SAP sap = getSAP("digraph6.txt");

        assertEquals(2, sap.length(5, 3));
    }

    @Test
    public void testDigrahp9() {
        SAP sap = getSAP("digraph9.txt");

        assertEquals(2, sap.length(1, 6));
    }

        private SAP getSAP(String file) {
        In in = new In("/home/marcelo/Coding/Learning/Algorithms/WordNet/resources/" + file);
        Digraph digraph = new Digraph(in);
        return new SAP(digraph);
    }

    @Test
    public void testDigraphWordnet() {
        SAP sap = getSAP("digraph-wordnet.txt");

        assertEquals(8, sap.length(49485, 38982));
        assertEquals(8, sap.length(List.of(49485), List.of(38982)));

        assertEquals(13, sap.length(List.of(18395), List.of(49224)));
        assertEquals(10, sap.length(List.of(43416), List.of(41812, 53506)));
        assertEquals(6, sap.length(List.of(30121, 42237), List.of(59057)));
        assertEquals(15, sap.length(List.of(13197, 71017), List.of(36369, 48457)));
        assertEquals(-1, sap.length(List.of(), List.of(11666, 27253, 55970, 72627, 73960)));
    }

    @Test
    public void testExceptions() {
        SAP sap = getSAP("digraph1.txt");

        assertThrows(IllegalArgumentException.class, () -> sap.length(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> sap.length(0, -1));
        assertThrows(IllegalArgumentException.class, () -> sap.length(13, 0));
        assertThrows(IllegalArgumentException.class, () -> sap.length(0, 13));

        assertThrows(IllegalArgumentException.class, () -> sap.length(List.of(4, 6, 9), null));
        assertThrows(IllegalArgumentException.class, () -> sap.length(null, List.of(0, 2, 3, 5, 7, 8, 10)));
        assertThrows(IllegalArgumentException.class, () -> sap.length(null, null));
        assertThrows(IllegalArgumentException.class, () -> sap.length(List.of(4, -1, 6, 9), List.of(0, 2, 3, 5, 7, 8, 10)));
        assertThrows(IllegalArgumentException.class, () -> sap.length(List.of(4, 6, 9), List.of(0, 2, -1, 3, 5, 7, 8, 10)));
        assertThrows(IllegalArgumentException.class, () -> sap.length(List.of(4, 6, 9, 13), List.of(0, 2, 3, 5, 7, 8, 10)));
        assertThrows(IllegalArgumentException.class, () -> sap.length(List.of(4, 6, 9), List.of(0, 2, 3, 5, 7, 8, 10, 13)));

    }

    @Test
    public void testCreateTwoAtSameTime() {
        SAP sap1 = getSAP("digraph1.txt");
        SAP sap2 = getSAP("digraph2.txt");

        assertEquals(1, sap1.length(1, 5));
        assertEquals(1, sap1.ancestor(1, 5));
        assertEquals(2, sap2.length(1, 5));
        assertEquals(0, sap2.ancestor(1, 5));
    }
}