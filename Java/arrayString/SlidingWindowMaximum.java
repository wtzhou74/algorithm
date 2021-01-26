package arrayString;

import java.util.ArrayDeque;
import java.util.Deque;

//Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Return the max sliding window.
//
//Example:
//
//Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3
//Output: [3,3,5,5,6,7] 
//Explanation: 
//
//Window position                Max
//---------------               -----
//[1  3  -1] -3  5  3  6  7       3
// 1 [3  -1  -3] 5  3  6  7       3
// 1  3 [-1  -3  5] 3  6  7       5
// 1  3  -1 [-3  5  3] 6  7       5
// 1  3  -1  -3 [5  3  6] 7       6
// 1  3  -1  -3  5 [3  6  7]      7
//Note:
//You may assume k is always valid, 1 ≤ k ≤ input array's size for non-empty array.
//
//Follow up:
//Could you solve it in linear time?
public class SlidingWindowMaximum {

	// Time: O(N * k)
	// Space: O(n - k + 1)
	public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0) return new int[0];
        int[] res = new int[nums.length - k + 1];
        int left = 0, right = k - 1;
        int i = 0;
        // Time: (N * K)
        while (right < nums.length) {
            res[i] = helper(nums, left, right);
            left++;
            right++;
            i++;
        }
        return res;
    }
	// Applying priorityQueue ==> Time will be O(nlgN)
    public int helper(int[] nums, int left, int right) {
        int max = Integer.MIN_VALUE;
        for (int i = left; i <= right; i++) {
            max = Math.max(max, nums[i]);
        }
        return max;
    }
    
    
//    ArrayDeque<Integer> deq = new ArrayDeque<Integer>(); // double-ended queue
//    int [] nums;
//
//    // Applying deque to realize O(n) time
//    public void clean_deque(int i, int k) {
//      // remove indexes of elements not from sliding window
//      if (!deq.isEmpty() && deq.getFirst() == i - k)
//        deq.removeFirst();
//
//      // remove from deq indexes of all elements 
//      // which are smaller than current element nums[i]
//      while (!deq.isEmpty() && nums[i] > nums[deq.getLast()])                          
//    	  deq.removeLast();
//    }
//
//    public int[] dequeSol(int[] nums, int k) {
//      int n = nums.length;
//      if (n * k == 0) return new int[0];
//      if (k == 1) return nums;
//
//      // init deque and output
//      this.nums = nums;
//      int max_idx = 0;
//      for (int i = 0; i < k; i++) {
//        clean_deque(i, k);
//        deq.addLast(i);
//        // compute max in nums[:k]
//        if (nums[i] > nums[max_idx]) max_idx = i;
//      }
//      int [] output = new int[n - k + 1];
//      output[0] = nums[max_idx];
//
//      // build output
//      for (int i  = k; i < n; i++) {
//        clean_deque(i, k);
//        deq.addLast(i);
//        output[i - k + 1] = nums[deq.getFirst()];
//      }
//      return output;
//    }
    
    // Time: O(n)
    // Space: O(k) + O(n - k + 1) => O(n)
    public int[] applyDeque(int[] nums, int k) {
    	if (nums.length == 0) return new int[0];
        int[] res = new int[nums.length - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();
        // initialize deque with first k elements
        int max_inx = 0;
        for (int i = 0; i < k; i++) {
            while (!deque.isEmpty() && nums[i] > nums[deque.getLast()]) {
                deque.removeLast();// since it is smaller than current one, then it CANNOT BE max
            }
            deque.addLast(i);//add INDEX instead of val since the value can be duplicate
            if (nums[i] > nums[max_inx]) max_inx = i;
        }
        res[0] = nums[max_inx];// Check each window
        for (int i = k; i < nums.length; i++) {
            // check the deque size in case it is less or equals to the window size
            if (!deque.isEmpty() && deque.getFirst() == i - k) {// DONOT use deque.size()==k since the deque's size can be LESS THAN window size, it only keeps the POTENTAIL maxs.
                deque.removeFirst();// should remove the first one before adding new one
            }
            while (!deque.isEmpty() && nums[i] > nums[deque.getLast()]) { // The max of window is always kept in the FIRST position
                deque.removeLast();// since it is smaller than current one, then it CANNOT BE max
            }
            // the first will always be the largest one in current window
            deque.addLast(i);
            res[i - k + 1] = nums[deque.getFirst()];
        }
        return res;
    }
    
    public static void main(String[] args) {
    	new SlidingWindowMaximum().applyDeque(new int[] {-6,-10,-7,-1,-9,9,-8,-4,10,-5,2,9,0,-7,7,4,-2,-10,8,7},  7);
    }
}
