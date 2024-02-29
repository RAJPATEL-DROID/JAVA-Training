//package lambdaExpression;

// if a interface has only one method, it is called functional interface
@FunctionalInterface
interface MyLamda
{
    int add(int a, int b);
}
public class lambdaDemo2
{
    public static void main(String[] args)
    {
        MyLamda obj = (a,b) -> a+b;

        System.out.println(obj.add(1,2));
        
    }
}
