package concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class ThreadPoolExecutorExample2 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            String threadName = "Task: " + i;
            service.execute(newRunnable(threadName));
        }

        service.shutdown();
    }
    private static Runnable newRunnable(String msg){
        return new Runnable() {
            @Override
            public void run(){
                System.out.println("Thread Name : " + Thread.currentThread().getName() + " task : "  + msg);
            }
        };    
    }
}