package graph;

import java.util.LinkedList;
import java.util.Queue;

//You have a map that marks the location of a treasure island. Some of the map area has jagged rocks and dangerous reefs. Other areas are safe to sail in. There are other explorers trying to find the treasure. 
//So you must figure out a shortest route to the treasure island.
//
//Assume the map area is a two dimensional grid, represented by a matrix of characters. You must start from the top-left corner of the map and can move one block up,
//down, left or right at a time. The treasure island is marked as X in a block of the matrix. X will not be at the top-left corner. Any block with dangerous rocks or reefs will be marked as D. You must not enter dangerous blocks. You cannot leave the map area. Other areas O are safe to sail in. The top-left corner is always safe. Output the minimum number of steps to get to the treasure.
//
//Example:
//
//Input:
//[['O', 'O', 'O', 'O'],
// ['D', 'O', 'D', 'O'],
// ['O', 'O', 'O', 'O'],
// ['X', 'D', 'D', 'O']]
//
//Output: 5
//Explanation: Route is (0, 0), (0, 1), (1, 1), (2, 1), (2, 0), (3, 0) The minimum route takes 5 steps.

// The minimu movement   ====> level ==> for each level is a step, rather than a single movement 
public class TreasureIsland {

	class Point {
		int i;
		int j;
		
		public Point(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}
	
	public int treasureIsland(char[][] matrix) {
		int steps = 0;
		Queue<Point> queue = new LinkedList<>();
		// starting from top-left corner
		queue.offer(new Point(0, 0));
		while (!queue.isEmpty()) {
			int size = queue.size();
			steps++;// starting a step, no matter what the result is after this step, Comparing with RottingOranges.java
			while (size > 0) {
				Point point = queue.poll();
				int row = point.i;
				int col = point.j;
				// movement
				//up
				if (row - 1 >= 0 && matrix[row - 1][col] != 'D') {
					if (matrix[row - 1][col] == 'X') return steps;
					// set it to VISITED
					matrix[row - 1][col] = 'D';
					queue.offer(new Point(row - 1, col));
				}
				//down
				if (row + 1 < matrix.length && matrix[row + 1][col] != 'D') {
					if (matrix[row + 1][col] == 'X') return steps;
					// set it to VISITED
					matrix[row + 1][col] = 'D';
					queue.offer(new Point(row + 1, col));
				}
				//left
				if (col - 1 >= 0 && matrix[row][col - 1] != 'D') {
					if (matrix[row][col - 1] == 'X') return steps;
					// set it to VISITED
					matrix[row][col - 1] = 'D';
					queue.offer(new Point(row, col - 1));
				}
				
				if (col + 1 < matrix[0].length && matrix[row][col + 1] != 'D') {
					if (matrix[row][col + 1] == 'X') return steps;
					// set it to VISITED
					matrix[row][col + 1] = 'D';
					queue.offer(new Point(row, col + 1));
				}
				size--;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		char[][] matrix = new char[][] {
			{'O', 'O', 'O', 'O'},
            {'D', 'O', 'D', 'O'},
            {'O', 'O', 'O', 'O'},
            {'X', 'D', 'D', 'O'}
		};
		int steps = new TreasureIsland().treasureIsland(matrix);
		System.out.println(steps);
	}
}
