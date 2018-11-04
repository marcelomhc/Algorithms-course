import edu.princeton.cs.algs4.Picture;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

public class SeamCarver {
    private int[] currentPicture;
    private double[] energy;
    private boolean isTranspose;
    private int width;
    private int height;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if(picture == null) {
            throw new IllegalArgumentException("Argument to constructor cant be null");
        }

        width = picture.width();
        height = picture.height();
        currentPicture = new int[width() * height()];
        energy = new double[width() * height()];
        isTranspose = false;

        for(int col = 0; col < width(); col ++) {
            for(int row = 0; row < height(); row++) {
                energy[get1dIdx(col, row)] = -1;
                currentPicture[get1dIdx(col, row)] = picture.getRGB(col, row);
            }
        }
    }

    // current picture
    public Picture picture() {
        Picture myPicture = new Picture(width, height);
        for (int idx = 0; idx < currentPicture.length; idx++) {
            myPicture.setRGB(get2dCol(idx), get2dRow(idx), currentPicture[idx]);
        }
        return myPicture;
    }

    // width of current picture
    public int width() {
        if(isTranspose) return height;
        return width;
    }

    // height of current picture
    public int height() {
        if(isTranspose) return width;
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validateAxis(x, y);
        int pos = get1dIdx(x, y);
        if (isBorderPixel(x, y)) {
            energy[pos] = 1000.0;
        } else {
            if (energy[pos] == -1) {
                int dX = calculateDeltaX(x, y);
                int dY = calculateDeltaY(x, y);
                energy[pos] = sqrt(dX + dY);
            }
        }
        return energy[pos];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        setTranspose(true);
        int[] seam = findVerticalSeam();
        setTranspose(false);
        return seam;
    }

    private void setTranspose(boolean flag) {
        isTranspose = flag;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] edgeTo = new int[width() * height()];
        double[] energySum = new double[width() * height()];
        for(int row = 0; row < height(); row++) {
            for(int col = 0; col < width(); col++) {
                if(isFirst(row)) {
                    energySum[get1dIdx(col, row)] = energy(col, row);
                    edgeTo[get1dIdx(col, row)] = -1;
                } else {
                    double left;
                    double mid;
                    double right;
                    int prevRow = row - 1;
                    if(isFirst(col)) {
                        left = Double.MAX_VALUE;
                        mid = energySum[get1dIdx(col, prevRow)];
                        right = energySum[get1dIdx(col+1, prevRow)];
                    } else if(isLastCol(col)) {
                        left = energySum[get1dIdx(col-1, prevRow)];
                        mid = energySum[get1dIdx(col, prevRow)];
                        right = Double.MAX_VALUE;
                    } else {
                        left = energySum[get1dIdx(col-1, prevRow)];
                        mid = energySum[get1dIdx(col, prevRow)];
                        right = energySum[get1dIdx(col+1, prevRow)];
                    }

                    if (mid < left) {
                        if (right < mid) {
                            edgeTo[get1dIdx(col, row)] = col+1;
                            energySum[get1dIdx(col, row)] = right + energy(col, row);
                        } else {
                            edgeTo[get1dIdx(col, row)] = col;
                            energySum[get1dIdx(col, row)] = mid + energy(col, row);
                        }
                    } else {
                        if (right < left) {
                            edgeTo[get1dIdx(col, row)] = col+1;
                            energySum[get1dIdx(col, row)] = right + energy(col, row);
                        } else {
                            edgeTo[get1dIdx(col, row)] = col-1;
                            energySum[get1dIdx(col, row)] = left + energy(col, row);
                        }
                    }
                }
            }
        }
        return getSeam(edgeTo, energySum);
    }

    private int[] getSeam(int[] edgeTo, double[] energy) {
        int curRow = height() - 1;
        int curr = -1;
        double minEnergy = Double.MAX_VALUE;
        for(int col = 0; col < width(); col++) {
            if(energy[get1dIdx(col, curRow)] < minEnergy) {
                minEnergy = energy[get1dIdx(col, curRow)];
                curr = col;
            }
        }

        int[] seam = new int[height()];
        seam[curRow] = curr;
        curr = edgeTo[get1dIdx(curr,curRow)];
        while(curr != -1) {
            curRow--;
            seam[curRow] = curr;
            curr = edgeTo[get1dIdx(curr,curRow)];
        }
        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        setTranspose(true);
        removeVerticalSeam(seam);
        setTranspose(false);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if(seam == null) {
            throw new IllegalArgumentException("Seam cannot be null");
        }
        if(width() == 1) {
            throw new IllegalArgumentException("Picture cannot be reduced anymore");
        }
        validateSeam(seam);

        int newWidth = width() - 1;
        int[] newPicture = new int[newWidth * height()];

        //Update picture
        for(int row = 0; row < seam.length; row++) {
            int colToRemove = seam[row];
            System.arraycopy(currentPicture, row*width(), newPicture, row*newWidth, colToRemove);
            System.arraycopy(currentPicture, row*width()+colToRemove+1, newPicture, row*newWidth+colToRemove, newWidth-colToRemove);
            energy[get1dIdx(colToRemove, row)] = -1;
            energy[get1dIdx(colToRemove-1, row)] = -1;
        }

        setWidth(newWidth);
        currentPicture = newPicture;
    }

    private void setWidth(int newValue) {
        if(isTranspose) height = newValue;
        width = newValue;
    }

    private void validateSeam(int[] seam) {
        if(seam.length != height()) {
            throw new IllegalArgumentException("Seam length differ from picture");
        }
        int last = seam[0];
        for(int idx = 1; idx < seam.length; idx++) {
            if(abs(last-seam[idx]) > 1) {
                throw new IllegalArgumentException("Seam is not valid");
            }
            last = seam[idx];
        }
    }

    private int get1dIdx(int col, int row) {
        if(isTranspose) return col*height() + row;
        return row*width() + col;
    }

    private int get2dCol(int idx) {
        if(isTranspose) return idx / height();
        return idx % width();
    }

    private int get2dRow(int idx) {
        if(isTranspose) return idx % height();
        return idx / width();
    }

    private boolean isBorderPixel(int col, int row) {
        if (isFirst(col) || isLastCol(col)) {
            return true;
        }
        if (isFirst(row) || isLastRow(row)) {
            return true;
        }
        return false;
    }

    private boolean isFirst(int idx) {
        return idx == 0;
    }

    private boolean isLastRow(int row) {
        return row == height()-1;
    }

    private boolean isLastCol(int col) {
        return col == width()-1;
    }

    private int calculateDeltaY(int x, int y) {
        return calculateDelta(
            currentPicture[get1dIdx(x, y-1)],
            currentPicture[get1dIdx(x, y+1)]
        );
    }

    private int calculateDeltaX(int x, int y) {
        return calculateDelta(
            currentPicture[get1dIdx(x-1, y)],
            currentPicture[get1dIdx(x+1, y)]
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
