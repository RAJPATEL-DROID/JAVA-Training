//package functionalInterfaces;

import java.util.function.Consumer;

class Person {
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

public class consumerDemo
{
    // functional interfaces
    // 2) Customer --> modifies data, no output
    // methods available: accept()
    public static void main(String[] args)
    {
        Person p1 = new Person();

        Consumer<Person> setName = t -> t.setName("Yash Prajapati");

        setName.accept(p1);

        System.out.println(p1.getName());

    }
}
