abstract class Computer
{
    abstract public void code();
}

class Laptop extends Computer
{
    @Override
    public void code()
    {
        System.out.println("Coding on laptop...");
    }
}
class Desktop extends Computer
{
    @Override
    public void code()
    {
        System.out.println("Coding on desktop...");
    }
}

class Developer
{
    public void work(Computer comp)
    {
        comp.code();
    }
}
public class abstractionCode {
    public static void main(String[] args)
    {
        Laptop x1carbon = new Laptop();

        Desktop desktop = new Desktop();

        Developer yash = new Developer();

        yash.work(x1carbon);
    }
}
