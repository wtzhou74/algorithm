package arrayString;

public class DiagonalTraverse {
	
	/**
	 * Given a matrix of M x N elements 
	 * (M rows, N columns), return all elements of the matrix in
	 * Diagonal order as shown in the below image.
	 * */

	public static int[] diagonalTraverseSolution(int[][] matrix)
	{
		// NULL matrix
		if (matrix.length == 0)
			return new int[0];
		
		int r = 0, c = 0;
		int m = matrix.length, n = matrix[0].length;
		int arr[] = new int[m * n];
		
		// PAY CLOSE ATTENTION TO THE WALK PATTERN
		for (int i = 0; i < arr.length; i++) {
			arr[i] = matrix[r][c];
			//case: 00, 01 10, 20 11 02, 03 12 21 30, 31 22 13 04, 14 23 32, 
			// MOVING (diagonal) UP
			if ((r + c) % 2 == 0) {
				// change walk direction
				if (c == n - 1) {
					//GOIND DOWN	
					r++;
				} else if (r == 0) {
					// MOVE RIGHT
					c++;
				} else {
					r--;
					c++;
				}
			} else { // MOVING (diagonal) DOWN
				if (r == m - 1) {
					// MOVE RIGHT
					c++;
				} else if (c == 0) {
					// WALK DOWN
					r++;
				} else {
					r++;
					c--;
				}
			}
		}
		return arr;
        

        
	}
}
