package dynamicProgramming;

//Given an unsorted array return whether an increasing subsequence of length 3 exists or not in the array.
//
//Formally the function should:
//
//Return true if there exists i, j, k
//such that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return false.
//
//Note: Your algorithm should run in O(n) time complexity and O(1) space complexity.
//
//Example 1:
//
//Input: [1,2,3,4,5]
//Output: true
//Example 2:
//
//Input: [5,4,3,2,1]
//Output: false
public class IncreasingTripletSubsequence {

	// brute force
	// TIME O(N ^ 2)
	// SPACE O(1)
	public boolean increasingTriplet(int[] nums) {
        if (nums.length < 3) return false;
        for (int i = 0; i < nums.length; i++) {
            int count = 1;
            int prev = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] > prev) {
                    prev = nums[j]; 
                    count++;
                } else if (nums[j] > nums[i]) {
                	// 1,5, 2, 3 !!!!!!! UP DOWN UP DOWN
                    count = 2; // restart the second one, so reset count to 2
                    prev = nums[j];
                }
                if (count == 3) return true;
            }
        }
        return false;
    }
	
	public boolean sol2(int[] nums) {
		if (nums.length < 3) {
			return false;
		}
		int first = Integer.MAX_VALUE;
		int second = Integer.MIN_VALUE;
		for (int i = 0; i < nums.length; i++) {
			int now = nums[i];
			// SET FIRST/SECOND IN ORDER
			// Find the less value, starting from there might find the increase subsequence
			if (now < first) {
				first = now;
			} else if (now <= second) {
				second = now;
			} else if (now > second) return true;
		}
        return false;
	}
	public static void main(String[] args) {
		// new IncreasingTripletSubsequence().increasingTriplet(new int[] {0,4,2,1,0,-1,-3});
		new IncreasingTripletSubsequence().sol2(new int[] {1,0,0,1});
	}
}
