package com.trainee;

@SuppressWarnings("unchecked")

public class GenericExample<T>
{
    T thingsToPrint;

    public GenericExample(T thingsToPrint)
    {
        this.thingsToPrint = thingsToPrint;
    }

}