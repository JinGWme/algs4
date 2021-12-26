import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import java.net.NetPermission;

public class SeamCarver {

    private final Picture picture;

    private void validateColumnIndex(int i) {
        if (i < 0 || i >= picture.width()) throw new IllegalArgumentException("Column index");
    }
    private void validateRowIndex(int i) {
        if (i < 0 || i >= picture.height()) throw new IllegalArgumentException("Row index");
    }
    private long energy(Color a, Color b) {
        long sum = (a.getRed()-b.getRed())*(a.getRed()-b.getRed());
        sum += (a.getGreen()-b.getGreen())*(a.getGreen()-b.getGreen());
        sum += (a.getBlue()-b.getBlue())*(a.getBlue()-b.getBlue());
        return sum;
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
    public double energy(int x, int y){
        validateColumnIndex(x);
        validateRowIndex(y);
        if (x == 0 || y == 0) return 1000;
        if (x == picture.width()-1 || y == picture.height()-1) return 1000;
        long columnEnergy = energy(picture.get(x, y-1), picture.get(x, y+1));
        long rowEnergy = energy(picture.get(x-1, y), picture.get(x+1, y));
        return Math.sqrt(columnEnergy + rowEnergy);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam(){
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return null;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

    }


    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

    }
}