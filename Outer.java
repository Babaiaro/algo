package oop;

public class Outer {
    int y = 15;
    public class Inner {
        int x = 25;
    }

    public static void main(String[] args) {
        Outer myOuter = new Outer();
        Outer.Inner myInner = myOuter.new Inner(); 
        System.out.println(myInner.x + " and " + myOuter.y);

    }
    
}



