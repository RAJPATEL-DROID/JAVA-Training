
class while1 {
    public static void main(String[] args)
    {
        while(true)
        {
	        System.out.println("Hello");
        }
        
        // it will give compile time error of unreachable code
        System.out.println("Hi");
    }
}