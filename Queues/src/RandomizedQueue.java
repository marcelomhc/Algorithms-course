import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;         // array of items
    private int n;            // number of elements on stack
    
   public RandomizedQueue() { 
       a = (Item[]) new Object[2];
       n = 0;
   }
   public boolean isEmpty() {
       return n == 0;
   }
   public int size() {
       return n;
   }
   // resize the underlying array holding the elements
   private void resize(int capacity) {
       assert capacity >= n;

       // textbook implementation
       Item[] temp = (Item[]) new Object[capacity];
       for (int i = 0; i < n; i++) {
           temp[i] = a[i];
       }
       a = temp;

      // alternative implementation
      // a = java.util.Arrays.copyOf(a, capacity);
   }
   public void enqueue(Item item) {
       if (isNull(item)) throw new NullPointerException("Null items are not accepted");
       if (n == a.length) resize(2*a.length);    // double size of array if necessary
       a[n++] = item;                            // add item
   }
   public Item dequeue() {
       if (isEmpty()) throw new NoSuchElementException("Stack underflow");
       int itemPosition = StdRandom.uniform(n);
       Item item = a[itemPosition];
       a[itemPosition] = a[n-1];
       a[n-1] = null;                              // to avoid loitering
       n--;
       // shrink size of array if necessary
       if (n > 0 && n == a.length/4) resize(a.length/2);
       return item;
   }
   public Item sample() {
       if (isEmpty()) throw new NoSuchElementException("Stack underflow");
       int itemPosition = StdRandom.uniform(n);
       return a[itemPosition];
       
   }
   public Iterator<Item> iterator() {
       return new ListIterator();
   }

   private class ListIterator implements Iterator<Item> {
       private int i;
       private Item[] it;
       
       public ListIterator() { 
         i = n-1; 
         it = (Item[]) new Object[n];
         for (int k = 0; k < n; k++) {
             it[k] = a[k];
         }
       }
       public boolean hasNext()  { return i >= 0;                     }
       public void remove()      { throw new UnsupportedOperationException();  }

       public Item next() {
           if (!hasNext()) throw new NoSuchElementException();
           int itemPosition = StdRandom.uniform(i+1);
           Item item = it[itemPosition];
           it[itemPosition] = it[i--];
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