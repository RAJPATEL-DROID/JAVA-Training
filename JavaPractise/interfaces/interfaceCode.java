interface Computer
{
    int age = 18;
    String cmpName = "motadata";
    void getData();
    void code();
}

class Laptop implements Computer
{
    public void code()
    {
        System.out.println("Coding on laptop...");
    }

    public void getData()
    {
        System.out.println(age);
        System.out.println(cmpName);
    }
}
class Desktop implements Computer
{
    public void code()
    {
        System.out.println("Coding on desktop...");
    }
    public void getData()
    {
        System.out.println(age);
        System.out.println(cmpName);
    }
}

class Developer
{
    public void work(Computer comp)
    {
        comp.code();
    }
    public void getData(int age, String cmpName)
    {
        System.out.println(age);
        System.out.println(cmpName);
    }
}

public class interfaceCode {
    public static void main(String[] args)
    {
        Laptop x1carbon = new Laptop();
        Desktop desktop = new Desktop();
        Developer yash = new Developer();
        
        yash.work(x1carbon);
        yash.work(desktop);

        yash.getData();
    }    
}