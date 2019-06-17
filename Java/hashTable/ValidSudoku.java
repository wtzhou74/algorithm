package hashTable;

import java.util.HashSet;
import java.util.Set;

/**
 * Determine if a 9x9 Sudoku board is valid. Only the filled cells need to be validated according to the following rules:
 * 1. Each row must contain the digits 1-9 without repetition.
 * 2. Each column must contain the digits 1-9 without repetition.
 * 3.Each of the 9 3x3 sub-boxes of the grid must contain the digits 1-9 without repetition.
 * 
 * The Sudoku board could be partially filled, where empty cells are filled with the character '.'.
 * */
public class ValidSudoku {

	//intuitive solution
//	 public boolean isValidSudoku(char[][] board) {
//	        if (board == null || board[0].length != 9 || board.length != 9) { return false;}
//	        boolean rule1 = checkByRule1(board);
//	        boolean rule2 = checkByRule2(board);
//	        boolean rule3 = checkByRule3(board);
//	        
//	        return rule1 && rule2 && rule3;
//	        
//	    }
//	    
//	    public boolean checkByRule1(char[][] board) {
//	        for (int i = 0; i < 9; i++) {
//	            Set<Character> set = new HashSet<>();
//	            for (int j = 0; j < 9; j++) {
//	                if (board[i][j] != '.' && isCharBetween1and9(board[i][j])) {
//	                    if (set.contains(board[i][j])) {
//	                        return false;
//	                    } else set.add(board[i][j]);
//	                }
//	            }
//	        }
//	        return true;
//	    }
//	    
//	    public boolean checkByRule2(char[][] board) {
//	        for (int i = 0; i < 9; i++) {
//	            Set<Character> set = new HashSet<>();
//	            for (int j = 0; j < 9; j++) {
//	                if (board[j][i] != '.' && isCharBetween1and9(board[j][i])) {
//	                    if (set.contains(board[j][i])) {
//	                        return false;
//	                    } else set.add(board[j][i]);
//	                }
//	            }
//	        }
//	        return true;
//	    }
//	    
//	    public boolean checkByRule3(char[][] board) {
//	        for (int i = 0; i < 9; i += 3) {
//	            for (int j = 0; j < 9; j += 3) {
//	                if (!checkSubBox(board, i, j)) {
//	                    return false;
//	                }
//	            }
//	        }
//	        return true;
//	                                                 
//	    }
//	    
//	    public boolean checkSubBox(char[][] board, int row, int column) {
//	        Set<Character> set = new HashSet<>();
//	        for (int i = row; i < row + 3; i++) {
//	            for (int j = column; j < column + 3; j++) {
//	                if (board[i][j] != '.' && isCharBetween1and9(board[i][j])) {
//	                    if (set.contains(board[i][j])) {
//	                        return false;
//	                    } else set.add(board[i][j]);
//	                }
//	            }
//	        }
//	        return true;
//	    }
//	    
//	    public boolean isCharBetween1and9(char character) {
//	        if (character >= '1' && character <= '9') {
//	            return true;
//	        } else {
//	            return false;
//	        }
//	    }
	
	public boolean isValidSudoku(char[][] board) {
        if (board == null || board[0].length != 9 || board.length != 9) { return false;}
        boolean rule = checkByRule(board);
        boolean rule3 = checkByRule3(board);
        
        return rule && rule3;
        
    }
    
    public boolean checkByRule(char[][] board) {
        for (int i = 0; i < 9; i++) {
            Set<Character> rowSet = new HashSet<>();
            Set<Character> cloumnSet = new HashSet<>();
            for (int j = 0; j < 9; j++) {
            	// row
                // set add() returns false if the value exisits
                if (board[i][j] != '.' && !rowSet.add(board[i][j])) {
                    return false;
                }
                // column
                if (board[j][i] != '.' && !cloumnSet.add(board[j][i])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    
    public boolean checkByRule3(char[][] board) {
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                if (!checkSubBox(board, i, j)) {
                    return false;
                }
            }
        }
        return true;
                                                 
    }
    
    public boolean checkSubBox(char[][] board, int row, int column) {
        Set<Character> set = new HashSet<>();
        for (int i = row; i < row + 3; i++) {
            for (int j = column; j < column + 3; j++) {
                if (board[i][j] != '.' && !set.add(board[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
	    
	    
	    
	    // Concise solution
	    public boolean isValidSudokuSol(char[][] board) {
	        for(int i = 0; i<9; i++){
	            HashSet<Character> rows = new HashSet<Character>();
	            HashSet<Character> columns = new HashSet<Character>();
	            HashSet<Character> cube = new HashSet<Character>();
	            for (int j = 0; j < 9;j++){
	                if(board[i][j]!='.' && !rows.add(board[i][j]))
	                    return false;
	                if(board[j][i]!='.' && !columns.add(board[j][i]))
	                    return false;
	                int RowIndex = 3*(i/3);
	                int ColIndex = 3*(i%3);
	                if(board[RowIndex + j/3][ColIndex + j%3]!='.' && !cube.add(board[RowIndex + j/3][ColIndex + j%3]))
	                    return false;
	            }
	        }
	        return true;
	    }
}
