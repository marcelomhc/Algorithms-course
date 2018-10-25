import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private int n;          // size of the stack
    private Node first;     // top of stack
    private Node last;
    
    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    public Deque() {
       first = null;
       last = null;
       n = 0;   
    }
    public boolean isEmpty() {
       return n == 0;
    }
    public int size() {
       return n;
    }
    public void addFirst(Item item) {
        if (isNull(item)) throw new NullPointerException("Null items are not accepted");
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (oldfirst != null) oldfirst.prev = first;
        n++;
        if (n == 1) last = first; 
    }
    public void addLast(Item item) {
        if (isNull(item)) throw new NullPointerException("Null items are not accepted");
        if (isEmpty()) addFirst(item);
        else {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.prev = oldlast;
            oldlast.next = last;
            n++;
        }
    }
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        if (first != null) first.prev = null;
        else last = null;
        n--;
        return item;                   // return the saved item
    }
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        if (n == 1) return removeFirst();
        else {
            Item item = last.item;
            last = last.prev;
            last.next = null;
            n--;
            return item;
        }
    }
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    public static void main(String[] args) {
       // unit testing (optional)
    }
    
    private boolean isNull(Item item) {
        return item == null;
    }
}
