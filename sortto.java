package java_feb;

public class sortto {
    class Solution {
    public int removeDuplicates(int[] nums) {
        int j = 1;
        for ( int i = 1; i < nums.length; i++){
            if (nums[i] == nums[i-1]){
                continue;
            }
            nums[j++] = nums[i];
        }
        return j;
        
    }
    public void main(String args[]){
        int[] nums = {1, 2, 3, 4, 4, 4, 4, 5, 6, 4,};

        int newLength = removeDuplicates(nums);
        System.out.println("New length: " + newLength);

        System.out.println("Arrays afer removing duplicates: ");

        for ( int i = 0; i < newLength; i++){
            System.out.println(nums[i] + "");
        }
    }


    }
    
}
