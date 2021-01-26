package graphs;

import java.util.LinkedList;
import java.util.Queue;

//Given an N x N grid containing only values 0 and 1, where 0 represents water and 1 represents land, find a water cell such that its distance to the nearest land cell is maximized and return the distance.
//
//The distance used in this problem is the Manhattan distance: the distance between two cells (x0, y0) and (x1, y1) is |x0 - x1| + |y0 - y1|.
//
//If no land or water exists in the grid, return -1.
//
// 
//
//Example 1:
//
//
//
//Input: [[1,0,1],[0,0,0],[1,0,1]]
//Output: 2
//Explanation: 
//The cell (1, 1) is as far as possible from all the land with distance 2.
//Example 2:
//
//
//
//Input: [[1,0,0],[0,0,0],[0,0,0]]
//Output: 4
//Explanation: 
//The cell (2, 2) is as far as possible from all the land with distance 4.
// 
//
//Note:
//
//1 <= grid.length == grid[0].length <= 100
//grid[i][j] is 0 or 1

// ==> 一个求“最短路径”的graph  ===> BFS 的层次
//  ====> 1) 从每个0开始，找到最近的1，得到距离， 然后从距离里面找最大  ===> Time (O(m * n + m * n))
//  ====> 2) 既然目的地是 1,那么“从1开始”，把所有的 1 放入 Q中 （剩下都是 0了），
//				再一层层往外扩，每扩一层，距离增加 1， 直到没有点为此  ====> Time (O(m * n))
// 这里引出的 "Manhattan distance" 在Matrix,其实就是层次 （每层距离都是 1）
public class AsFarFromLandAsPossible {

	public int maxDistance(int[][] grid) {
        int[][] dir = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (!visited[i][j] && grid[i][j] == 1) {
                    queue.offer(new int[] {i, j});
                }
            }
        }
        if (queue.size() == 0 || queue.size() == grid.length * grid[0].length) return -1;
        
        int distance = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            distance++;
            while (size > 0) {
                int[] cell = queue.poll();
                int i = cell[0];
                int j = cell[1];
                
                for (int[] d : dir) {
                    int nextRow = i + d[0];
                    int nextCol = j + d[1];
                    if (nextRow < 0 || nextRow >= grid.length
                       || nextCol < 0 || nextCol >= grid[0].length
                       || visited[nextRow][nextCol]
                        //|| grid[nextRow][nextCol] == 1 // 因为所有的 1已经在一开始就加入 Q了，所以此处没必要再加上，但不影响
                        ) {
                        continue;
                    }
                    visited[nextRow][nextCol] = true;
                    queue.offer(new int[]{nextRow, nextCol});
                }
                size--;
            }
            
        }
        return distance - 1;
    }
}
