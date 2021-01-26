package matrix;

//According to the Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970."
//
//Given a board with m by n cells, each cell has an initial state live (1) or dead (0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules (taken from the above Wikipedia article):
//
//Any live cell with fewer than two live neighbors dies, as if caused by under-population.
//Any live cell with two or three live neighbors lives on to the next generation.
//Any live cell with more than three live neighbors dies, as if by over-population..
//Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
//Write a function to compute the next state (after one update) of the board given its current state. The next state is created by applying the above rules simultaneously to every cell in the current state, where births and deaths occur simultaneously.
//
//Example:
//
//Input: 
//[
//  [0,1,0],
//  [0,0,1],
//  [1,1,1],
//  [0,0,0]
//]
//Output: 
//[
//  [0,0,0],
//  [1,0,1],
//  [0,1,1],
//  [0,1,0]
//]
//Follow up:
//
//Could you solve it in-place? Remember that the board needs to be updated at the same time: You cannot update some cells first and then use their updated values to update other cells.
//In this question, we represent the board using a 2D array. In principle, the board is infinite, which would cause problems when the active area encroaches the border of the array. How would you address these problems?



public class GameOfLife {

	// SPACE: o(M * N)
	public void gameOfLife(int[][] board) {
        if (board == null || board.length == 0) return;
        int[][] res = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                helper(board, res, i, j);
            }
        }
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                board[i][j] = res[i][j];
            }
        }
        
    }
    public void helper(int[][] board, int[][] res, int i, int j) {
    	// PAY CLOSE ATTENTION TO THE EDGE CASE AND INDEX
    	
//        int up = i == 0 ? 0 : board[i - 1][j];
//        int down = i == board.length - 1 ? 0 : board[i + 1][j];
//        int left = j == 0 ? 0 : board[i][j - 1];
//        int right = j == board[0].length - 1 ? 0 : board[i][j + 1];
//        int leftTop = i == 0 || j == 0 ? 0 : board[i - 1][j - 1];
//        int rightTop = i == 0 || j == board[0].length - 1 ? 0 : board[i - 1][j + 1];
//        int leftBottom = j == 0 || i == board.length - 1 ? 0 : board[i + 1][j - 1];
//        int rightBottom = i == board.length - 1 || j == board[0].length - 1 ? 0 : board[i + 1][j + 1];
//        int oneSum = up + down + left + right + leftTop + rightTop + leftBottom + rightBottom;
    	
    	// ALTERNATIVE  ==> 3 X 3 SUB-MATRIX
    	int oneSum = 0;
        for (int m = i - 1; m <= i + 1; m++) {
            if (m < 0 || m > board.length - 1) continue;
            for (int n = j - 1; n <= j + 1; n++) {
                if (n < 0 || n > board[0].length - 1) continue;
                if (m == i && n == j) continue;
                if (board[m][n] == 1) oneSum++;
            }
        }
        // DO NOT use UPDATED values to update other cells
        if (board[i][j] == 0 && oneSum == 3) res[i][j] = 1;
        else if (board[i][j] == 1 && (oneSum < 2 || oneSum > 3)) res[i][j] = 0;
        else res[i][j] = board[i][j];
    }
    
    // Space: O(1)
    public void sol2(int[][] board) {
    	if (board == null || board.length == 0) return;
        //int[][] res = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                helper(board, i, j);
            }
        }
        // for (int i = 0; i < res.length; i++) {
        //     for (int j = 0; j < res[0].length; j++) {
        //         board[i][j] = res[i][j];
        //     }
        // }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 2) board[i][j] = 1;
                if (board[i][j] == -1) board[i][j] = 0;
            }
        }
    }
    
    public void helper(int[][] board, int i, int j) {
        
        // 3 x 3 submatrix
        int oneSum = 0;
        for (int m = i - 1; m <= i + 1; m++) {
            if (m < 0 || m > board.length - 1) continue;
            for (int n = j - 1; n <= j + 1; n++) {
                if (n < 0 || n > board[0].length - 1) continue;
                if (m == i && n == j) continue;
                if (board[m][n] == 1 || board[m][n] == -1) oneSum++;
            }
        }
        if (board[i][j] == 0 && oneSum == 3) board[i][j] = 2;// Means original value is 0
        else if (board[i][j] == 1 && (oneSum < 2 || oneSum > 3)) board[i][j] = -1; // Means original value is 1
    }
}
