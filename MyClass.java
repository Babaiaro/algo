import java.util.Arrays;

public class MyClass {
    public static void main(String[] args){
        int[] x = {1, 2};
        int[] y = {3, 4};
        int[] a = {1, 2};
        int[] z = x.clone();
        int[] w = x.clone();

        modifyPrimitiveTypes(z, w);  // ← pass both arrays

        if (isEqual(x, a)){
            System.out.println("Values of x & a are Equal");
        } else {
            System.out.println("Values of x & a are not Equal");
        }
    }

    private static boolean isEqual(int[] a, int[] b){
        if (a.length != b.length)  // ← remove semicolon
            return false;

        for(int i = 0; i < a.length; i++){
            if (a[i] != b[i])  // ← remove semicolon
                return false;
        }
        return true;
    }

    private static void modifyPrimitiveTypes(int[] x, int[] y){
        x[0] = 0;
        y[0] = 10;
        System.out.println("x= " + Arrays.toString(x) + "; y= " + Arrays.toString(y));
    }
}
