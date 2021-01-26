package arrayString;
//
//Write an efficient program to find the sum of contiguous subarray within a one-dimensional array of numbers which has the largest sum.
public class LargestSumContiguousSubarray {

	public int maxContiguousArraySum(int[] a) {
		int max_so_far = a[0]; 
	    int curr_max = a[0]; 
	  
	    for (int i = 1; i < a.length; i++) 
	    { 
	        curr_max = Math.max(a[i], curr_max+a[i]); 
	        max_so_far = Math.max(max_so_far, curr_max); 
	    } 
	    return max_so_far; 
	}
	
	public void sol(int[] nums) {
		// find positive digit, otherwise, max_ending_here = 0
		int start = 0, end = 0, s = 0;
		int res = Integer.MIN_VALUE;
		int max_ending_here = 0;
		for (int i = 0; i < nums.length; i++) {
			
			max_ending_here += nums[i]; // adding next element
			
			if (res < max_ending_here) {
				res = max_ending_here; // Got a current max subarray (LOCAL MAXIMIZATION)
				start = s;
				end = i;
			}
			// kEEP TRACK OF THE START OF NEW SUBARRAY
			if (max_ending_here < 0) {
				max_ending_here = 0; // which means max_ending_here = nums[i], and start a new subarray
				s = i + 1;
			}
		}
		
		System.out.println("Maximum contiguous sum is " 
                + res); 
		System.out.println("Starting index " + start); 
		System.out.println("Ending index " + end); 
	}
	
	public int sol3 (int[] nums) {
		int max = Integer.MIN_VALUE;
        int currMax = 0;
        for (int i = 0; i < nums.length; i++) {
            if (currMax + nums[i] < nums[i]) {
                currMax = nums[i]; // start a new subarray
            } else {
                currMax += nums[i]; // keep adding the next element
            }
    
            max = Math.max(max, currMax);
        }
        return max;
		
//		int max_so_far = 0, max_ending_here = 0; 
//		   for (int i = 0; i < size; i++) 
//		   { 
//		       max_ending_here = max_ending_here + a[i]; 
//		       if (max_ending_here < 0) 
//		           max_ending_here = 0; 
//		  
//		       /* Do not compare for all elements. Compare only    
//		          when  max_ending_here > 0 */
//		       else if (max_so_far < max_ending_here) 
//		           max_so_far = max_ending_here; 
//		   } 
//		   return max_so_far; 
	}
	
	// USING TO UNDERSTAND PREVIOUS SOLUTIONs
	public int sol4(int[] nums) {
		int currMax = nums[0]; // Keeps track of the start of the subarray
		//int max = Integer.MIN_VALUE;
		int max = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (currMax < 0) { // currMax + nums[i] < nums[i]
				currMax = nums[i];
			} else {
				currMax += nums[i];
			}
			max = Math.max(max, currMax); // FIND A CURRENT MAX SUBARRAY
		}
		return max;
	}
	
	public static void main(String[] args) {
		int[] nums = new int[] {1};
		int max = new LargestSumContiguousSubarray().sol4(nums);
		System.out.println(max);
	}
}
