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
                        if (head.y < n - 1) {
                            mark(q, g, head.x, head.y + 1);
                        }
                        if (head.y > 0) {
                            mark(q, g, head.x, head.y - 1);
                        }
                        if (head.x < m - 1) {
                            mark(q, g, head.x + 1, head.y);
                        }
                        if (head.x > 0) {
                            mark(q, g, head.x - 1, head.y);
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
	
	private class Node {
       int x;
       int y;
       public Node(int x, int y) {
           this.x = x;
           this.y = y;
       }
}
}
