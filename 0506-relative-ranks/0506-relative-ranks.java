
import java.util.*;

class Solution {

    static void merge(int[] arr, int[] index, int low, int mid, int high) {
        ArrayList<Integer> temp = new ArrayList<>();

        int left = low;
        int right = mid + 1;

        while (left <= mid && right <= high) {
            if (arr[index[left]] >= arr[index[right]]) {
                temp.add(index[left]);
                left++;
            } else {
                temp.add(index[right]);
                right++;
            }
        }

        while (left <= mid) {
            temp.add(index[left]);
            left++;
        }

        while (right <= high) {
            temp.add(index[right]);
            right++;
        }

        for (int i = low; i <= high; i++) {
            index[i] = temp.get(i - low);
        }
    }

    static void mergeSort(int[] arr, int[] index, int low, int high) {
        if (low >= high) return;

        int mid = (low + high) / 2;

        mergeSort(arr, index, low, mid);
        mergeSort(arr, index, mid + 1, high);

        merge(arr, index, low, mid, high);
    }

    public String[] findRelativeRanks(int[] score) {
        int n = score.length;

        int[] index = new int[n];
        String[] ans = new String[n];

        for (int i = 0; i < n; i++) {
            index[i] = i;
        }

        mergeSort(score, index, 0, n - 1);

        for (int i = 0; i < n; i++) {
            if (i == 0)
                ans[index[i]] = "Gold Medal";
            else if (i == 1)
                ans[index[i]] = "Silver Medal";
            else if (i == 2)
                ans[index[i]] = "Bronze Medal";
            else
                ans[index[i]] = String.valueOf(i + 1);
        }

        return ans;
    }
}
