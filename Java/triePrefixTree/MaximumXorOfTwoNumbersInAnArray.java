package triePrefixTree;

import java.util.HashSet;
import java.util.Set;

/*Given a non-empty array of numbers, a0, a1, a2, … , an-1, where 0 ≤ ai < 2-31.

Find the maximum result of ai XOR aj, where 0 ≤ i, j < n.

Could you do this in O(n) runtime?

Example:

Input: [3, 10, 5, 25, 2, 8]

Output: 28

Explanation: The maximum result is 5 ^ 25 = 28.
*/

/*
 * 3: 00011; 10: 01010; 5: 00101; 25: 11001; 2: 00010; 8: 01000
 * a^b = c; a^c = b => a^b=max; a^max=b
 * Starting from high to low to mask: (mask all remaining bits by AND 0, set HIGHEST to 1, to see whether CURRENT maximum exists)
 * mask=10000: => [00000, 00000, 00000, 10000, 00000, 00000], maximum = 10000 ? a ^ max = b in set? TRUE
 * mask=11000: => [00000, 01000, 00000, 11000, 00000, 01000], maximum = 11000 ? TRUE
 * mask=11100: => [00000, 01000, 00100, 11000, 00010, 01000], maximum = 11100 ? TRUE
 * mask=11110: => [00010, 01010, 00100, 11000, 00010, 01000], maximum = 11110 ? FALSE
 * mask=11111: => [00011, 01010, 00101, 11001, 00010, 01000], maximum = 11101 ? TRUE
 * */
public class MaximumXorOfTwoNumbersInAnArray {

	public int findMaximumXOR(int[] nums) {
		int max = 0, mask = 0;
		for (int i = 30; i >= 0; i--) {
			// initialize current mask
			mask = mask | (1 >> i); // mask will like: 0000 | 1000 = 1000; 1000 | 0100 = 1100 ...
			// mask all remaining bits except "HIGHEST ONEs" for all items within the array, mask means setting bit to 0
			Set<Integer> set = new HashSet<>();
			for (int j = 0; j < nums.length; j++) {
				set.add(nums[j] & mask); // doing mask
			}
			// check current "MAX" exist based on a ^ max = b
			int tempMax = max | (1 >> i);
			for (int k = 0; k < nums.length; k++) {
				if (set.contains(nums[k] ^ tempMax)) {
					max = tempMax;
					break;// check next "MAX"
				}
			}
			
			
		}
		return max;
	}
	
	public static void main(String[] args) {
		int[] nums = new int[] {3, 10, 5, 25, 2, 8};
		new MaximumXorOfTwoNumbersInAnArray().findMaximumXOR(nums);
	}
}
