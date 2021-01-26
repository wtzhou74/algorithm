package slidingWindow;

//Your are given an array of positive integers nums.
//
//Count and print the number of (contiguous) subarrays where the product of all the elements in the subarray is less than k.
//
//Example 1:
//Input: nums = [10, 5, 2, 6], k = 100
//Output: 8
//Explanation: The 8 subarrays that have product less than 100 are: [10], [5], [2], [6], [10, 5], [5, 2], [2, 6], [5, 2, 6].
//Note that [10, 5, 2] is not included as the product of 100 is not strictly less than k.
//Note:
//
//0 < nums.length <= 50000.
//0 < nums[i] < 1000.
//0 <= k < 10^6.
public class SubarrayProdLessThanK {

	// O（N）
	// Space: O(1)
	// Sliding window 问题，size可变
	public int numSubarrayProductLessThanK(int[] nums, int k) {
        int i = 0, j = i;
        int count = 0;
        int prod = nums[i];
        while (j < nums.length) {
        	//int prod = 1;
            // for (int m = i; m <= j; m++) { // 形成 O(N^2), 比如 nums=[1,1,1,1,1,1]
            //     prod *= nums[m];  // 在指针移动时，借助乘除记录production的值
            // }
        	if (prod < k) {
                count += (j - i + 1); // +1 本身，这里是连续的subarray,不存在排列组合
                j++;
                if (j < nums.length) // 按指针取时一定要注意判断溢出
                    prod *= nums[j];
            } else {
                if (i == j) {
                    j++;
                    if(j < nums.length) // ！！！！
                        prod = nums[j]; // 需要重置prod, 如果直接按下面除，会得到 1， [100, 5, 100, 6] k=100
                } else {                   
                    prod /= nums[i]; 
                }
                i++;
                
            }
        }
        return count;
    }
	
	public static void main(String[] args) {
		int[] nums = new int[] {
				5,100,100,6
		};
		new SubarrayProdLessThanK().numSubarrayProductLessThanK(nums, 100);
	}
}
