package graph;

public class SurroundedRegions {

	// !!!!!! Starting from board which is 'O', marking all 'O' along the path.
	public void solve(char[][] board) {
        if (board.length == 0 || board == null) {
            return;
        }
        boolean[][] visited = new boolean[board.length][board[0].length]; // Stack overflow without this statement
        // !!!!!! Starting from board which is 'O', marking all 'O' along the path.
        for (int i = 0; i < board[0].length; i++) {
            if (board[0][i] == 'O' && !visited[0][i]) {// first row
                helper(board, 0, i, visited);
            }
            if (board[board.length - 1][i] == 'O' && !visited[board.length - 1][i]) { // last row
                helper(board, board.length - 1, i, visited);
            }
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == 'O' && !visited[i][0]) {// first col
                helper(board, i, 0, visited);
            }
            if (board[i][board[0].length - 1] == 'O' && !visited[i][board[0].length - 1]) {// last col
                helper(board, i, board[0].length - 1, visited);
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } 
                if (board[i][j] == '*') {
                    board[i][j] = 'O';
                }
            }
        }
    }
    
    public void helper(char[][] board, int row, int col, boolean[][] visited) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || visited[row][col]) return;
        if (board[row][col] == 'X') return; // confrong an 'X', then no need to go this direction
        if (board[row][col] == 'O'){
            board[row][col] = '*';
        }
        visited[row][col] = true;
        helper(board, row + 1, col, visited);
        helper(board, row - 1, col, visited);
        helper(board, row, col + 1, visited);
        helper(board, row, col - 1, visited);
        
    }
    
	
	// WRONG SOLUTION below, Since the direction can go deeper, DFS
//	public void solve(char[][] board) {
//        if (board.length == 0 || board == null) {
//            return;
//        }
//        
//        for (int i = 0; i < board[0].length; i++) {
//            if (board[0][i] == 'O') {
//                board[0][i] = '*';
//            }
//            if (board[board.length - 1][i] == 'O') {
//                board[board.length - 1][i] = '*';
//            }
//        }
//        for (int i = 0; i < board.length; i++) {
//            if (board[i][0] == 'O') {
//                board[i][0] = '*';
//            }
//            if (board[i][board[0].length - 1] == 'O') {
//                board[i][board[0].length - 1] = '*';
//            }
//        }
//        
//    }
	
    
	// WRONG: Similiar to NumOfIsland, path can goes to the next row/col or next next ...etc.
    public boolean beFlipped(char[][] board, int col, int row) {
        boolean left = false, right = false, up = false, down = false;
        for (int i = 0; i < col; i++) {
            if (board[row][i] == '*') continue;
            if (board[row][i] == 'X') left = true;
        }
        for (int i = col + 1; i < board[0].length; i++) {
            if (board[row][i] == '*') continue;
            if (board[row][i] == 'X') right = true;
        }
        for (int i = 0; i < row; i++ ) {
            if (board[i][col] == '*') continue;
            if (board[i][col] == 'X') up = true;
        }
        for (int i = row + 1; i < board.length; i++) {
            if (board[i][col] == '*') continue;
            if (board[i][col] == 'X') down = true;
        }
        
        return left && right && up && down;
    }
    
    public static void main(String[] args) {
    	char[][] board = new char[][] {{'O','X','X','O','X'},{'X','O','O','X','O'},{'X','O','X','O','X'},{'O','X','O','O','O'},{'X','X','O','X','O'}};
    	new SurroundedRegions().solve(board);
    }
}
