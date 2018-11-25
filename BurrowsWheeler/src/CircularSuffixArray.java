public class CircularSuffixArray {
    int length;
    int[] sortedArray;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }

        length = s.length();
        sortedArray = new int[length];

        for (int i = 0; i < length; i++) {
            sortedArray[i] = i;
        }

        for (int i = 0; i < length; i++) {
            sort(s, sortedArray, 0, length - 1, 0);
        }
    }

    // length of s
    public int length() {
        return length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if(i < 0 || i >= length()) throw new IllegalArgumentException();

        return sortedArray[i];
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

    private void sort(String s, int[] indexes, int lo, int hi, int d)
    {
        if (hi <= lo) return;
        int lt = lo, gt = hi;
        int v = getCircularChar(s, indexes[lo], d);
        int i = lo + 1;
        while (i <= gt)
        {
            int t = getCircularChar(s, indexes[i], d);
            if
            (t < v) exch(indexes, lt++, i++);
            else if (t > v) exch(indexes, i, gt--);
            else
                i++;
        }
        sort(s, indexes, lo, lt-1, d);
        if (v >= 0) sort(s, indexes, lt, gt, d+1);
        sort(s, indexes, gt+1, hi, d);
    }

    private void exch(int[] indexes, int i, int i1) {
        int temp = indexes[i];
        indexes[i] = indexes[i1];
        indexes[i1] = temp;
    }

    private int getCircularChar(String s, int index, int d) {
        return s.charAt((index+d)%length());
    }
}