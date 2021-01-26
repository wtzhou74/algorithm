package queueAndStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. 
 * An island is surrounded by water and is formed by connecting adjacent lands 
 * horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
 * 
 * BFS or DFS is common solution for traverse of GRID (2D)
 * */
public class NumberOfIslands {

	// recursive solution
	public int numberOfIslandsSol(char[][] grid)
	{
		if(grid.length == 0 | grid == null)
		{
			return 0;
		}
		
		int count = 0;
		for(int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++)
			{
				// find all nodes of this island, and SET THEIR VALUE TO '0'
				if (grid[i][j] == '1')
				{
					checkIsland(grid, i, j);
					count++;
				}
			}
			
		}
		return count;
	}
	
	// recursion  / DFS solution
	public void checkIsland(char[][] grid, int i, int j)
    {
        // border of island or the node is 0
		// COMPLETE CONDITION OF RECURSIO
        if(i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == '0') return ;
        
        // set value of visited X to '0', then the node will not be traversed again.!!!!!!
        grid[i][j] = '0';	
        
        // check four direction of specified node
        checkIsland(grid, i, j-1);//left
        checkIsland(grid, i-1, j);//up
        checkIsland(grid, i+1, j);//down
        checkIsland(grid, i, j+1);//right
    }
	
	// BFS
	public int numberOfIslandsBFS(char[][] grid)
	{
		char[][] g = grid;
        if (g == null || g.length == 0) return 0;
        
        int m = g.length, n = g[0].length, r = 0;
        Queue<Node> q = new LinkedList<>();
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (g[i][j] == '1') {
                    mark(q, g, i, j);
                    while (!q.isEmpty()) {
                        Node head = q.poll();
                        if (head.col < n - 1) {
                            mark(q, g, head.row, head.col + 1);
                        }
                        if (head.col > 0) {
                            mark(q, g, head.row, head.col - 1);
                        }
                        if (head.row < m - 1) {
                            mark(q, g, head.row + 1, head.col);
                        }
                        if (head.row > 0) {
                            mark(q, g, head.row - 1, head.col);
                        }
                    }

                    r++;
                }
            }
        }
        
        return r;
	}
	
	private void mark(Queue q, char[][] g, int i, int j) {
        if (g[i][j] == '1') {
            q.offer(new Node(i, j));
            g[i][j] = 'x';
        }
	}
	
public int numIslands(char[][] grid) {
		
		if (grid == null || grid.length == 0)
            return 0;
        int count = 0;
        Queue<Node> q = new LinkedList<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1' && 
                    !visited[i][j]) {
                    q.offer(new Node(i, j));
                    count++;
                    visited[i][j] = true;  //！！！入Queue的同时设置visited,否则后面会重复进Queue
                    // 重某个点出发，BFS，四个方向分别陆续出入Queue
                    while (!q.isEmpty()) {
                        Node node = q.poll();
                        //visited[node.row][node.col] = true;
                        // 三个条件： 1） index是否合理； 2) 是否visited  3) 是否是 ‘1’
                        if (node.row > 0 && !visited[node.row - 1][node.col] && grid[node.row - 1][node.col] == '1') {
                            q.offer(new Node(node.row - 1, node.col));
                            visited[node.row - 1][node.col] = true; //!!! 入Queue的同时设置visited,否则后面会重复进Queue,TLE
                        }
                        if (node.row < grid.length - 1 && !visited[node.row + 1][node.col] && grid[node.row + 1][node.col] == '1') {
                            q.offer(new Node(node.row + 1, node.col));
                            visited[node.row + 1][node.col] = true;
                        }
                        if (node.col > 0 && !visited[node.row][node.col - 1] && grid[node.row][node.col - 1] == '1') {
                            q.offer(new Node(node.row, node.col - 1));
                            visited[node.row][node.col - 1] = true;
                        }
                        if (node.col < grid[0].length - 1 && !visited[node.row][node.col + 1] && grid[node.row][node.col + 1] == '1') {
                            q.offer(new Node(node.row, node.col + 1));
                            visited[node.row][node.col + 1] = true;
                        }
                    }
                }
            }
        }
        return count;
	}

	private class Node {
       int row;
       int col;
       public Node(int x, int y) {
           this.row = x;
           this.col = y;
       }
}
}
