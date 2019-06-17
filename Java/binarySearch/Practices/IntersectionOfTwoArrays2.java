package binarySearch.Practices;

import java.util.Arrays;

public class IntersectionOfTwoArrays2 {

	public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length == 0 || nums2.length == 0) return new int[0];
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int[] result = new int[Math.min(nums1.length, nums2.length)];
        int i = 0, j = 0;
        int counter = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                i++;
            } else if (nums1[i] > nums2[j])
            {
                j++;
            } else {
                result[counter] = nums1[i];
                i++;
                j++;
                counter++;
            }
        }
        
        return Arrays.copyOfRange(result, 0, counter);
    }
}
