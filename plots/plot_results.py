import csv
import math
from pathlib import Path
import matplotlib.pyplot as plt

SCRIPT_DIR = Path(__file__).resolve().parent
CSV_PATH = SCRIPT_DIR / "results.csv"

ROWS = []
with open("results.csv", newline="") as f:
    for row in csv.DictReader(f):
        ROWS.append({
            "algorithm": row["algorithm"],
            "input": row["input"],
            "n": int(row["n"]),
            "time_ms": float(row["time_ms"]),
            "comparisons": int(row["comparisons"]),
            "max_depth": int(row["max_depth"]),
        })

SERIES = sorted({(r["algorithm"], r["input"]) for r in ROWS})


def points(algorithm, input_type, value):
    rows = sorted([r for r in ROWS if r["algorithm"] == algorithm and r["input"] == input_type],
                  key=lambda r: r["n"])
    return [r["n"] for r in rows], [value(r) for r in rows]


def ratio(row):
    n = row["n"]
    if row["algorithm"] == "QuickSelect":
        return row["comparisons"] / n
    return row["comparisons"] / (n * math.log2(n))


def draw(filename, title, ylabel, value, log_y):
    plt.figure(figsize=(9, 5))
    for algorithm, input_type in SERIES:
        x, y = points(algorithm, input_type, value)
        plt.plot(x, y, marker="o", label=f"{algorithm} / {input_type}")
    plt.xscale("log")
    if log_y:
        plt.yscale("log")
    plt.xlabel("n")
    plt.ylabel(ylabel)
    plt.title(title)
    plt.grid(True, alpha=0.3)
    plt.legend(fontsize=8)
    plt.tight_layout()
    plt.savefig(filename, dpi=150)
    plt.close()
    print("saved", filename)


draw("time_vs_n.png", "Time vs n", "time, ms", lambda r: r["time_ms"], True)
draw("depth_vs_n.png", "Max recursion depth vs n", "max depth", lambda r: r["max_depth"], False)
draw("ratio_vs_n.png", "Ratio vs n", "comparisons / expected growth", ratio, False)
