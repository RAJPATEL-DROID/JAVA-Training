import java.io.FileReader;

import java.util.Scanner;

class myException extends Exception
{
    public myException(String msg)
    {
        super(msg);
    }
}

public class userException
{
    public static void main (String[] args)
    {

        try {
            int i = 10;
            if (i == 10)
            {
                throw new myException("Custom exception called");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        try(FileReader f = new FileReader("1.txt"); Scanner sc = new Scanner(f))
        {
            // use file
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}