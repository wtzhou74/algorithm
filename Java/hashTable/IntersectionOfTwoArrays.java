package hashTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IntersectionOfTwoArrays {
	public static int[] intersection(int[] nums1, int[] nums2) {
		int length1 = nums1.length;
        int length2 = nums2.length;
        List<Integer> temp = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (int i : nums1) {
            set.add(i);
        }
        for (int i : nums2) {
            if (set.contains(i)) {
                temp.add(i);
                set.remove(i);
            }
        }
        int[] result = new int[temp.size()];
        int j = 0;
        for(int i : temp) {
            result[j] = i;
            j++;
        }
        return result;
    }
	
	public int[] intersectionSol(int[] nums1, int[] nums2) {
        int i=0, j=0, k=0;
        
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        
        while(i<nums1.length && j<nums2.length){
            if(nums1[i] == nums2[j] ){
                if(i==0 || nums1[i]!=nums1[i-1]) nums1[k++] = nums1[i];// use nums1 to store intersected element
                i++;
                j++;
                
                // intuitive solution
//                temp.add(nums1[i]);
//                i++;
//                j++;
//                while (i < nums1.length && nums1[i] == nums1[i - 1]) {
//                    i++;
//                }
//                while (j < nums2.length && nums2[j] == nums2[j - 1]){
//                    j++;
//                }
            } else if(nums1[i] > nums2[j]) j++;
            else i++;
        }
        
       
        int[] ans = new int[k];
        
        for(i=0;i<k; i++){
            ans[i] = nums1[i];
        }
        
        return ans;
    }
	
	public static void main(String[] args) {
		int[] nums1 = {1,2,2,1};
		int[] nums2 = {2,2};
		intersection(nums1, nums2);
	}
}
