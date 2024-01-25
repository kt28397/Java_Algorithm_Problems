public class WeightedQuickUnionUF {
    private int[] parent;  // parent[i] = parent of i
    private int[] size;    // size[i] = number of sites in subtree rooted at i
    private int count;     // number of components

    /**
     * Initializes an empty union-find data structure with n sites
     * @param n the number of sites
     */
    public WeightedQuickUnionUF(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Returns the number of components.
     * @return the number of components (between 1 and n)
     */
    public int count() {
        return count;
    }

    /**
     * Returns the component identifier for the component containing site p.
     * @param p the integer representing one site
     * @return the component identifier for the component containing site p
     */
    public int find(int p) {
        validate(p);
        while (p != parent[p]) {
            p = parent[p];
        }
        return p;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    /**
     * Returns true if the the two sites are in the same component.
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @return true if the two sites p and q are in the same component; false otherwise
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Merges the component containing site p with the component containing site q.
     * @param p the integer representing one site
     * @param q the integer representing the other site
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }
}
