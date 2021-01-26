package test;

public class NumberOfIslands {

	public int numberOfIslands(char[][] grid) {
		if (grid == null || grid.length == 0) return 0;
		boolean[][] isvisited = new boolean[grid.length][grid[0].length];
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 1 && !isvisited[i][j]) {
					count++;
				}
			}
		}
		return count;
	}
	
	public void  helper(int i, int j, char[][] grid, boolean[][] isvisited) {
		if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length
				|| isvisited[i][j] || grid[i][j] != 0) {
			return;
		}
		isvisited[i][j] = true;
		helper(i + 1, j, grid, isvisited);//down
		helper(i - 1, j, grid, isvisited);//up
		helper(i, j + 1, grid, isvisited);//right
		helper(i, j - 1, grid, isvisited);//left
	}
}
