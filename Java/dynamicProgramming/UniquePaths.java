package dynamicProgramming;

public class UniquePaths {

	//private int res = 0;
    public int uniquePaths(int m, int n) {
        //int res = 0;
        int[][] cache = new int[m][n];// For memoization, MEMORIZE PREVIOUSLY result
        return helper(m, n, 0, 0, cache);
        //return res;
    }
    
    // A process to deal with SUBPROBLEM
    public int helper(int m, int n, int down, int right, int[][] cache) {
        if (down >= m || right >= n) return 0;
        if(cache[down][right] != 0) {
            return cache[down][right];
        }
        if (down == m - 1 && right == n - 1) return 1;// FOUND a solution // 只有一种选择
        // two ways to go
        int downPath = helper(m, n, down + 1, right, cache);
        int rightPath = helper(m, n, down, right + 1, cache);
        cache[down][right] = downPath + rightPath;// MEMORIZE THE RESULT OF [DOWN, RIGHT]
        return cache[down][right];
    }
    
    public int uniquePathsDP(int m, int n) {
        if (n < 0) return 0;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) { // 初始化只有一列
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) { // 初始化只有一行
            dp[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1]; // 把不同情况下的dp值相加
            }
        }
        return dp[m - 1][n - 1];
    }
}
