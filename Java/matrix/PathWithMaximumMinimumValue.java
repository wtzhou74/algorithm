package matrix;

import java.util.Arrays;
import java.util.PriorityQueue;

//Given a matrix of integers A with R rows and C columns, find the maximum score of a path starting at [0,0] and ending at [R-1,C-1].
//
//The score of a path is the minimum value in that path.  For example, the value of the path 8 →  4 →  5 →  9 is 4.
//
//A path moves some number of times from one visited cell to any neighbouring unvisited cell in one of the 4 cardinal directions (north, east, west, south).
//
// 
//
//Example 1:
//
//
//
//Input: [[5,4,5],[1,2,6],[7,4,6]]
//Output: 4
//Explanation: 
//The path with the maximum score is highlighted in yellow. 
//Example 2:
//
//
//
//Input: [[2,2,1,2,2,2],[1,2,2,2,1,2]]
//Output: 2
//Example 3:
//
//
//
//Input: [[3,4,6,3,4],[0,2,1,1,7],[8,8,3,2,7],[3,2,4,9,8],[4,1,2,0,0],[4,6,5,4,3]]
//Output: 3
// 
//
//Note:
//
//1 <= R, C <= 100
//0 <= A[i][j] <= 10^9

// 对所有从起点到终点的路径，求出每个路径对应的Score的最大值，而Score是每条路径中的最小值
public class PathWithMaximumMinimumValue {

	class Cell {
        int r;
        int c;
        int w;
        public Cell(int r, int c, int w) {
            this.r = r;
            this.c = c;
            this.w = w;
        }
    }
    public int maximumMinimumPathBFS(int[][] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        
        boolean[][] visited = new boolean[A.length][A[0].length];
        // ！！！！！
        // 用一个 PriorityQueue 使得BFS时候，先走Score最大的那条路 (别的路径即使后面有比Score更大的值，由于此处其值小于Score，那么那些路径上的最终Score肯定等于或者小于当前这个值)，
        // 而这条路上，最小的那个就是所求的值
        PriorityQueue<Cell> queue = new PriorityQueue<>((a, b) -> b.w - a.w);
        queue.offer(new Cell(0, 0, A[0][0]));
        int pathMinValue = A[0][0];
        // Time： O(NlgN)
        // Space: O(N)
        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            int r = cell.r;
            int c = cell.c;
            
            if (!visited[r][c]) {
                visited[r][c] = true;
                pathMinValue = Math.min(pathMinValue, A[r][c]); // 记录 含有最大Score上的最小值
                if (r == A.length - 1 && c == A[0].length - 1) {
                    return pathMinValue;
                }
                // move left
                if (r - 1 >= 0) {
                    queue.offer(new Cell(r - 1, c, A[r - 1][c]));
                }
                // move right
                if (r + 1 < A.length) {
                    queue.offer(new Cell(r + 1, c, A[r + 1][c]));
                }
                // move up
                if (c - 1 >= 0) {
                    queue.offer(new Cell(r, c - 1, A[r][c - 1]));
                }
                // move down
                if (c + 1 < A[0].length) {
                    queue.offer(new Cell(r, c + 1, A[r][c + 1]));
                }
            }
            
        }
        return pathMinValue;
        
    }
    
    // DFS
    public int maximumMinimumPathDFS(int[][] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        
        boolean[][] visited = new boolean[A.length][A[0].length];
        //int[] ans = new int[1];
        dfs(0, 0, A, visited, Integer.MAX_VALUE);
        return res;
        
    }
    int res = 0;
    // 直接的DFS 导致 TLE
    public void dfs(int r, int c, int[][] A, boolean[][] visited, int score) {
        if (r < 0 || r >= A.length || c < 0 || c >= A[0].length
           || score <= res || visited[r][c]) {
            return;
        }
        // score <= res //当前路径上如果 已知的res的值大于等于Score，那就没必要再往下了，因为res是含有最大Score的一个路径
        // 如果这个Score已经比它小了，没必要往下了，因为最终结果是要最大的SCORE
        score = Math.min(A[r][c], score);
        if (r == A.length - 1 && c == A[0].length - 1) {
            res = Math.max(res, score);
            return;
        }
        
        visited[r][c] = true;
        
        dfs(r - 1, c, A, visited, score);
        dfs(r + 1, c, A, visited, score);
        dfs(r, c - 1, A, visited, score);
        dfs(r, c + 1, A, visited, score);
        
        visited[r][c] = false; // 要backtrack, 因为要回到原来点往其他方向走 ！！！
    }
    
    
    public int maximumMinimumPath(int[][] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
            
        int[] nums = new int[A.length * A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                nums[i * A[0].length + j] = A[i][j];
            }
        }
        
        Arrays.sort(nums);
        int l = 0, r = nums.length - 1;
        while (l < r) {
            boolean[][] visited = new boolean[A.length][A[0].length];
            int mid = l + (r - l) / 2;
            if (!dfs1(0, 0, A, visited, nums[mid])) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return nums[l];
        
    }
    
    public boolean dfs1(int r, int c, int[][] A, boolean[][] visited, int score) {
        if (r < 0 || r >= A.length || c < 0 || c >= A[0].length
           || score <= A[r][c] || visited[r][c]) {
            return false;
        }
        if (r == A.length - 1 && c == A[0].length)
            return true;
        visited[r][c] = true;
        boolean left = dfs1(r - 1, c, A, visited, score);
        boolean right = dfs1(r + 1, c, A, visited, score);
        boolean up = dfs1(r, c - 1, A, visited, score);
        boolean down = dfs1(r, c + 1, A, visited, score);
        //visited[r][c] = false;
        return left || right || up || down;
    }
    
    public static void main(String[] args) {
    	int[][] A = new int[][] {
    		{5, 4, 5},
    		{1, 2, 6},
    		{7, 4, 6}
    	};
    	new PathWithMaximumMinimumValue().maximumMinimumPath(A);
    }
}
