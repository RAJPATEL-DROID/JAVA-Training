package concurrency;

public class LockExample {

     private boolean isLocked = false;
     private Thread lockingThread = null;

     public synchronized void lock() throws InterruptedException{
        while(isLocked){
            wait();
        }
        isLocked = true;
        lockingThread = Thread.currentThread();
     }

     public synchronized void unlock()
     {
        if(this.lockingThread != Thread.currentThread()){
            throw new IllegalMonitorStateException("Calling  thread not locked this lock");
        }
        isLocked = false;
        notify();
     }
}