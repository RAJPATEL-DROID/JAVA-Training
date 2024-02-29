//package functionalInterfaces;

import java.util.function.Predicate;

public class predicateDemo
{
    // functional interfaces
    // 1) Predicate --> boolean result
    // methods available: test()
    public static void main(String[] args)
    {
        // true if str.length() > 5, else false
        Predicate<String> checkLength = str -> str.length() > 5;

        System.out.println(checkLength.test("Yash")); // false

        System.out.println(checkLength.test("Prajapati")); // true


    }
}
