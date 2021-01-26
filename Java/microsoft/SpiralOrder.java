package microsoft;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SpiralOrder {

	/**
	 * Given a matrix of m x n elements (m rows, n columns), 
	 * return all elements of the matrix in spiral order.
	 * 
	 * */
	public List<Integer> spiralMatrixSolution(int[][] matrix)
	{
		if (matrix == null || matrix.length == 0)
            return new ArrayList<>();
        List<Integer> result = new LinkedList<>();
        int c = 0, r = 0;
        int maxC = matrix[0].length - 1;
        int maxR = matrix.length - 1;
        while (c <= maxC && r <= maxR) {
        	// move right
            for (int i = c; i <= maxC; i++) { // col is changing
                result.add(matrix[r][i]);
            }
            r++;
            // move down
            for (int i = r; i <= maxR; i++) {
                result.add(matrix[i][maxC]);
            }
            maxC--;
            // move left
            for (int i = maxC; i >= c && maxR >= r; i--) {
                result.add(matrix[maxR][i]);
            }
            //a如果没加 maxR >= r， 在判断左移或者上移的时候，会走回去，因为 C 始终满足，但是 R不满足了，原路返回了
            // [[ 1, 2, 3,4],[ 5, 6, 7, 8 ],[ 9,10,11,12 ]] //a会得到错误的[1,2,3,4,8,12,11,10,9,5,6,7，6]
            maxR--;
            // move up
            for (int i = maxR; i >= r && maxC >= c; i--) {
                result.add(matrix[i][c]);
            }
            c++;
        }
        return result;
		
		//return res;
	}
	
	public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return new ArrayList<>();
        List<Integer> result = new LinkedList<>();
        int[] rowDir = new int[]{0, 1, 0, -1};
        int[] colDir = new int[]{1, 0, -1, 0}; // 移动的方向，最开始左移
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        int c = 0, r = 0;
        int di = 0;
        for (int i = 0; i < matrix.length * matrix[0].length; i++) {
            result.add(matrix[r][c]);
            visited[r][c] = true;
            int nr = r + rowDir[di];
            int cr = c + colDir[di];
            if (nr < 0 || cr < 0 || nr >= matrix.length 
                || cr >= matrix[0].length || visited[nr][cr]) {
                di = (di + 1) % 4; // 转弯
                r += rowDir[di];
                c += colDir[di];
            } else {
                r = nr;
                c = cr;
            }
        }
        return result;
    }
	
	public static void main(String[] args) {
		int[][] matrix = new int[][] {
			{1,2,3,4},
			{5,6,7,8},
			{9,10,11,12}
		};
		new SpiralOrder().spiralMatrixSolution(matrix);
	}
}
