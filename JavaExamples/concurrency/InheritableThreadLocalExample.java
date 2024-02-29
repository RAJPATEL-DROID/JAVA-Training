package concurrency;

public class InheritableThreadLocalExample {
    public static void main(String[] args) {
        
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
        
        Thread thread1 = new Thread(() -> {
            System.out.println("Thread 1");
            threadLocal.set("ThreadL0cal -1");
            inheritableThreadLocal.set("Inheritable Thread -1 ");
            
            System.out.println(threadLocal.get());
            System.out.println(inheritableThreadLocal.get());
            
            Thread childThread = new Thread( ()->{
                System.out.println("Child Thread");
                System.out.println(threadLocal.get());
                System.out.println(inheritableThreadLocal.get());
            });
            childThread.start();
        }
        );
        
        thread1.start();
        
        Thread thread2 = new Thread(() -> {
            threadLocal.set("Thread 2 Local");
            inheritableThreadLocal.set("Thread2 Inheritable Local");
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            
            System.out.println("Thread 2");
            System.out.println(threadLocal.get());
            System.out.println(inheritableThreadLocal.get());
        }); 
        
        thread2.start();
    }
        
}
