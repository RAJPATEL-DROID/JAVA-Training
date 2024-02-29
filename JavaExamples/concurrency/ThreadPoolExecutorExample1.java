package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExecutorExample1 {

     public static void main(String[] args) {
            ExecutorService  executorService = Executors.newFixedThreadPool(10);
            
            executorService.execute(newRunnable("Task3"));
            executorService.execute(newRunnable("Task1"));
            executorService.execute(newRunnable("Task2"));

            executorService.shutdown();
     }

     private static Runnable newRunnable(String msg){
        return new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                String completeMsg = Thread.currentThread().getName() + " :" + msg;
                
                System.out.println(completeMsg);
            }
        };
     }
}