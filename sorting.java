import java.util.Scanner;
import java.util.Arrays;

public class sorting {

        public void sortColors(int[] nums) {
            int zeroInsertPos = 0;
            int twoInsertPos = nums.length - 1;
            int i = 0;
            while (i <= twoInsertPos){
                if (nums[i] == 0){
                    swap(nums, zeroInsertPos, i);
                    zeroInsertPos++;
                    i++;
                }else if(nums[i] == 1){
                    i++;
                }else{
                    swap(nums, i, twoInsertPos);
                    twoInsertPos--;
                }
            }



        }
        private void swap(int[] nums, int i, int j){
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        public static void main(String[] args){
            Scanner scanner = new Scanner(System.in);
            sorting sr = new sorting();

            System.out.println("Give me the size of the numbers: ");
            int size = scanner.nextInt();
            int[] nums = new int[size];

            System.out.println("Now enter the numbers (0,1 and 2): ");
            for (int i = 0; i < size; i++){
                nums[i] = scanner.nextInt();
            }

            sr.sortColors(nums);
            System.out.println(Arrays.toString(nums));

        }


}
