package math;

//Given a sorted array of integers nums and integer values a, b and c. Apply a quadratic function of the form f(x) = ax2 + bx + c to each element x in the array.
//
//The returned array must be in sorted order.
//
//Expected time complexity: O(n)
//
//Example 1:
//
//Input: nums = [-4,-2,2,4], a = 1, b = 3, c = 5
//Output: [3,9,15,33]
//Example 2:
//
//Input: nums = [-4,-2,2,4], a = -1, b = 3, c = 5
//Output: [-23,-5,1,7]
public class SortedTransformedArray {

	// a二元方程 - 抛物线  ===> 开头方向由 a决定
	public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        if (nums.length == 0) return new int[0];
        //int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
        	// a先算出值，相当于把值放到抛物线上，从两端向中间收（增大，或减小） ==> Two pointer 就好
        	// a不用跟中间点（值）（-b/(2*a)）比较
            nums[i] = a * nums[i] * nums[i] + b * nums[i] + c;
        }
        int i = 0, j = nums.length - 1;
        int[] res = new int[nums.length];
        int len = nums.length - 1;
        if (a > 0) {
        	// a从两端越往中间靠就越小
            while (i <= j) {
                if (nums[i] > nums[j]) {
                    res[len--] = nums[i++];
                } else {
                    res[len--] = nums[j--];
                }
            }
        } else {
        	// a从两端越往中间靠就越大
            i = 0; j = nums.length - 1;
            int k = 0;
            while (i <= j) {
                if (nums[i] < nums[j]) {
                    res[k++] = nums[i++];
                } else {
                    res[k++] = nums[j--];
                }
            }
        }
        
        return res;
    }
}
