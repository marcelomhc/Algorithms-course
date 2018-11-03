import edu.princeton.cs.algs4.Picture;

import java.awt.*;

import static java.lang.Math.sqrt;

public class SeamCarver {
    private Picture currentPicture;
    private double[][] energy;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if(picture == null) {
            throw new IllegalArgumentException("Argument to constructor cant be null");
        }
        currentPicture = new Picture(picture);
        energy = new double[width()][height()];
        for(int col = 0; col < width(); col ++) {
            for(int row = 0; row < height(); row++) {
                energy[col][row] = -1;
            }
        }
    }

    // current picture
    public Picture picture() {
        return currentPicture;
    }

    // width of current picture
    public int width() {
        return currentPicture.width();
    }

    // height of current picture
    public int height() {
        return currentPicture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validateAxis(x, y);
        if (energy[x][y] == -1) {
            if (isBorderPixel(x, y)) {
                return 1000.0;
            }

            int dX = calculateDeltaX(x, y);
            int dY = calculateDeltaY(x, y);

            energy[x][y] = sqrt(dX + dY);
        }
        return energy[x][y];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return new int[]{0};
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return new int[]{0};
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

    }

    private boolean isBorderPixel(int x, int y) {
        if (x == 0 || x == width()-1) {
            return true;
        }
        if (y == 0 || y == height()-1) {
            return true;
        }
        return false;
    }

    private int calculateDeltaY(int x, int y) {
        return calculateDelta(
            currentPicture.getRGB(x, y-1),
            currentPicture.getRGB(x, y+1)
        );
    }

    private int calculateDeltaX(int x, int y) {
        return calculateDelta(
            currentPicture.getRGB(x-1, y),
            currentPicture.getRGB(x+1, y)
        );
    }

    private int calculateDelta(int prev, int next) {
        int dR = ((prev >> 16) & 0xFF) - ((next >> 16) & 0xFF);
        int dG = ((prev >>  8) & 0xFF) - ((next >>  8) & 0xFF);
        int dB = ((prev >>  0) & 0xFF) - ((next >>  0) & 0xFF);
        return dR*dR + dG*dG + dB*dB;
    }

    private void validateAxis(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            throw new IllegalArgumentException("Index out of range");
        }
    }
}
