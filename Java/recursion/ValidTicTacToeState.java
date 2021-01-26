package recursion;

//A Tic-Tac-Toe board is given as a string array board. Return True if and only if it is possible to reach this board position during the course of a valid tic-tac-toe game.
//
//The board is a 3 x 3 array, and consists of characters " ", "X", and "O".  The " " character represents an empty square.
//
//Here are the rules of Tic-Tac-Toe:
//
//Players take turns placing characters into empty squares (" ").
//The first player always places "X" characters, while the second player always places "O" characters.
//"X" and "O" characters are always placed into empty squares, never filled ones.
//The game ends when there are 3 of the same (non-empty) character filling any row, column, or diagonal.
//The game also ends if all squares are non-empty.
//No more moves can be played if the game is over.
//Example 1:
//Input: board = ["O  ", "   ", "   "]
//Output: false
//Explanation: The first player always plays "X".
//
//Example 2:
//Input: board = ["XOX", " X ", "   "]
//Output: false
//Explanation: Players take turns making moves.
//
//Example 3:
//Input: board = ["XXX", "   ", "OOO"]
//Output: false
//
//Example 4:
//Input: board = ["XOX", "O O", "XOX"]
//Output: true
//Note:
//
//board is a length-3 array of strings, where each string board[i] has length 3.
//Each board[i][j] is a character in the set {" ", "X", "O"}.
public class ValidTicTacToeState {

	// Note:  理解题意，这里 return true 只需要下一步能被合理走就行， 不一定要赢 ！！！
	public boolean validTicTacToe(String[] board) {
        int xCount = 0, oCount = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < 3; j++) {
                char c = board[i].charAt(j);
                if (c == 'X') xCount++;
                if (c == 'O') oCount++;
            }
        }
        // FALSE的情况
        // - 1） O元素比X多
        // - 2） X元素比O多超过1
        // - 3） 走X的时候 X已经满了，或者要走 O的时候， O已经满了  ===> 说明X已经满了，O还在走，才X在满的情况下又回到 O了，即门当前状态不合理
        //    ==> 比如： [“XXX", "OO ", "   "]；  也是 return true,因为该状态合理，而不是说轮到 O 走了， X已经赢了，所以return false
        if (xCount < oCount || xCount - oCount > 1) return false;
        // 这里反推  ===> 正向找所有是 true的情况较多，需要需要考虑的 corner case就多
        //			===> 这时候考虑反向先找出  false的情况，剩余的 就是 true了
        if (xCount == oCount) // xCount最多只能 oCount相差1的，因为X先走，且一人一步
            if (!win(board, 'X')) //对下步走 X的情况， 找出 false的
                return false;  // 但这里要注意， 本题只需要看“当前状态”是否合理，“而不是”看这一步走下去能不能赢，即有连续的3个X/O！！！！！！
        if (xCount - oCount == 1) 
            if (!win(board, 'O')) //对下一步走 O 的情况，找出false的
                return false;
        
        return true;
        
    }
    
    public boolean win(String[] board, char w) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            count = 0;
            // row
            for (int j = 0; j < board.length; j++) {
                if (board[i].charAt(j) == w)
                    count++;
            }
            if (count == board.length) return false;
            
            // col
            count = 0;
            for (int j = 0; j < board.length; j++) {
                if (board[j].charAt(i) == w)
                    count++;
            }
            if (count == board.length) return false;
        }
        // Diagonal
        count = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i].charAt(i) == w)
                count++;
        }
        if (count == board.length) return false;
        
        // re-diagonal
        count = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i].charAt(board.length - i - 1) == w)
                count++;
        }
        if (count == board.length)
            return false;
        else return true;
    }
    
    
    // recursive solution
    public boolean validTicTacToeRecursive(String[] board) {
        int xCount = 0, oCount = 0;
        char[][] B = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                char c = board[i].charAt(j);
                if (c == 'X') xCount++;
                if (c == 'O') oCount++;
                B[i][j] = c;
            }

        // 处理 false的情况
        if (xCount < oCount || xCount - oCount > 1) return false;
     // - there is one way you can take pieces off without triggering the end condition, 
        // - then it means there is at least one way you can start from an empty board and
        // - reach the state given.
        
     // Second, start with current state, find a way to remove pieces without
       //meeting the end condition
        return recursive(B, xCount, oCount, true);
    
    }
    
    public boolean find3(char[][] board) {
    	String[] rows = new String[board.length];
    	String[] cols = new String[board.length];
    	String[] diags = new String[2];
    	
    	for (int i = 0; i < board.length; i++) {
            String row = "";
            String col = "";
    		for (int j = 0; j < board[i].length; j++) {
    			row += board[i][j] + "";
    			col += board[j][i] + "";
    		}
            rows[i] = row;
            cols[i] = col;
    	}
        String diag = "";
        String rDiag = "";
    	for (int i = 0; i < board.length; i++) {
    		diag += board[i][i] + "";
    	}
    	diags[0] = diag;
        
    	for (int i = 0; i < board.length; i++) {
    		rDiag += board[i][board.length - i -1] + "";
    	}
        diags[1] = rDiag;
    	
    	for (int i = 0; i < rows.length; i++) {
    		if (rows[i].equalsIgnoreCase("XXX") ||
    				rows[i].equalsIgnoreCase("OOO") ||
    				cols[i].equalsIgnoreCase("XXX") ||
    				cols[i].equalsIgnoreCase("OOO"))
    			return true;
    	}
    	for (int i = 0; i < diags.length; i++) {
    		if (diags[i].equalsIgnoreCase("XXX") || 
    				diags[i].equalsIgnoreCase("OOO"))
    			return true;
    	}
    	return false;
    }
    
    
    public boolean recursive(char[][] board, int numX, int numO, boolean start) {
    	if (numX == 0 && numO == 0) return true;
    	if (find3(board) && !start) 
    		return false;
    	boolean turn = numX > numO;
    	boolean ret = false;
    	// If to take 'X' off, recur on the choices of X. Same logic for 'O'.
    	for (int i = 0; i < 3; i++) {
    		for (int j = 0; j < 3; j++) {
    			if (turn && board[i][j] == 'X') {
    				board[i][j] = ' ';// set current board[i][j] to empty
    				ret = ret || recursive(board, numX - 1, numO, false);
    				board[i][j] = 'X';// restore board
    			} else if (!turn && board[i][j] == 'O') {
    				board[i][j] = ' ';
    				ret = ret || recursive(board, numX, numO - 1, false);
    				board[i][j] = 'O';
    			}
    		}
    	}
    	return ret;
    }
    
    // 正向处理， 明显复杂多了
    public boolean validTicTacToeStraight(String[] board) {
        int xCount = 0, oCount = 0;
        int cont3 = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < 3; j++) {
                char c = board[i].charAt(j);
                if (c == 'X') xCount++;
                if (c == 'O') oCount++;
            }
        }
        if (xCount < oCount || xCount - oCount > 1) return false;
        if (xCount > oCount)
            return win(board, 'O', xCount + oCount);
        else 
            return win(board, 'X', xCount + oCount);
    }
    
    public boolean win(String[] board, char w, int total) {
        System.out.println(w);
        int rowCount = 0;
        boolean found = false;
        for (int i = 0; i < 3; i++) {
            String row = board[i];
            rowCount = 1;
            for (int j = 1; j < row.length(); j++) {
                if (row.charAt(j) == w 
                    && row.charAt(j) == row.charAt(j - 1)) {
                    rowCount++;
                }
            }
            if (total == 9 && row.charAt(0) == 'O' && rowCount == 3)
                return false;
            if (rowCount == 3) found = true;
        }
        
        int colCount = 0;
        for (int j = 0; j < board[0].length(); j++) {
            colCount = 1;
            for (int i = 1; i < board.length; i++) {
                if (board[i - 1].charAt(j) == w 
                    && board[i - 1].charAt(j) == board[i].charAt(j))
                    colCount++;
            }
            System.out.println(colCount);
            if (total == 9 && board[0].charAt(j) == 'O' && colCount == 3)
                return false;
            if (colCount == 3) found = true;
        }
        
        int dCount = 1;
        for (int i = 1; i < board.length; i++) {
            if (board[i - 1].charAt(i - 1) == w && board[i - 1].charAt(i - 1) == board[i].charAt(i)) {
                dCount++;
            }
        }
        if (total == 9 && board[0].charAt(0) == 'O' && dCount == 3)
            return false;
        if (dCount == 3) found = true;
        
        int rdCount = 1;
        for (int i = 1; i < board.length; i++) {
            if (board[i - 1].charAt(board.length - i) == w && board[i - 1].charAt(board.length - i + 1 - 1)
                == board[i].charAt(board.length - i - 1))
                rdCount++;
        }
        if (total == 9 && board[0].charAt(board.length - 1) == 'O' && rdCount == 3)
            return false;
        if (rdCount == 3) found = true;
        
        if (total < 9 && found) return false;
        else 
            return true;
    }
    
    public static void main(String[] args) {
    	new ValidTicTacToeState().validTicTacToeRecursive(new String[]{"XOX", "OXO", "XOX"});
    }
	
}
