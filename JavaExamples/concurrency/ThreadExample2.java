package concurrency;

public class ThreadExample2 {

    public static class  Mythread extends Thread {
        public void run(){
            System.out.println("Thread is running");
        }
        
    }
    public static void main(String[] args) {
        Mythread t = new Mythread();
        t.start();
    }
}
