import edu.princeton.cs.algs4.Picture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SeamCarverTest {

    private static final String PATH_IMG_3X4 = "/home/marcelo/Coding/Learning/Algorithms/SeamCarver/resources/3x4.png";
    private static final String PATH_IMG_3X7 = "/home/marcelo/Coding/Learning/Algorithms/SeamCarver/resources/3x7.png";
    private static final String PATH_IMG_10X10 = "/home/marcelo/Coding/Learning/Algorithms/SeamCarver/resources/10x10.png";
    private static final String PATH_IMG_DIAGONALS = "/home/marcelo/Coding/Learning/Algorithms/SeamCarver/resources/diagonals.png";
    private static final String PATH_IMG_1X8 = "/home/marcelo/Coding/Learning/Algorithms/SeamCarver/resources/1x8.png";
    private static final String PATH_IMG_STRIPES = "/home/marcelo/Coding/Learning/Algorithms/SeamCarver/resources/stripes.png";

    @Test
    public void test3x4() {
        SeamCarver sc = new SeamCarver(new Picture(PATH_IMG_3X4));

        assertArrayEquals(new int[]{0,1,1,0}, sc.findVerticalSeam());
        assertArrayEquals(new int[]{1,2,1}, sc.findHorizontalSeam());
    }

    @Test
    public void test3x7() {
        SeamCarver sc = new SeamCarver(new Picture(PATH_IMG_3X7));

        assertArrayEquals(new int[]{0,1,1,1,1,1,0}, sc.findVerticalSeam());
        assertArrayEquals(new int[]{1,2,1}, sc.findHorizontalSeam());
    }

    @Test
    public void test10x10() {
        SeamCarver sc = new SeamCarver(new Picture(PATH_IMG_10X10));

        assertArrayEquals(new int[]{6,7,7,7,7,7,8,8,7,6}, sc.findVerticalSeam());
        assertArrayEquals(new int[]{0,1,2,3,3,3,3,2,1,0}, sc.findHorizontalSeam());
    }

    @Test
    public void testDiagonals() {
        SeamCarver sc = new SeamCarver(new Picture(PATH_IMG_DIAGONALS));

        assertArrayEquals(new int[]{0,1,1,1,1,1,1,1,1,1,1,0}, sc.findVerticalSeam());
        assertArrayEquals(new int[]{0,1,1,1,1,1,1,1,0}, sc.findHorizontalSeam());
    }

    @Test
    public void testStripes() {
        SeamCarver sc = new SeamCarver(new Picture(PATH_IMG_STRIPES));

        assertArrayEquals(new int[]{ 0,1,1,1,1,1,1,1,1,1,1,0}, sc.findVerticalSeam());
        assertArrayEquals(new int[]{0,1,1,1,1,1,1,1,0}, sc.findHorizontalSeam());

        sc.removeVerticalSeam(sc.findVerticalSeam());
    }

    @Test
    public void test1x8() {
        SeamCarver sc = new SeamCarver(new Picture(PATH_IMG_1X8));

        sc.findVerticalSeam();
    }
}