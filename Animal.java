package oop;

interface Animal {
    public void animalSound();
    public void sleep();
    
}

class Dog implements Animal{
    public void animalSound() {
        System.out.println("gaf gaf gaf");
    }
    public void sleep() {
        System.out.println("ZZzzz");
    }
}

class Main{
    public static void main(String[] args) {
        Dog myDog = new Dog();
        myDog.animalSound();
        myDog.sleep();
    }
}
