public class sudoku {
    public static void main(String[] args) {
        int[][] board = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        if (solve(board)) print(board);
    }

    private static boolean solve(int[][] b) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (b[r][c] == 0) {
                    for (int n = 1; n <= 9; n++) {
                        if (isValid(b, r, c, n)) {
                            b[r][c] = n;
                            if (solve(b)) return true;
                            b[r][c] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValid(int[][] b, int r, int c, int n) {
        for (int i = 0; i < 9; i++) {
            if (b[r][i] == n || b[i][c] == n) {
                return false;
            }
            if (b[3 * (r / 3) + i / 3][3 * (c / 3) + i % 3] == n) {
                return false;
            }
        }
        return true;
    }

    private static void print(int[][] b) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                System.out.print(b[r][c] + " ");
            }
            System.out.println();
        }
    }
}

