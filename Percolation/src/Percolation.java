import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private WeightedQuickUnionUF grid;
  private boolean[] openSites;
  private int gridSize;
  private int openCounter = 0;
  
  private int xyTo1D(int row, int col) {
    return 1 + (row - 1) * gridSize + (col - 1);
  }
  
  private void validate(int i) {
    if (i < 1 || i > this.gridSize) {
      throw new IndexOutOfBoundsException("row index i out of bounds");
    }
  }

  /**
   * Percolation constructor of N * N grid
   */
  public Percolation(int n) {
    if (n <= 0) {
      throw new java.lang.IllegalArgumentException();
    }
    this.openSites = new boolean[n * n + 2];
    
    this.gridSize = n;
    this.grid = new WeightedQuickUnionUF(n * n + 2);
    for (int row = 1; row <= n; row++) {
      for (int col = 1; col <= n; col++) {
        int position = xyTo1D(row, col);
        this.openSites[position] = false;
      }
    }
    this.openSites[0] = true;
    this.openSites[n * n + 1] = true;
  }

  /**
   * open site (row, col) if it is not open already
   */
  public void open(int row, int col) {
    validate(row);
    validate(col);
    if (!isOpen(row, col)) {
      int position = this.xyTo1D(row, col);
      this.openSites[position] = true;
       
      // connect top
      if (row == 1) {
        this.grid.union(0, position);
      } else if (isOpen(row - 1, col)) {
        this.grid.union(this.xyTo1D(row - 1, col),position);
      }
      // connect bottom
      if (row < this.gridSize && isOpen(row + 1,col)) {
        this.grid.union(this.xyTo1D(row + 1, col),position);
      }
      // connect left
      if (col > 1 && isOpen(row,col - 1)) {
        this.grid.union(this.xyTo1D(row, col - 1),position);
      }
      // connect right
      if (col < this.gridSize && isOpen(row,col + 1)) {
        this.grid.union(this.xyTo1D(row, col + 1),position);
      }
       
       
      this.openCounter++;
    }
  }
  
  /**
   * is site (row, col) open?
   */
  public boolean isOpen(int row, int col) {
    validate(row);
    validate(col);
    return this.openSites[this.xyTo1D(row, col)];
  }
  
  /**
   * is site (row, col) full?
   */
  public boolean isFull(int row, int col) {
    validate(row);
    validate(col);
    return this.grid.connected(0, this.xyTo1D(row, col));
  }
  
  /**
   * 
   * @return Total of open sites
   */
  public int numberOfOpenSites() {
    return this.openCounter;
  }
  
  /**
   * 
   * @return true if grid percolates
   */
  public boolean percolates() {
    for (int i=1; i <= this.gridSize; i++) {
      if (this.grid.connected(0,this.xyTo1D(this.gridSize, i))) {
        return true;
      }
    }
    return false;
  }
}
