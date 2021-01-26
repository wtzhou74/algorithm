package dynamicProgramming;

//Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?
//
//Example:
//
//Input: 3
//Output: 5
//Explanation:
//Given n = 3, there are a total of 5 unique BST's:
//
//   1         3     3      2      1
//    \       /     /      / \      \
//     3     2     1      1   3      2
//    /     /       \                 \
//   2     1         2                 3
public class UniqueBinarySearchTrees {

	// a遍历N 个数，每个数都可以作为 root, 那么当它为 root 的时候， 其对应的 BST 个数是多少  ===> DP 问题
	// a这里只要个数而不需要具体输出树， 所以这里找出关系即可
	// a结果只跟节点的个数有关， 跟顺序无关， 这就是为什么 dp[4]的结果适用与 [4, 5, 6, 7]
	public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;// Empty is also a valid BST !!!!
        dp[1] = 1;
        // a树节点的个数
        for (int i = 2; i <= n; i++) {
        	// [1, 2, 3, 4, 5, 6, 7]  ===> when i = 3, left f(1, 3), right f(4, 7)
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j]; // 乘法这里是因为 左右两边的组合问题  1 =》 （2， 3）  ==》 (1, 2;) (1, 3)
            }
        }
        return dp[n];
        
    }
	
	
	// Recursive + MEMO
	public int recursiveMemo(int n) {
        int[] memo = new int[n + 1];
        memo[0] = 1;
        memo[1] = 1;
        
        return helper(1, n, memo);
        
    }
    
    public int helper(int left, int right, int[] memo) {
        if (left >= right) {
            return 1;
        }
        int count = 0;
        // if (memo[right - left] != 0)
        //     return memo[right - left];
        
        for (int i = left; i <= right; i++) {
            int leftCount = memo[i - left] != 0 ? memo[i - left] : helper(left, i - 1, memo);
            int rightCount = memo[right - i] != 0 ? memo[right - i] : helper(i + 1, right, memo);
            
            count += leftCount * rightCount;
        }
        
        memo[right - left + 1] = count;// memorized new number
        
        return count;
    }
}
