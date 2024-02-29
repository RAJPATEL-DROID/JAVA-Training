package concurrency;

public class ThreadExample3 {
    public static class  MyRunnabel implements Runnable {
            @Override 
            public void run()
            {
                System.out.println("MyRunnable is running");
            }        
    }

    public static void main(String[] args) {
           Thread t = new Thread(new MyRunnabel());
           t.start();
    }
}
