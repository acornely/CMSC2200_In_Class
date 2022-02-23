public class Circle {
    double radius;
    double area;
    double circumference;

    public Circle(double radius) {
        this.radius = radius;
    }

    public void circumference() {
        circumference = 2 * 3.14 * radius;
    }

    public void area() {
        area = 3.14 * radius * radius;
    }

    public static double circumference(double radius) {
        return 2 * 3.14 * radius;
    }

    public static double area(double radius) {
        return 3.14 * radius * radius;
    }

    public static void main(String[] args) {
        Circle circle1 = new Circle(2.5);
        circle1.circumference();
        circle1.area();
        System.out.println(circle1.circumference);
        System.out.println(circle1.area);
        //note we don't HAVE to do Circle.circumference, since we're in the class itself
        double circumference = circumference(2.5);
        double area = area(2.5);
        System.out.println(circumference);
        System.out.println(area);
    }
}
