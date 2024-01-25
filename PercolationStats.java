public class PercolationStats {
    private final int trials;  // Number of trials to perform.
    private final double[] thresholds;  // Array to store results of each trial.

    /**
     * Constructor to initialize and perform a series of percolation experiments.
     * @param n The size of the percolation grid (n-by-n).
     * @param trials The number of independent experiments to run.
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("The Size and the number of trials should be positive");
        }
        this.trials = trials;
        thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                // Open a random site until the system percolates.
                int row = 1 + (int) (Math.random() * ((n+1) - 1)); //low + (int) (Math.random() * (high - low));
                int col = 1 + (int) (Math.random() * ((n+1) - 1));
                perc.open(row, col);
            }
            // Calculate the percolation threshold for this trial.
            thresholds[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
    }

    // Calculates the sample mean of percolation threshold.
    public double mean() {
        double sum = 0.0;
        for (double v : thresholds) {
            sum += v;
        }
        return sum / thresholds.length;
    }

    // Calculates the sample standard deviation of percolation threshold.
    public double stddev() {
        double mean = mean();
        double sum = 0.0;
        for (double v : thresholds) {
            sum += (v - mean) * (v - mean);
        }
        return Math.sqrt(sum / (thresholds.length - 1));
    }

    // Calculates the low endpoint of 95% confidence interval.
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // Calculates the high endpoint of 95% confidence interval.
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    // Test client to run the percolation experiments.
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);  // Grid size.
        int trials = Integer.parseInt(args[1]);  // Number of trials.
        PercolationStats ps = new PercolationStats(n, trials);

        // Print the results of the experiments.
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
