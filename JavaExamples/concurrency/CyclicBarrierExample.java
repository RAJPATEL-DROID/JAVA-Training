package concurrency;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample{
    public static void main(String[] args) {
        Runnable barrierAction1 = new Runnable() {
            @Override
            public void run(){
                System.out.println("Barrier Action 1 executed");
            }
        };

        Runnable barrierAction2 = new Runnable() {
            @Override
            public void run(){
                System.out.println("Barrier Action 2 executed");
            }
        };
        
        CyclicBarrier barrier1 = new CyclicBarrier(2, barrierAction1);
        CyclicBarrier barrier2 = new CyclicBarrier(2,barrierAction2);

        CyclicBarrierRunnable barrierRunnable1 = new CyclicBarrierRunnable(barrier1,barrier2);
        CyclicBarrierRunnable barrierRunnable2 = new CyclicBarrierRunnable(barrier1,barrier2);

        new Thread(barrierRunnable1).start();
        new Thread(barrierRunnable2).start();

    }

    static class CyclicBarrierRunnable implements Runnable
    {
        CyclicBarrier barrier1 = null;
        CyclicBarrier barrier2 = null;

        public CyclicBarrierRunnable(CyclicBarrier barrier1, CyclicBarrier barrier2){
            this.barrier1 = barrier1;
            this.barrier2  = barrier2;
        }

        public void run(){
            try {
                
                Thread.sleep(1000);
    
                System.out.println(Thread.currentThread().getName()+ " Waiting at barrier 1");
                this.barrier1.await();
    
                Thread.sleep(1000);
    
                System.out.println(Thread.currentThread().getName()+ " Waiting at barrier 2");
                this.barrier2.await();
                
                System.out.println(Thread.currentThread().getName() + " Done !");
                
            } catch (Exception e) {
                    e.printStackTrace();
            }
        }
    }
}
