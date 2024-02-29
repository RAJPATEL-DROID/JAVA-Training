package DSA.Sorting;

public class selectionSort
{
    public static void selection_sort(int[] arr, int n)
    {
        for(int i = 0; i < n - 1; i++)
        {
            int mini = i;
            for(int j = i + 1; j < n; j++)
            {
                if(arr[j] < arr[mini])
                {
                    mini = j;
                }
            }

            int temp = arr[mini];
            arr[mini] = arr[i];
            arr[i] = temp;
        }

        System.out.println("After Selection Sort");
        for(int num : arr)
        {
            System.out.print(num + " ");
        }
    }

    public static void main(String[] args)
    {
        int[] numbs = {3, 7, 2, 6, 9, 1, 4};

        int length = numbs.length;
        System.out.println("Before selection sort");
        for(int num : numbs)
        {
            System.out.print(num + " ");
        }
        selection_sort(numbs, length);

    }
}
