# algo

Java practice code: language fundamentals, small programs, and LeetCode-style problems.

## What's in here

| Location | Contents |
|---|---|
| Root folder | Java basics (classes, attributes, `this`, enums, lambdas, threads), exception handling (`try`/`catch`/`finally`, custom exceptions, file errors), and array problems (merge sorted array, sort by parity, sorting) |
| `jev/` | Small standalone programs: Wordle, password strength analyzer, Roman numeral conversion, array rotation |
| `jav/` | Student management classes and a simple neural network |
| `java_olds/` | Older exercises: palindromes, anagrams, Fibonacci, hashing, partitioning, Sudoku |

## Programs worth trying

- `Wordle.java` — Wordle in the terminal
- `jev/PasswordAnalyzer.java` — estimates password strength from its entropy
- `sudoku.java` — works on a 9x9 Sudoku board
- `toRoman.java` / `fromRoman.java` — convert between integers and Roman numerals

## Running a file

Most files are self-contained. Compile one, then run the class it defines:

```sh
javac Wordle.java
java Wordle
```

Files that start with a `package` line (for example `checking_account.java`) need to be compiled from a matching folder structure.
