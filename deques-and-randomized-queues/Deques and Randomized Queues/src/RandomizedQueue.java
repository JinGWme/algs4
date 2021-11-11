import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int itemCnt = 0;

    // does not support change to the queue after iterator is created.
    private class RandomizedQueueIterator implements Iterator<Item> {

        private final int[] randomIndex;
        private int index;

        public RandomizedQueueIterator() {
            randomIndex = new int[itemCnt];
            for (int i = 0; i < itemCnt; i++)
                randomIndex[i] = i;
            StdRandom.shuffle(randomIndex);
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index != itemCnt;
        }

        @Override
        // move to next but return current;
        public Item next() {
            if (index == itemCnt)
                throw new NoSuchElementException();
            return s[randomIndex[index++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not allowed");
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return itemCnt == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return itemCnt;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (itemCnt == s.length)
            resize(2 * itemCnt);

        s[itemCnt++] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < itemCnt; i++)
            copy[i] = s[i];
        s = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (itemCnt == 0)
            throw new NoSuchElementException();
        int target = StdRandom.uniform(itemCnt);
        Item i = s[target];
        s[target] = s[--itemCnt];
        s[itemCnt] = null;
        if (itemCnt > 0 && itemCnt == s.length / 4)
            resize(s.length / 2);
        return i;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (itemCnt == 0)
            throw new NoSuchElementException();
        return s[StdRandom.uniform(itemCnt)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rqi = new RandomizedQueue<>();

        StdOut.println("size:" + rqi.size());
        StdOut.println(rqi.isEmpty());

        rqi.enqueue(1);
        rqi.enqueue(3);
        rqi.enqueue(5);
        rqi.enqueue(7);
        rqi.enqueue(9);

        StdOut.print("size:" + rqi.size());
        StdOut.print(" " + rqi.sample());
        StdOut.print(" " + rqi.sample());
        StdOut.print(" " + rqi.sample());
        StdOut.println();

        StdOut.print("size:" + rqi.size());
        StdOut.print(" " + rqi.dequeue());
        StdOut.print(" " + rqi.dequeue());
        StdOut.print(" " + rqi.dequeue());
        StdOut.println();

        StdOut.print("size:" + rqi.size());
        for (Integer integer : rqi) {
            StdOut.print(" " + integer);
        }
        StdOut.println();
        StdOut.print("size:" + rqi.size());
        for (Integer integer : rqi) {
            StdOut.print(" " + integer);
        }
        StdOut.println();
        StdOut.print("size:" + rqi.size());
        StdOut.print(" " + rqi.dequeue());
        StdOut.print(" " + rqi.dequeue());
        // StdOut.print(" " +rqi.dequeue());
        StdOut.println();
        StdOut.println(rqi.isEmpty());
    }

}
