package concurrency;

class ThreadLocalExample {
    public static void main(String[] args) {
        ThreadLocal<String> threadlocal = new ThreadLocal<>();

        Thread thread1 = new Thread(()-> {
            threadlocal.set("Thread 1");
        

            String value = threadlocal.get();
            System.out.println(value);

            threadlocal.remove();

            System.err.println(threadlocal.get());
        });

        Thread thread2 = new Thread(()-> {
            threadlocal.set("Thread 2");
            String value = threadlocal.get();
            System.out.println(value);
            try{
                Thread.sleep(2000);
            }catch(Exception e)
            {
                e.printStackTrace();
            }

            value = threadlocal.get();
            System.out.println(value);
        });

        thread1.start();
        thread2.start();
    }
}