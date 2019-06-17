package arrayString;

import java.util.ArrayList;
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
}
