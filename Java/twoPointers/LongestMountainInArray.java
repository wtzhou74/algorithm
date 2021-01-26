package twoPointers;

//Let's call any (contiguous) subarray B (of A) a mountain if the following properties hold:
//
//B.length >= 3
//There exists some 0 < i < B.length - 1 such that B[0] < B[1] < ... B[i-1] < B[i] > B[i+1] > ... > B[B.length - 1]
//(Note that B could be any subarray of A, including the entire array A.)
//
//Given an array A of integers, return the length of the longest mountain. 
//
//Return 0 if there is no mountain.
//
//Example 1:
//
//Input: [2,1,4,7,3,2,5]
//Output: 5
//Explanation: The largest mountain is [1,4,7,3,2] which has length 5.
//Example 2:
//
//Input: [2,2,2]
//Output: 0
//Explanation: There is no mountain.
//Note:
//
//0 <= A.length <= 10000
//0 <= A[i] <= 10000
//Follow up:
//
//Can you solve it using only one pass?
//Can you solve it in O(1) space?
public class LongestMountainInArray {

	// 注意区别处理上升下降的情况
	public int longestMountain(int[] A) {
        if (A.length < 3) return 0;
        int i = 0, j = 0;
        int increase = -1;
        int res = Integer.MIN_VALUE;
        while (j + 1 < A.length) {
            if (i == j) {
                if (A[j] >= A[j + 1]) {
                    i++;
                    j++;
                } else {
                    j++;
                    increase = 1;
                }
            } else {
                if (increase == 1) { // 递增的情况
                    if (A[j + 1] > A[j]) {
                        j++;
                    } else if (A[j + 1] < A[j]) {
                        j++;
                        increase = 0;
                    } else {
                        j++;
                        i = j;
                        increase = -1;
                    }
                } else if (increase == 0) { //递减的情况
                    if (A[j + 1] >= A[j]) {
                        //System.out.println(i + " " + j);
                        res = Math.max(res, j - i + 1);
                        i = j; // 从当前点开始，这时候已经开始递增了，不是 ++, 
                        increase = -1;
                    } else {
                        j++;
                    }
                }
            }
        }
        if (increase == 0) res = Math.max(res, j - i + 1); // 走到最后时的情况
        return res == Integer.MIN_VALUE ? 0 : res;
    }
}
