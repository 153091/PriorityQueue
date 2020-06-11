import edu.princeton.cs.algs4.StdOut;

/**
 * insert() - O(n)
 * delMAx() - O(1)
 * max() - O(1)
 *
 * *  Limitations
 *  *  -----------
 *  *   - no array resizing
 *  *   - does not check for overflow or underflow.
 *
 * % java OrderedMaxPQ.java
 * Note: OrderedMaxPQ.java uses unchecked or unsafe operations.
 * Note: Recompile with -Xlint:unchecked for details.
 * this
 * test
 * is
 * a
 * */


public class OrderedMaxPQ<Key> {
    private Key[] pq;
    private int n;

    public OrderedMaxPQ(int capacity) {
        pq = (Key[]) new Object[capacity];
        n=0;
    }

    public void insert(Key key) {   // from InsertionSort
        pq[n++] = key;
        for (int i = n-1; i>0; i--)
            if(less(i, i-1)) exch(i, i-1);
    }
    public Key delMax() {
        Key copy = pq[--n];
        pq[n] = null; // avoid loitering
        return copy;

    }
    public int size() {
        return  n;
    }
    public boolean isEmpty() {
        return n==0;
    }

    /***************************************************************************
     * Helper functions.
     ***************************************************************************/


    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    private boolean less(int i ,int j) {
        return ((Comparable<Key>) pq[i]).compareTo(pq[j])<0;
    }

    public static void main(String[] args) {
        OrderedMaxPQ<String> pq = new OrderedMaxPQ<>(10);
        pq.insert("this");
        pq.insert("is");
        pq.insert("a");
        pq.insert("test");
        while (!pq.isEmpty())
            StdOut.println(pq.delMax());
    }
}