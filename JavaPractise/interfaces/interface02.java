interface DB
{
    void readRecord(int id);
    void insertRecord(int id, String name, int age);
    void updateRecord(int id);
    void deleteRecord(int id);
}

class mySQL implements DB
{
    public void readRecord(int id)
    {
        System.out.println(id);
    }
    public void insertRecord(int id, String name, int age)
    {
        System.out.println(id+" "+name+" "+age);
    }
    public void updateRecord(int id)
    {
        System.out.println(id);
    }
    public void deleteRecord(int id)
    {
        System.out.println(id);
    }
}

class NoSql implements DB
{
    public void readRecord(int id)
    {
        System.out.println(id+" nosql");
    }
    public void insertRecord(int id, String name, int age)
    {
        System.out.println(id+" "+name+" "+age+" nosql");
    }
    public void updateRecord(int id)
    {
        System.out.println(id+" nosql");
    }
    public void deleteRecord(int id)
    {
        System.out.println(id+" nosql");
    }
}

public class interface02
{
    public static void main(String[] args)
    {
        DB obj = new mySQL();
        obj.insertRecord(1, "yash", 22);

        obj = new NoSql();
        obj.insertRecord(2, "john", 25);
    }
}
