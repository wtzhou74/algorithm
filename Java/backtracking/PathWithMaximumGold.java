package backtracking;

//In a gold mine grid of size m * n, each cell in this mine has an integer representing the amount of gold in that cell, 0 if it is empty.
//
//Return the maximum amount of gold you can collect under the conditions:
//
//Every time you are located in a cell you will collect all the gold in that cell.
//From your position you can walk one step to the left, right, up or down.
//You can't visit the same cell more than once.
//Never visit a cell with 0 gold.
//You can start and stop collecting gold from any position in the grid that has some gold.
// 
//
//Example 1:
//
//Input: grid = [[0,6,0],[5,8,7],[0,9,0]]
//Output: 24
//Explanation:
//[[0,6,0],
// [5,8,7],
// [0,9,0]]
//Path to get the maximum gold, 9 -> 8 -> 7.
//Example 2:
//
//Input: grid = [[1,0,7],[2,0,6],[3,4,5],[0,3,0],[9,0,20]]
//Output: 28
//Explanation:
//[[1,0,7],
// [2,0,6],
// [3,4,5],
// [0,3,0],
// [9,0,20]]
//Path to get the maximum gold, 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7.
// 
//
//Constraints:
//
//1 <= grid.length, grid[i].length <= 15
//0 <= grid[i][j] <= 100
//There are at most 25 cells containing gold.

public class PathWithMaximumGold {

	public int getMaximumGold(int[][] grid) {
        if (grid.length == 0) return 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (visited[i][j] || grid[i][j] == 0) {
                    continue;
                }
                max = Math.max(max, dfs(grid, i, j, visited));
            }
        }
        return max;
    }
    
    //private int gold = 0;
    public int dfs(int[][] grid, int row, int col, boolean[][] visited) {
        if (row < 0 || col < 0 || row >= grid.length ||
            col >= grid[0].length ||
            grid[row][col] == 0 || visited[row][col]) {
            return 0;
        }
        int gold = grid[row][col]; // 初始化不是 0， 而是沿着某条路径，把路上符合条件的值加起来
        				// 所以此处正好到点（i，j），那我们把值加上，接下来加上分别往四个方向返回的值，以此递归下去
        				// 最后选最大
        visited[row][col] = true;
        int up = gold + dfs(grid, row - 1, col, visited);
        int down = gold + dfs(grid, row + 1, col, visited);
        int left = gold + dfs(grid, row, col - 1, visited);
        int right = gold + dfs(grid, row, col + 1, visited);
        
        visited[row][col] = false; // trackback，在下一次dfs进来的时候，这个点还能被访问到
        
        return Math.max(up, Math.max(down, Math.max(left, right)));
        
        
        //return gold;
    }
    
    public static void main(String[] args) {
    	int[][] grid = new int[][] {
    		{0, 6, 0},
    		{5, 8, 7},
    		{0, 9, 0}
    	};
    	new PathWithMaximumGold().getMaximumGold(grid);
    }
}
