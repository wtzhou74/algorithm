package recursionII.backtracking;

/*The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.

Given an integer n, return the number of distinct solutions to the n-queens puzzle.

Example:

Input: 4
Output: 2
Explanation: There are two distinct solutions to the 4-queens puzzle as shown below.
[
 [".Q..",  // Solution 1
  "...Q",
  "Q...",
  "..Q."],

 ["..Q.",  // Solution 2
  "Q...",
  "...Q",
  ".Q.."]
]*/

public class NQueensII {

	int res = 0;
	public int totalNQueues(int n) {
		int[] colIndex = new int[n]; // record previous placed queues for checking purpose
		placeQueue(0, colIndex);
		return res;
	}
	public void placeQueue(int row, int[] colIndex) {
		// BASE CASE / GOAL
		if (row == colIndex.length) res++; // reaches the last row, one solution was found, AND COLINDEX records the position of each queue.
		for (int col = 0; col < colIndex.length; col++) {
			// place Queue here
			// check if it is valid
			if (checkValid(row, col, colIndex)) {
				// check next row
				colIndex[row] = col;
				// row++; WRONG, it is gonna CHANGE ROW CANDIDATE
				// place next row
				placeQueue(row + 1, colIndex); // RECURSIVE NEXT ROW; SO DONOT WHILE ROW at very beginning.
				// STACK => GO BACK TO ITS PARENT/PREVIOUS NODE IF CURRENT NODE FAILED.
				// backtracking, and explore if the queue can be placed into the next column
				// As an important behaviour of backtracking, we should be able to ABONDON our previous decision 
				// at the moment we decide to move on to the next candidate
				// colIndex.remove(colIndex.size() - 1) ???
				colIndex[colIndex.length - 1] = 0; // USED TO TRACK BACK
			}
			// BACKTRACKING => eliminate unnecessary exploration
		}
		
	}
	// check if there are any previously placed queues on the same column, diagonal or anti-diagonal
	public boolean checkValid(int row, int col, int[] colIndex) {
		for (int i = 0; i < row; i++) {
			// For same column, diagonal, anti-diagonal, no need for same column because of i < row
			if (col == colIndex[i] || row - col == i - colIndex[i] || row + col == i + colIndex[i]) return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		new NQueensII().totalNQueues(4);
	}
}
