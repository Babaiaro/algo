import java.util.Arrays;
import java.util.Scanner;

public class Rotation {
    
    public void rotate(int[] nums, int k) {
        int n = nums.length;

        k %= n;

        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;

            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Rotation rotator = new Rotation();

        // 1. Ask for the size of the array
        System.out.print("Enter the number of elements in your array: ");
        int size = scanner.nextInt();
        int[] nums = new int[size];

        // 2. Ask for the actual elements
        System.out.println("Enter the " + size + " elements (press Enter after each one):");
        for (int i = 0; i < size; i++) {
            nums[i] = scanner.nextInt();
        }

        // 3. Ask for the rotation steps (k)
        System.out.print("Enter the number of steps (k) to rotate by: ");
        int k = scanner.nextInt();

        // Show the original array before running the algorithm
        System.out.println("\nOriginal array: " + Arrays.toString(nums));

        // Run the rotation
        rotator.rotate(nums, k);

        // 4. Output the final result
        System.out.println("Rotated array:  " + Arrays.toString(nums));

        // Close the scanner to prevent memory leaks
        scanner.close();
    }
}
