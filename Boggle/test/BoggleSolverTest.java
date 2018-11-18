import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoggleSolverTest {

    private static final String DICTIONARY_ALGS4 = "/home/marcelo/Coding/Learning/Algorithms/Boggle/resources/dictionary-algs4.txt";
    private static final String DICTIONARY_YAWL = "/home/marcelo/Coding/Learning/Algorithms/Boggle/resources/dictionary-yawl.txt";

    private static final String BOARD_4_X_4 = "/home/marcelo/Coding/Learning/Algorithms/Boggle/resources/board4x4.txt";
    private static final String BOARD_Q = "/home/marcelo/Coding/Learning/Algorithms/Boggle/resources/board-q.txt";
    private static final String BOARD_POINTS = "/home/marcelo/Coding/Learning/Algorithms/Boggle/resources/board-points%d.txt";

    @Test
    public void test4x4Board() {
        In in = new In(DICTIONARY_ALGS4);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(BOARD_4_X_4);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        assertEquals(33, score);
    }

    @Test
    public void testQBoard() {
        In in = new In(DICTIONARY_ALGS4);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(BOARD_Q);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        assertEquals(84, score);
    }

    @Test
    public void testBoard0() {
        testYawlDictFor(0);
    }

    @Test
    public void testBoard1() {
        testYawlDictFor(1);
    }

    @Test
    public void testBoard2() {
        testYawlDictFor(2);
    }

    @Test
    public void testBoard3() {
        testYawlDictFor(3);
    }

    @Test
    public void testBoard4() {
        testYawlDictFor(4);
    }

    @Test
    public void testBoard5() {
        testYawlDictFor(5);
    }

    @Test
    public void testBoard100() {
        testYawlDictFor(100);
    }

    @Test
    public void testBoard200() {
        testYawlDictFor(200);
    }

    @Test
    public void testBoard300() {
        testYawlDictFor(300);
    }

    @Test
    public void testBoard400() {
        testYawlDictFor(400);
    }

    @Test
    public void testBoard500() {
        testYawlDictFor(500);
    }

    @Test
    public void testBoard750() {
        testYawlDictFor(750);
    }

    @Test
    public void testBoard777() {
        testYawlDictFor(777);
    }

    @Test
    public void testBoard1000() {
        testYawlDictFor(1000);
    }

    @Test
    public void testBoard1111() {
        testYawlDictFor(1111);
    }

    @Test
    public void testBoard1250() {
        testYawlDictFor(1250);
    }

    @Test
    public void testBoard1500() {
        testYawlDictFor(1500);
    }

    @Test
    public void testBoard2000() {
        testYawlDictFor(2000);
    }

    @Test
    public void testBoard4410() {
        testYawlDictFor(4410);
    }

    @Test
    public void testBoard4527() {
        testYawlDictFor(4527);
    }

    @Test
    public void testBoard4540() {
        testYawlDictFor(4540);
    }

    @Test
    public void testBoard13464() {
        testYawlDictFor(13464);
    }

    @Test
    public void testBoard26539() {
        testYawlDictFor(26539);
    }

    private void testYawlDictFor(int points) {
        In in = new In(DICTIONARY_YAWL);
        BoggleSolver solver = new BoggleSolver(in.readAllStrings());
        BoggleBoard board = new BoggleBoard(String.format(BOARD_POINTS, points));
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            score += solver.scoreOf(word);
        }
        assertEquals(points, score);
    }
}