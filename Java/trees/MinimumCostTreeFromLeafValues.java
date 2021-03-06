package trees;

//Given an array arr of positive integers, consider all binary trees such that:
//
//Each node has either 0 or 2 children;
//The values of arr correspond to the values of each leaf in an in-order traversal of the tree.  (Recall that a node is a leaf if and only if it has 0 children.)
//The value of each non-leaf node is equal to the product of the largest leaf value in its left and right subtree respectively.
//Among all possible binary trees considered, return the smallest possible sum of the values of each non-leaf node.  It is guaranteed this sum fits into a 32-bit integer.
//
// 
//
//Example 1:
//
//Input: arr = [6,2,4]
//Output: 32
//Explanation:
//There are two possible trees.  The first has non-leaf node sum 36, and the second has non-leaf node sum 32.
//
//    24            24
//   /  \          /  \
//  12   4        6    8
// /  \               / \
//6    2             2   4
// 
//
//Constraints:
//
//2 <= arr.length <= 40
//1 <= arr[i] <= 15
//It is guaranteed that the answer fits into a 32-bit signed integer (ie. it is less than 2^31).
public class MinimumCostTreeFromLeafValues {

	// DP ==> Recursion + MEMO
	// 【1， 2，3， 4】 ==>  (1), 然后处理(2, 3, 4) r// (1, 2), 然后处理(3, 4) ......
	public int mctFromLeafValues(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0; 
        }
        int[][] memo = new int[arr.length][arr.length];
        
        return helper(0, arr.length - 1, arr, memo);
    }
    
    public int helper(int l, int r, int[] arr, int[][] memo) {
        if (l >= r) {
            return 0;//只有一个点，没有非叶子节点，其值为0
        }
        
        if (memo[l][r] != 0)
            return memo[l][r];
        
        int res = Integer.MAX_VALUE;
        
        for (int i = l; i < r; i++) {
                        
            int maxLeft = 0;
            int maxRight = 0;
            
            for (int j = l; j <= i; j++) {
                maxLeft = Math.max(maxLeft, arr[j]);
            }
            
            for (int j = i + 1; j <= r; j++) {
                maxRight = Math.max(maxRight, arr[j]);
            }
            
            int nonLeafNode = maxLeft * maxRight;// 非叶子节点等于左右子树最大叶子值的乘积
            
            int left = helper(l, i, arr, memo); // 处理左子树，得到非叶子节点的最大值
            int right = helper(i + 1, r, arr, memo);
            
            res = Math.min(res, nonLeafNode + left + right);
        }
        
        memo[l][r] = res;
        
        return res;
    }
    
    
    ////////////////////////DP ARRAY
    public int dp(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0; 
        }
        int[][] dp = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length - 1; i++) {
            dp[i][i+1] = arr[i] * arr[i + 1];
        }
        
        // ！！！！子问题是每个字串， 
        // 如何表示出子问题 ， 即最外层循环
        for (int i = 1; i < arr.length; i++) { // i 是 数值间距离
            // split left/right
            for (int l = 0; l < arr.length - i; l++) { //左边界
                int r = i + l; // 右边界
                
//                 if (i == 1) { // 只有两个元素时候  dp[i][i+1] 如上
//                     dp[l][r] = arr[l] * arr[r];
//                 } else {
                    for (int k = l; k < r; k++) {
                        int leftMax = 0;
                        for (int m = l; m <= k; m++) {
                            leftMax = Math.max(leftMax, arr[m]);
                        }
                        int rightMax = 0;
                        for (int m = k + 1; m <= r; m++) {
                            rightMax = Math.max(rightMax, arr[m]);
                        }

                        int noLeafNode = leftMax * rightMax;
                        
                        if (dp[l][r] == 0) // 当第一个时，其数值是0 （默认值）
                            dp[l][r] = dp[l][k] + dp[k + 1][r] + noLeafNode;
                        else 
                            dp[l][r] = Math.min(dp[l][r], dp[l][k] + dp[k + 1][r] + noLeafNode);
                    }
                //}
                
            }
        }
        return dp[0][arr.length - 1];
    }
    
    
    public static void main(String[] args) {
    	new MinimumCostTreeFromLeafValues().dp(new int[] {6, 2, 4});
    }
    
}
