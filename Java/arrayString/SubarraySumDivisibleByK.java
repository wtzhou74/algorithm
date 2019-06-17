package arrayString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SubarraySumDivisibleByK {

	/**
	 * !!!!! SUM[i, j] % k == 0
	 * 		SUM[i, j] = sum[0, j] - sum[0, i-1]
	 * 		SUM[i, j] % k = (sum[0, j] - sum[0, i-1]) % k == 0 => sum[0, j] % k == sum[0, i-1] % k 
	 * 
	 * => Place mode into the array
	 * => for each mode[i] > 1, combination, mode[5] = 3, then pick any two from 5, namely (5 * 4 ) / 2
	 * */
	public static int subArraySumDivByK (int[] nums, int k) {
		int count = 0, sum = 0;
		//Map<Integer, Integer> map = new HashMap<>();
		int[] mode = new int[k];
		Arrays.fill(mode, 0);
		mode[0] = 1;
		//map.put(0, 1);
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
			mode[((sum % k) + k) % k]++;
		}
		
		for (int i = 0; i < k; i++) {
			if (mode[i] > 1) {
				// pick any two of mode[i] > 1 (sum[0, j] % k == sum[0, i-1] % k ) => permutation and combination
				count += (mode[i] * (mode[i] - 1)) / 2;
			}
		}
		
		return count;
	}
	
	public static void main(String[] args) {
		int arr[] = { 4, 5, 0, -2, -3, 1 }; 
        int k = 5; 
        System.out.println(subArraySumDivByK(arr, k)); 
        int arr1[] = { 4, 5, 0, -12, -23, 1 }; 
        int k1 = 5; 
        System.out.println(subArraySumDivByK(arr1, k1)); 
	}
}
