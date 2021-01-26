package graphs;

//You are given a map of a server center, represented as a m * n integer matrix grid, where 1 means that on that cell there is a server and 0 means that it is no server. Two servers are said to communicate if they are on the same row or on the same column.
//
//Return the number of servers that communicate with any other server.
//
// 
//
//Example 1:
//
//
//
//Input: grid = [[1,0],[0,1]]
//Output: 0
//Explanation: No servers can communicate with others.
//Example 2:
//
//
//
//Input: grid = [[1,0],[1,1]]
//Output: 3
//Explanation: All three servers can communicate with at least one other server.
//Example 3:
//
//
//
//Input: grid = [[1,1,0,0],[0,0,1,0],[0,0,1,0],[0,0,0,1]]
//Output: 4
//Explanation: The two servers in the first row can communicate with each other. The two servers in the third column can communicate with each other. The server at right bottom corner can't communicate with any other server.
// 
//
//Constraints:
//
//m == grid.length
//n == grid[i].length
//1 <= m <= 250
//1 <= n <= 250
//grid[i][j] == 0 or 1
public class CountServersThatCommunicate {

	
	public int countServers(int[][] grid) {
        if (grid.length <= 1) return 0;
        //int result = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (!visited[i][j]) {
                    //int count = 1;
                    int count = helper(grid, i, j, visited);
                    if (count > 1) {
                        res += count;
                    }
                }
            }
        }
        return res;
    }
    
    private int block = 0;
	// a由于在同一行或同一列 （不一定要相邻）都算 !!!!!， 所以下面的算法不正确， 下面只会统计相邻的
    public int helper(int[][] grid, int r, int c, boolean[][] visited) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length ||
           grid[r][c] == 0 || visited[r][c]) {
            return 0;
        }
        visited[r][c] = true;
        int count = 1;
        // int block = 0;
        //if (grid[r][c] == 1)
        //count++;
        count += helper(grid, r + 1, c, visited);
        count += helper(grid, r - 1, c, visited);
        count += helper(grid, r, c + 1, visited);
        count += helper(grid, r, c - 1, visited);
        // map.put(block, count);
        //block++; // 因为在回调的时候，后面的递归还会再被执行到，因为每个点都会有 四个方向走，一旦最后语句被执行，此时 block就会被 +1, 所以最终的 block值不是 几个块
        return count;
    }
    
    // a分别处理 行 和 列， 统计 1 的个数
    public int helper0(int[][] grid, int r, int c, boolean[][] visited) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length ||
           grid[r][c] == 0 || visited[r][c]) {
            return 0;
        }
        visited[r][c] = true;
        int count = 1;
        for (int i = 0; i < grid[r].length; i++) {
            count += helper(grid, r, i, visited);
        }
        for (int i = 0; i < grid.length; i++) {
            count += helper(grid, i, c, visited);
        }
        return count;
    }
    
    public static void main(String[] args) {
    	new CountServersThatCommunicate().countServers(new int[][] {
    		{1,1,0,0},
    		{0,0,1,0},
    		{0,0,1,0},
    		{0,0,0,1}
    	});
    }
}
