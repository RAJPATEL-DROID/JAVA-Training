package com.trainee;

public class SingletonExample {

    private static SingletonExample s = null;

    private SingletonExample() {
    };

    private static SingletonExample getSingletonExample() {
        if (s == null) {

            s = new SingletonExample();
            return s;
        }
        return s;
    }

    public Object clone() {
        return this;
    }
}