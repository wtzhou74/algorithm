package recursionII.backtracking;

/*Write a program to solve a Sudoku puzzle by filling the empty cells.

A sudoku solution must satisfy all of the following rules:

Each of the digits 1-9 must occur exactly once in each row.
Each of the digits 1-9 must occur exactly once in each column.
Each of the the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
Empty cells are indicated by the character '.'.

Note:

The given board contain only digits 1-9 and the character '.'.
You may assume that the given Sudoku puzzle will have a single unique solution.
The given board size is always 9x9.
*
*
*/


//We start from first cell in the sudoku.
//Base case -1 - When we are done with all rows, we have our solution.
//Base case -2- When we are done with a column, we move on to next row to solve.
//Then we can fill each cell any value from 1-9 given that it satisfies our constraints, 
//if it does we move on to next cell, otherwise, we backtrack our choice and fill in next value.

public class SudokuSolver {

	// Reference: 
	// 	- https://leetcode.com/explore/learn/card/recursion-ii/472/backtracking/2796/discuss/380898/Backtracking-solution-through-JAVA-with-explanation
	public void solveSudoku(char[][] board) {
		placeNumber(0, 0, board);
	}
	
	// LINE BY LINE
	public boolean placeNumber(int row, int col, char[][] board) { // the board here is the similar to colIndex in NQueues
		// BASE CASE 1: if col == colLength, starting for next row
		if (col == board[row].length) {
			col = 0;
			row++; // NOTE: ROW can reach the LENGHT below, BUT note OutOfIndex error
		}
		// BASE CASE 2 / GOAL
		if (row == board.length) {
			return true; // FIND THE SOLUTION
		}
		
		if (board[row][col] != '.') return placeNumber(row, col + 1, board); // SKIP FILLED CELL, and then check next cell RECURSIVELY
		// fill out the cell with specific value (0 -9)
		// ONE POSSIBLE SOLUTION
		for (int val = 1; val <= board.length; val++) {
			char placedValue = (char)(val + '0');
			// Apply constraints to check the value is valid
			if (isValid(row, col, board, placedValue)) {
				board[row][col] = placedValue;
				// all cells were filled, then return true
				// DONOT DO COL++, it is gonna change COL value
				if (placeNumber(row, col+1, board)) return true;
				// if the choice made is not valid, backtracking our decision, reset '.' again.
				// " remove the column placements in N Queens problem. "
				board[row][col] = '.'; // REMOVING / PRUNING for Backtracking
			}
		}
				
		return false;
	}
	
	public boolean isValid(int row, int col, char[][] board, char val) {
		for (int i = 0; i < board[row].length; i++) {
            if (board[row][i] == val || board[i][col] == val) {
                return false;
            }
            
        }
                    
        // check sub-boxes
		// which block [row, col] is in
        int r = row / 3;
        int l = col / 3;
        for (int i = r * 3; i < r * 3 + 3; i++ ) {
            for (int j = l * 3; j < l * 3 + 3; j++) {
                if (board[i][j] == val) return false;
            }
        }
        return true;
	}
}
