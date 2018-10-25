import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
  private final SearchNode last;
  
    public Solver(Board initial) {
      if (initial == null) throw new java.lang.IllegalArgumentException();
      
      MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
      pq.insert(new SearchNode(null, 0, initial, false));
      pq.insert(new SearchNode(null, 0, initial.twin(), true));
      
      int dim = initial.dimension();
      int[][] goalBoard = new int[dim][dim];
      for (int i = 0; i < dim; i++) {
        for (int j = 0; j < dim; j++) {
          goalBoard[i][j] = i * dim + j + 1;
        }
      }
      goalBoard[dim-1][dim-1] = 0;
      
      Board goal = new Board(goalBoard);
      
      SearchNode curr;
      curr = pq.delMin();
      while (!curr.board.equals(goal)) {
        for (Board b : curr.board.neighbors()) {
          if (curr.prev == null || !b.equals(curr.prev.board)) pq.insert(new SearchNode(curr, curr.moves + 1, b, curr.isTwin));
        }
        curr = pq.delMin();
      }
      last = curr;
    }
    public boolean isSolvable() {
      return !last.isTwin;
    }
    public int moves() {
      if (isSolvable()) return last.moves;
      else return -1;
    }
    public Iterable<Board> solution() {
      if (!isSolvable()) return null;
      
      Stack<Board> s = new Stack<>();
      
      SearchNode curr = last;
      while (curr != null) {
        s.push(curr.board);
        curr = curr.prev;
      }
      return s;
    }
    public static void main(String[] args) {

      // create initial board from file
      In in = new In(args[0]);
      int n = in.readInt();
      int[][] blocks = new int[n][n];
      for (int i = 0; i < n; i++)
          for (int j = 0; j < n; j++)
              blocks[i][j] = in.readInt();
      Board initial = new Board(blocks);

      // solve the puzzle
      Solver solver = new Solver(initial);

      // print solution to standard output
      if (!solver.isSolvable())
          StdOut.println("No solution possible");
      else {
          StdOut.println("Minimum number of moves = " + solver.moves());
          for (Board board : solver.solution())
              StdOut.println(board);
      }
  }
    private class SearchNode implements Comparable<SearchNode> {
      private final SearchNode prev;
      private final int moves;
      private final Board board;
      private final boolean isTwin;
      
      public SearchNode(SearchNode p, int m, Board b, boolean twin) {
        this.prev = p;
        this.moves = m;
        this.board = b;
        this.isTwin = twin;
      }
      
      @Override
      public int compareTo(SearchNode that) {
        return (this.board.manhattan() + this.moves) - (that.board.manhattan() + that.moves);
      }
      
    }
}