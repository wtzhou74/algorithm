package graphs;

import java.util.HashMap;
import java.util.Map;

//Given an integer array A, you partition the array into (contiguous) subarrays of length at most K.  After partitioning, each subarray has their values changed to become the maximum value of that subarray.
//
//Return the largest sum of the given array after partitioning.
//
// 
//
//Example 1:
//
//Input: A = [1,15,7,9,2,5,10], K = 3
//Output: 84
//Explanation: A becomes [15,15,15,9,10,10,10]
// 
//
//Note:
//
//1 <= K <= A.length <= 500
//0 <= A[i] <= 10^6
public class PartitionArrayForMaximumSum {

	// Time: O(nk)
	// Space: O(N)
	// a典型的区间动态规划
	public int maxSumAfterPartitioning(int[] A, int K) {
        int[] dp = new int[A.length + 1];
        // dp[0] = 0;
        for (int i = 1; i <= A.length; i++) {
            int maxCur = 0;
            // J 是子数组的个数，可以是 1， 2， ... ， K
            for (int j = 1; j <= K; j++) { // dp[i - k] 之前已经知道，接下来 对 DP[i]， 前面可以有  [i-1, i], [i - 2, i], ...[i - K, i]
                if (i >= j) { // 分别按 1个分，2个分， ... K 个分
                    maxCur = Math.max(maxCur, A[i - j]);
                    dp[i] = Math.max(dp[i], dp[i - j] + maxCur * j ); // [i-1, i] 按 1 个分，[i- 2] 按每 2 个元素一分， 以此类推， 在这中间的数，等于 maxCur乘以 子数组的个数 j
                }
            }
        }
        return dp[A.length];
    }
	
	// DFS + Memo
	public int dfsMemo(int[] A, int K) {
		Map<Integer, Integer> memo = new HashMap<>();
		return helper(A, K, 0, memo);
	}
	public int helper(int[] A, int K, int start, Map<Integer, Integer> memo) {
        if (start >= A.length) return 0;
        if (memo.containsKey(start)) {
            return memo.get(start);
        }
        int curMax = 0, max = 0, distance = 0;
        // i=start开始的形式， 是DFS到最后，然后再回溯
        for (int i = start; i < A.length; i++) {
            curMax = Math.max(curMax, A[i]);
            distance = i - start + 1;
            if (distance > K) break;
            // a利用之前的值计算当前的值
            // a如果此处用start + 1, 在回溯的时候，[start == i, i + j] 这里 i > start 因为 i++, 而此次还用 start，会多出遍历，
            // a因为 i++时候， start值不变，而此时对 [i==start, j], 是要计算 f(j)的值 （在之前的遍历已计算出），而不是从start开始
            max = Math.max(max, helper(A, K, i + 1, memo) + curMax * distance); // DFS, 一直到最后一个元素，再返回
        }
        
        memo.put(start, max);
        
        return max;
    }
	
	
	public static void main(String[] args) {
		new PartitionArrayForMaximumSum().dfsMemo(new int[] {1,15,7,9}, 3);
	}
}
