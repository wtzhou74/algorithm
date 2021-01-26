package arrayString;

import java.util.Comparator;
import java.util.PriorityQueue;

//Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.
//
//Example 1:
//
//Input: [3,2,1,5,6,4] and k = 2
//Output: 5
//Example 2:
//
//Input: [3,2,3,1,2,4,5,5,6] and k = 4
//Output: 4
//Note:
//You may assume k is always valid, 1 ≤ k ≤ array's length.

public class KthLargestElementInAnArray {

	public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(nums.length, 
            new Comparator<Integer>() {
                public int compare(Integer a, Integer b) {
                    return b - a;
                }
            });
        for (Integer num : nums) {
            queue.offer(num);
        }
        int count = 0;
        while (!queue.isEmpty()) {
            int temp = queue.poll();
            count++;
            if (count == k) return temp;
        }
        return -1;
    }
}
