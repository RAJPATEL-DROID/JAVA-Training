package concurrency;

public class ThreadExample5 {
    public static class StoppableRunnable implements Runnable{
        private boolean stopRequested = false;

        public synchronized void requestStop()
        {
            this.stopRequested = true;
        }

        public synchronized boolean isStopRequested()
        {
            return this.stopRequested;
        }

        private void sleep(long millis)
        {
            try
            {
                Thread.sleep(millis);

            }
            catch(InterruptedException e)
            {
                System.err.println(e.getMessage());
            }
        }

        @Override 
        public void run()
        {
            System.out.println("Stoppable Runnable");
            while (!(isStopRequested()) ) 
            {
                sleep(1000);
                System.out.println("....");   
            }
            System.out.println("Stoppable Runnable Stopped");
        }
    }

    public static void main(String[] args) {
        StoppableRunnable sr = new StoppableRunnable();
        Thread thread = new Thread(sr, "The Thread");
        thread.start();

        try
        {

            Thread.sleep(5000);// Main Thread
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("Requesting to Stop");
        sr.requestStop();
        System.out.println("Stop Requesting");
        

    }

}
