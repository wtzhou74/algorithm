package amazon;

//Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.
//
//Example:
//
//Input: 
//
//1 0 1 0 0
//1 0 1 1 1
//1 1 1 1 1
//1 0 0 1 0
//
//Output: 4

public class MaximalSquare {

	// brute force - O((m*n)^2)
	public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return 0;
        int rows = matrix.length, cols = matrix[0].length;
        int max = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != '0') {
                    int sqLen = 0;
                    boolean flag = true;
                    while (i + sqLen < rows && j + sqLen < cols && flag) {
                        for (int k = i; k <= i + sqLen; k++) {
                            if (matrix[k][j + sqLen] == '0') {
                                flag = false;
                                break;
                            }
                        }
                        for (int k = j; k <= j + sqLen; k++) {
                            if (matrix[i + sqLen][k] == '0') {
                                flag = false;
                                break;
                            }
                        }
                        if (flag)
                            sqLen++;
                    }
                    max = Math.max(max, sqLen * sqLen);
                    
                }
            }
        }
        return max;
    }
	
	
	// DP, 与Edit Distance类似的问题
	// O(m*n)
	public int maximalSquareDP(int[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return 0;
        int rows = matrix.length, cols = matrix[0].length;
        int maxLen = 0;
        int[][] dp = new int[rows + 1][cols + 1];
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (matrix[i - 1][j - 1] == 1) { // 非 1 的就不用判断，因为这里要形成 square
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    maxLen = Math.max(maxLen, dp[i][j]);
                }
            }
        }
        return maxLen * maxLen;
    }
	
	public int maximalSquareDP2(char[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return 0;
        int rows = matrix.length, cols = matrix[0].length;
        int maxLen = '0';
        // a直接修改原matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != '0' && i != 0 && j != 0) { // 排除第一行第一列，因为matrix[i-1][j-1]
                	//a更新右下角的一个
                    matrix[i][j] = (char)(Math.min(Math.min(matrix[i - 1][j] - '0', matrix[i][j - 1] - '0'), matrix[i - 1][j - 1] - '0') + '1');
                }
                maxLen = Math.max(matrix[i][j], maxLen);
            }
        }
        return (maxLen - '0') * (maxLen - '0');
    }
	
	// a用1D的dp数组
	public int maximalSquareDP3(char[][] matrix) {
        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
        int[] dp = new int[cols + 1];
        int maxsqlen = 0, prev = 0;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                int temp = dp[j];
                if (matrix[i - 1][j - 1] == '1') {
                    dp[j] = Math.min(Math.min(dp[j - 1], prev), dp[j]) + 1;
                    maxsqlen = Math.max(maxsqlen, dp[j]);
                } else {
                    dp[j] = 0;
                }
                prev = temp;
            }
        }
        return maxsqlen * maxsqlen;
    }
	
	public static void main(String[] args) {
		int[][] matrix = new int[][] {
			{0, 1, 1, 1, 0},
			{1, 1, 0, 1, 0},
			{0, 1, 1, 1, 1},
			{0, 1, 0, 0, 1},
			{0, 0, 1, 1, 1}
		};
		new MaximalSquare().maximalSquareDP(matrix);
	}
}
