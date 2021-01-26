package matrix;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//You are given a m x n 2D grid initialized with these three possible values.
//
//-1 - A wall or an obstacle.
//0 - A gate.
//INF - Infinity means an empty room. We use the value 231 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.
//Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.
//
//Example: 
//
//Given the 2D grid:
//
//INF  -1  0  INF
//INF INF INF  -1
//INF  -1 INF  -1
//  0  -1 INF INF
//After running your function, the 2D grid should be:
//
//  3  -1   0   1
//  2   2   1  -1
//  1  -1   2  -1
//  0  -1   3   4
public class WallsAndGates {

	// DFS  ==> 每次从 0 开始， 更新一遍 distance
	public void wallsAndGates(int[][] rooms) {
        if (rooms == null || rooms.length == 0) return;
        boolean[][] visited = new boolean[rooms.length][rooms[0].length];//没必要
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
            	// 以此从0开始，更新一遍 CELL's distance
                if (!visited[i][j] && rooms[i][j] == 0) {
                    rooms[i][j] = 1; //设置1 就是 visited, 这样回来的时候不会再从这个0开始
                    dfs(rooms, i, j, visited, 0);
                }
            }
        }
    }
    
    int inf = Integer.MAX_VALUE;
    public void dfs(int[][] rooms, int i, int j, boolean[][] visited, int dis) {
        if (i < 0 || i >= rooms.length || j < 0 ||
           j >= rooms[i].length // || visited[i][j] 
           || rooms[i][j] == -1 || rooms[i][j] <= dis) { //！！！ <= dis 保证了不会往回走，实现了visited的功能，同时在第二/三个0
        							/// 开始dfs的时候，如果之前 cell的distance更小，说明有别的0可以更快到达，就不用再往下了
            return ;
        }
        if (rooms[i][j] > dis) { // DFS的过程中不断更新路过的cell的distance
            rooms[i][j] = dis; // 留下小的记录
        }
        //visited[i][j] = true;
        dfs(rooms, i - 1, j, visited, dis + 1);
        dfs(rooms, i + 1, j, visited, dis + 1);
        dfs(rooms, i, j - 1, visited, dis + 1);
        dfs(rooms, i, j + 1, visited, dis + 1);
        
    }
    
    // BFS， 即从收集所有的0开始， 那么跟这些相邻的0 就是 dis=1; 紧接着从这些1开始，继续BFS下去，直到所有INF都填满
    public void wallsAndGatesBFS(int[][] rooms) {
        if (rooms == null || rooms.length == 0) return;
        List<int[]> directions = Arrays.asList(
            new int[] {1, 0},
            new int[] {-1, 0},
            new int[] {0, 1},
            new int[] {0, -1}
        );
        
        int inf = Integer.MAX_VALUE;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                if (rooms[i][j] == 0) {
                    queue.offer(new int[] {i, j});
                }
            }
        }
        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            count++;
            while (size > 0) {
                int[] cell = queue.poll();
                int row = cell[0];
                int col = cell[1];
                for (int i = 0; i < directions.size(); i++) {
                    int r = row + directions.get(i)[0];
                    int c = col + directions.get(i)[1];
                    if (r < 0 || r >= rooms.length ||
                       c < 0 || c >= rooms[0].length ||
                       rooms[r][c] != inf) { // 我们只修改 inf 的cell
                        continue;
                    }
                    rooms[r][c] = count; //相当于设置了 visited标识
                    queue.offer(new int[] {r, c});
                }
                size--;
            }
            
        }
    }
    
    public static void main(String[] args) {
    	int max = Integer.MAX_VALUE;
    	int[][] rooms = new int[][] {
    		{max, -1, 0},
    		{max, max, max, -1},
    		{max, -1, max, -1},
    		{0, -1, max, max}
    	};
    	new WallsAndGates().wallsAndGates(rooms);
    }
}
