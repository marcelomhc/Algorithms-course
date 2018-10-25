import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board {
  private int[] board;
  private final int n;
  
    public Board(int[][] blocks)    {
      int size = blocks.length;
      this.n = size;
      this.board = new int[size * size];
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          this.board[i*size+j] = blocks[i][j];
        }
      }
    }
    public int dimension() {
      return this.n;
    }
    public int hamming() {
      int hamming = 0;
      for (int i = 0; i < n*n; i++) {
        if (board[i] != 0 && board[i] != i+1) hamming++;
      }
      return hamming;
    }
    public int manhattan() {
      int manhattan = 0;
      for (int i = 0; i < n*n; i++) {
        if (board[i] != 0 && board[i] != i+1) {
          int col = Math.abs(i % n - (board[i] - 1) % n);
          int row = Math.abs(i / n - (board[i] - 1) / n);
          manhattan += col + row;
        }
      }
      return manhattan;
    }
    public boolean isGoal() {
      return this.hamming() == 0;
    }
    public Board twin() {
      int[][] twin = new int[n][n];
      int p1, p2;
      if (board[0] == 0) {
        p1 = 1;
        p2 = 2;
      } else if (board[1] == 0) {
        p1 = 0;
        p2 = 2;
      } else {
        p1 = 0;
        p2 = 1;
      }
      exchange(p1, p2);
      for (int i = 0; i < n*n; i++) twin[i / n][i % n] = board[i];
      exchange(p1, p2);
      
      return new Board(twin);
    }
    public boolean equals(Object y) {
    if (y == this) return true;
    if (y == null) return false;
    if (y.getClass() != this.getClass()) return false;
    Board that = (Board) y;
    return Arrays.equals(this.board, that.board);
    }
    public Iterable<Board> neighbors() {
      Stack<Board> st = new Stack<Board>();
      int i0 = 0, j0 = 0;
      int[][] newBoard = new int[n][n];
      for (int i = 0; i < n*n; i++) {
        newBoard[i / n][i % n] = board[i];
        if (board[i] == 0) {
          i0 = i / n;
          j0 = i % n;
        }
      }
      if (i0 > 0) {
        exchangei(newBoard, i0, j0);
        st.push(new Board(newBoard));
        exchangei(newBoard, i0, j0);
      }
      if (i0 < n - 1) {
        exchangei(newBoard, i0 + 1, j0);
        st.push(new Board(newBoard));
        exchangei(newBoard, i0 + 1, j0);
      }
      if (j0 > 0) {
        exchangej(newBoard, i0, j0);
        st.push(new Board(newBoard));
        exchangej(newBoard, i0, j0);
      }
      if (j0 < n - 1) {
        exchangej(newBoard, i0, j0 + 1);
        st.push(new Board(newBoard));
        exchangej(newBoard, i0, j0 + 1);
      }
      return st;
    }
    public String toString() {
      StringBuilder s = new StringBuilder();
      s.append(n + "\n");
      for (int i = 0; i < n*n; i++) {
        s.append(String.format("%2d ", board[i]));
        if ((i+1) % n == 0) s.append("\n");
      }
      return s.toString();
  }
    private void exchange(int p1, int p2) {
      int aux = board[p2];
      board[p2] = board[p1];
      board[p1] = aux;
    }
    private void exchangei(int[][] newBoard, int i0, int j0) {
      int aux = newBoard[i0][j0];
      newBoard[i0][j0] = newBoard[i0 - 1][j0];
      newBoard[i0 - 1][j0] = aux;
    }
    private void exchangej(int[][] newBoard, int i0, int j0) {
      int aux = newBoard[i0][j0];
      newBoard[i0][j0] = newBoard[i0][j0 - 1];
      newBoard[i0][j0 - 1] = aux; 
    }
    public static void main(String[] args) {
      In in = new In(args[0]);
      int n = in.readInt();
      int[][] blocks = new int[n][n];
      for (int i = 0; i < n; i++)
          for (int j = 0; j < n; j++)
              blocks[i][j] = in.readInt();
      Board initial = new Board(blocks);
      System.out.println(initial.manhattan());
      System.out.println(initial.twin().toString());
      System.out.println(initial.toString());
      for (Board b : initial.neighbors()) {
        System.out.println(b.toString());
      }
    }
}