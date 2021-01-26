package arrayString;

import java.util.PriorityQueue;

/*Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

Note:

The number of elements initialized in nums1 and nums2 are m and n respectively.
You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
Example:

Input:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6],       n = 3

Output: [1,2,2,3,5,6]*/
public class MergedSortedArray {

	public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums2.length == 0) return;
        int[] temp = new int[m + n];
        int i = 0, j = 0, k = 0;
        while (i < m && j < n) {
            if (nums1[i] < nums2[j]) {
                temp[k++] = nums1[i++];
            } else {
                temp[k++] = nums2[j++];
            }
        }
        while (i < m) temp[k++] = nums1[i++];
        while (j < n) temp[k++] = nums2[j++];
        
        for (int l = 0; l < temp.length; l++) {
            nums1[l] = temp[l];
        }
    }
	
	public void mergeWithHeap(int[] nums1, int m, int[] nums2, int n) {
		PriorityQueue<Integer> queue = new PriorityQueue<>(m + n); 
        for (int j = 0; j < m; j++) {
        	queue.offer(nums1[j]);
        }
        for (int item : nums2) queue.offer(item);
        int i = 0;
        while (!queue.isEmpty()) {
            nums1[i++] = (int)queue.poll();
        }
	}
	
	public static void main(String[] args) {
		new MergedSortedArray().mergeWithHeap(new int[] {1,2,3,0,0,0}, 3, new int[] {2,5,6}, 3);
	}
}
