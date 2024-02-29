package concurrency;

public class ThreadLocalExample2 {
    public static void main(String[] args) {
        ThreadLocal<Object> threadlocal = new ThreadLocal<>() {
            @Override
            protected Object initialValue() {
                return new Object();
            }
        };

        ThreadLocal<Object> threadlocal2 = ThreadLocal.withInitial(() -> {
            return new Object();
        });

        Thread thread1 = new Thread(() -> {

            System.out.println("ThreadLocal2 : " + threadlocal2.get());
            System.out.println("ThreadLocal1 : " + threadlocal.get());
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("ThreadLocal1 : " + threadlocal.get());
            System.out.println("ThreadLocal2 : " + threadlocal2.get());

        });

        thread1.start();
        thread2.start();

    }
}
