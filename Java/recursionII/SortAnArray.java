package recursionII;

import java.util.Arrays;

//Given an array of integers nums, sort the array in ascending order.
//
//
//
//Example 1:
//
//Input: [5,2,3,1]
//Output: [1,2,3,5]
//Example 2:
//
//Input: [5,1,1,2,0,0]
//Output: [0,0,1,1,2,5]
// 
//
//Note:
//
//1 <= A.length <= 10000
//-50000 <= A[i] <= 50000
public class SortAnArray {

	// TIME: O(NlogN)
	// Space: O(N)
	//Merge Sort 1: Top Down
	public int[] mergeSortTopDown(int[] nums) {
		if (nums.length <= 1) return nums;
		//Step 1: divide
		//Step 2: solve subproblems (divide) recursively
		int mid = nums.length / 2;
		int[] left = mergeSortTopDown(Arrays.copyOfRange(nums, 0, mid));
		int[] right = mergeSortTopDown(Arrays.copyOfRange(nums,  mid, nums.length));
		
		// Step 3: Merge sorted left and right sublist (the way to solve each subproblem)
		return merge(left, right);
	}
	// merge
	public int[] merge(int[] left, int[] right) {
		int[] res = new int[left.length + right.length];
		int i = 0, j = 0, k = 0;
		while (i < left.length && j < right.length) {
			if (left[i] > right[j]) {
				res[k] = right[j];
				j++;
				k++;
			} else if (left[i] < right[j]) {
				res[k] = left[i];
				i++;
				k++;
			} else {
				res[k] = left[i];
				//res[k + 1] = left[i]; // Not needed because the next loop, the same value will be added to the array
				k++;
				i++;
				//j++;
			}
		}
		while(i < left.length) res[k++] = left[i++];
		while(j < right.length) res[k++] = right[j++];
		
		return res;
	}
	
	
	public int[] divide(int[] nums, int start) {
		
		if (start == 0) {
			return Arrays.copyOfRange(nums, start, start + 1);
		}
		
		int[] res = divide(nums, start - 1);
		res = Arrays.copyOfRange(nums, start, start + 1);
//		int[] res = Arrays.copyOfRange(nums, start, start + 1);
//		for (int i = 0; i < res.length; i++) {
//			System.out.println(res[i]);
//		}
		return res;
	}
	

	// D&C Bottom up
	/**
	 * Step 1: loop through array on group size (divide group)
	 * Step 2: sort each group by sorting two (previous) sorted subarrays within the  group
	 * */
	
	// test bottom up
	public int[] testSortArray(int[] nums) {
		if (nums.length <= 1) return nums;
        for (int width = 1; width < nums.length; width *= 2) {
            // merge each group
            for (int i = 0; i < nums.length; i = i + width * 2) {
                if (i + width* 2 > nums.length) {
                	testmerge(nums, i, nums.length, i + width);//J +I is the middle value
                } else {
                	testmerge(nums, i, i + width * 2, i + width);
                }
            	// testmerge(nums, j, j + width * 2, j + width);
            }
        }
        return nums;
	}
	
	public int[] testmerge(int[] nums, int start, int end, int mid) {
        int[] aux = new int[end-start];
        int i = start;
        int j = mid;
        int k = 0;
//        int[] left = Arrays.copyOfRange(nums, start, mid);        
//        int[] right = Arrays.copyOfRange(nums, mid, end); mid can larger than end for j + width
        
        while (i < mid && j < end) {
        	if (nums[i] < nums[j]) {
        		aux[k++] = nums[i++];
        	} else {
        		aux[k++] = nums[j++];
        	}
        }
        while (i < mid && i < end) aux[k++] = nums[i++]; // mid can larger than end!!!!!!
        while(j < end) aux[k++] = nums[j++];

//        while (i < mid || j < end) {
//        	if (i < mid && j < end) {
//        		if (nums[i] < nums[j]) {
//        			aux[k++] = nums[i++];
//        		} else {
//        			aux[k++] = nums[j++];
//        		}
//        	} else if (i == mid) {
//        		aux[k++] = nums[j++];
//        	} else if (j == end){
//        		aux[k++] = nums[i++];
//        	}
//        }
        
        for (int x = 0; x < aux.length; x++) {
            nums[start + x] = aux[x];
        }
        
        return nums;
    }
	
	// Bin sort, could result in a great waste of space
	// Usually, it is applied in the case that the items are distributed equally and the items are positive.
	// optimized with RadixSort https://www.runoob.com/w3cnote/sort-algorithm-summary.html
	public int[] sort3(int[] nums) {
		if (nums.length == 0) {
			return nums;
		}
		int[] aux = new int[1000001];
		// handle positive numbers
		for (int i = 0; i < nums.length; i++) {
			aux[nums[i]] = 1;
		}
		// handle minus numbers
		// ABS
		int j = 0;
		// positive aux array
		// minus aux array
		for (int i = 0; i < aux.length;) {
			while (aux[i] > 0) {
				//nums[j++] = aux[i];
				i++;
			}
		}
		return nums;
	}
	public static void main(String[] args) {
		int[] res = new SortAnArray().mergeSortTopDown(new int[] {-4,0,7,4,9,-5,-1,0,-7,-1});
		for (int i = 0;i < res.length; i++) {
			System.out.println(res[i]);
		}
		
	}
}
