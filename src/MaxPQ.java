/**  %java MaxPQ.java < input.txt
 Note: MaxPQ.java uses unchecked or unsafe operations.
 Note: Recompile with -Xlint:unchecked for details.
 Q X P (6 left on pq)
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MaxPQ<Key> implements Iterable<Key> {
    private Key[] pq;
    private int n;
    private Comparator<Key> comparator;


    public MaxPQ(int capacity) {
        pq = (Key[]) new Object[capacity+1]; // +1 т.к. отсчет с i=1, а не с i=0
        n = 0;
    }
    public MaxPQ(int capacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        pq = (Key[]) new Object[capacity+1]; // +1 т.к. отсчет с i=1, а не с i=0
        n = 0;
    }
    public MaxPQ() {
        pq = (Key[]) new Object[2];
        n = 0;
    }
    public MaxPQ(Comparator<Key> comparator) {
        this.comparator = comparator;
        pq = (Key[]) new Object[2];
        n = 0;
    }
    public MaxPQ(Key[] keys) {
        n = keys.length;
        pq = (Key[]) new Object[n+1]; // +1 т.к. отсчет с i=1, а не с i=0
        for (int i=1; i<=n; i++)
            pq[i] = keys[i-1];
        for (int k =n/2; k>=1; k--) //выстроить элементы в нужном порядке
            sink(k);
    }


/*****************
 * PQ operations *
 *************** */

    // is array empty?
    public boolean isEmpty() {
        return n==0;
    }

    // return max key
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    // return size
    public int size() {
        return n;
    }

    // new element of PQ
    public void insert(Key key) {
        if (n == pq.length-1) resize(pq.length*2);
        pq[++n]= key;
        swim(n);
    }

    // delete maximum element
    public Key delMax() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        Key max = pq[1];
        exch(1, n--);
        sink(1);
        pq[n+1] = null; // prevent loitering
        if ((n>0) && n == (pq.length-1)/4) resize(pq.length/2);
        return max;
    }

    // new size of the array
    private void resize(int capacity){
        Key[] copy = (Key[]) new Object[capacity];
        for (int i=1; i<=n; i++)
            copy[i] = pq[i];
        pq = copy;
    }

/************************
* Heap helper functions *
*********************** */

    private void swim(int k) {
        while (k>1 && less(k/2, k)) {
            exch(k/2, k);
            k=k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = k * 2;
         if (j<n && less(j, j+1)) j++;
         if (!less(k, j)) break;
         exch(k, j);
         k=j;
        }
    }

/*************************
* Array helper functions *
************************ */

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    private boolean less(int i, int j) {
        if (comparator == null) return ((Comparable<Key>) pq[i]).compareTo(pq[j])<0;
        else return comparator.compare(pq[i], pq[j])<0;
    }

/************************
*       Iterator        *
*********************** */

    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {
        private MaxPQ<Key> copy; //create new PQ

        public HeapIterator() {
            if (comparator == null) copy = new MaxPQ<>(size());
            else                    copy = new MaxPQ<>(size(), comparator);
            for (int i = 1; i<=n; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMax();  // возвращать в порядке убываания! А pq[N--] не подойдет
        }

        public void remove() { throw new UnsupportedOperationException(); }
    }


    public static void main(String[] args) {

        MaxPQ<String> pq = new MaxPQ<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) pq.insert(item);
            else if (!pq.isEmpty()) StdOut.print(pq.delMax() + " ");
        }
        StdOut.println("(" + pq.size() + " left on pq)");
    }
}
