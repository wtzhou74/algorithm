package oa.amazon;

import java.util.LinkedList;
import java.util.Queue;

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
	
	private int[][] dir = new int[][] {{1,0}, {-1,0}, {0, 1}, {0, -1}};
	public void dfs(char[][] grid, int row, int col, boolean[][] visited) {
        if (row < 0 || row > grid.length - 1 
            || col < 0 || col > grid[row].length - 1 || visited[row][col]
           || grid[row][col] == '0')
            return;
        visited[row][col] = true; // 一定要注意visited设置的顺序， 如果这里挪到for里面，第一个进来的就不会
        				// 被设置为true!!!!!
        for (int i = 0; i < dir.length; i++) {
            int r = row + dir[i][0];
            int c = col + dir[i][1];
            if (r < 0 || r >= grid.length || c < 0 || c >= grid[r].length)
                continue;
            if (!visited[r][c] && grid[r][c] == '1') {
                //visited[r][c] = true;  //放这里不对！！！
                dfs(grid, r, c, visited);
            }
        }
    }
	// BFS GRAPH TRAVERSAL
	
	class Cell{
        int row;
        int col;
        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    //private int[][] dir = new int[][] {{1,0}, {-1,0}, {0, 1}, {0, -1}};
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;
        int count = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<Cell> queue = new LinkedList<>();
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (!visited[i][j] && grid[i][j] != '0') {
                    queue.offer(new Cell(i, j));
                    visited[i][j] = true;
                    while (!queue.isEmpty()) {
                        Cell cell = queue.poll();
                        //visited[cell.row][cell.col] = true; // 不能仅在这里，否则还在queue中的元素
                        					// 还会被加入到queue中，所以要在入Queue之前就设置好
                        for (int k = 0; k < dir.length; k++) {
                            int r = cell.row + dir[k][0];
                            int c = cell.col + dir[k][1];
                            if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length)
                                continue;
                            if (!visited[r][c] && grid[r][c] == '1') {
                                visited[r][c] = true;
                                queue.offer(new Cell(r, c));
                            }
                                
                        }
                    }
                    count++;
                }
            }
        }
        return count;
    }
	
}
