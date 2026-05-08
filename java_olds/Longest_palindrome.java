package java_olds;

import java.util.HashSet;

public class Longest_palindrome {
        public int longestPalindrome(String s) {
            if (s == null || s.length() == 0){
                return 0;
            }
            HashSet<Character> hash_set = new HashSet<Character>();
            int palindrome_length = 0;
            for (char ch : s.toCharArray()){
                if (hash_set.contains(ch)){
                    hash_set.remove(ch);
                    palindrome_length += 2;

                }else{
                    hash_set.add(ch);
                }
            }
            if(!hash_set.isEmpty()){
                palindrome_length++;
            }
            return palindrome_length;
        }

      public static void main(String[] args) {
        
            Longest_palindrome solver = new Longest_palindrome();
            String s = "abccccdd";
            
            // Call the method and store the integer result
            int result = solver.longestPalindrome(s);
            
            System.out.println("Longest Palindrome Length: " + result);
        }
    

    
}
