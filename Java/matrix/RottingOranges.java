package matrix;

import java.util.LinkedList;
import java.util.Queue;

public class RottingOranges {

	public int orangesRotting(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        // All updates will start from rotten organge   ===> "Root node"   ===> Their adjacent nodes are its Children
        // BFS
        Queue<Integer> queue = new LinkedList<>();
        int counter = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    int cellInx = i * grid[0].length + j;
                    queue.offer(cellInx);
                }
            }
        }
        while (!queue.isEmpty()) {
        	int size = queue.size();
            
            while (size > 0) {

                int cellInx = queue.poll();
                // check its 4-directionally cells
                int i = cellInx / grid[0].length; // row
                int j = cellInx % grid[0].length; // col
                
            	// up
                if (i - 1 >= 0 && grid[i - 1][j] == 1) {
                    grid[i - 1][j] = 2;
                    queue.offer((i - 1) * grid[0].length + j); // [[1, 1, 2, 0, 2]]  // both two 1 will be updated, but the first will be updated at the second time.
                }
                // down
                if (i + 1 < grid.length && grid[i + 1][j] == 1) {
                    grid[i + 1][j] = 2;
                    queue.offer((i + 1) * grid[0].length + j);
                }
                // right
                if (j + 1 < grid[0].length && grid[i][j + 1] == 1) {
                    grid[i][j + 1] = 2;
                    queue.offer(i * grid[0].length + (j + 1));
                }
                // left
                if (j - 1 >= 0 && grid[i][j - 1] == 1) {
                    grid[i][j - 1] = 2;
                    queue.offer(i * grid[0].length + (j - 1));
                }
                size--;
            }
            if (queue.size() > 0) counter++;// non-leaf nodes  // One leve one minute, not one update one min ==> See RottingOranges.png
        }
        
        // BELOW ARE WRONG, for UPDATE CELL may AFFECT PREVIOUS VISITED CELL, e.g. [1, 1, 2, 0, 2, 0], The first 1 and second 1.
        // ======> Place it in a Queue, once get a 2, throw it into a Queue again, then check its adjacent nodes (Child nodes)
//         int counter = 0;
//         for (int i = 0; i < grid.length; i++) {
//             for (int j = 0; j < grid[0].length; j++) {
//                 if (grid[i][j] == 0) continue;
    
//                 if (grid[i][j] == 2) {
//                     boolean updated = false;
//                     // up
//                     if (i - 1 >= 0 && grid[i - 1][j] == 1) {
//                         grid[i - 1][j] = 2;
//                         updated = true;
//                     }
//                     // down
//                     if (i + 1 < grid.length && grid[i + 1][j] == 1) {
//                         grid[i + 1][j] = 2;
//                         updated = true;
//                     }
//                     // right
//                     if (j + 1 < grid[0].length && grid[i][j + 1] == 1) {
//                         grid[i][j + 1] = 2;
//                         updated = true;
//                     }
//                     // left
//                     if (j - 1 >= 0 && grid[i][j - 1] == 1) {
//                         grid[i][j - 1] = 2;
//                         updated = true;
//                     }
//                     if (updated) counter++;
//                 }
//             }
//         }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    return -1;
                }
            }
        }
        return counter;
    }
	
	public int orangesRotting2(int[][] grid) {
        if (grid.length == 0 || grid[0].length == 0) 
            return 0;
        Queue<int[]> q = new LinkedList<>();
        int total = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 2)
                    q.offer(new int[]{i, j});
                if (grid[i][j] == 1)
                    total++;
            }
        }
        int count = 0;                
        while (!q.isEmpty()) {
            int size = q.size();
            if (total > 0)
                count++;
            while (size > 0) {
                int[] cell = q.poll();
                int row = cell[0];
                int col = cell[1];
                if (row > 0 && grid[row - 1][col] == 1) {
                    q.offer(new int[]{row - 1, col});
                    grid[row - 1][col] = 2;
                    total--;
                }
                if (row < grid.length - 1 && grid[row + 1][col] == 1) {
                    q.offer(new int[]{row + 1, col});
                    grid[row + 1][col] = 2;
                    total--;
                }
                if (col > 0 && grid[row][col - 1] == 1) {
                    q.offer(new int[]{row, col - 1});
                    grid[row][col - 1] = 2;
                    total--;
                }
                if (col < grid[0].length - 1 && grid[row][col + 1] == 1) {
                    q.offer(new int[]{row, col + 1});
                    grid[row][col + 1] = 2;
                    total--;
                }
                size--;
            }
        }
        return total == 0 ? count : -1;
    }
	
	public static void main(String[] args) {
		//new RottingOranges().orangesRotting(new int[][] {{2,1,1}, {1,1,0}, {0, 1, 1}});
		new RottingOranges().orangesRotting(new int[][] {{1}, {2}});
	}
}
