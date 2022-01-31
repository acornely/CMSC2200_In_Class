public class Dog {
    String name;
    boolean hasTail;

    public static void main(String[] args) {
        Dog myDog = new Dog();
        myDog.name = "Spike";
        myDog.hasTail = true;
        System.out.println(myDog.name);
    }
}