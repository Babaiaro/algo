import java.util.Scanner;

public class fromRoman {
    
    public static int romanToInt(String s) {
        int total = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            int current = value(s.charAt(i));
            int next = (i + 1 < n) ? value(s.charAt(i + 1)) : 0;
            if (current < next) {
                total -= current;
            } else {
                total += current;
            }
        }
        return total;
    }
    
    private static int value(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }
    
    public static void main(String[] args) {
        Scanner v = new Scanner(System.in);
        System.out.println("What is your Roman number? ");
        String var = v.next().toUpperCase();   // accept lowercase too
        v.close();
        
        int total = romanToInt(var);
        System.out.println(var + " = " + total);
    }
}