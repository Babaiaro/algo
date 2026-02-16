package java_feb.algo1;

public class Sudoku {
    // The 9x9 grid to store Sudoku values (1-9)
    private int[][] grid;

    // Constructor to load the puzzle into the class
    public Sudoku(int[][] grid) {
        this.grid = grid;
    }

    /**
     * Predicate function: Returns 1 if the value at (i, j) is n, else 0.
     */
    private int p(int i, int j, int n) {
        // Standard Sudoku values are 1-9. 
        // We check if the grid cell matches the number we're looking for.
        return (grid[i][j] == n) ? 1 : 0;
    }

    /**
     * Validates that every column (0-8) contains every number (1-9).
     * Returns 1 if valid, 0 if a number is missing in any column.
     */
    public int everyColumnContainsEveryNumber() {
        int every_column_contains_every_number = 1;

        // Loop through each column (j)
        for (int j = 0; j < 9; j++) {

            int every_number_is_in_the_column = 1;
            
            // Loop through each required number (n) from 1 to 9
            for (int n = 1; n <= 9; n++) {
                
                int number_n_is_in_the_column = 0;

                // Loop through each row (i) in the current column
                for (int i = 0; i < 9; i++) {
                    // Bitwise OR: if p returns 1 once, the variable stays 1
                    number_n_is_in_the_column |= p(i, j, n);
                }

                // Bitwise AND: if any number_n is 0, this becomes 0
                every_number_is_in_the_column &= number_n_is_in_the_column;
            }

            // Bitwise AND: if any column is incomplete, the whole result is 0
            every_column_contains_every_number &= every_number_is_in_the_column;
        }
        return every_column_contains_every_number;
    }
    public static void main(String[] args) {
    int[][] myBoard = {
        {5,3,4,6,7,8,9,1,2},
        {6,7,2,1,9,5,3,4,8},
        {1,9,8,3,4,2,5,6,7},
        {8,5,9,7,6,1,4,2,3},
        {4,2,6,8,5,3,7,9,1},
        {7,1,3,9,2,4,8,5,6},
        {9,6,1,5,3,7,2,8,4},
        {2,8,7,4,1,9,6,3,5},
        {3,4,5,2,8,6,1,7,9}
    };

    Sudoku game = new Sudoku(myBoard);
    if (game.everyColumnContainsEveryNumber() == 1) {
        System.out.println("All columns are valid!");
    } else {
        System.out.println("Validation failed.");
    }
}


    
    
}
