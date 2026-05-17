
    // Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.util.Scanner;

public class toRoman {
   public toRoman() {
   }

   public static void main(String[] var0) {
      Scanner var1 = new Scanner(System.in);
      System.out.print("What number do you want me to convert to Roman? ");
      int var2 = var1.nextInt();
      var1.close();
      if (var2 >= 1 && var2 <= 3999) {
         System.out.println(var2 + " in Roman numerals is: " + toRoman(var2));
      } else {
         System.out.println("Please enter a number between 1 and 3999.");
      }
   }

   static String toRoman(int var0) {
      int[] var1 = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
      String[] var2 = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
      StringBuilder var3 = new StringBuilder();

      for(int var4 = 0; var4 < var1.length; ++var4) {
         while(var0 >= var1[var4]) {
            var3.append(var2[var4]);
            var0 -= var1[var4];
         }
      }

      return var3.toString();
   }
}

