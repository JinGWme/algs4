import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int nodeNum;

    private class Node<item> {
        Item value;
        Node<Item> prev;
        Node<Item> next;
    }

    private class DequeIterator implements Iterator<Item> {

        Node<Item> current;

        public DequeIterator() {
            current = first;
        }

        @Override
        public boolean hasNext() {
            // checks for 'current', not 'next'
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null)
                throw new NoSuchElementException("From next");

            // This function returns 'current', not 'next'
            Item i = current.value;
            current = current.next;
            return i;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported for iterator");
        }

    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        nodeNum = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return nodeNum;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node<Item> oldFirst = first;
        first = new Node<>();
        first.value = item;
        first.prev = null;
        first.next = oldFirst;

        if (oldFirst != null)
            oldFirst.prev = first;

        if (last == null)
            last = first;
        nodeNum++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node<Item> oldLast = last;
        last = new Node<>();
        last.value = item;
        last.prev = oldLast;
        last.next = null;
        if (oldLast != null)
            oldLast.next = last;

        if (first == null)
            first = last;
        nodeNum++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }

        Item i = first.value;
        first = first.next;
        if (first != null)
            first.prev = null;
        else
            last = null;
        nodeNum--;
        return i;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        Item i = last.value;
        last = last.prev;
        if (last != null)
            last.next = null;
        else
            first = null;
        nodeNum--;
        return i;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dqi = new Deque<>();
        dqi.addFirst(1);
        dqi.addLast(4);
        dqi.addFirst(3);
        dqi.addLast(5);
        dqi.addFirst(2);
        dqi.addLast(6);
        for (Integer i : dqi)
            StdOut.print(i + " ");
        StdOut.println(dqi.size());
        dqi.removeFirst();
        dqi.removeLast();
        for (Integer i : dqi)
            StdOut.print(i + " ");
        StdOut.println(dqi.size());

        dqi.removeLast();
        dqi.removeFirst();
        for (Integer i : dqi)
            StdOut.print(i + " ");
        StdOut.println(dqi.size());
        dqi.removeLast();
        dqi.removeFirst();
        for (Integer i : dqi)
            StdOut.print(i + " ");
        StdOut.println(dqi.size());
        // while(dqi.size()!=0)
        // StdOut.print(dqi.removeFirst()+" ");
        StdOut.println();

        try {
            dqi.removeFirst();
        } catch (NoSuchElementException e) {
            StdOut.println(e);
        }
        try {
            dqi.addFirst(null);
        } catch (NoSuchElementException e) {
            StdOut.println(e);
        }

    }
}
