package DSA.Sorting;

public class insertionSort
{
    static void insertion_sort(int[] arr, int len)
    {
        for(int i = 0; i < len; i++)
        {
            int j = i;
            while(j > 0 && arr[j - 1] > arr[j])
            {
                arr[j] = arr[j - 1] ^ arr[j];
                arr[j - 1] = arr[j - 1] ^ arr[j];
                arr[j] = arr[j] ^ arr[j - 1];
                j--;
            }
        }

        for(int n : arr)
        {
            System.out.println(" " + n);
        }
    }

    public static void main(String[] args)
    {
        int[] arr = {3, 7, 2, 6, 9, 1, 4};

        insertion_sort(arr, arr.length);
    }
}
