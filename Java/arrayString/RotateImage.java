package arrayString;

public class RotateImage {

	// BE VERY CAREFUL ON THE INDEX
	 public static void rotate(int[][] matrix) { 
        int n = matrix.length;
        //rotateHelper(n, 0, n - 1, matrix);
        if (n == 0 || n == 1) return;
        //if (n == 1) return matrix;
        
        int j = n - 1, end = n - 1, start = 0;
        while (n >= 2) {
        	j = end;
            for (int i = start; i < end; i++) {
                int temp = matrix[start][i];
                //most left
                matrix[start][i] = matrix[j][start];
                //the lowest level
                matrix[j][start] = matrix[end][j];
                //the most right
                matrix[end][j] = matrix[i][end];
                matrix[i][end] = temp;           
                j--;

            }
            n = n - 2;// NOTE n/2
            start++;
            end--;
        }
	         
	 }
	 
	 // Recursive solution, but more time consuming
	 public int[][] rotateHelper(int n, int start, int end, int[][] matrix) {
         if (n == 0) return null;
         if (n == 1) return matrix;
       
         int j = end;
         for (int i = start; i < end; i++) {
               int temp = matrix[start][i];
               matrix[start][i] = matrix[j][start];
               matrix[j][start] = matrix[end][j];
               matrix[end][j] = matrix[i][end];
               matrix[i][end] = temp;           
               j--;

           }
        return rotateHelper(n - 2, start+1, end-1, matrix);
    }
	 
	 public static void main(String[] args) {
		 int[][] matrix = {{5, 1, 9,11}, {2, 4, 8,10}, {13, 3, 6, 7}, {15,14,12,16}};
		 rotate(matrix);
	 }
}
