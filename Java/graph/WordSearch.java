package graph;

//Given a 2D board and a word, find if the word exists in the grid.
//
//The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. 
// The same letter cell may not be used more than once.
//
//Example:
//
//board =
//[
//  ['A','B','C','E'],
//  ['S','F','C','S'],
//  ['A','D','E','E']
//]
//
//Given word = "ABCCED", return true.
//Given word = "SEE", return true.
//Given word = "ABCB", return false.

// THE SAME LETTER CELL MAY NOT BE USED MORE THAN ONCE
// ====> DOES NOT mean that the same cell cannot be CHECKED TWICE
// ==> Compared to NumberOfIsland, the answer can be PARTIAL of the PATH, e.g. A(AAB), so, DONOT apply visitedPath ARRAY as NumberOfIsland.java
public class WordSearch {

	public boolean exist(char[][] board, String word) {
        if (board.length == 0) return false;
        // REPLACEMENT: word.equals(curr) => get TLE exception, since for each potential solution, they will check ALL elements of word's length
        char[] chars = word.toCharArray();// THE LONGEST is word.length, they will check EACH LETTER one by one.
        // WRONG!!!! e.g.
        // { [C, A, A]
        //   [A, A, A]
        //   [B, C, D]
        // }
        // TARGET = AAB  => path: AAAB; visitedPath, HOWEVER, AAB should be checked again
        //boolean[][] visitedPath = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
               //if (board[i][j] != '*') { // NO NEEDED
                   if (helper(board, i, j, chars, 0)) return true;
                   
               //} 
            }
        }
        return false;
    }
    public boolean helper(char[][] board, int row, int col, 
                          char[] chars, int curr) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[row].length ||
           chars[curr] != board[row][col])
            return false;
        
        // chars += board[row][col];
        // if (chars.equals(word))  ==> TLE
        if (curr == chars.length - 1) return true;
        
        char original = board[row][col];        
        
        board[row][col] = '*';
        // If the previous ONE is TRUE, then there is no need to EXECUTE extra ones
        // It is BETTER than UP, DOWN, LEFT, RIGHT => return up || down...  ==> ALL FOUR MUST BE EXECUTED
        boolean isSolution = helper(board, row - 1, col, chars, curr + 1) 
        || helper(board, row + 1, col, chars, curr + 1)
        || helper(board, row, col - 1, chars, curr + 1)
        || helper(board, row, col + 1, chars, curr + 1);
        board[row][col] = original; // need to track back to TRY EXTRA candidate - Compared to NumberOfLands
        
        return isSolution;
    }
}
