package oa.amazon;

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
public class TreasureIsland {

	class Point {
		int i;
		int j;
		
		public Point(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}
	
	// Similar to https://leetcode.com/problems/walls-and-gates/
	// TIME: O(M*N)
	// Space: O(M*N)
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
				
				// 以下写法更简洁
				//int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
				
//				int size = queue.size();
//				for (int i = 0; i < size; i++) {
//					Coordinate coordinate = queue.poll();
//					int x = coordinate.x;
//					int y = coordinate.y;
//					if (island[x][y] == 'X') return steps; // 找到了 就 return 了
//
//					for (int[] dir : dirs) {
//						int newX = x + dir[0];
//						int newY = y + dir[1];
//
//						if (newX >= 0 && newX < island.length && newY >= 0 && newY < island[0].length &&
//								island[newX][newY] != 'D' && !visited[newX][newY]) {
//							queue.add(new Coordinate(newX, newY));
//							visited[newX][newY] = true;
//						}
//					}
//				}
//				steps++;
			}
		}
		return -1;
	}
	
	// DFS
	int min = Integer.MAX_VALUE;
	public int treasureIslanddfs(char[][] matrix) {
		if (matrix == null || matrix.length == 0) return 0;
		boolean[][] visited = new boolean[matrix.length][matrix[0].length];
		//int steps = 0;
		dfs(matrix, visited, 0, 0, 0);
		return min;
	}
	
	public void dfs (char[][] matrix, boolean[][] visited, int i, int j, int steps) {
		if (i < 0 || i > matrix.length - 1 || j < 0 || j > matrix[0].length - 1
				|| matrix[i][j] == 'D' || visited[i][j]) {
			return;
		}
		if (matrix[i][j] == 'X') {
			min = Math.min(min, steps);
			return;
		}
		visited[i][j] = true;
		dfs(matrix, visited, i + 1, j, steps + 1);//down
		dfs(matrix, visited, i - 1, j, steps + 1);//up
		dfs(matrix, visited, i, j + 1, steps + 1);//right
		dfs(matrix, visited, i, j - 1, steps + 1);//left
		//visited[i][j] = false; //可不要
	}
	
	public static void main(String[] args) {
		char[][] matrix = new char[][] {
			{'O', 'O', 'O', 'O'},
            {'D', 'O', 'D', 'O'},
            {'O', 'O', 'O', 'O'},
            {'X', 'D', 'D', 'O'}
		};
		int steps = new TreasureIsland().treasureIslanddfs(matrix);
		System.out.println(steps);
	}
}
