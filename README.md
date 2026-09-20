# Assignment 1 — Divide and Conquer & Asymptotic Notations

Fast sorting and selection engine: MergeSort, QuickSort and QuickSelect with metrics
(comparisons, maximum recursion depth, time) and a benchmark that exports `results.csv`.

## Project structure

```
src/main/java/algorithms/
    Metrics.java              counters: comparisons, max depth, time
    InsertionSort.java        cutoff for MergeSort, groups of 5 for the deterministic select
    MergeSort.java            one reusable buffer + cutoff 15 + linear merge
    Partition.java            3-way partition, shared by QuickSort and both selects
    QuickSort.java            random pivot, smaller side first
    QuickSelect.java          k-th smallest element, one side only
    DeterministicSelect.java  bonus A: median of medians
    Point.java, ClosestPair.java   bonus B: closest pair of points
src/main/java/benchmark/
    BenchmarkRunner.java      main benchmark, writes results.csv
    SelectComparison.java     bonus A: QuickSelect vs DeterministicSelect
src/test/java/algorithms/     JUnit 5 tests
plots/plot_results.py         builds the three PNG plots from results.csv
```

## Build

```bash
mvn clean package
```

Requires Java 17+ and Maven.

## Run the tests

```bash
mvn test
```

## Run the benchmark (creates results.csv)

```bash
mvn compile exec:java
```

The runner uses sizes 1 000, 10 000, 100 000, 1 000 000 and the input types
`random`, `sorted`, `duplicates`. Every case is executed 5 times and the run with the
median time is written to `results.csv`
(columns: `algorithm,input,n,time_ms,comparisons,max_depth`).

Bonus comparison of the two select algorithms:

```bash
mvn compile exec:java -Dexec.mainClass=benchmark.SelectComparison
```

## Build the plots

```bash
pip install matplotlib
python plots/plot_results.py
```

This creates `plots/time_vs_n.png`, `plots/depth_vs_n.png` and `plots/ratio_vs_n.png`.

## Report

The analysis (asymptotic bounds, recurrences, Master Theorem, Θ check and discussion)
is in `REPORT.md`.

## Git workflow

Feature branches: `feature/mergesort`, `feature/quicksort`, `feature/select`,
`feature/metrics`. Only working code is merged into `main`, the final state is tagged `v1.0`.
