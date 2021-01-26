package twoPointers;

//Given an array of integers A sorted in non-decreasing order, return an array of the squares of each number, also in sorted non-decreasing order.
//
//		 
//
//Example 1:
//
//Input: [-4,-1,0,3,10]
//Output: [0,1,9,16,100]
//Example 2:
//
//Input: [-7,-3,2,3,11]
//Output: [4,9,9,49,121]
// 
//
//Note:
//
//1 <= A.length <= 10000
//-10000 <= A[i] <= 10000
//A is sorted in non-decreasing order.
public class SquaresOfASortedArray {

	// S1: PriorityQueue / Arrays.sort(array)
	// S2: bucket sort
	public int[] sortedSquares(int[] A) {
        if (A.length == 0) return A;
        int[] aux = new int[10001];
        for (int i = 0; i < A.length; i++) {
            if(A[i] < 0 )
                aux[-A[i]]++;
            else 
                aux[A[i]]++;
        }
        int[] res = new int[A.length];
        int j = 0;
        for (int i = 0; i < aux.length; i++) {
            if (aux[i] > 0) {
                for (int k = 1; k <= aux[i]; k++) {
                    res[j] = i * i;
                    j++;
                }
            }
        }
        return res;
    }
	
	// S3: Three-pointers
	public int[] threePointers(int[] A) {
        if (A.length == 0) return A;
        int[] res = new int[A.length];
        int i = 0, j = A.length-1;
        int k = A.length - 1; // 从后往前填充，因为原数组是按序的
        while (i <= j) {
            if (A[i] * A[i] < A[j] * A[j])
            {
                res[k] = A[j] * A[j];
                j--;
                k--;
            } else {
                res[k] = A[i] * A[i];
                i++;
                k--;
            }
        }
        return res;
    }
}
