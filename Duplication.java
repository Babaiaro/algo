package java_feb.algo1;

import java.util.Arrays;

public class Duplication {
    public int rmDuplicates(int[] nums, int k){
        int insertPosition = k;

        for ( int i = k; i < nums.length; i++){
            if(nums[i] != nums[insertPosition - k]){
                nums[insertPosition] = nums[i];
                insertPosition++;

            }

        }
        return insertPosition;
    }
    public static void main(String args[]){
        Duplication solver = new Duplication();

        int[] nums = {1, 1, 1, 1, 2, 3, 3, 3, 3, 4, 5, 6,7,8};
        int k = 2;
 
        int newLength = solver.rmDuplicates(nums, k);

        System.out.println("The new length is: " + newLength);
        System.out.println("Modified array: " + Arrays.toString(Arrays.copyOf(nums, newLength)));
    }
    
}
