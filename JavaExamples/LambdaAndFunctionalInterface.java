
@FunctionalInterface
interface interf{
    void sum(int a,int b);
    default int square(int x){
        return x*x;
    }
}

/**
 *  MyLambda
 */
interface  MyLambda {

    void display(String s);
}
public class LambdaAndFunctionalInterface{
    public void reverse(String str) {
        StringBuffer sb = new StringBuffer(str);
        sb.reverse();
        System.err.println(sb);

    }
    public static void main(String[] args) {
        interf i = (a,b)->{ // Lambda bFunction is used with functional interface
            System.out.println("hello this is lambda function");
            System.out.println(a+b);
        };
        i.sum(10,2);
        System.out.println(i.square(10));

        // Method referencing
        MyLambda m = System.out::println;
        m.display("Hello");
        
        LambdaAndFunctionalInterface ld = new LambdaAndFunctionalInterface();
        MyLambda ml = ld::reverse;
        ml.display("Hello");

    }
}
