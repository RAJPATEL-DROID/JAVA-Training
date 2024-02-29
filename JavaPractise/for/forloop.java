
public class forloop
{
    public static void main(String[] args)
    {
        int[] arr = {1,2,3,4,5};

        int[][] arr1 = {{1,2,3,4,5},{6,7,8,9,0}};

        System.out.println("1-D array");

        for(int ele: arr)
        {
            System.out.println(ele);
        }

        System.out.println("2-D array");
        
        for(int[] i: arr1)
        {
            for(int j: i)
            {
                System.out.println(j);
            }
        }

        for(int i=0; i<arr1.length;i++)
        {
            for(int j=0; j<arr1[i].length;j++) 
            {
                System.out.println(i+" "+j+" => "+arr1[i][j]);
            }
        }
    }
}
