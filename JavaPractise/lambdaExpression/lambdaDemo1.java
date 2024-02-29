package lambdaExpression;

// if a interface has only one method, it is called functional interface
@FunctionalInterface
interface MyLamda
{
    void display();
    //void display1();
}

/* class My implements MyLamda
{
    public void display()
    {
        System.out.println("Hello World");
    }
} */
public class lambdaDemo1
{
    public static void main(String[] args)
    {
        // MyLamda obj = new MyLamda() // inner-class
        // {
        //     public void display()
        //     {
        //         System.out.println("Hello World");
        //     }
        // };

        MyLamda obj = () -> // lambda-expression
        {
            System.out.println("Hello World");
        };
        
        obj.display();
    }
}
