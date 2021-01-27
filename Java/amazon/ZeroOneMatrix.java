package amazon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//Given a matrix consists of 0 and 1, find the distance of the nearest 0 for each cell.
//
//The distance between two adjacent cells is 1.
//
// 
//
//Example 1:
//
//Input:
//[[0,0,0],
// [0,1,0],
// [0,0,0]]
//
//Output:
//[[0,0,0],
// [0,1,0],
// [0,0,0]]
//Example 2:
//
//Input:
//[[0,0,0],
// [0,1,0],
// [1,1,1]]
//
//Output:
//[[0,0,0],
// [0,1,0],
// [1,2,1]]
// 
//
//Note:
//
//The number of elements of the given matrix will not exceed 10,000.
//There are at least one 0 in the given matrix.
//The cells are adjacent in only four directions: up, down, left and right.
public class ZeroOneMatrix {

	//BFS
	// 也是个DP问题， 参考： https://leetcode.com/problems/01-matrix/solution/
	public int[][] updateMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return new int[0][0];
        int[][] directions = new int[][] {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };
        int[][] res = new int[matrix.length][matrix[0].length];
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    queue.offer(new int[] {i, j});
                    //matrix[i][j] = -1;// 这个被用作 visited
                    visited[i][j] = true; // 这样的话可以不用 新建 int[][]，直接在 matrix上改
                }
            }
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        int dis = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            dis++;
            while (size > 0) {
                int[] cell = queue.poll();
                int r = cell[0];
                int c = cell[1];
                for (int[] direction : directions) {
                    int row = r + direction[0];
                    int col = c + direction[1];
                    if (row < 0 || row >= m || col < 0 
                        || col >= n || matrix[row][col] == -1) {
                        continue;
                    }
                    if (matrix[row][col] > 0) {
                        res[row][col] = dis;
                        matrix[row][col] = -1;
                        queue.offer(new int[]{row, col});
                    }
                }
                size--;
            }
        }
        return res;
    }
	
	// 本题中不是一个好的解法  ==> 类似题的DFS解法，参考 WallsAndGates.java
	public int[][] updateMatrixDFS(int[][] matrix) {
        if(matrix.length==0) return matrix;
        
        for(int i = 0; i<matrix.length; i++)
            for(int j = 0; j<matrix[0].length; j++)
                if(matrix[i][j]==1&&!hasNeiberZero(i, j,matrix)) 
                    matrix[i][j] = matrix.length+matrix[0].length+1;
        
        for(int i = 0; i<matrix.length; i++)
            for(int j = 0; j<matrix[0].length; j++)
                if(matrix[i][j]==1)
                    dfs(matrix, i, j, -1);
        
        return matrix;
    }
    private void dfs(int[][] matrix, int x, int y, int val){
        if(x<0||y<0||y>=matrix[0].length||x>=matrix.length||matrix[x][y]<=val)
            return;
        
        if(val>0) matrix[x][y] = val;
        
        dfs(matrix, x+1, y, matrix[x][y]+1);
        dfs(matrix, x-1, y, matrix[x][y]+1);
        dfs(matrix, x, y+1, matrix[x][y]+1);
        dfs(matrix, x, y-1, matrix[x][y]+1);
        
    }
    private boolean hasNeiberZero(int x, int y, int[][] matrix){
        if(x>0&&matrix[x-1][y]==0) return true;
        if(x<matrix.length-1&&matrix[x+1][y]==0) return true;
        if(y>0&&matrix[x][y-1]==0) return true;
        if(y<matrix[0].length-1&&matrix[x][y+1]==0) return true;
        
        return false;
    }
    
	public static void main(String[] args) {
		int[][] matrix = new int[][] {
			{0, 0, 0},
			{0, 1, 0},
			{1, 1, 1}
		};
		new ZeroOneMatrix().updateMatrix(matrix);
	}
}
