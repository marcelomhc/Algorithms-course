import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board2D {
  private int board[][];
  private int n;
  
    public Board2D(int[][] blocks)    {
      int size = blocks.length;
      this.n = size;
      this.board = new int[size][size];
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          this.board[i][j] = blocks[i][j];
        }
      }
    }
    public int dimension() {
      return this.n;
    }
    public int hamming() {
      int hamming = 0;
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (board[i][j] != 0 && board[i][j] != i*n+j) hamming++;
        }
      }
      return hamming;
    }
    public int manhattan() {
      int manhattan = 0;
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (board[i][j] != 0 && board[i][j] != i*n+j) {
            int col = Math.abs(j - (board[i][j] - 1) % n);
            int row = Math.abs(i - (board[i][j] - 1) / n);
            manhattan += col + row;
          }
        }
      }
      return manhattan;
    }
    public boolean isGoal() {
      return this.hamming() == 0;
    }
    public Board twin() {
      int[][] twin = new int[n][n];
      int i1, j1, i2, j2;
      if (board[0][0] == 0) {
        i1 = 0;
        j1 = 1;
        i2 = 1;
        j2 = 0;
      } else if(board[0][1] == 0) {
        i1 = 0;
        j1 = 0;
        i2 = 1;
        j2 = 0;
      } else {
        i1 = 0;
        j1 = 0;
        i2 = 0;
        j2 = 1;
      }
      exchange(i1, j1, i2, j2);
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          twin[i][j] = board[i][j];
        }
      }
      exchange(i1, j1, i2, j2);
      
      return new Board(twin);
    }
    public boolean equals(Object y) {
    if (y == this) return true;
    if (y == null) return false;
    if (y.getClass() != this.getClass()) return false;
    Board that = (Board) y;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (this.board[i][j] != 0) return false;
      }
    }
    return true;
    }
    public Iterable<Board> neighbors() {
      Stack<Board> st = new Stack<Board>();
      int zero = 0;
      
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (board[i][j] == 0) {
            zero = i;
            break;
          }
        }
      }
      
      if (zero > n) {
        
      }
      return null;
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
    private void exchange(int i1, int j1, int i2, int j2) {
      int aux = board[i1][j1];
      board[i1][j1] = board[i2][j2];
      board[i2][j2] = aux;
    }
    public static void main(String[] args) {
      In in = new In("C:\\Users\\Marcelo\\Documents\\Coding\\Algorithms\\8Puzzle\\8puzzle\\puzzle02.txt");
      int n = in.readInt();
      int[][] blocks = new int[n][n];
      for (int i = 0; i < n; i++)
          for (int j = 0; j < n; j++)
              blocks[i][j] = in.readInt();
      Board initial = new Board(blocks);
      System.out.println(initial.manhattan());
      System.out.println(initial.twin().toString());
      System.out.println(initial.toString());
    }
}