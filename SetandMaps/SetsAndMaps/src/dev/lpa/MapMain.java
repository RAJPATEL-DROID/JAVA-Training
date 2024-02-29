package dev.lpa;

import java.util.*;

public class MapMain {

    public static void main(String[] args) {

        List<Contact> phones = ContactData.getData("phone");
        List<Contact> emails = ContactData.getData("email");

        List<Contact> fullList = new ArrayList<>(phones);
        fullList.addAll(emails);
        fullList.forEach(System.out::println);
        System.out.println("-----------------------------");

        Map<String, Contact> contacts = new HashMap<>();

        for (Contact contact : fullList) {
            contacts.put(contact.getName(), contact);
        }
        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));
//
//        System.out.println("-----------------------------");
//        System.out.println(contacts.get("Charlie Brown"));
//
//        System.out.println(contacts.get("Chuck Brown"));
//
//        Contact defaultContact = new Contact("Chuck Brown");
//        System.out.println(contacts.getOrDefault("Chuck Brown", defaultContact));
//
        System.out.println("-----------------------------");
        contacts.clear();
        for (Contact contact : fullList) {
            Contact duplicate = contacts.put(contact.getName(), contact);
            if (duplicate != null) {
//                System.out.println("current = " + contact);
//                System.out.println("duplicate = " + duplicate);
                contacts.put(contact.getName(), contact.mergeContactData(duplicate));
            }
        }
        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));

//        System.out.println("-----------------------------");
//        contacts.clear();
//
//        for (Contact contact : fullList) {
//            contacts.putIfAbsent(contact.getName(), contact);
//        }
//        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));
//
//        System.out.println("-----------------------------");
//        contacts.clear();
//
//        for (Contact contact : fullList) {
//            Contact duplicate = contacts.putIfAbsent(contact.getName(), contact);
//            if (duplicate != null) {
//                contacts.put(contact.getName(), contact.mergeContactData(duplicate));
//            }
//        }
//        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));
//
//        System.out.println("-----------------------------");
//        contacts.clear();
//        fullList.forEach(contact -> contacts.merge(contact.getName(), contact,
//                Contact::mergeContactData
//                ));
//        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));
//
//        System.out.println("-----------------------------");
//        for (String contactName : new String[] {"Daisy Duck", "Daffy Duck",
//                "Scrooge McDuck"}) {
//            contacts.computeIfAbsent(contactName, k -> new Contact(k));
//        }
//        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));
//
//        System.out.println("-----------------------------");
//        for (String contactName : new String[] {"Daisy Duck", "Daffy Duck",
//                "Scrooge McDuck"}) {
//            contacts.computeIfPresent(contactName, (k, v) -> {
//                v.addEmail("Fun Place"); return v; });
//        }
//        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));
//
//        System.out.println("-----------------------------");
//        contacts.replaceAll((k, v) -> {
//            String newEmail = k.replaceAll(" ", "") + "@funplace.com";
//            v.replaceEmailIfExists("DDuck@funplace.com", newEmail);
//            return v;
//        });
//        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));
//
//        System.out.println("-----------------------------");
//        Contact daisy = new Contact("Daisy Jane Duck", "daisyj@duck.com");
//
//        Contact replacedContact = contacts.replace("Daisy Duck", daisy);
//        System.out.println("daisy = " + daisy);
//        System.out.println("replacedContact = " + replacedContact);
//        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));
//
//        System.out.println("-----------------------------");
//        Contact updatedDaisy = replacedContact.mergeContactData(daisy);
//        System.out.println("updatedDaisy = " + updatedDaisy);
//        boolean success = contacts.replace("Daisy Duck", daisy,
//                updatedDaisy);
//        if (success) {
//            System.out.println("Successfully replaced element");
//        } else {
//            System.out.printf("Did not match on both key: %s and value: %s %n"
//                    .formatted("Daisy Duck", replacedContact));
//        }
//        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));
//
//        System.out.println("-----------------------------");
//        success = contacts.remove("Daisy Duck", daisy);
//        if (success) {
//            System.out.println("Successfully removed element");
//        } else {
//            System.out.printf("Did not match on both key: %s and value: %s %n"
//                    .formatted("Daisy Duck", daisy));
//        }
//        contacts.forEach((k, v) -> System.out.println("key=" + k + ", value= " + v));


        HashMap<Integer,Integer> mp = new HashMap<>();
        mp.put(10,21);
        mp.put(20,12);
        mp.put(30,31);

//        for(Integer it : mp.keySet()){
//
//            if(mp.get(it) == 21){
//                mp.remove(it);
//            }
//        }
//        Iterator<Integer> itr = mp.keySet().iterator();
//        while(itr.hasNext()){
//            if(itr.next() == 20){
//                itr.remove();
//            }
//        }

//        for(Iterator<Integer> itr = mp.keySet().iterator(); itr.hasNext();){
//            if(itr.next() == 20){
//                itr.remove();
//            }
//        }
//
//        for(Integer it : mp.keySet())
//        {
//            System.out.println(mp.get(it));
//        }
//
//        System.out.println(mp);
        System.out.println(user.dir);
    }
}
