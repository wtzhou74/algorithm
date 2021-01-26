package unionFind;
//
//Given an m x n 2d grid map of '1's (land) and '0's (water), return the number of islands.
//
//An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
//
// 
//
//Example 1:
//
//Input: grid = [
//  ["1","1","1","1","0"],
//  ["1","1","0","1","0"],
//  ["1","1","0","0","0"],
//  ["0","0","0","0","0"]
//]
//Output: 1
//Example 2:
//
//Input: grid = [
//  ["1","1","0","0","0"],
//  ["1","1","0","0","0"],
//  ["0","0","1","0","0"],
//  ["0","0","0","1","1"]
//]
//Output: 3
// 
//
//Constraints:
//
//m == grid.length
//n == grid[i].length
//1 <= m, n <= 300
//grid[i][j] is '0' or '1'.
public class NumberOfIslands {

	class DSU {
        int[] parent;
        int count;
        public DSU(char[][] grid) {
            parent = new int[grid.length * grid[0].length];
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == '1') {
                        parent[i * grid[0].length + j] = i * grid[0].length + j;
                        count++; // 初始化的同时，各个cell代表的就是一个组，在接下来合并后，count--；
                    }
                }
            }
        }
        
        public int findParent(int c) {
            if (parent[c] == c) {
                return c;
            }
            int p = parent[c];
            p = findParent(p);
            return p;
        }

        public void union(int i, int j) {
            int pi = findParent(i);
            int pj = findParent(j);

            if (pi != pj) {
                parent[pi] = pj;
                count--; // 合并了一组，总组数就得 -1
            }
        }
    }
    
    
    
    int[][] dirs = new int[][] {{1,0}, {-1, 0}, {0, 1}, {0, -1}};
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;
        DSU dsu = new DSU(grid);
        int numRow = grid.length;
        int numCol = grid[0].length;
        //int r = 0, c = 0;
        //boolean[][] visited = new boolean[numRow][numCol];
        for (int r = 0; r < numRow; r++) {
            for (int c = 0; c < numCol; c++) {
                if (grid[r][c] == '0')
                    continue;
                for (int[] dir : dirs) {
                    int nr = r + dir[0];
                    int nc = c + dir[1];
                    if (nr < 0 || nc < 0 || nr >= numRow || nc >= numCol
                       || grid[nr][nc] == '0') {
                        continue;
                    }
                    //visited[nr][nc] = true;
                    //System.out.println(numCol * nr + nc);
                    dsu.union(numCol * r + c, numCol * nr + nc);
                }
            }
        }
        
        return dsu.count;
    }
}
