package matrix;

//Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
//
//Example 1:
//
//Input:
//11110
//11010
//11000
//00000
//
//Output: 1
//Example 2:
//
//Input:
//11000
//11000
//00100
//00011
//
//Output: 3

// IMPLICITLY CONSTRAINT: VISITED PATH !!!!!
public class NumberOfIslands {

	// DFS of GRAPH traversal
	// Time: O(M * N)
	// Space: O(mn)
	public int numberOfIsland(char[][] grid) {
		if (grid.length == 0) return 0;
		int res = 0;
		boolean[][] visitedPath = new boolean[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				// starting from a cell whose value is 1
				// AND the PATH starting from this vell HAS NOT BEEN VISITED !!!!!!
				if (grid[i][j] == '1' && !visitedPath[i][j]) {
					helper(grid, i, j, visitedPath);// each is a solution
					res ++;
				}
			}
			
		}
		return res;
		
	}
	public void helper(char[][] grid, int row, int col, boolean[][] visitedPath) {
        if (row < 0 || row >= grid.length || col < 0 
                || col >= grid[0].length || 
                grid[row][col] != '1' || visitedPath[row][col]) {
            return ; 
        }
        visitedPath[row][col] = true; // the path has been visited
        helper(grid, row -1, col, visitedPath); // for current path, go up,
        helper(grid, row + 1, col, visitedPath);// ... go down
        helper(grid, row, col - 1, visitedPath);// .... go left
        helper(grid, row, col + 1, visitedPath);// .... go right
        
        // NO NEED TO TRACE BACK, HERE WE JUST NEED TO FIND A PATH, THEN DONE, GO BACK AND STARTING FROM NEXT '1' CELL
	}
	
	// TODO: BFS GRAPH TRAVERSAL
	
}
