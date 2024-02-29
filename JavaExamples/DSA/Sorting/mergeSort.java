package DSA.Sorting;

import java.util.ArrayList;

class Solution1
{
    private static void merge(int[] arr, int low, int mid, int high)
    {
        ArrayList<Integer> list = new ArrayList<>();
        int left = low;
        int right = mid + 1;

        while(left <= mid && right <= high)
        {
            if(arr[left] <= arr[right])
            {
                list.add(arr[left]);
                left++;
            }
            else
            {
                list.add(arr[right]);
                right++;
            }
        }

        while(left <= mid)
        {
            list.add(arr[left++]);
        }
        while(right <= high)
        {
            list.add(arr[right++]);
        }

        for(int i = low; i <= high; i++)
        {
            arr[i] = list.get(i - low);
        }

    }

    public static void mergeSort(int[] arr, int low, int high)
    {
        if(low >= high)
            return;

        int mid = (low + high) / 2;
        mergeSort(arr, low, mid);
        mergeSort(arr, mid + 1, high);
        merge(arr, low, mid, high);
    }

}

public class mergeSort
{
    public static void main(String[] args)
    {
        int arr[] = {23, 65, 21, 76, 54, 26, 17, 52, 4};
        int n = arr.length;
        Solution1.mergeSort(arr, 0, n - 1);

        for(int num : arr)
        {
            System.out.print(" " + num);
        }


    }
}
