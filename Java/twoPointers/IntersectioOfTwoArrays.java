package twoPointers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//Given two arrays, write a function to compute their intersection.
//
//Example 1:
//
//Input: nums1 = [1,2,2,1], nums2 = [2,2]
//Output: [2]
//Example 2:
//
//Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
//Output: [9,4]
//Note:
//
//Each element in the result must be unique.
//The result can be in any order.
public class IntersectioOfTwoArrays {

	// Two-pointers
	public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) return null;
        Arrays.sort(nums1);// O(nLgN)
        Arrays.sort(nums2);
        int i = 0, j = 0;
        Set<Integer> set = new HashSet<>();
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j])
            {
                set.add(nums1[i]);
                i++;
                j++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                i++;
            }
        }
        return set.stream().mapToInt(k -> k).toArray();
        
    }
	
	public int[] sol2(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) return null;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums1.length; i++) {
            set.add(nums1[i]);
        }
        Set<Integer> res = new HashSet<>();
        for (int i = 0; i < nums2.length; i++) {
            if (set.contains(nums2[i]))
                res.add(nums2[i]);
        }
        return res.stream().mapToInt(i -> i).toArray();
    }
}
