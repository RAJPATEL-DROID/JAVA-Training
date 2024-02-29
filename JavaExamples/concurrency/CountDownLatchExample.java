package concurrency;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        
        new Thread(new Service(countDownLatch, 3)).start();
        new Thread(new Service(countDownLatch, 4)).start();
        new Thread(new Service(countDownLatch, 6)).start();

        countDownLatch.await();
        System.out.println("Main Thread Completed");
    }

    static class Service implements Runnable
    {
        private CountDownLatch countDownLatch;
        private int waitTime;
        public Service(CountDownLatch countDownLatch, int waitTime){
            this.countDownLatch = countDownLatch;
            this.waitTime = waitTime;
        }

        @Override
        public void run(){
         System.err.println("Service Started : " + Thread.currentThread().getName());
         
         try {
            Thread.sleep(waitTime);
         } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
         }
         System.out.println("Serive Completed : "  + Thread.currentThread().getName());

         countDownLatch.countDown();
        }
    }
}
