public class MathExample {
    int x = 3;
    int y = 4;
    public int add(int a, int b) {
        return a + b;
    }
    public static int addStatic(int a, int b) {
        return a + b;
    }
    public int subtract(int a, int b) {
        return a - b;
    }
    public static int subtractStatic(int a, int b) {
        return a - b;
    }

    public static void main(String[] args) {
        MathExample ex = new MathExample();
        System.out.println(String.valueOf(ex.add(ex.x,ex.y)));
        System.out.println(String.valueOf(addStatic(3,4)));
        System.out.println(String.valueOf(ex.subtract(ex.x,ex.y)));
        System.out.println(String.valueOf(subtractStatic(3,4)));
    }
}