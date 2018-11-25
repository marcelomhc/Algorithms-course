import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CircularSuffixArrayTest {

    @Test
    public void testAbracadabra() {
        String s = "ABRACADABRA!";
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(s);
        int[] solution = new int[]{11, 10, 7, 0, 3, 5, 8, 1, 4, 6, 9, 2};

        for (int i = 0; i < s.length(); i++) {
            assertEquals(solution[i], circularSuffixArray.index(i));
        }
    }

    @Test
    public void testExceptions() {
        assertThrows(IllegalArgumentException.class, () -> new CircularSuffixArray(null));
        CircularSuffixArray suffixArray = new CircularSuffixArray("Test");
        assertThrows(IllegalArgumentException.class, () -> suffixArray.index(-1));
        assertThrows(IllegalArgumentException.class, () -> suffixArray.index(5));
    }

    @Test
    public void testAbraAndCadabra() {
        String s = "ABRACADABRA!";
        String s2 = "CADABRA!ABRA";
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(s);
        CircularSuffixArray circularSuffixArray2 = new CircularSuffixArray(s2);


        for (int i = 0; i < s.length(); i++) {
            assertEquals(s2.charAt(circularSuffixArray2.index(i)), s.charAt(circularSuffixArray.index(i)));
        }
    }
}