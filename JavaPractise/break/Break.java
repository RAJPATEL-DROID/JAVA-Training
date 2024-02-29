public class Break {
    public static void main(String[] args)
    {
        System.out.println("hellow world");

        for(int i = 0;i<10;i++)
        {
            if (i==5) {
                break;
            }
            if (i%2==0) {
                System.out.println(i);
                continue;
            }
            
        }
    }
}

