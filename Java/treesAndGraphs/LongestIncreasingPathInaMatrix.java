package treesAndGraphs;

//Given an integer matrix, find the length of the longest increasing path.
//
//From each cell, you can either move to four directions: left, right, up or down. You may NOT move diagonally or move outside of the boundary (i.e. wrap-around is not allowed).
//
//Example 1:
//
//Input: nums = 
//[
//  [9,9,4],
//  [6,6,8],
//  [2,1,1]
//] 
//Output: 4 
//Explanation: The longest increasing path is [1, 2, 6, 9].
//Example 2:
//
//Input: nums = 
//[
//  [3,4,5],
//  [3,2,6],
//  [2,2,1]
//] 
//Output: 4 
//Explanation: The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.

public class LongestIncreasingPathInaMatrix {

	int longest = Integer.MIN_VALUE;
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        // TLE without cache
        int[][] cache = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
            	// Try each cell, and find its corresponding increasing sequence
            	// Should also check VISITED cell
                longest = Math.max(longest, dfs(matrix, i, j, Integer.MIN_VALUE, cache));
            }
        }
        return longest;
    }
    
    public int dfs (int[][] matrix, int row, int col, int pre, int[][] cache) {
        if (row < 0 || row >= matrix.length 
            || col < 0 || col >= matrix[0].length 
            || matrix[row][col] <= pre) { // In comparison with Previous value !!!!!!!
            return 0;
        }
        if (cache[row][col] != 0) return cache[row][col];
        
        // CURRENT cell is matrix[row][col], so, all FOUR action will compare with CURRENT CELL
        int right = 1 +  dfs(matrix, row + 1, col, matrix[row][col], cache);
        int left = 1 + dfs(matrix, row - 1, col, matrix[row][col], cache);
        int down = dfs(matrix, row, col + 1, matrix[row][col], cache) + 1;
        int up = dfs(matrix, row, col - 1, matrix[row][col], cache) + 1;
        
        int max = Math.max(Math.max(left, right), Math.max(up, down));
        cache[row][col] = max;
        
        return max;
    }
}
