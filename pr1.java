package java_feb.algo1;

public class pr1 {
    public static void main(String args[]){
  int[] numbers = {-4, -6, -1, 0, 1, -4, 2, 5, 8, 9,};

  int j = 1;

  for ( int i = 0; i < numbers.length; i++){
    if (numbers[i] < 0){
        continue;
    }
    numbers[j] = numbers[i];
    j++;

  }
  System.out.println(j);
}
    
}
