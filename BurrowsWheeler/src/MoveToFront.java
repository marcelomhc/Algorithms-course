import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.LinkedList;
import java.util.List;


public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        List<Character> orderedChars = new LinkedList<>();
        for (int i = 0; i < 256; i++) {
            orderedChars.add((char) i);
        }

        while(!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar(8);
            BinaryStdOut.write(moveToFront(orderedChars, c), 8);
        }

        BinaryStdOut.flush();
    }

    private static int moveToFront(List<Character> orderedChars, char c) {
        int pos = orderedChars.indexOf(c);
        orderedChars.remove(pos);
        orderedChars.add(0, c);

        return pos;
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        List<Character> orderedChars = new LinkedList<>();
        for (int i = 0; i < 256; i++) {
            orderedChars.add((char) i);
        }

        while(!BinaryStdIn.isEmpty()) {
            int c = BinaryStdIn.readInt(8);
            BinaryStdOut.write(frontMove(orderedChars, c), 8);
        }

        BinaryStdOut.flush();
    }

    private static char frontMove(List<Character> orderedChars, int pos) {
        char c = orderedChars.get(pos);
        orderedChars.remove(pos);
        orderedChars.add(0, c);

        return c;
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        String operation = args[0];
        if(operation.equals("-")) encode();
        if(operation.equals("+")) decode();
    }
}