//  import java.util.concurrent.locks.Lock;
//  import java.util.concurrent.locks.ReentrantLock;
// import java.util.concurrent.Semaphore;

public class PracticeExample
{
    //    public static class MyRunnable implements Runnable
    //    {
    int i = 1;

    //         Semaphore semaphore = new Semaphore(1);
    //         // Lock lock = new ReentrantLock();
    //         @Override
    //         public void run()
    //        {
    //             while(true){
    //                 if( i > 10)break;
    //
    //                 // synchronized(this) {
    //                     // lock.lock()
    //                     try {
    //                         semaphore.acquire();
    //                         System.out.println(Thread.currentThread().getName() + " : " + i++);
    //                         if(Thread.currentThread().getName() == "Thread 2")
    //                             System.out.println(Thread.currentThread().getName() + " : " + i++);
    //                     } catch (InterruptedException e) {
    //                         e.printStackTrace();
    //                     }finally{
    //                         semaphore.release();
    //                     };

    // lock.unlock();
    // }
    //        }
    //    }
    //}

    public static void main(String[] args)
    {
        //        Thread t1 = new Thread(new MyRunnable(), "Thread 1");
        //        Thread t2 = new Thread(new MyRunnable(),"Thread 2");

        //        t1.start();

        //        t2.start();


        //     }


        int[] a = new int[21];
        System.out.println();
        //        System.out.println(a[0]);
        //        System.out.println(x, y);
    }
}
//
//class sequence
//{
//
//    public void th1() throws Exception
//    {
//        int value = 1;
//        while(true)
//        {
//            synchronized(this)
//            {
//                if(value == 0)
//                    System.out.println(Thread.currentThread().getName() + " : " + value++);
//                wait();//
//                System.out.println(Thread.currentThread().getName() + " : " + value++);
//                notify();
//                Thread.sleep(1000);
//            }
//        }
//    }
//
//    public void th2() throws Exception
//    {
//        int val = 1;
//        while(true)
//        {
//            synchronized(this)
//            {
//
//                System.out.println(Thread.currentThread().getName() + " : " + val++);
//                if(val % 2 == 0)
//                {
//                    notify();
//                    wait(); // /
//                }
//
//
//                Thread.sleep(1000);
//            }
//        }
//    }
//}
//
//public class PracticeExample
//{
//
//    public static void main(String[] args) throws Exception
//    {
//        byte b = 127;
//        System.out.println(++b);
//        //        System.out.println(sum(10, 20));
//        //        sequence sq = new sequence();
//        //
//        //        Thread th = new Thread(() -> {
//        //            try
//        //            {
//        //                sq.th1();
//        //            } catch(Exception e)
//        //            {
//        //                throw new NewException("Just for fun");
//        //            }
//        //
//        //        });
//        //
//        //        Thread th3 = new Thread(() -> {
//        //            try
//        //            {
//        //                sq.th2();
//        //            } catch(Exception e)
//        //            {
//        //                throw new NewException("Just for fun");
//        //            }
//        //
//        //        });
//        //
//        //        th.start();
//        //        th3.start();
//        //
//        //        th.join();
//        //        th3.join();
//    }
//
//    //    public static int sum(int x, int y)
//    //    {
//    //        try
//    //        {
//    //            return x + y;
//    //        } catch(Exception ignored)
//    //        {
//    //            return 0;
//    //        } finally
//    //        {
//    //            return 100;
//    //        }
//    //    }
//
//}
