package java_feb.algo1;

import java.util.Arrays;

public class Dimension { // Standard Java naming: Classes start with Uppercase
    
    public int[][] construct2DArray(int[] original, int m, int n) {
        // 1. Validation: Does the 1D array actually fit into an m x n grid?
        if (original.length != m * n) {
            return new int[0][0];
        }
        
        int[][] result = new int[m][n];
        
        // 2. Mapping logic
        for (int i = 0; i < original.length; i++) {
            result[i / n][i % n] = original[i];
        }
        
        return result;
    }

    public static void main(String[] args) {
        Dimension solver = new Dimension();
        
        int[] original = {1, 2, 3, 4, 5, 6,7,8};
        int m = 4; // rows
        int n = 2; // columns

        int[][] result = solver.construct2DArray(original, m, n);

        // Print the 2D array properly
        System.out.println(Arrays.deepToString(result));
    }

}