import java.util.Scanner;

public class Test
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        try
        {
            //            String name = System.console().readLine("Hi, How are you ?");
            String name = sc.nextLine();
            System.out.println("Name + " + name);
        } catch(NullPointerException e)
        {
            System.out.println("Run from the terminal");
        }


    }
}
