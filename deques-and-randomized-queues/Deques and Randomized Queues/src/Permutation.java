import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rqs = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rqs.enqueue(StdIn.readString());
        } 

        int cnt = Integer.parseInt(args[0]);
        while (cnt > 0 && !rqs.isEmpty()) {
            StdOut.println(rqs.dequeue());
            cnt--;
        }
    }
}
