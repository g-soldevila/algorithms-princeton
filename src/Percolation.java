import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int rowElements;
    private int gridElements;
    public WeightedQuickUnionUF uf;
    private boolean[][] grid;
    private int virtualTopIndex;
    private int virtualBottomIndex;
    private int openElements;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("N must be > 0");

        rowElements = n;
        gridElements = n * n;

        // Add 2 extra elements to UF for virtualTop and virtualBottom
        uf = new WeightedQuickUnionUF(gridElements + 2);
        virtualTopIndex = gridElements;
        virtualBottomIndex = gridElements + 1;

        grid = new boolean[rowElements][rowElements];
        openElements = 0;

        for (int i = 1; i <= rowElements; i++) {
            // connect all top row elements to virtual top
            uf.union(toGridAsArrayIndex(1, i), virtualTopIndex);
            // connect all bottom row elements to virtual bottom
            uf.union(toGridAsArrayIndex(rowElements, i), virtualBottomIndex);
        }
    }

    // opens the site (row, col) if it is not open already. Assuming top left is 1, 1
    public void open(int row, int col) {
        checkExists(row, col);
        if (!isOpen(row, col)) {

            grid[toZeroIndex(row)][toZeroIndex(col)] = true;
            openElements++;

            int elementArrayIndex = toGridAsArrayIndex(row, col);

            // Left
            int leftRow = row;
            int leftCol = col - 1;
            if (exists(leftRow, leftCol) && isOpen(leftRow, leftCol)) {
                uf.union(elementArrayIndex, toGridAsArrayIndex(leftRow, leftCol));
            }

            // Right
            int rightRow = row;
            int rightCol = col + 1;
            if (exists(rightRow, rightCol) && isOpen(rightRow, rightCol)) {
                uf.union(elementArrayIndex, toGridAsArrayIndex(rightRow, rightCol));
            }

            // Top
            int topRow = row - 1;
            int topCol = col;
            if (exists(topRow, topCol) && isOpen(topRow, topCol)) {
                uf.union(elementArrayIndex, toGridAsArrayIndex(topRow, topCol));
            }

            // Bottom
            int bottomRow = row + 1;
            int bottomCol = col;
            if (exists(bottomRow, bottomCol) && isOpen(bottomRow, bottomCol)) {
                uf.union(elementArrayIndex, toGridAsArrayIndex(bottomRow, bottomCol));
            }
        }
    }

    // is the site (row, col) open? Assuming top left is 1, 1
    public boolean isOpen(int row, int col) {
        checkExists(row, col);
        return grid[toZeroIndex(row)][toZeroIndex(col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkExists(row, col);
        return uf.connected(virtualTopIndex, toGridAsArrayIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openElements;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(virtualTopIndex, virtualBottomIndex);
    }

    // Helper methods
    // =============================

    private int toZeroIndex(int i) {
        return i - 1;
    }
    private int toGridAsArrayIndex(int row, int col) {
        // Add 1 to count for virtual Top
        return toZeroIndex(row) * rowElements + toZeroIndex(col);
    }
    // Assumes 1 based index, top left is 1, 1
    private boolean exists(int row, int col) {
        return !(row < 1 || row > rowElements || col < 1 || col > rowElements);
    }

    private void checkExists(int row, int col) {
        if (!exists(row, col)) throw new IndexOutOfBoundsException("Index is out of bounds");
    }

    private void printGrid() {
        final StringBuffer str = new StringBuffer();
        int i = 0, j = 0;
        for (i = 0; i < rowElements; i++) {
            for (j = 0; j < rowElements; j++) {
                str.append(" " + grid[i][j]);
            }
            str.append("\n");
        }
        StdOut.println(str.toString());
    }

    public static void main(String[] args) {

    }
}
