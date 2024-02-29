//package singleton;

public class Employee
{
    public static volatile Employee singletonObj = null;

    // private constructor
    private Employee() {}
    
    public static Employee instance()
    {
        // first check to check for the instance created in heap
        if(singletonObj==null)
        {
            // to handle multi-threading
            synchronized(Employee.class) // lock mechanism
            {
                // second check, if instance is already created by any thread or not
                if(singletonObj==null)
                {
                    singletonObj = new Employee();
                }
            }
        }

        return singletonObj;
    }

    public static void main(String[] args)
    {
        Employee obj = new Employee();

        System.out.println(obj.instance());
    }
}
