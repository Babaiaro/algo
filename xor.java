package java_feb;

public class xor {
    public int findOddOccurance(int[] nums){
        int result = 0;
        
        for(int x : nums){
            result = result ^ x;
        }
        System.out.println(result);
        
        return result;
    }
    
}
