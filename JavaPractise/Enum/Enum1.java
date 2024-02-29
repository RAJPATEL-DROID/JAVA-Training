enum Dept
{
    IT("MR. John","Block A"),CS("Mr A","B"),ECE("Mr C","D");
    String head;
    String loc;
    private Dept(String head,String loc)
    {
        this.head=head;
        this.loc=loc;
        System.out.println(this.name());
    }
}

public class Enum1
{
    public static void main(String[] args)
    {
        Dept d = Dept.IT;
        System.out.println(d.ordinal()); //kaya index pr che e print krse
        System.out.println(d.head); // j value assign kri aanu name print krse.

    }

}