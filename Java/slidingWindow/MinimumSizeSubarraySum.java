package slidingWindow;

//Given an array of n positive integers and a positive integer s, find the minimal length of a contiguous subarray of which the sum ≥ s. If there isn't one, return 0 instead.
//
//Example: 
//
//Input: s = 7, nums = [2,3,1,2,4,3]
//Output: 2
//Explanation: the subarray [4,3] has the minimal length under the problem constraint.
//Follow up:
//If you have figured out the O(n) solution, try coding another solution of which the time complexity is O(n log n). 
public class MinimumSizeSubarraySum {

	// Time: O(n)
	public int minSubArrayLen(int s, int[] nums) {
        if (nums.length == 0) return 0;
        if (nums[0] >= s) return 1;        
        int i = 0, j = 0;
        //int sum = nums[0];
        int sum = 0;
        int count = Integer.MAX_VALUE;
        while (j < nums.length) {
//            if (sum < s) {
//                j++;
//                if (j < nums.length) sum += nums[j]; // 注意初始值 以及 下标
//                //j++;
//            } else {
//                count = Math.min(count, j - i + 1);
//                //System.out.println(j +"" + i + " " + count);
//                if (j == i) { // 走到中间时i 碰到 j,此时让 j先走，但sum重赋值
//                    sum = nums[i];
//                    j++;
//                } else {
//                    sum -= nums[i];
//                    i++;
//                }
//            }
        	sum += nums[j];
            while (sum >= s) { // 在此区间看有没有还有更小的
                count = Math.min(count, j - i + 1);
                sum -= nums[i];
                i++;
            }
            j++;
        }
        return count == Integer.MAX_VALUE ? 0 : count;
    }
	
	// Time: O(nlgn)  => 参考 MinimumSizeSubarraySum.png
	// Binary Search, 但对象是  sums，原数组是无序的，不能直接BS， 但其sums 是有序的
	// ===> 起源于 brute force， 每个点作为起点，看子序列的和是否大于等于s  O(n^3),其中 O（n）是用于算子序列的和
	// ====> 先O（N）得出 sums 数组，BS处理该数组得出影响 sum跟S关系的元素
	public int minSubArrayLenBS(int s, int[] nums) {
        if (nums.length == 0) return 0;
        if (nums[0] >= s) return 1;        
        
        int[] sums = new int[nums.length + 1];
        //sums[0] = nums[0];
        for (int i = 1; i < sums.length; i++) { // 画图更直观，一般累加和数组长度多一个 
            sums[i] = sums[i - 1] + nums[i - 1];
        }
        int count = Integer.MAX_VALUE;
        for (int i = sums.length - 1; i >= 0; i--) { // 从后往前
            if (sums[i] < s) break; // 如果当前的sum都比s小了，那么 0-i之间任何子序列的和都不会大于等于s了，跳出
            int idx = binarySearch(sums, sums[i] - s, 0, i);// 向上取整，保证 [idx, i] 之间的和大于等于 s的，因为  需要sum[i] - s 的值等于S， 那如果往上取值，这个值也肯定满足条件，因为这个值比差值大或等于，往下可以，但区间更大
            //System.out.println(idx);
            count = Math.min(count, i - idx);
            
        }
        
        return count == Integer.MAX_VALUE ? 0 : count;
    }
    public int binarySearch(int[] sums, int target, int left, int right) {
        while (left < right) {
            int mid = left + (right - left + 1) / 2; // 向下取整
            if (sums[mid] <= target) {
                left = mid;
            } else {
                right = mid - 1;
            }
        
        }
        return left;
    }
}
