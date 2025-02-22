import java.util.*;


public class Solution {
    public int[] MySort (int[] arr) {
        quickSort(arr,0,arr.length-1);
        return arr;
    }

    private void quickSort(int[] arr,int left,int right) {
        if ( left < right) {
            int i = left;
            int j = right;
            int pivot = arr[i];
            while (i < j) {
                while (i < j && arr[j] >= pivot) {
                    j--;
                }
                if (i < j) {
                    arr[i] = arr[j];
                }
                while(i < j && arr[i] <= pivot) {
                    i++;
                }
                if (i < j) {
                    arr[j] = arr[i];
                }
            }
            arr[i] = pivot;
            quickSort(arr,left, i - 1);
            quickSort(arr,i + 1,right);
        }
    }
}