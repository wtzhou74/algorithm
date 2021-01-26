package matrix;

import java.util.HashSet;
import java.util.Set;

//Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.
//
//Example 1:
//
//Input: 
//[
//  [1,1,1],
//  [1,0,1],
//  [1,1,1]
//]
//Output: 
//[
//  [1,0,1],
//  [0,0,0],
//  [1,0,1]
//]
//Example 2:
//
//Input: 
//[
//  [0,1,2,0],
//  [3,4,5,2],
//  [1,3,1,5]
//]
//Output: 
//[
//  [0,0,0,0],
//  [0,4,5,0],
//  [0,3,1,0]
//]
//Follow up:
//
//A straight forward solution using O(mn) space is probably a bad idea.
//A simple improvement uses O(m + n) space, but still not the best solution.
//Could you devise a constant space solution?

public class SetMatrixZeros {

	// TIME: O(M*N)
	// Space: O(M+N)
	public void setZeros(int[][] matrix) {
		Set<Integer> rowzero = new HashSet<>();
		Set<Integer> colzero = new HashSet<>();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0) {
					rowzero.add(i);
					colzero.add(j);
				}
			}
		}
		for (int row : rowzero) {
			int j = 0;
			while (j < matrix[0].length) {
				matrix[row][j] = 0;
				j++;
			}
		}
		for (int col : colzero) {
			int j = 0;
			while (j < matrix.length) {
				matrix[j][col] = 0;
				j++;
			}
		}
	}
	
	// Time: O(m*n) * (m + n)
	// Space: O(1)
	public void sol2(int[][] matrix) {
		int flag = -100000;
        int rowLength = matrix.length;
        int colLength = matrix[0].length;
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    for (int k = 0; k < colLength; k++) {
                    	// Set the cell whose value is NOT 0 of row/col to the FLAG
                        if (matrix[i][k] != 0) {
                            matrix[i][k] = flag;
                        }
                    }
                    for (int k = 0; k < rowLength; k++) {
                        if (matrix[k][j] != 0) {
                            matrix[k][j] = flag;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < colLength; j++) {
                if (matrix[i][j] == flag) {
                    matrix[i][j] = 0;
                }
            }
            
        }
	}
	
	public static void main(String[] args) {
		int[][] matrix = new int[][] {{0,1,2,0}, {3,4,5,2}, {1,3,1,5}};
		new SetMatrixZeros().setZeros(matrix);
	}
}
