package com.trainee;


/**
 * InnerInterface
 */
interface InnerInterface 
{

    void methodOne();
    
    void methodTwo();

    void methodThree();

    void methodFour();

    void methodFive();

    void methodSix();

    void methodSeven();

    void methodEight();

    void methodNine();

    void methodTen();

    void methodEleven();

}

abstract class AdapterClass implements InnerInterface{
    public void methodOne(){};
    
    public void methodTwo(){};

    public void methodThree(){};

    public void methodFour(){};

    public void methodFive(){};

    public void methodSix(){};

    public void methodSeven(){};

    public void methodEight(){};

    public void methodNine(){};

    public void methodTen(){};

    public void methodEleven(){};
}

public class Inter extends AdapterClass
{
    public void methodOne()
    {
        System.out.println("Hello this is methodOne of Interface");
    }

    public void methodTwo()
    {
        System.out.println("Hello this is methodTwo of Interface");
    }

    public static void main(String[] args) {
        System.out.println("HElloo");
        Inter a = new Inter();
        a.methodOne();
        a.methodTwo();

    }
}
;