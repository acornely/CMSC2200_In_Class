public class WorksheetSolutions {
    public static int[] reverseArray(int[] array) {
        int[] reversed = new int[array.length];
        for(int i = 0; i < array.length; i++) {
            reversed[array.length - 1 - i] = array[i];
        }
        return reversed;
    }

    public static int minimum(int[] array) {
        int min = array[0];
        for(int i = 1; i < array.length; i++) {
            if(array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public static boolean isSorted(int[] array) {
        for(int i = 1; i < array.length; i++) {
            if(array[i] < array[i-1]) {
                return false;
            }
        }
        return true;
    }

    //this is an example of overloading!
    public static boolean isSorted(int[] array, boolean ascending) {
        if(ascending) {
            for(int i = 1; i < array.length; i++) {
                if(array[i] < array[i-1]) {
                    return false;
                }
            }
            return true;
        } else {
            for(int i = 1; i < array.length; i++) {
                if(array[i] > array[i-1]) {
                    return false;
                }
            }
            return true;
        }
    }
}