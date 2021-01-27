package amazon;

//Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
//
//Integers in each row are sorted from left to right.
//The first integer of each row is greater than the last integer of the previous row.
//Example 1:
//
//Input:
//matrix = [
//  [1,   3,  5,  7],
//  [10, 11, 16, 20],
//  [23, 30, 34, 50]
//]
//target = 3
//Output: true
//Example 2:
//
//Input:
//matrix = [
//  [1,   3,  5,  7],
//  [10, 11, 16, 20],
//  [23, 30, 34, 50]
//]
//target = 13
//Output: false
public class SearchA2DMatrix {

	// 跟SearchA2DMatrixII.java 一个思路
	public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0)
            return false;
        int row = matrix.length, col = matrix[0].length;
        int r = 0, c = col - 1;
        // 从最右上角开始，每次可以rule out 一整个列
        while (r < row && c >= 0) {
            if (matrix[r][c] == target)
                return true;
            if (matrix[r][c] > target) {
                c--;
            } else {
                r++;
            }
        }
        return false;
    }
	
	// 可转换为1维数组，进行 BS 查找	
	public boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0)
            return false;
        int row = matrix.length, col = matrix[0].length;
        int[] aux = new int[row * col];
        int k = 0;
        // 这里不需要转换，可根据 idx值，转而求所在的 [r][c] !!!!, 如下解法
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                aux[k++] = matrix[i][j];
            }
        }
        
        return bs(aux, target);        
        //return false;
    }
	
	// 直接BS， 将所得的mid，转换回 cell[row][col]  ==> row = mid / n; col = mid % n
	public boolean searchMatrix3(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0)
            return false;
        if (matrix[0].length == 0) return false;
        int row = matrix.length, col = matrix[0].length;
        int left = 0, right = row * col - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            int r = mid / col, c = mid % col; // 转换回cell对应的索引
            if (matrix[r][c] >= target) {
                right = mid; 
            } 
            else
                left = mid + 1;
        }
        //System.out.println(left);
        return matrix[left / col][left % col] == target;
    }
    
    public boolean bs(int[] nums, int target) {
        if (nums.length == 0) return false;
        if (nums.length == 1) 
            return nums[0] == target;
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                //if (nums[mid] == target) return true;
                right = mid; 
            } 
            else
                left = mid + 1;
        }
        
        return nums[left] == target;
    }
    
}
