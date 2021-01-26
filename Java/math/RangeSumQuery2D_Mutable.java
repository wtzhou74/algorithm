package math;

//Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
//
//Range Sum Query 2D
//The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) and (row2, col2) = (4, 3), which contains sum = 8.
//
//Example:
//Given matrix = [
//  [3, 0, 1, 4, 2],
//  [5, 6, 3, 2, 1],
//  [1, 2, 0, 1, 5],
//  [4, 1, 0, 1, 7],
//  [1, 0, 3, 0, 5]
//]
//
//sumRegion(2, 1, 4, 3) -> 8
//update(3, 2, 2)
//sumRegion(2, 1, 4, 3) -> 10
//Note:
//The matrix is only modifiable by the update function.
//You may assume the number of calls to update and sumRegion function is distributed evenly.
//You may assume that row1 ≤ row2 and col1 ≤ col2.

public class RangeSumQuery2D_Mutable {

	// cumulative sum   ==>  [1, 2, 3, [4, 5, 6], 7, 8, 9]  calculate sum(4, 6) ==> [1, 3, 6, [10, 15, 21], 28, 36, 45]  ===> sumOf6 - sumOf3
	
    private int[][] colSum;
    private int[][] matrix;
    public RangeSumQuery2D_Mutable(int[][] matrix) {
        if (matrix.length > 0)
            buildColSum(matrix);
        else
            this.matrix = matrix;
    }
    
    public void buildColSum(int[][] matrix) {
        int rLen = matrix.length;
        int cLen = matrix[0].length;
        this.colSum = new int[rLen][cLen];
        this.matrix = matrix;
        
        // Better option to do update/sumRegion  TODO
//        this.colSum = new int[m+1][n];
//        
//        for(int i = 1; i <= m; i++) {
//            for(int j = 0; j  < n; j++) {
//                colSum[i][j] = colSum[i-1][j] +matrix[i-1][j];
//            }
//        }
        for (int i = 0; i < cLen; i++) {
            colSum[0][i] = matrix[0][i];
        }
        for (int i = 1; i < rLen; i++) {
            for (int j = 0; j < cLen; j++) {
                colSum[i][j] = colSum[i - 1][j] + matrix[i][j];
            }
        }
    }
    
    public void update(int row, int col, int val) {
        if (row < 0 || col < 0 || row >= matrix.length || col >= matrix[0].length) return;
        
        int diff = val - matrix[row][col];
        for (int i = row; i < matrix.length; i++) {
            colSum[i][col] = colSum[i][col] + diff;
        }
        matrix[row][col] = val;
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (matrix.length == 0) return 0;
        int total = 0;
        for (int i = col1; i <= col2; i++) {
        	if (row1 == 0) {
        		total += colSum[row2][i];
        	} else
        		total += (colSum[row2][i] - colSum[row1 - 1][i]);
        }
        
        return total;
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////////////
	// Converting 2D to one 1D
	private int[] cells;
    private int rLen;
    private int cLen;
//    public RangeSumQuery2D_Mutable(int[][] matrix) {
//        if (matrix.length == 0) {
//            this.rLen = 0;
//            this.cLen = 0;
//        }else {
//            this.cells = new int[matrix.length * matrix[0].length];
//            this.rLen = matrix.length;
//            this.cLen = matrix[0].length;
//            for (int i = 0; i < matrix.length; i++) {
//                for (int j = 0; j < matrix[0].length; j++) {
//                    int idx = i * matrix[0].length + j;
//                    cells[idx] = matrix[i][j];
//                }
//            }  
//        }  
//    }
    
    public void update1(int row, int col, int val) {
        int idx = row * cLen + col;
        if (idx < rLen * cLen) {
            cells[idx] = val;
        }
    }
    
    public int sumRegion1(int row1, int col1, int row2, int col2) {
        if (row1 < 0 || col1 < 0 || row2 < 0 || col2 < 0 ||
           row1 >= rLen || row2 >= rLen || col1 >= cLen || col2 >= cLen)
            return -1;
        int total = 0;
        int tempRow = row1;
        for (int i = row1 * cLen; i < row2 * cLen + cLen; i++ ) {
            //System.out.println(tempRow + "-" + row1);
            //if (tempRow != row1 && i % cLen == 0) tempRow++;
            if (i >= row1 * cLen + cLen && i % cLen == 0) tempRow++;
            if (i >= tempRow * cLen + col1 && i <= tempRow * cLen + col2) {
                total += cells[i];
            }
        }
        
        return total;
    }
    
    public static void main(String[] args) {
    	int[][] matrix = new int[][] {{1}, {-5}, {-8}, {2}, {-6}, {-3}, {5}, {-7}, {8}, {-7}};
    	RangeSumQuery2D_Mutable range = new RangeSumQuery2D_Mutable(matrix);
    	range.sumRegion(5, 0, 8, 0);
    	range.update(3, 0, -1);
    	range.update(3, 0, -1);
    	range.update(0, 0, -6);
    	range.sumRegion(4, 0, 7, 0);
    	
    }
}
