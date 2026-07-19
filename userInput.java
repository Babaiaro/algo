package oop;

import java.util.Scanner;

public class userInput {
    public static void main(String[] args) {
        Scanner sr = new Scanner(System.in);
        System.out.println("Your name, age and salary: ");

        String name = sr.nextLine();
        int age = sr.nextInt();
        double salary = sr.nextDouble();

        System.out.println("Your name is: " + name);
        System.out.println("Your age is: " + age);
        System.out.println("Your salary is: " + salary);
    }
    
}
