package DSA.Sorting;

public class bubbleSort
{
    static void bubble_Sort(int[] arr, int len)
    {
        for(int i = len - 1; i >= 0; i--)
        {
            for(int j = 0; j < i; j++)
            {
                if(arr[j] > arr[j + 1])
                {
                    arr[j] = arr[j] ^ arr[j + 1];
                    arr[j + 1] = arr[j] ^ arr[j + 1];
                    arr[j] = arr[j] ^ arr[j + 1];
                }
            }
        }

        for(int num : arr)
        {
            System.out.print(num + " ");
        }


    }

    public static void main(String[] args)
    {
        int[] nums = {3, 7, 2, 6, 9, 1, 4};

        bubble_Sort(nums, nums.length);


    }
}
