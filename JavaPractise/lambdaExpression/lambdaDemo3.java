//package lambdaExpression;

@FunctionalInterface
interface InnerlambdaDemo3
{
    void reverse(String s);
}

public class lambdaDemo3
{
    lambdaDemo3()
    {
        System.out.println("Constructor Initiated");
    }

    lambdaDemo3(String s)
    {
        System.out.println(s.toUpperCase());
    }

    public static void reverse(String s)
    {
        StringBuffer sb = new StringBuffer(s);

        sb.reverse();

        System.out.println(sb);

    }

    public void toLower(String s)
    {
        System.out.println(s.toLowerCase());
    }

    public static void main(String[] args)
    {
        // for static methods
        InnerlambdaDemo3 obj = lambdaDemo3::reverse;

        obj.reverse("Yash Prajapati");

        // for non-static methods
        lambdaDemo3 classObj = new lambdaDemo3();

        InnerlambdaDemo3 obj1 = classObj::toLower;

        obj1.reverse("YASH");

        // reference to constructor
        InnerlambdaDemo3 obj3 = lambdaDemo3::new;
        obj3.reverse("my-name-is-yash");

    }
}
