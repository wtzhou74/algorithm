package amazon;

import java.util.LinkedList;
import java.util.Queue;

//An image is represented by a 2-D array of integers, each integer representing the pixel value of the image (from 0 to 65535).
//
//Given a coordinate (sr, sc) representing the starting pixel (row and column) of the flood fill, and a pixel value newColor, "flood fill" the image.
//
//To perform a "flood fill", consider the starting pixel, plus any pixels connected 4-directionally to the starting pixel of the same color as the starting pixel, plus any pixels connected 4-directionally to those pixels (also with the same color as the starting pixel), and so on. Replace the color of all of the aforementioned pixels with the newColor.
//
//At the end, return the modified image.
//
//Example 1:
//Input: 
//image = [[1,1,1],[1,1,0],[1,0,1]]
//sr = 1, sc = 1, newColor = 2
//Output: [[2,2,2],[2,2,0],[2,0,1]]
//Explanation: 
//From the center of the image (with position (sr, sc) = (1, 1)), all pixels connected 
//by a path of the same color as the starting pixel are colored with the new color.
//Note the bottom corner is not colored 2, because it is not 4-directionally connected
//to the starting pixel.
//Note:
//
//The length of image and image[0] will be in the range [1, 50].
//The given starting pixel will satisfy 0 <= sr < image.length and 0 <= sc < image[0].length.
//The value of each color in image[i][j] and newColor will be an integer in [0, 65535].
public class FloodFill {

	// BFS
	public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if (image == null || image.length == 0)
            return image;
        // 在该题中，比较的始终是初始color,跟后面没有关系，所以初始color跟要fill的color一样，直接就返回
        if (image[sr][sc] == newColor) return image;
        
        int[][] direction = new int[][] {{1,0},{-1,0}, {0, 1}, {0, -1}};
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[image.length][image[0].length];
        int color = image[sr][sc];
        queue.offer(new int[]{sr, sc});
        //image[sr][sc] = newColor;
        visited[sr][sc] = true; 
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                int[] cell = queue.poll();
                image[cell[0]][cell[1]] = newColor;
                for (int[] d : direction) {
                    int r = cell[0] + d[0];
                    int c = cell[1] + d[1];
                    
                    if (r < 0 || r >= image.length ||
                       c < 0 || c >= image[0].length
                       || visited[r][c]) {
                        continue;
                    }
                    if (image[r][c] == color) {
                        queue.offer(new int[]{r, c});
                        visited[r][c] = true;
                    }
                }
                size--;
            }
        }
        return image;
    }
	
	// DFS
	public int[][] floodFill1(int[][] image, int sr, int sc, int newColor) {
        if (image == null || image.length == 0)
            return image;
        if (image[sr][sc] == newColor) return image;
        boolean[][] visited = new boolean[image.length][image[0].length];
        int color = image[sr][sc];
        dfs(image, sr, sc, newColor, color, visited);
        return image;
    }
    
    public void dfs(int[][] image, int sr, int sc, int newColor, int color,
                   boolean[][] visited) {
        if (sr < 0 || sc < 0 || sr >= image.length 
            || sc >= image[0].length || visited[sr][sc]
           || image[sr][sc] != color)
            return;
        visited[sr][sc] = true;
        image[sr][sc] = newColor;
        dfs(image, sr + 1, sc, newColor, color, visited);
        dfs(image, sr - 1, sc, newColor, color, visited);
        dfs(image, sr, sc + 1, newColor, color, visited);
        dfs(image, sr, sc - 1, newColor, color, visited);
    }
}
