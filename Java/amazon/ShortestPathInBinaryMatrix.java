package amazon;

import java.util.LinkedList;
import java.util.Queue;

//In an N by N square grid, each cell is either empty (0) or blocked (1).
//
//A clear path from top-left to bottom-right has length k if and only if it is composed of cells C_1, C_2, ..., C_k such that:
//
//Adjacent cells C_i and C_{i+1} are connected 8-directionally (ie., they are different and share an edge or corner)
//C_1 is at location (0, 0) (ie. has value grid[0][0])
//C_k is at location (N-1, N-1) (ie. has value grid[N-1][N-1])
//If C_i is located at (r, c), then grid[r][c] is empty (ie. grid[r][c] == 0).
//Return the length of the shortest such clear path from top-left to bottom-right.  If such a path does not exist, return -1.
//
// 
//
//Example 1:
//
//Input: [[0,1],[1,0]]
//
//
//Output: 2
//
//Example 2:
//
//Input: [[0,0,0],[1,1,0],[1,1,0]]
//
//
//Output: 4
//
// 
//
//Note:
//
//1 <= grid.length == grid[0].length <= 100
//grid[r][c] is 0 or 1
public class ShortestPathInBinaryMatrix {

	// 读懂题意后，就是 沿着是0的（8个方向）从左上角走到右下角  ===> 最短路径  ==> BFS
	public int shortestPathBinaryMatrix(int[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;
        int row = grid.length, col = grid[0].length;
        // top-left, 和 bottom-right都必须是 0
        if (grid[0][0] != 0 || grid[row - 1][col - 1] != 0)
            return -1;
        if (grid[0].length == 1) // EDGE case, 否则在while的时候，不会在里面return, 外面return -1 就不对了
        			// 所以这里我们单独判断！！！！！
            return 1;
        // 同样这里要有个visited,因为在按8个方向走的时候会走回原来的点的
        boolean[][] visited = new boolean[row][col];
        int[][] dirs = new int[][] {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1},
            {1, 1},
            {-1, -1},
            {1, -1},
            {-1, 1}
        };
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {0, 0});
        visited[0][0] = true;
        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            count++;
            while (size > 0) {
                int[] cell = queue.poll();
                for (int[] dir : dirs) {
                    int r = cell[0] + dir[0];
                    int c = cell[1] + dir[1];
                    if (r < 0 || r >= row || c < 0 || c >= col
                       || visited[r][c] || grid[r][c] == 1)
                        continue;
                    if (r == row - 1 && col - 1 == c)
                        return count + 1;
                    if (grid[r][c] == 0) {
                        visited[r][c] = true;
                        queue.offer(new int[] {r, c});
                    }  
                }
                size--;
            }
        }
        return -1;
    }
	
	public int DFS(int[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;
        int row = grid.length, col = grid[0].length;
        if (grid[0][0] != 0 || grid[row - 1][col - 1] != 0)
            return -1;
        if (grid[0].length == 1)
            return 1;
        boolean[][] visited = new boolean[row][col];
        visited[0][0] = true;
        int[][] dirs = new int[][] {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1},
            {1, 1},
            {-1, -1},
            {1, -1},
            {-1, 1}
        };
        dfs(grid, 0, 0, visited, dirs);
        return min == Integer.MAX_VALUE ? -1 : min;
        
    }
	int min = Integer.MAX_VALUE;
    public int dfs(int[][] grid, int r, int c, boolean[][] visited,
                  int[][] dirs) {
        // if (r < 0 || r >= grid.length || c < 0 || 
        //    c >= grid[0].length || grid[r][c] == 1 ||
        //    visited[r][c])
        //     return ;
        // visited[r][c] = true;
        if (r == grid.length - 1 && c == grid[0].length - 1) {
            return 1;
        }
        int depth = 0;
        for (int[] dir : dirs) {
            int newR = r + dir[0];
            int newC = c + dir[1];
            if (newR < 0 || newR >= grid.length || newC < 0 || 
           newC >= grid[0].length || grid[newR][newC] == 1 ||
           visited[newR][newC])
                continue;
            visited[newR][newC] = true;
            depth = dfs(grid, newR, newC, 
                visited, dirs) + 1;
            System.out.println(depth);
            min = Math.min(min, depth);
        }
        return min;
    }
    
    public static void main(String[] args) {
    	int[][] grid = new int[][] {
    		{0, 0, 0},
    		{1, 1, 0},
    		{1, 1, 0}
    	};
    	new ShortestPathInBinaryMatrix().DFS(grid);
    }
}
