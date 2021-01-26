package matrix;

//Design a Tic-tac-toe game that is played between two players on a n x n grid.
//
//You may assume the following rules:
//
//A move is guaranteed to be valid and is placed on an empty block.
//Once a winning condition is reached, no more moves is allowed.
//A player who succeeds in placing n of their marks in a horizontal, vertical, or diagonal row wins the game.
//Example:
//Given n = 3, assume that player 1 is "X" and player 2 is "O" in the board.
//
//TicTacToe toe = new TicTacToe(3);
//
//toe.move(0, 0, 1); -> Returns 0 (no one wins)
//|X| | |
//| | | |    // Player 1 makes a move at (0, 0).
//| | | |
//
//toe.move(0, 2, 2); -> Returns 0 (no one wins)
//|X| |O|
//| | | |    // Player 2 makes a move at (0, 2).
//| | | |
//
//toe.move(2, 2, 1); -> Returns 0 (no one wins)
//|X| |O|
//| | | |    // Player 1 makes a move at (2, 2).
//| | |X|
//
//toe.move(1, 1, 2); -> Returns 0 (no one wins)
//|X| |O|
//| |O| |    // Player 2 makes a move at (1, 1).
//| | |X|
//
//toe.move(2, 0, 1); -> Returns 0 (no one wins)
//|X| |O|
//| |O| |    // Player 1 makes a move at (2, 0).
//|X| |X|
//
//toe.move(1, 0, 2); -> Returns 0 (no one wins)
//|X| |O|
//|O|O| |    // Player 2 makes a move at (1, 0).
//|X| |X|
//
//toe.move(2, 1, 1); -> Returns 1 (player 1 wins)
//|X| |O|
//|O|O| |    // Player 1 makes a move at (2, 1).
//|X|X|X|
public class DesignTicTacToe {
	/** Initialize your data structure here. */
    int[] rowCount;//a 用每一行的总和是否等于n 或 -n 来判断是否 win
    int[] colCount;
    int dig;//a 只有一条正对角线
    int redig;
    int n;
    public DesignTicTacToe(int n) {
        this.n = n;
        this.rowCount = new int[n];
        this.colCount = new int[n];
        this.dig = 0;
        this.redig = 0;
    }
    
    /** Player {player} makes a move at ({row}, {col}).
        @param row The row of the board.
        @param col The column of the board.
        @param player The player, can be either 1 or 2.
        @return The current winning condition, can be either:
                0: No one wins.
                1: Player 1 wins.
                2: Player 2 wins. */
    public int move(int row, int col, int player) {
    	if (n == 1)
            return player;
        if (player == 1) {
            rowCount[row]++;//有 win 说明在那行上 所有都被同一个player 标注，所以其总和要么 n 要么 -n
            colCount[col]++;
            if (row == col)
                dig++;
            if (row + col == n - 1)
                redig++;
        } else {
            rowCount[row]--;
            colCount[col]--;
            if (row == col)
                dig--;
            if (row + col == n - 1)
                redig--;
        }
        if (dig == n || redig == n) return 1;
        if (dig == -n || redig == -n) return 2;
        
        // a判断每一行/列上的总和
        for (int i = 0; i < n; i++) {
            if (rowCount[i] == n || colCount[i] == n) return 1;
            if (rowCount[i] == -n || colCount[i] == -n) return 2;
        }
        return 0;
        
    }
    
    /////////////////////////////////////////////////////////////
    

	/** Initialize your data structure here. */
    char[][] board;
//    int n;
//    public DesignTicTacToe(int n) {
//        this.board = new char[n][n];
//        this.n = n;
//    }
    
    /** Player {player} makes a move at ({row}, {col}).
        @param row The row of the board.
        @param col The column of the board.
        @param player The player, can be either 1 or 2.
        @return The current winning condition, can be either:
                0: No one wins.
                1: Player 1 wins.
                2: Player 2 wins. */
    public int move1(int row, int col, int player) {
        if (n == 1)
            return player;
        if (player == 1)
            board[row][col] = 'X';
        else
            board[row][col] = 'O';
        boolean result = win(row, col, player);
        if (result) return player;
        return 0;
        
    }
    
    public boolean win(int row, int col, int player) {
        char c = 'O';
        if (player == 1)
            c = 'X';
        int j = n - 1;
        boolean h = true, v = true, d1 = true, d2 = true;
        for (int i = 0; i < n; i++) {
            if(board[row][i] != c)
                h = false;
            if (board[i][col] != c)
                v = false;
            if (board[i][i] != c) {
                d1 = false;
            }
            if (board[i][j] != c) {
                d2 = false;                
            }
            j--;
        }
        return h || v || d1 || d2;
    }
    
    
    public static void main(String[] args) {
    	DesignTicTacToe d = new DesignTicTacToe(2);
    	d.move(0,  0, 2);
    	d.move(0, 1, 1);
    	d.move(1,  1,  2);
    }
}
