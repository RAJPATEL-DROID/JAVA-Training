//package functionalInterfaces;

import java.util.function.Supplier;

public class supplierDemo
{
    // functional interfaces
    // 4) Supplier --> only output
    // methods available: get()
    public static void main(String[] args)
    {
        Supplier<Double> getRandomDouble = () -> Math.random();

        System.out.println(getRandomDouble.get());

    }
}
