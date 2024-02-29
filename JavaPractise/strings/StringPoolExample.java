public class StringPoolExample
{
    public static void main(String[] args)
    {
        String s1 = "Java";
        String s2 = "Java";
        String s3 = new String("Java");
        String s4 = new String("Java").intern();

        System.out.println((s1 == s2)+", String are equal."); // true
        System.out.println((s1 == s3)+", String are not equal."); // false
        System.out.println((s1 == s4)+", String are equal."); // true
    }
}