package com.Streams;

import java.util.*;
import java.util.stream.Collectors;

public class Task
{
    public static void main(String[] args)
    {
        Map<Integer, List<Integer>> mapOfList = new HashMap<>();

        mapOfList.put(1, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        mapOfList.put(2,Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        mapOfList.put(3,Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        mapOfList.put(4,Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        mapOfList.put(5,Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        mapOfList.put(6,Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        mapOfList.put(7,Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        mapOfList.put(8,Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        mapOfList.put(9,Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        mapOfList.put(10,Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        Map<Integer,List<Integer>> ls = mapOfList.entrySet().stream()
                .filter(entry -> entry.getKey() % 2 == 0)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, entry -> entry.getValue().stream()
                        .filter(val -> val%2 != 0)
                        .collect(Collectors.toList())
                        )
                );
        System.out.println(ls);

//        System.out.println(mapOfList);
    }
}
