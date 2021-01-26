package arrayString;

import java.util.ArrayList;
import java.util.List;


//Given a sorted integer array nums, where the range of elements are in the inclusive range [lower, upper], return its missing ranges.
//
//Example:
//
//Input: nums = [0, 1, 3, 50, 75], lower = 0 and upper = 99,
//Output: ["2", "4->49", "51->74", "76->99"]

public class MissingRanges {

	
	// Pay close attention to the OVERFLOW, [-2147483648,2147483647]; =====> Covert int to double!!!!!!
	public List<String> findMissingRanges(int[] nums, int lower, int upper) {
		double l = (double)lower;
		double u = (double)upper;
        List<String> res = new ArrayList<>();
        if (nums.length == 0) {
            if (u - l >= 1)
                res.add(lower + "->" + upper);
            else if (u - l == 0) {
                res.add(lower + "");
            }
            return res;
        }
        for (int i = 0; i < nums.length; i++) {
        	double temp1 = (double)nums[i];
            if (i == 0) {
               if (nums[i] != l) {
                    if (temp1 - l == 1)
                        res.add(lower + "");
                    else {
                        res.add(lower + "->" + (nums[i] - 1));
                    }
               } else {
                   continue;
               }
           } else {

           		double temp2 = (double)nums[i - 1];
        	   // [-2147483648,2147483647]  overflow
               if (temp1 - temp2 == 2) {
                   res.add((nums[i] - 1)+"");
               } else if (temp1 - temp2 > 2) {
                   res.add(nums[i - 1] + 1 + "->" + (nums[i] - 1));
               } else {
                   continue;
               }
           } 
        }
        if (u - nums[nums.length - 1] > 1) {
            res.add(nums[nums.length - 1] + 1 + "->" + upper);
        } else if (u - (double)nums[nums.length - 1] == 1) {
            res.add(upper + "");
        }
        return res;
    }
	
	public static void main(String[] args) {
		int[] nums = new int[] {-2147483648,2147483647};
		new MissingRanges().findMissingRanges(nums, -2147483648, 2147483647);
	}
}
