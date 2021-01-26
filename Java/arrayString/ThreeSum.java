package arrayString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
//
//Note:
//
//The solution set must not contain duplicate triplets.
//
//Example:
//
//Given array nums = [-1, 0, 1, 2, -1, -4],
//
//A solution set is:
//[
//  [-1, 0, 1],
//  [-1, -1, 2]
//]
public class ThreeSum {

	public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length < 3) return res;
        Arrays.sort(nums);
        Set<String> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            twoSum(nums, i, -nums[i], i + 1, set, res);
        }
        return res;
    }
    public void twoSum(int[] nums, int first, int target, int start, Set<String> set,
                               List<List<Integer>> res) {
        int end = nums.length - 1;
        while (start < end) {
        	// i.e. [0, 0, 0]
            // if (start + 1 < nums.length && nums[start] == nums[start + 1]) {
            //     start++;
            //     continue;
            // }
            // if (end - 1 >= 0 && nums[end - 1] == nums[end]) {
            //     end--;
            //     continue;
            // }
            if (nums[start] + nums[end] == target) {
                String sol = "" + nums[first] + nums[start] + nums[end];
                if (!set.contains(sol)) {
                    set.add(sol);
                    
                    List<Integer> group = new ArrayList<>();
                    group.add(nums[first]);
                    group.add(nums[start]);
                    group.add(nums[end]);
                    res.add(group); // DO NOT BREAK here, i.e. [-2, 0, 1, 1, 2 ]
                }
                
                start++;
                end--;
            } else if (nums[start] + nums[end] > target) {
                end--;
            } else {
                start++;
            }
        }
    }
    
    public List<List<Integer>> threeSum1(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length < 3) return res;
        Arrays.sort(nums);
        if(nums[0] * 3 > 0 || nums[nums.length - 1] * 3 < 0)
            return new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
        	// 不能用 nums[i+1] == nums[i] 否则会丢掉前面的数
            if (i > 0 && nums[i] == nums[i - 1]) continue; //remove duplicates
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
            	// !!!!!!一定要注意判断重复这里只能先 == 了，才能根据后面去重复
                if (nums[j] + nums[k] == -nums[i]) {
                    List<Integer> sol = Arrays.asList(nums[i], nums[j], nums[k]);
                    res.add(sol);
                    j++;
                    k--;
                    // remove duplicates
                    // 这里也只能跟前面 -1比较，不能用 +1， 因为 ，j的索引已经不再是 == 时候的索引了，++过了
                    // 所以-1那个位置才是已经校验过了的，用新的 j 跟已判断过的 j比较， 而+1是还没判断的数
                    while (j < k && nums[j] == nums[j - 1]) 
                        j++;
                    while (j < k && nums[k] == nums[k + 1])
                        k--;
                } else if (nums[j] + nums[k] > -nums[i])
                    k--;
                else j++;
                
            }
        }
        return res;
    }
    
	// TLE BELOW
	public List<List<Integer>> sol2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length < 3) return res;
        Arrays.sort(nums);
        Set<String> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int k = isInArray(nums, i, j, nums[i] + nums[j]);
                if (k > -1) {
                    List<Integer> sol = new ArrayList<>();
                    sol.add(nums[i]);
                    sol.add(nums[j]);
                    sol.add(nums[k]);
                    String item = "" + nums[i] + nums[j] + nums[k];
                    if (!set.contains(item)) {
                        set.add(item);
                        res.add(sol);
                    }
                    
                }
            }
        }
        return res;
    }
    public int isInArray(int[] nums, int i, int j, int target ) {
        for (int k = j+1; k < nums.length; k++) {
            if (nums[k] + target == 0) {
                return k;
            }
        }
        return -1;
    }
	
	// For test below
//	public List<List<Integer>> threeSum(int[] newNums) {
//		List<List<Integer>> res = new ArrayList<>();
//        helper(newNums, res, new ArrayList<>(), 0, 0);
//        return res;
//    }
//    public void helper(int[] newNums, List<List<Integer>> res, List<Integer> group, int i, int j) {
//        if (i == 3) {
//        	
//        	int sum = 0;
//            for (int item : group) {
//                sum += item;
//            }
//            //if (sum == 0) {
//            	res.add(new ArrayList<>(group));
//            //}
//            //res.add(group);
//            //group = new ArrayList<>();
//            return;
//        }
//        for (; j < newNums.length; j++) {// j wont start from 0 for each time, otherwise, that is PERMUTATION
//            if (!group.contains(newNums[j])) {// ONLY WORKs when there are DUPLICATEs in the array, otherwise, there are duplicate groups. !!!!
//                group.add(newNums[j]);
//                helper(newNums, res, group, i + 1, j+1);// j+1 here means move to the next adjacent element for CURRENT solution.
//                group.remove(group.size() - 1);
//            }
//            
//        }
//    }
    
    public static void main(String[] args) {
    	new ThreeSum().threeSum(new int[] {-2,0,1,1,2});
    }
}
