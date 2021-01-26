package oa.amazon;

import java.util.LinkedList;
import java.util.Queue;

//Given a 2D grid, each cell is either a zombie 1 or a human 0. Zombies can turn adjacent (up/down/left/right) human beings into zombies every hour. Find out how many hours does it take to infect all humans?
//
//Example:
//
//Input:
//[[0, 1, 1, 0, 1],
// [0, 1, 0, 1, 0],
// [0, 0, 0, 0, 1],
// [0, 1, 0, 0, 0]]
//
//Output: 2
//
//Explanation:
//At the end of the 1st hour, the status of the grid:
//[[1, 1, 1, 1, 1],
// [1, 1, 1, 1, 1],
// [0, 1, 0, 1, 1],
// [1, 1, 1, 0, 1]]
//
//At the end of the 2nd hour, the status of the grid:
//[[1, 1, 1, 1, 1],
// [1, 1, 1, 1, 1],
// [1, 1, 1, 1, 1],
// [1, 1, 1, 1, 1]]

// Similar to RottingOranges.java
public class ZombieInMatrix {

	public int zombieInMatrix (int[][] matrix) {
		if (matrix == null || matrix[0].length == 0) return 0;
		
		int rLen = matrix.length;
		int cLen = matrix[0].length;
		
		Queue<Integer> queue = new LinkedList<>();
		int total = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 1) {
					int cellIdx = i * cLen + j;
					queue.offer(cellIdx);
					total++;
				}
			}
		}
		
		// Time O(l * C) : l - level of tree, c - nodes of each level
		int hours = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			while (size > 0) {
				int cellIdx = queue.poll();
				int r = cellIdx / cLen, c = cellIdx % cLen;
				// up
				if (r - 1 >= 0 && matrix[r - 1][c] != 1) {
					matrix[r - 1][c] = 1;
					queue.offer((r - 1) * cLen + c);
					total++;
				}
					
				// down
				if (r + 1 <= rLen - 1 && matrix[r + 1][c] != 1) {
					matrix[r + 1][c] = 1;
					queue.offer((r + 1) * cLen + c);
					total++;
				}
					
				// left
				if (c - 1 >= 0 && matrix[r][c - 1] != 1) {
					matrix[r][c - 1] = 1;
					queue.offer(r * cLen + (c - 1));
					total++;
				}
				if (c + 1 <= cLen - 1 && matrix[r][c + 1] != 1) {
					matrix[r][c + 1] = 1;
					queue.offer(r * cLen + (c + 1));
					total++;
				}
				size--;
			}
			if (queue.size() > 0 && total < rLen * cLen) hours++;// == 0 means all cells have been already affected, no need to add +1 again
		}
		System.out.println(hours);
		return hours;
	}
	
	public static void main(String[] args) {
		int[][] matrix = new int[][] {
			{1, 0},
			{0, 0}
			//{0, 0, 0, 0, 0},
			//{0, 0, 0, 0, 0}
		};
		new ZombieInMatrix().zombieInMatrix(matrix);
	}
}
