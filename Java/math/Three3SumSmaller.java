package math;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//Given an array of n integers nums and a target, find the number of index triplets i, j, k with 0 <= i < j < k < n that satisfy the condition nums[i] + nums[j] + nums[k] < target.
//
//Example:
//
//Input: nums = [-2,0,1,3], and target = 2
//Output: 2 
//Explanation: Because there are two triplets which sums are less than 2:
//             [-2,0,1]
//             [-2,0,3]
//Follow up: Could you solve it in O(n2) runtime?
public class Three3SumSmaller {

	public int threeSumSmaller(int[] nums, int target) {
        if (nums.length < 3)  return 0;
        int[] aux = new int[nums.length];
        Arrays.sort(nums);
        int count = 0;
        // for (int i = 0; i < aux.length; i++) {
        //     aux[i] = target - nums[i];
        // } //a没必要
        // O(N^2)
        for (int i = 0; i < nums.length; i++) {
            count += numOfLessThan(target - nums[i], nums, i);
        }
        return count;
    }
	
	// Time: O(N)
    public int numOfLessThan(int target, int[] nums, int idx) {
        int i = idx + 1, j = nums.length - 1; //a已经排序了， i 起点从 idx的后一个开始，不用考虑前面了，否则重复
        int count = 0; // 第三个值的索引可以通过Binary Search 查到， count += (j - i); 但是O（N * LgN）
        while (i < j) {
            if (nums[i] + nums[j] >= target) {
                j--;
                continue;
            } else {
            	// count++; 不是加 1， 而是一个区间内的组合，比如 i = 0, j = 3; [0, 0, 1, 3], target = 4
            	// a这里面有多少个pair， [0, 3], [1, 3], [0, 0], [0, 1], [0, 1], [0, 3]， 其值就是 j - i
            	// a如果 ++, 跟3匹配的就只计算了1次
                count += j - i;
                i++;
            }
            
        }
        return count;
    }  
    
    public static void main(String[] args) {
    	new Three3SumSmaller().threeSumSmaller(new int[] {-2, 0, 0, 1, 3}, 2);
    }
}
