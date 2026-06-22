package jun.oop.inheritance;

class Aminal {
    public void Animalsound(){
        System.out.println("Animal makes sound");
    }   
}

class Dog extends Aminal{
    public void DogSound(){
        System.out.println("Gaf Gaf");
    }
}

class Cow extends Aminal{
    public void CowSound(){
        System.out.println("MOO MOO");
    }
}

class Main{
    public static void main(String[] args) {
        Aminal aSound = new Aminal();
        Dog dSound = new Dog();
        Cow cSound = new Cow();
        aSound.Animalsound();
        dSound.DogSound();
        cSound.CowSound();
    }
}