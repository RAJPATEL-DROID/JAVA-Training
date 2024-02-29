package concurrency;

public class ThreadExample4
{
    public static void main(String[] args)
    {
        Runnable runnable = () -> {
            System.out.println("Runnable Running");
            try
            {
                Thread.sleep(2000);
            } catch(InterruptedException e)
            {
                System.out.println(e.getMessage());
            }
            System.out.println(Thread.currentThread().getName());

        };
        Thread t = new Thread(runnable, "New Thread");
        t.start();
        System.out.println(t.getName());
        System.out.println(Thread.currentThread().getName());
    }

}
