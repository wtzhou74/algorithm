package recursionII;

/*Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

Integers in each row are sorted in ascending from left to right.
Integers in each column are sorted in ascending from top to bottom.
Example:

Consider the following matrix:

[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
Given target = 5, return true.

Given target = 20, return false.*/

public class SearchA2DMatrixII {
	
	// brute force solution
	public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0) return false;
        for (int i = 0; i < matrix.length; i++) {
        	// APPLY BINARY SEARCH to optimize it logN
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == target) return true;
            }
        }
        return false;
    }
	
	// Time: O(m + n) : m-row, n-col
	// RULE OUT a ROW/COL each time
	// 从最左下角开始，这个cell是所在行的最大，是所在列的最小，那么如果target小于它，直接就跳过一整列了，大于的话就右移
	// 同样这里可以从最右上角开始
	// 这里其实也是类似 D&C的思路，每次cut掉一行/一列， 直到找到对应行/列，开始 BS
	public boolean searchWithNum(int[][] matrix, int target) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
		// Starting from the most left bottom corner element
		// !!!!!!! BECAUSE it is the smallest element of its col, but it is the biggest element of its row
		int row = matrix.length - 1;
		int col = 0;
		while (row >= 0 && col < matrix[0].length) {
			if (matrix[row][col] == target) return true;
			if (matrix[row][col] > target) row--;
			else col++;
		}
		return false;
		
//		if (matrix == null || matrix.length == 0)
//            return false;
//        int row = matrix.length, col = matrix[0].length;
		// 多次一举了，加上放倒会出错，对 [[]]; 而对这个case,while进不去，直接return false了
//        // if (target < matrix[0][0] || target > matrix[row -  1][col - 1])
//        //     return false;
//        int r = row - 1, c = 0;
//        while (r >= 0 && c < col) {
//            if (target < matrix[r][c]) {
//                r--;
//                continue;
//            }
//            if (target > matrix[r][c]) {
//                c++;
//            } else {
//                return true;
//            }
//        }
//        return false;
	}
	
	//D&C
	private int[][] matrix;
    private int target;

    private boolean searchRec(int left, int up, int right, int down) {
        // this submatrix has no height or no width.
        if (left > right || up > down) {
            return false;
        // `target` is already larger than the largest element or smaller
        // than the smallest element in this submatrix.
        } else if (target < matrix[up][left] || target > matrix[down][right]) {
            return false;
        }

        int mid = left + (right-left)/2;

        // Locate `row` such that matrix[row-1][mid] < target < matrix[row][mid]
        int row = up;
        while (row <= down && matrix[row][mid] <= target) {
            if (matrix[row][mid] == target) {
                return true;
            }
            row++;
        }

        return searchRec(left, row, mid-1, down) || searchRec(mid+1, up, right, row-1);
    }

    public boolean searchMatrix2(int[][] mat, int targ) {
        // cache input values in object to avoid passing them unnecessarily
        // to `searchRec`
        matrix = mat;
        target = targ;

        // an empty matrix obviously does not contain `target`
        if (matrix == null || matrix.length == 0) {
            return false;
        }

        return searchRec(0, 0, matrix[0].length-1, matrix.length-1);
    }
    
    public static void main(String[] args) {
    	int[][] mat = new int[][] {
    		{1,   4,  7, 11, 15},
    		{2,   5,  8, 12, 19},
    		{3,   6,  9, 16, 22},
    		{10, 13, 14, 17, 24},
    		{18, 21, 23, 26, 30}
    	};
    	new SearchA2DMatrixII().searchMatrix2(mat, 13);
    }
}
