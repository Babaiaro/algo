package java_feb.algo1;

import java.util.Arrays;

public class Partitioning {
        public int[] pivotArray(int[] nums, int pivot){
            int n = nums.length;

            int[] result = new int[n];
            int insertPosition = 0;
            int pivotFreq = 0;

            for (int num : nums){
                if (num < pivot){
                    result[insertPosition] = num;
                    insertPosition++;
                }else if(num == pivot){
                    pivotFreq++;
                }
            }

            while (pivotFreq > 0){
                result[insertPosition++] = pivot;
                pivotFreq--;
            }
            
            for (int num : nums){
                if (num > pivot){
                    result[insertPosition++] = num;
                }

            }
            return result; 
        }

        public static void main(String[] args){
            int[] nums = {1, 2, 3, 5, 2, 2, 3,8,99,6,5,3,5,4,121,89,};
            int pivot = 5;

            Partitioning solver = new Partitioning();

            int[] newLength = solver.pivotArray(nums, pivot); 

            System.out.println("The new list after partition" + Arrays.toString(newLength));


        }
    

    
    
}
