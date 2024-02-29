package concurrency;

public class Synchronizer {
    LockExample lock = new LockExample();

    public void doSynchronized() throws InterruptedException
    {
        this.lock.lock();
        // Critical section

        this.lock.unlock();
    }
}
