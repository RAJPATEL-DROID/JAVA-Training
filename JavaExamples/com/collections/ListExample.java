package com.collections;

import java.util.ArrayList;
import java.util.Vector;

public class ListExample
{
    public static void main(String[] args)
    {

        ArrayList<Integer> arr = new ArrayList<>();

        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++)
        {
            arr.add(i);
        }
        long end = System.currentTimeMillis();

        System.out.println("Total time : " + (end - start) + " ms");

        Vector<Integer> arr2 = new Vector<>();

        start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++)
        {
            arr2.add(i);
        }
        end = System.currentTimeMillis();

        System.out.println("Total time : " + (end - start) + " ms");


    }

}
