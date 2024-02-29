/**
 *  NewException
 */
class  NewException extends RuntimeException {

    NewException(String s)
    {
        super(s);
    }
}

class NewException2 extends RuntimeException
{
    NewException2(String s)
    {
        super(s);
    }
}



public class CustomizedException {
    public static void main (String[] args)  throws NewException,NewException2
    {
        
        String temp = "55";
        
        int age = Integer.parseInt(temp);

        if( age < 27)
        {
            throw new NewException("Please wait for some more time to get married");

        }
        else if( age >= 55)
        {
            throw new NewException2("Your time is up grandpa,go live alone!!");
        }
        else
        {
            System.out.println("Keep Trying :)");
        }
    }
}
