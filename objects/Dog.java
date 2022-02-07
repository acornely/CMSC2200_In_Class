public class Dog {
    String name;
    
    static boolean hasTail = true;

    //default constructor
    public Dog() {
        
    }

    //parameterized constructor 
    public Dog(String name) {
        //here name = "Spike" at line 17
        this.name = name;
    }

    public static void main(String[] args) {
        Dog myDog = new Dog("Spike");
        Dog myDog2 = new Dog("Spot");
        Dog myDog3 = new Dog("kdshkasjfdlhsafd");
        System.out.println(myDog.name);
        System.out.println(myDog2.name);
        System.out.println(myDog3.name);
        System.out.println(Dog.hasTail);

    }
}