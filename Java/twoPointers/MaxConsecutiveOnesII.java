package twoPointers;

//Given a binary array, find the maximum number of consecutive 1s in this array if you can flip at most one 0.
//
//Example 1:
//Input: [1,0,1,1,0]
//Output: 4
//Explanation: Flip the first zero will get the the maximum number of consecutive 1s.
//    After flipping, the maximum number of consecutive 1s is 4.
//Note:
//
//The input array will only contain 0 and 1.
//The length of input array is a positive integer and will not exceed 10,000
//Follow up:
//What if the input numbers come in one by one as an infinite stream? In other words, you can't store all numbers coming
//from the stream as it's too large to hold in memory. Could you solve it efficiently?
public class MaxConsecutiveOnesII {

	public int findMaxConsecutiveOnes(int[] nums) {
        if (nums.length == 0 || nums.length == 1) 
            return nums.length;
        int i = 0, j = 0;
        int max = Integer.MIN_VALUE;
        int numOfZero = 0;
        int zeroIdx = 0;
        while (j < nums.length) {
            if (nums[j] == 0) {                
                numOfZero++;
                if (numOfZero == 1)
                    zeroIdx = j;
            }
            if (numOfZero > 1) {
                numOfZero = 0;
                max = Math.max(max, j - i);
                i = zeroIdx + 1;// reset i to the index of FIRST ZERO, instead of j
                j = i;
                if (nums.length - j + 1 < max) break;
            } else {
                j++;
            }
        }
        max = Math.max(max, j - i);
        
        return max;
    }
	
	// TRICK 这里是两个0的位置， 所以确认两个0的位置，到第三个0的时候计算长度或者到末尾
	public int findMaxConsecutiveOnesII(int[] nums) {
        if (nums.length == 0 || nums.length == 1) 
            return nums.length;
        int max = Integer.MIN_VALUE;
        int preZero = -1; // 直接记录0所在的index
        int curZero = -1;
        int i = 0;
        while (i < nums.length) {
            if (nums[i] == 0) {
                max = Math.max(max, i - preZero - 1);//不包括最后一个0（第三个0）
                preZero = curZero;
                curZero = i;
            }
            i++;
        }
        max = Math.max(max, i - preZero -1);//一直找到最后一个
        return max;
    }
	
	// Sliding window
	// refer to： https://leetcode.com/problems/max-consecutive-ones-iii/discuss/247564/JavaC%2B%2BPython-Sliding-Window
	// 本题的本质是： 求最长字串，在最多只有一个(K个) 0的情况下
	// ==》 1） A[i] - A[j] , if 之间 K <= 0， continue to increment j
	// ==>2) A[i] - A[j] , if K > 0; continue to increment i
	// ==> 这里最后的 j - i只所以是最终结果， 因为在第一次包含有K个指定数时， 这时的 j-i是第一个可能结果，之后每增加 j 或 i, 其 windowSize肯定是 >= 当前的 j - i的
	public int findMaxConsecutiveOnesIII(int[] nums) {
        if (nums.length == 0 || nums.length == 1) 
            return nums.length;
        int zeroCount = 1;
        int i = 0, j;
        for (j = 0; j < nums.length; j++) {
            if (nums[j] == 0) zeroCount--; // 记录其中间的 含有指定数值的个数
            if (zeroCount < 0) {
                if (nums[i] == 0) zeroCount++; // 如果当前是指定值，那么往前挪的时候，指定值就 -1，所以要加回去
                i++;                
            }
        }
        return j - i;
    }
}
