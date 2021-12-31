import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private Picture picture;

    private double[][] energyMatrix() {
        double[][] a = new double[picture.height()][picture.width()];
        for (int row = 0; row < picture.height(); row++)
            for (int col = 0; col < picture.width(); col++)
                a[row][col] = energy(col, row);
        return a;
    }

    private void validateColumnIndex(int i) {
        if (i < 0 || i >= picture.width())
            throw new IllegalArgumentException("Column index");
    }

    private void validateRowIndex(int i) {
        if (i < 0 || i >= picture.height())
            throw new IllegalArgumentException("Row index");
    }

    private long energySquare(Color a, Color b) {
        long sum = (a.getRed() - b.getRed()) * (a.getRed() - b.getRed());
        sum += (a.getGreen() - b.getGreen()) * (a.getGreen() - b.getGreen());
        sum += (a.getBlue() - b.getBlue()) * (a.getBlue() - b.getBlue());
        return sum;
    }

    private void relax(int x, int y, int fx, int fy, double[][] energyGrid, double[][] distanceTo, int[][] edgeTo) {
        if (x < 0 || x >= energyGrid[0].length)
            return;
        if (distanceTo[y][x] > distanceTo[fy][fx] + energyGrid[y][x]) {
            distanceTo[y][x] = distanceTo[fy][fx] + energyGrid[y][x];
            edgeTo[y][x] = fx;
        }
    }

    // always tries to find vertical seam from the energyMatrix perspective
    // from picture perspective, it is a horizontal seam. This is controled by
    // SCUtility.java
    private int[] findSeam(double[][] energyMatrix) {
        int height = energyMatrix.length, width = energyMatrix[0].length;
        double[][] distanceTo = new double[height][width];
        for (double[] ds : distanceTo)
            Arrays.fill(ds, Double.MAX_VALUE);
        int[][] edgeTo = new int[height][width];
        for (int[] is : edgeTo)
            Arrays.fill(is, -1);

        for (int x = 0; x < width; x++) {
            distanceTo[0][x] = energyMatrix[0][x];
        }

        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                relax(x - 1, y + 1, x, y, energyMatrix, distanceTo, edgeTo);
                relax(x, y + 1, x, y, energyMatrix, distanceTo, edgeTo);
                relax(x + 1, y + 1, x, y, energyMatrix, distanceTo, edgeTo);
            }
        }

        double minEnergy = Double.MAX_VALUE;
        int minXIndex = 0;
        for (int x = 0; x < width; x++) {
            if (minEnergy > distanceTo[height - 1][x]) {
                minEnergy = distanceTo[height - 1][x];
                minXIndex = x;
            }
        }

        int[] seam = new int[height];
        seam[height - 1] = minXIndex;
        for (int y = height - 1; y > 0; y--) {
            seam[y - 1] = edgeTo[y][seam[y]];
        }
        return seam;
    }

    // create a seam carver object base one the given picture
    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    // current picture
    public Picture picture() {
        return this.picture;
    }

    // width of current picture
    public int width() {
        return this.picture.width();
    }

    // height of current picture
    public int height() {
        return this.picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validateColumnIndex(x);
        validateRowIndex(y);
        if (x == 0 || y == 0)
            return 1000;
        if (x == picture.width() - 1 || y == picture.height() - 1)
            return 1000;
        long columnEnergy = energySquare(picture.get(x, y - 1), picture.get(x, y + 1));
        long rowEnergy = energySquare(picture.get(x - 1, y), picture.get(x + 1, y));
        return Math.sqrt(columnEnergy + rowEnergy);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        double[][] energy = energyMatrix();
        // transpose matrix
        double transposedEnergy[][] = new double[energy[0].length][energy.length];
        for (int y = 0; y < energy.length; y++) {
            for (int x = 0; x < energy[0].length; x++) {
                transposedEnergy[x][y] = energy[y][x];
            }
        }
        return findSeam(transposedEnergy);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] energy = energyMatrix();
        return findSeam(energy);
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        Picture np = new Picture(picture.width(), picture.height() - 1);
        for (int col = 0; col < picture.width(); col++) {
            int nrow = 0;
            for (int row = 0; row < picture.height(); row++) {
                if (seam[col] == row)
                    continue;
                else
                    np.set(col, nrow++, picture.get(col, row));
            }
        }
        this.picture = np;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        Picture np = new Picture(picture.width(), picture.height());
        for (int row = 0; row < picture.height(); row++) {
            int ncol = 0;
            for (int col = 0; col < picture.width(); col++) {
                if (seam[row] == col)
                    continue;
                else
                    np.set(col, ncol++, picture.get(col, row));
            }
        }
        this.picture = np;
    }
}