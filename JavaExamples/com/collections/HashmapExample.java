package com.collections;

import java.util.HashMap;
import java.util.Map;

public class HashmapExample
{
    static String getStart(Map<String, String> mp)
    {
        Map<String, String> rev = new HashMap<>();

        for(String key : mp.keySet())
        {
            rev.put(mp.get(key), key);
        }
        for(String key : mp.keySet())
        {
            if(!rev.containsKey(key))
            {
                return key;
            }
        }
        return null;
    }

    static void PrintIternary(Map<String, String> mp)
    {
        String start = getStart(mp);

        while(mp.containsKey(start))
        {
            System.out.print(start + " -> ");
            start = mp.get(start);
        }
        System.out.println(start);
    }

    public static void main(String[] args)
    {
        Map<String, String> mp = new HashMap<>();
        mp.put("Chennai", "Bengaluru");
        mp.put("Mumbai", "Delhi");
        mp.put("Goa", "Chennai");
        mp.put("Delhi", "Goa");

        PrintIternary(mp);
    }


}

