package matrix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SpiralOrder {

	/**
	 * Given a matrix of m x n elements (m rows, n columns), 
	 * return all elements of the matrix in spiral order.
	 * 
	 * */
	public static List<Integer> spiralMatrixSolution(int[][] matrix)
	{
		// EMPTY matrix
		if (matrix.length == 0) return new ArrayList<Integer>();
		
		List<Integer> nums = new ArrayList<Integer>();
        //if (matrix == null || matrix.length == 0) return nums;
        int upperRow = 0;
        int lowerRow = matrix.length - 1;
        int rightCol = matrix[0].length - 1;
        int leftCol = 0;
        while (upperRow <= lowerRow && leftCol <= rightCol) {
            for (int i = leftCol; i <= rightCol; i++) {
                nums.add(matrix[upperRow][i]);
            }
            upperRow++;
            
            for (int j = upperRow; j <= lowerRow; j++) {
                nums.add(matrix[j][rightCol]);
            }
            rightCol--;
            
            for (int i = rightCol; i >= leftCol && upperRow <= lowerRow; i--) {
                nums.add(matrix[lowerRow][i]);
            }
            lowerRow--;
            
            for (int j = lowerRow; j >= upperRow && leftCol <= rightCol; j--) {
                nums.add(matrix[j][leftCol]);
            }
            leftCol++;
        }
        return nums;
		
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
}
