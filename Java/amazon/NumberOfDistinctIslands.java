package amazon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.
//
//Count the number of distinct islands. An island is considered to be the same as another if and only if one island can be translated (and not rotated or reflected) to equal the other.
//
//Example 1:
//11000
//11000
//00011
//00011
//Given the above grid map, return 1.
//Example 2:
//11011
//10000
//00001
//11011
//Given the above grid map, return 3.
//
//Notice that:
//11
//1
//and
// 1
//11
//are considered different island shapes, because we do not consider reflection / rotation.
//Note: The length of each dimension in the given grid does not exceed 50.
public class NumberOfDistinctIslands {

	// Number of Islands 的问题，不同点这里是要判断island的形状
	//			===> 根据走的路径（上下左右），为每个动作都设定一个固定值，1，2，3，4，这样出来的path就可以转换成比如：2313的了
	
	public int numDistinctIslands(int[][] grid) {
        if (grid == null) return 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        // set 可以放 list, 直接判断 list 是否一样
        Set<List<Integer>> set = new HashSet<>();
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (!visited[i][j] && grid[i][j] == 1) {
                    List<Integer> path = new ArrayList<>();
                    dfs(grid, i, j, visited, path, 0);
                    //count++;
                    // String s = "";
                    // for (int p : path) {
                    //     s += p;
                    // }
                    //System.out.println(s); // 不用转换成String, set可以放list,并且根据list的元素是否一样判断是否是同一个list
                    set.add(path);
                }
            }
        }
        System.out.println(count);
        return set.size();
    }
    
    public void dfs(int[][] grid, int row, int col, 
                   boolean[][] visited, List<Integer> path, int p) {
        if (row < 0 || row >= grid.length || col < 0
           || col >= grid[0].length || visited[row][col] || grid[row][col] == 0) {
            return;
        }
        visited[row][col] = true;
        path.add(p);
        dfs(grid, row - 1, col, visited, path, 1);
        dfs(grid, row + 1, col, visited, path, 2);
        dfs(grid, row, col - 1, visited, path, 3);
        dfs(grid, row, col + 1, visited, path, 4);
        path.add(0);/// !!!!非常重要，回溯的时候通过已经访问过的点也赋值0， 这样就可以区分同样走法但shape不一样的island
        // 比如：  100
        //      110
        //      000
        //      110
        //      100
        // 这里的两个island是不一样的，但没有在回溯时候设置 0或别的值，其得到的（有效）path是一样的，都是 下-右
        //  但是其实际的path是不一样的，第一个island 下-右，（024000）  而第二个island是 下-回-右（020400）
    }
}
