import edu.princeton.cs.algs4.StdOut;

/**
 * insert() - O(1)
 * delMAx() - O(n)
 * max() - O(n)
 *
 * *  Limitations
 *  *  -----------
 *  *   - no array resizing
 *  *   - does not check for overflow or underflow.
 *
 * % java UnorderedMaxPQ.java
 * Note: UnorderedMaxPQ.java uses unchecked or unsafe operations.
 * Note: Recompile with -Xlint:unchecked for details.
 * this
 * test
 * is
 * a
 * */


public class UnorderedMaxPQ<Key> {
    private Key[] pq;
    private int n;

    public UnorderedMaxPQ(int capacity) {
        pq = (Key[]) new Object[capacity];
        n=0;
    }

    public void insert(Key key) {
        pq[n++] = key;
    }
    public Key delMax() {
        int max = 0;
        for (int i = 1; i<n; i++)
            if (less(max, i)) max = i;
        exch(max, n-1);
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
	UnorderedMaxPQ<String> pq = new UnorderedMaxPQ<>(10);
        pq.insert("this");
        pq.insert("is");
        pq.insert("a");
        pq.insert("test");
        while (!pq.isEmpty())
            StdOut.println(pq.delMax());
    }
}
