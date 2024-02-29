class ExceptionHandler {
    // public static void main(String[] args) 
    // {
    
    //     doStuff();
    
    // }
    
    // public static void doStuff()
    // {

    //     System.out.println(doMoreStuff());
    // }

    // public static int doMoreStuff()
    // {
    //     try
    //     {
            
    //         System.out.println(10/0);
    //         return 99;
    //     } 
    //     catch (ArithmeticException e) 
    //     {
    //         System.out.println("Can not divide integer with 0");
    //         System.out.println(e.getLocalizedMessage());
    //         System.out.println(e.getMessage());
    //         return 91;
    //     }
    //     finally
    //     {
    //         System.out.println("Finally it is executing");
    //         return 12;
    //     }
                        
    // }
    public static void main(String[] args) {
        try
        {
            System.out.println(10/0);

        }
        catch(ArithmeticException e)
        {
            System.out.println(10/0);
        }
        finally
        {          
            String s = null;
            System.out.println(s.length()); // Only this will be handled by default handler
        }
    }
}
