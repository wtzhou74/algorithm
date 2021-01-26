package recursionII.backtracking;

import java.util.Arrays;

//Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k non-empty subsets whose sums are all equal.
//
//
//
//Example 1:
//
//Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
//Output: True
//Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
// 
//
//Note:
//
//1 <= k <= len(nums) <= 16.
//0 < nums[i] < 10000.
public class PartitionToKEqualSumSubsets {

	public boolean canPartitionKSubsets(int[] nums, int k) {
        if (nums.length < k) return false;
        if (k == 1) return true;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if (sum % k != 0) return false;
        int sumGroup = sum / k;
        Arrays.sort(nums);
        if (nums[nums.length - 1] > sumGroup) return false;
        
        // 这边可以做如下处理优化， 比如 [5,5,5,4,1]; n = 4; 这样backtracking 只需要对 [4,1],k=1 即可
        // 同时修改； backtrack(int[] nums, int k, int start, int END, boolean[] visited, 
        //				int target, int bucketSum)
//        int end = nums.length - 1;
//        for (; end > 0; end--) {
//            if (nums[end] == sumGroup) {
//                k--;
//            } else {
//                break;
//            }
//        }
//        if (k == 0 && end == 0) return true;
//        if (k == 0 && end > 0) return false;
        
        return backtrack(nums, k, 0, new boolean[nums.length], sumGroup, 0);
    }
    
	// backtracking => DFS的过程， 一个个往桶里放，合适的放进去，到找到solution或不能继续往下为止
	//					同时不管找到没找到，都开始往回backtrack,通过比如从list移除最后一个元素，或者将visited
	//					设置为unvisited等等，就是回到上一层，并跳过之前的看过的元素，并看有没有其他solution
	// 
    public boolean backtrack(int[] nums, int k, int start, boolean[] visited, 
                            int target, int bucketSum) {
        if (k == 1)
            return true;
        if (bucketSum > target)
            return false;
        if (bucketSum == target)
        	// 这里我们找到了一个solution,但还有看有没有其他的，继续做backtracking
            return backtrack(nums, k - 1, 0, visited, target, 0);
        for (int i = start; i < nums.length; i++) {
            int temp = bucketSum + nums[i];
            // 查看每看过的元素
            if (!visited[i] && temp <= target) {
                visited[i] = true;
                if (backtrack(nums, k, i + 1, visited, target, bucketSum + nums[i]))
                    return true;
                visited[i] = false;// backtracking
            }
        }
        return false;
    }
    
    // 更优化，充分 借助sorted的结果，先大元素开始往桶里放，这样不需要visited[]了，直接判断和是否满足条件，
    // 因为sorted,跟大的元素合并用过后的数，往后不会和更小的元素也满足条件，所以不会再被加入，即使被再次check
    // Time: O(k^(n-k)*k!) making k! calls ; n-k nonzeros
    // Space: O(N)
    public boolean canPartitionKSubsetsOptimized(int[] nums, int k) {
        if (nums.length < k) return false;
        if (k == 1) return true;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        if (sum % k != 0) return false;
        int sumGroup = sum / k;
        Arrays.sort(nums);
        if (nums[nums.length - 1] > sumGroup) return false;
        int end = nums.length - 1;
        for (; end > 0; end--) {
            if (nums[end] == sumGroup) {
                k--;
            } else {
                break;
            }
        }
//        if (k == 0 && end == 0) return true;//这些影响不太大
//        if (k == 0 && end > 0) return false;
        return backtrack(nums, new int[k], end, sumGroup);
    }
    
    public boolean backtrack(int[] nums, int[] buckets, int end, 
                            int target) {
        if (end < 0) // 元素已经check完，所有元素放完
            return true;
        // 以桶为对象， 我们需要backtrack来填充这k个桶, 并且按从大到小依次往里试，每次从桶0开始放
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] + nums[end] <= target) {//判断元素是否满足条件，这里不需额外判断当前数是否
            							//visited,因为sorted,另外这是sum的过程
                buckets[i] += nums[end];
                if (backtrack(nums, buckets, end - 1, target))
                    return true;
                buckets[i] -= nums[end];
            }
            if (buckets[i] == 0) break; //这里如果元素已经是 0了，由于 sorted，又是sum，没必要再继续了
            						// 之前的结果是什么就是什么了，可以comment掉，但影响效率
        }
        // 回不到end<0，说明某个元素放任何一个桶都不能满足条件，所以不符合
        return false;
    }
    
    
    public boolean dpSolution(int[] nums, int k) {
        int N = nums.length;
        Arrays.sort(nums);
        int sum = Arrays.stream(nums).sum();
        int target = sum / k;
        if (sum % k > 0 || nums[N - 1] > target) return false;

        boolean[] dp = new boolean[1 << N];
        dp[0] = true;
        int[] total = new int[1 << N];

        for (int state = 0; state < (1 << N); state++) {
            if (!dp[state]) continue;
            for (int i = 0; i < N; i++) {
                int future = state | (1 << i);
                if (state != future && !dp[future]) {
                    if (nums[i] <= target - (total[state] % target)) {
                        dp[future] = true;
                        total[future] = total[state] + nums[i];
                    } else {
                        break;
                    }
                }
            }
        }
        return dp[(1 << N) - 1];
    }
    
    
    public static void main(String[] args) {
    	//new int[] {4,4,2,2,2,2,2,2}
    	new PartitionToKEqualSumSubsets().dpSolution(new int[] {4, 3, 2, 3, 5, 2, 1}, 4);
    }
}
