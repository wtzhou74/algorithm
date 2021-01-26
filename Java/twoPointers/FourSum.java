package twoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Given an array nums of n integers and an integer target, are there elements a, b, c, and d in nums such that a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.
//
//Note:
//
//The solution set must not contain duplicate quadruplets.
//
//Example:
//
//Given array nums = [1, 0, -1, 0, -2, 2], and target = 0.
//
//A solution set is:
//[
//  [-1,  0, 0, 1],
//  [-2, -1, 1, 2],
//  [-2,  0, 0, 2]
//]

public class FourSum {

	// 4sum ==> 降维 ==> 3sum ==> 2sum
	public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums.length == 0) return new ArrayList<>();
        Arrays.sort(nums);
        // 优化： 如果最小值 * 4 都大于target, 或者最大值 * 4 都小于 target,就没必要往下了
        if (target < nums[0] * 4 || target > nums[nums.length - 1] * 4)
            return new ArrayList<>();
        //Set<List<Integer>> res = new HashSet<>();
        List<List<Integer>> res = new ArrayList<>();
        // 4sum
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;// avoid duplicates
            // 3sum
            for (int j = i + 1; j < nums.length - 2; j++) {
            	// 优化： 如果最小值 * 3 都大于new target了， 就没必要往下了
            	if (nums[j] * 3 > target - nums[i]
                        || nums[nums.length - 1] * 3 < target - nums[i]) break;
                if (j > i + 1 && nums[j] == nums[j - 1])
                    continue;// avoid duplicates
                int remaining = target - nums[i] - nums[j];
                int k = j + 1;
                int m = nums.length - 1;
                // 2sum
                while (k < m) {
//                    if (nums[k] + nums[m] == remaining) {
//                        List<Integer> sol = Arrays.asList(nums[i], nums[j], nums[k], nums[m]); // 如果用 new ArrayList() ==> .add()， 如果元素一样，set也不能去掉相同的List
//                        res.add(sol); // 2sum没有去重，所以放入set
//                        k++;
//                        m--;
//                    } else if (nums[k] + nums[m] > remaining) 
//                        m--;
//                    else k++;
                	
                	// 借助 去重，这样就不需要用 set               	
                	if (nums[k] + nums[m] == remaining) {
                        if (nums[k] + nums[m] == remaining) {
                            List<Integer> sol = Arrays.asList(nums[i], nums[j], nums[k], nums[m]);
                            res.add(sol);
                        }
                        k++; m--;
                        // 2sum 去重
                        while(k < m && nums[k] == nums[k - 1]){
                            k++;
                        }
                        while(k < m && nums[m] == nums[m + 1]){
                            m--;
                        }
                        
                        //int val = nums[m];
                        //m--;
//                        while (k < m) { // 这样 while会一直持续，因为 nums[m] != val, m不会变， k < m 会一直成立
//                            if (nums[m] == val)
//                                m--;
//                        }
//                        while (k < m && nums[m] == val) {
//                        	m--;
//                        }
                    } else if (nums[k] + nums[m] > remaining)
                      m--; //这里没必要做去重，因为该值不是res中的元素，无非就是多走几步而已
                    else k++;          
                }
                    
            }
        }
        //return new ArrayList<>(res);
        return res;
        
    }
	
	// recursive solution  ===> 实现 kSum  ===> 参考 FourSum.png递归过程
	public List<List<Integer>> fourSumRecursive(int[] nums, int target) {
        if (nums.length == 0) return new ArrayList<>();
        Arrays.sort(nums);
        if (target < nums[0] * 4 || target > nums[nums.length - 1] * 4)
            return new ArrayList<>();
        kSum(nums, target, 4, new ArrayList<>(), 0);
        return res;
        
    }
    List<List<Integer>> res = new ArrayList<>();
    
    public void kSum(int[] nums, int target, int k, List<Integer> temp, int start) {
        if (k < 2 || nums.length < k) return;
        // 2sum
        else if (k == 2) {
            int left = start, right = nums.length - 1;
            while (left < right) {
                if (nums[left] + nums[right] == target) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[left]);
                    list.add(nums[right]);
                    list.addAll(temp);
                    res.add(list);
                    left++;
                    right--;
                    // 去掉重复的结果
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right + 1]) {
                        right--;
                    }
                } else if (nums[left] + nums[right] > target) {
                    right--;
                } else {
                    left++;
                }
            }
        } else {
            
            if (nums[start] * k > target || nums[nums.length - 1] * k < target) return;//优化作用
            for (int i = start; i < nums.length - k + 1; i++) {
                if (i > start && nums[i] == nums[i - 1]) continue;
                if (nums[i] * k > target) break;
                temp.add(nums[i]);
                kSum(nums, target - nums[i], k - 1, temp, i + 1);
                temp.remove(temp.size() - 1); // backtracking，
                //保持每个solution中元素个数固定，不删除，往回重新开始的时候目标对象元素个数就不是固定的4个了
            }
        }
    }
	
	public static void main(String[] args) {
		int[] nums = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
		new FourSum().fourSumRecursive(nums, 31);
	}
}
