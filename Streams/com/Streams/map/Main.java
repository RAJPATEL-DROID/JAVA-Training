package com.Streams.map;

import javax.swing.text.html.parser.Entity;
import java.util.*;
import java.util.stream.Collectors;

public class Main
{

    public static void main(String[] args)
    {
        Map<String,String > mp = new HashMap<>();

        mp.put("1","Django Unchained");
        mp.put("2", "Inglorious bastards");
        mp.put("3", "Reservoir dogs");

//        Optional<String> s = mp.keySet().stream()
//                .filter(e -> Objects.equals(e, "1"))
//                .map(mp::get)
//                .findFirst();
//        System.out.println(s.isEmpty());
//        System.out.println(s.isPresent());
//        System.out.println(s.get());
//
//        List<String> ls = mp.entrySet().stream()
//                .filter(e -> e.getValue().equals("Reservoir dogs"))
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());
//        System.out.println(ls);

        Map<Integer, Map<String,String> > mapOfMap = new HashMap<>();

        mapOfMap.put(1, mp);
        mapOfMap.get(1).putIfAbsent("4","Aliens");
      List<String> ls =  mapOfMap.entrySet().stream()
                .flatMap( e -> e.getValue()
                        .entrySet()
                        .stream().
                                map(Map.Entry::getValue)
                        .filter(s -> Objects.equals(s,"Aliens"))
                )
               .collect(Collectors.toList());
        System.out.println(ls);

//        Map<String ,String> mp1 = Map.ofEntries(Map.entry("1","django Unchained"),
//                Map.entry("2","Inglorious bastards"),
//                Map.entry("3","Reservoir Dogs"));
//
//
//        String s = mp1.get("1");
////        mp1.remove("1");
//        mp1.put("4","Aliens");
    }

}
