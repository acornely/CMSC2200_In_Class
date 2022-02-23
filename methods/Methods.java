import Math;
public class Methods {
    public static boolean isEven(int a) {
        if(a % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isPositive(int a) {
        if(a > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public static double pythagoreanTheorem(double a, double b, double c) {
        if(a == 0) {
            return Math.sqrt(c*c - b*b);
        } else if (b == 0) {
            return Math.sqrt(c*c - a*a);
        } else {
            return Math.sqrt(a*a + b*b);
        }
    }

    public static void main(String[] args) {
        System.out.println(isEven(4));
        System.out.println(isPositive(7));
        System.out.println(pythagoreanTheorem(1,2,0));
    }
}
