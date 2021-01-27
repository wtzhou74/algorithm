package amazon;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

//On an N x N board, the numbers from 1 to N*N are written boustrophedonically starting from the bottom left of the board, and alternating direction each row.  For example, for a 6 x 6 board, the numbers are written as follows:
//
//
//You start on square 1 of the board (which is always in the last row and first column).  Each move, starting from square x, consists of the following:
//
//You choose a destination square S with number x+1, x+2, x+3, x+4, x+5, or x+6, provided this number is <= N*N.
//(This choice simulates the result of a standard 6-sided die roll: ie., there are always at most 6 destinations, regardless of the size of the board.)
//If S has a snake or ladder, you move to the destination of that snake or ladder.  Otherwise, you move to S.
//A board square on row r and column c has a "snake or ladder" if board[r][c] != -1.  The destination of that snake or ladder is board[r][c].
//
//Note that you only take a snake or ladder at most once per move: if the destination to a snake or ladder is the start of another snake or ladder, you do not continue moving.  (For example, if the board is `[[4,-1],[-1,3]]`, and on the first move your destination square is `2`, then you finish your first move at `3`, because you do not continue moving to `4`.)
//
//Return the least number of moves required to reach square N*N.  If it is not possible, return -1.
//
//Example 1:
//
//Input: [
//[-1,-1,-1,-1,-1,-1],
//[-1,-1,-1,-1,-1,-1],
//[-1,-1,-1,-1,-1,-1],
//[-1,35,-1,-1,13,-1],
//[-1,-1,-1,-1,-1,-1],
//[-1,15,-1,-1,-1,-1]]
//Output: 4
//Explanation: 
//At the beginning, you start at square 1 [at row 5, column 0].
//You decide to move to square 2, and must take the ladder to square 15.
//You then decide to move to square 17 (row 3, column 5), and must take the snake to square 13.
//You then decide to move to square 14, and must take the ladder to square 35.
//You then decide to move to square 36, ending the game.
//It can be shown that you need at least 4 moves to reach the N*N-th square, so the answer is 4.
//Note:
//
//2 <= board.length = board[0].length <= 20
//board[i][j] is between 1 and N*N or is equal to -1.
//The board square with number 1 has no snake or ladder.
//The board square with number N*N has no snake or ladder.

public class SnakesAndLadders {

	// 1) 对题目的理解， cell中放的值比当前position大，说明是 ladder,如果小（不是-1），说明是snake,跳回去了
	// 2）  position + 1，2，... 6的判断，看是否能 +到6，不能超过 n * n
	// 3）  怎么根据position求出row，col, !!!!尤其注意col的求解
	public int snakesAndLadders(int[][] board) {
        if (board.length == 0)
            return 0;
        Queue<Integer> queue = new LinkedList<>();
        int row = board.length;
        int col = board[0].length;
        boolean[] visited = new boolean[row * col + 1];
        queue.offer(1);
        visited[1] = true;
        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                int cell = queue.poll();// position num
                //System.out.println(count + " " + cell);
                // 注意for i的变化， +不能超过 n*n的值
                for (int position = cell + 1; position <= Math.min(cell + 6, row * col); position++) {
                    //int position = cell + i;
                    int[] newCell = convert(position, row);
                    //System.out.println(cell + i);
//                    if (visited[cell + i]) continue;//要算出最终值才可以判断
                    //							!!!!!只有在 -1 的时候才取 position的值
                    // 比如 第2位放的是8， 第8位放的是56； 按照上面注释掉的逻辑，到8的时候发现visited了，该cell
                    //  被跳过，56也就丢了，所以，每次看 cell ?= -1, 不是的话放真正的值，否则放position
                    // 而实际上visited只是表示如果再碰到8时不是需要重新入Queue了，而如果8中放的不是-1，而是56，那56
                    //  就得入Queue了
                    
                    int realVal = board[newCell[0]][newCell[1]];
                    // 这里到达 n*n， 1) 有可能position就到，2) 也有可能position放的值realVal就是 n*n
                    if (position == row * col || realVal == row * col)
                        return count + 1;
                    if (realVal < 0) {
                        if (!visited[position]) {
                            queue.offer(position);
                            visited[position] = true;
                        }
                        
                    } else {
                        // snake or ladder
                        if (!visited[realVal]) {
                            queue.offer(realVal);
                            visited[realVal] = true;
                        }   
                    } 
                }
                size--;
            }
            count++;
        }
        return -1;
    }
    
    public int[] convert(int cell, int n) {
        int quote = (cell - 1) / n;
        int remainder = (cell - 1) % n;
        int row = n - quote - 1;
        int col = 0;
        if (row % 2 != n % 2) { // 不能简单 row % 2 ?= 0 来判断
            col = remainder;
        } else {
            col = n - remainder - 1;
        }
        //System.out.println(cell + " :" + row + " " + col + " " + n);
        return new int[] {row, col};
    }
    
    public static void main(String[] args) {
//    	int[][] board = new int[][] {
//    		{-1,-1,-1,46,47,-1,-1,-1},
//    		{51,-1,-1,63,-1,31,21,-1},
//    		{-1,-1,26,-1,-1,38,-1,-1},
//    		{-1,-1,11,-1,14,23,56,57},
//    		{11,-1,-1,-1,49,36,-1,48},
//    		{-1,-1,-1,33,56,-1,57,21},
//    		{-1,-1,-1,-1,-1,-1,2,-1},
//    		{-1,-1,-1,8,3,-1,6,56}
//    	};
    	int[][] board = new int[][] {
    		{-1,-1,-1},
    		{-1,9,8},
    		{-1,8,9}
    	};
    	int x = new SnakesAndLadders().snakesAndLadders(board);
    	System.out.println(x);
    }
}
