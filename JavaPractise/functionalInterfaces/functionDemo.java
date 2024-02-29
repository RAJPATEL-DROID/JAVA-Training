//package functionalInterfaces;

import java.util.function.Function;

public class functionDemo
{
    // functional interfaces
    // 3) Function --> input, output
    // methods available: apply()
    public static void main(String[] args)
    {

        Function<Integer, String> getInt = t -> t * 10 + "\n==> data is multiplied by 10";

        System.out.println(getInt.apply(2));

    }
}
