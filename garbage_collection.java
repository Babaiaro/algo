import java.util.Arrays;

public class garbage_collection {

    public static int getMin(int[] nums) {
        // NOTE: this is *not* the fastest way to get the min!
        int[] numsSorted = Arrays.copyOf(nums, nums.length);
        Arrays.sort(numsSorted);
        return numsSorted[0];
    }

    int[] myNums = new int[] {5, 3, 1, 4, 6};
    System.out.println(getMin(myNums));
}
