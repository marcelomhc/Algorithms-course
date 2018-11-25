import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        //---------------
        CircularSuffixArray circularArray = new CircularSuffixArray(s);
        int length = circularArray.length();
        int first = -1;
        for (int i = 0; i < length; i++) {
            if (circularArray.index(i) == 0) first = i;
        }

        BinaryStdOut.write(first);
        for (int i = 0; i < length; i++) {
            BinaryStdOut.write(s.charAt((length + circularArray.index(i) - 1) % length));
        }
        BinaryStdOut.flush();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();

        List<Character> t = new ArrayList<>();
        List<Character> tSorted = new ArrayList<>();
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            t.add(c);
            tSorted.add(c);
        }
        Queue<Integer>[] r = new Queue[256];
        for (int i = 0; i < 256; i++) {
            r[i] = new PriorityQueue<>();
        }
        int[] next = new int[t.size()];
        Collections.sort(tSorted);

        for (int i = 0; i < t.size(); i++) {
            r[Integer.valueOf(t.get(i))].add(i);
        }
        for (int i = 0; i < t.size(); i++) {
            next[i] = r[tSorted.get(i)].remove();
        }

        int curr = first;
        for (int i = 0; i < t.size(); i++) {
            BinaryStdOut.write(tSorted.get(curr));
            curr = next[curr];
        }
        BinaryStdOut.flush();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        String operation = args[0];
        if (operation.equals("-")) transform();
        if (operation.equals("+")) inverseTransform();
    }
}