package concurrency;

class TwoSums{
    private int n1,n2;

    public void add(int n1,int n2){
        this.n1 = n1;
        this.n2 = n2;
    }
    public void display(){
        System.out.println(this.n1 + this.n2);
    }
}

public class RaceCondition {
    

    public static void main(String[] args) {
        TwoSums t = new TwoSums();
        
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run(){
                t.add(1,8);     
                
                t.display();
            }
        });
        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run(){
                t.add(2,3);  
                t.display();
            }
        });
            th1.start();
            th2.start();
    }
}
