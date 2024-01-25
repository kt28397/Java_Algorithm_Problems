# Percolation

## Description
This project implements the Percolation model using a grid-based approach. It includes a simulation of percolation using a weighted quick union-find data structure to efficiently determine if the system percolates. Additionally, the project contains a statistical analysis class (`PercolationStats`) to compute the percolation threshold over multiple trials.

## Features
- `Percolation`: Class to model a percolation system.
- `PercolationStats`: Class to perform computational experiments and analyze percolation threshold.
- `WeightedQuickUnionUF`: Custom implementation of the weighted quick union-find algorithm.

## How to Run
1. Compile the Java files:
   ```bash
   javac Percolation.java PercolationStats.java WeightedQuickUnionUF.java
   java PercolationStats <grid size> <number of trials>

Example:
java PercolationStats 200 100

