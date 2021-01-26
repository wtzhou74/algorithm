package matrix;

import java.util.ArrayList;
import java.util.List;

//This question is about implementing a basic elimination algorithm for Candy Crush.
//
//Given a 2D integer array board representing the grid of candy, different positive integers board[i][j] represent different types of candies. A value of board[i][j] = 0 represents that the cell at position (i, j) is empty. The given board represents the state of the game following the player's move. Now, you need to restore the board to a stable state by crushing candies according to the following rules:
//
//If three or more candies of the same type are adjacent vertically or horizontally, "crush" them all at the same time - these positions become empty.
//After crushing all candies simultaneously, if an empty space on the board has candies on top of itself, then these candies will drop until they hit a candy or bottom at the same time. (No new candies will drop outside the top boundary.)
//After the above steps, there may exist more candies that can be crushed. If so, you need to repeat the above steps.
//If there does not exist more candies that can be crushed (ie. the board is stable), then return the current board.
//You need to perform the above rules until the board becomes stable, then return the current board.
//
// 
//
//Example:
//
//Input:
//board =
//[[110,5,112,113,114],[210,211,5,213,214],[310,311,3,313,314],[410,411,412,5,414],[5,1,512,3,3],[610,4,1,613,614],[710,1,2,713,714],[810,1,2,1,1],[1,1,2,2,2],[4,1,4,4,1014]]
//
//Output:
//[[0,0,0,0,0],[0,0,0,0,0],[0,0,0,0,0],[110,0,0,0,114],[210,0,0,0,214],[310,0,0,113,314],[410,0,0,213,414],[610,211,112,313,614],[710,311,412,613,714],[810,411,512,713,1014]]
//
//Explanation:
//
// 
//
//Note:
//
//The length of board will be in the range [3, 50].
//The length of board[i] will be in the range [3, 50].
//Each board[i][j] will initially start as an integer in the range [1, 2000].
public class CandyCrush {

	// Step 1: get candies to Crush
	// Step 2: Crush candies / setting 0
	// Step 3: drop cells if needed
	// Step 4: repeat / recursively
	public int[][] candyCrush(int[][] board) {
		List<int[]> toCrush = getCandiesToCrush(board);
		if (toCrush.size() == 0) return board;
		crushCandies(board, toCrush);
		restoreBoard(board);
		
		// repeat
		return candyCrush(board);
	}
	
	public List<int[]> getCandiesToCrush(int[][] board) {
		List<int[]> toCrush = new ArrayList<>();
		int row = board.length, col = board[0].length;
		
		// hori
		for (int i = 0; i < row; i++) {
			// a至少 3 个，所以 j+2
			for (int j = 0; j + 2 < col; j++) {
				int cell = board[i][j];
				// a只需要到 3，之后再有相同，会在j++的被处理
				if (cell != 0 && cell == board[i][j + 1]
						&& cell == board[i][j + 2]) {
					// a记录要cursh的[i, j]
					toCrush.add(new int[] {i, j});
					toCrush.add(new int[] {i, j + 1});
					toCrush.add(new int[] {i, j + 2});
				}
			}
		}
		
		// adjacently
		for (int j = 0; j < col; j++) {
			for (int i = 0; i + 2 < row; i++) {
				int cell = board[i][j];
				if (cell != 0 && cell == board[i + 1][j]
						&& cell == board[i + 2][j]) {
					toCrush.add(new int[] {i, j});
					toCrush.add(new int[] {i + 1, j});
					toCrush.add(new int[] {i + 2, j});
				}
			}
		}
		
		return toCrush;
	}
	
	
	public void crushCandies(int[][] board, List<int[]> toCrush) {
		for (int[] cell : toCrush) {
			board[cell[0]][cell[1]] = 0;
		}
	}
	
	public void restoreBoard(int[][] board) {
		// a按每列查看
		for (int j = 0; j < board[0].length; j++) {
			int bottom = board.length - 1;
			// a从最小面的一列开始往上看
			for (int i = board.length - 1; i >= 0; i--) {
				if (board[i][j] > 0) {
					board[bottom--][j] = board[i][j]; //相当于借助了辅助的数组，只存大于0的数
					// 但此处 bottom小的速度肯定没有 i 快，最多是一样，所以 直接利用board本身作为辅助数组
				}
			}
			// a沉淀完后剩余的cell都设置为0
			while (bottom >= 0) {
				board[bottom--][j] = 0;
			}
		}
	}
	
	// -----------------------------------------------
	// 取反 在原 board上标注出需要处理的 cell
	public int[][] candyCrush2(int[][] board) {
        
        // mark candies to be crushed
        boolean toCrush = markCandiesToBeCrushed(board);
        if (!toCrush) return board;
        // crush candies
        crushCandies(board);
        // drop zero
        droupZero(board);
        //process next round if needed
        return candyCrush(board);
    }


	
    public boolean markCandiesToBeCrushed(int[][] board) {
        
        boolean toCrush = false;
        // check each row
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length - 2; j++) {
                int first = Math.abs(board[i][j]);
                int second = Math.abs(board[i][j + 1]);
                int third = Math.abs(board[i][j + 2]);
                if (first != 0 && first == second   // 0 要排除，否则出现 infinite loop，因为drop后上面有0， 0 取负还是 0 ！！！！！
                   && first == third) {
                    board[i][j] = - first;
                    board[i][j + 1] = - first;
                    board[i][j + 2] = - first;
                    toCrush = true;
                }
            }
        }
        // check each single col
        for (int j = 0; j < board[0].length; j++) {
            for (int i = 0; i < board.length - 2; i++) {
                int first = Math.abs(board[i][j]);
                int second = Math.abs(board[i + 1][j]);
                int third = Math.abs(board[i + 2][j]);
                if (first != 0 
                    && first == second && first == third) { // 0 要排除，否则出现 infinite loop ！！！！！
                    
                    board[i][j] = -first;
                    board[i + 1][j] = -first;
                    board[i + 2][j] = -first;
                    toCrush = true;
                }
            }
        }
        return toCrush;
    }
    
    public void crushCandies(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] < 0) {
                    board[i][j] = 0;
                }
            }
        }
    }
    
    public void droupZero(int[][] board) {
        for (int j = 0; j < board[0].length; j++) {
            int auxIdx = board.length - 1;
            for (int i = board.length - 1; i >= 0; i--) {
                if (board[i][j] > 0) {
                    board[auxIdx][j] = board[i][j];
                    auxIdx--;
                }
            }
            
            // set 0 to remaining cells
            for (int k = auxIdx; k >= 0; k--) {
                board[k][j] = 0;
            }
        }
        
    }
}
