package twoPointers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//Given three integer arrays arr1, arr2 and arr3 sorted in strictly increasing order, return a sorted array of only the integers that appeared in all three arrays.
//
//		 
//
//Example 1:
//
//Input: arr1 = [1,2,3,4,5], arr2 = [1,2,5,7,9], arr3 = [1,3,4,5,8]
//Output: [1,5]
//Explanation: Only 1 and 5 appeared in the three arrays.
// 
//
//Constraints:
//
//1 <= arr1.length, arr2.length, arr3.length <= 1000
//1 <= arr1[i], arr2[i], arr3[i] <= 2000
public class IntersectionOfThreeSortedArrays {

	// Three Pointers
	public List<Integer> arraysIntersection(int[] arr1, int[] arr2, int[] arr3) {
        int i = 0, j = 0, k = 0;
        List<Integer> res = new ArrayList<>();
        while (i < arr1.length && j < arr2.length && k < arr3.length) {
            if (arr1[i] == arr2[j] && arr2[j] == arr3[k]) {
                res.add(arr1[i]);
                i++;
                j++;
                k++;
            } else {
                int max = Math.max(arr1[i], Math.max(arr2[j], arr3[k]));
                if (arr1[i] != max) {
                    i++;
                }
                if (arr2[j] != max) j++;
                if (arr3[k] != max) k++;
            }
        }
        return res;
    }
	
	// a桶排序，统计个数 ===> 也可用 Map<Integer, Integer> 代替
    public List<Integer> bucketCal(int[] arr1, int[] arr2, int[] arr3) {
        int[] aux = new int[2001]; // 仅适用都是正数的情况
        List<Integer> res = new ArrayList<>();
        for (int i : arr1) {
            aux[i]++;
        }
        for (int i : arr2) aux[i]++;
        for (int i : arr3) aux[i]++;
        for (int i = 0; i < aux.length; i++) {
            if (aux[i] == 3)
                res.add(i);
        }
        return res;
    }
}
