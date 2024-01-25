
public class Percolation {
    private boolean[][] grid;  // Grid to represent open/closed sites.
    private WeightedQuickUnionUF uf;  // Union-find structure to manage connectivity.
    private WeightedQuickUnionUF ufFull;  // Union-find structure without virtual bottom to avoid backwash.
    private int n;  // Size of the grid.
    private int virtualTopIndex;  // Index for the virtual top site.
    private int virtualBottomIndex;  // Index for the virtual bottom site.
    private int openSitesCount;  // Counter for the number of open sites.

    // Constructor: creates n-by-n grid with all sites initially blocked.
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Size of the grid must always be greater than 0");
        this.n = n;
        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2); // Including virtual top and bottom.
        ufFull = new WeightedQuickUnionUF(n * n + 1); // Only virtual top to prevent backwash.
        virtualTopIndex = n * n;
        virtualBottomIndex = n * n + 1;
        openSitesCount = 0;
    }

    // Opens the site (row, col) if it is not open already.
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            openSitesCount++;
            int currentSite = twoDtoOneD(row, col);

            // Connect to virtual top or bottom if in the first or last row.
            if (row == 1) {
                uf.union(currentSite, virtualTopIndex);
                ufFull.union(currentSite, virtualTopIndex);
            }
            if (row == n) {
                uf.union(currentSite, virtualBottomIndex);
            }

            // Connect to adjacent open sites.
            connectifOpen(row - 1, col, currentSite);
            connectifOpen(row + 1, col, currentSite);
            connectifOpen(row, col - 1, currentSite);
            connectifOpen(row, col + 1, currentSite);
        }
    }

    // Checks if the site (row, col) is open.
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row - 1][col - 1];
    }

    // Checks if the site (row, col) is full (connected to the top row).
    public boolean isFull(int row, int col) {
        validate(row, col);
        return ufFull.find(twoDtoOneD(row, col)) == ufFull.find(virtualTopIndex);
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // Checks if the system percolates (is there a path from top to bottom).
    public boolean percolates() {
        return uf.find(virtualTopIndex) == uf.find(virtualBottomIndex);
    }

    // Converts 2D coordinates to a 1D union-find index.
    private int twoDtoOneD(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    // Validates that the row and column indices are within bounds.
    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Row or Col values out of Bounds");
        }
    }

    // Connects the current site with an adjacent site if it's open.
    private void connectifOpen(int row, int col, int currentSite) {
        if (row >= 1 && row <= n && col >= 1 && col <= n && isOpen(row, col)) {
            uf.union(twoDtoOneD(row, col), currentSite);
            ufFull.union(twoDtoOneD(row, col), currentSite);
        }
    }
}
