package priorityQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

//Given 2 lists a and b. Each element is a pair of integers where the first integer represents the unique id and the second integer represents a value. Your task is to find an element from a and an element form b such that the sum of their values is less or equal to target and as close to target as possible. Return a list of ids of selected elements. If no pair is possible, return an empty list.
//
//Example 1:
//
//Input:
//a = [[1, 2], [2, 4], [3, 6]]
//b = [[1, 2]]
//target = 7
//
//Output: [[2, 1]]
//
//Explanation:
//There are only three combinations [1, 1], [2, 1], and [3, 1], which have a total sum of 4, 6 and 8, respectively.
//Since 6 is the largest sum that does not exceed 7, [2, 1] is the optimal pair.
//Example 2:
//
//Input:
//a = [[1, 3], [2, 5], [3, 7], [4, 10]]
//b = [[1, 2], [2, 3], [3, 4], [4, 5]]
//target = 10
//
//Output: [[2, 4], [3, 2]]
//
//Explanation:
//There are two pairs possible. Element with id = 2 from the list `a` has a value 5, and element with id = 4 from the list `b` also has a value 5.
//Combined, they add up to 10. Similarily, element with id = 3 from `a` has a value 7, and element with id = 2 from `b` has a value 3.
//These also add up to 10. Therefore, the optimal pairs are [2, 4] and [3, 2].
//Example 3:
//
//Input:
//a = [[1, 8], [2, 7], [3, 14]]
//b = [[1, 5], [2, 10], [3, 14]]
//target = 20
//
//Output: [[3, 1]]
//Example 4:
//
//Input:
//a = [[1, 8], [2, 15], [3, 9]]
//b = [[1, 8], [2, 11], [3, 12]]
//target = 20
//
//Output: [[1, 3], [3, 2]]

public class OptimalUtilization {

	// PriorietyQueue solution
	
	// Time: O(n^2), n is the number of total elements
	// Space: O(lgN)
	public List<List<Integer>> optimalUtilization(int[][] a, int[][] b, int target) {
		if (a.length == 0 || b.length == 0)
			return new ArrayList<>();
		List<List<Integer>> res = new ArrayList<>();
		// sort both 2d array
		// sort in order of largest to smallest
		Arrays.sort(a, new Comparator<int[]>() {
			public int compare(int[] a1, int[] a2) {
				return a1[1] - a2[1];
			}
		});
		
		Arrays.sort(b, new Comparator<int[]>() {
			public int compare(int[] a1, int[] a2) {
				return a1[1] - a2[1];
			}
		});
		
		//int i = 0, j = 0; // cannot know how pointer move
		int i = 0, j = b.length - 1;// now we know how the pointer move
		int max= Integer.MIN_VALUE;
		while (i < a.length && j >= 0) {
			if (a[i][1] + b[j][1] > target) {
				j--;
			} else {
				if (max > a[i][1] + b[j][1]) {
					// check larger one
					i++;
				} else if(max <= a[i][1] + b[j][1]) {
					if (max < a[i][1] + b[j][1]) {
						// got a LARGER max, we need clean the final pairs
						res.clear();
						max = a[i][1] + b[j][1];
					}
					// add a new pair to the result
					List<Integer> pair = new ArrayList<>();
					pair.add(a[i][0]);
					pair.add(b[j][0]);
					res.add(pair);
					
					// check if there are same value
					//int idx = j - 1;
					while (j - 1 >= 0 && b[j][1] == b[j - 1][1]) {
						List<Integer> newPair = new ArrayList<>();
						newPair.add(a[i][0]);
						newPair.add(b[j][0]);
						res.add(newPair);
						j--;
					}
					// check the LARGER one
					i++;
				}
//					if( max < a[i][1] + b[j][1]){
//					max = a[i][1] + b[j][1];
//					// record i, j
//					res.clear();// we got a new larger pair ==> remove previous ones
////					List<Integer> pair = new ArrayList<>();
////					pair.add(i);
////					pair.add(j);
////					res.add(pair);
////					// check if the adjacent[i] are same
////					int idx = j - 1;
////					while (idx >= 0 && b[idx][1] == b[idx + 1][1]) {
////						// same
////						List<Integer> newPair = new ArrayList<>();
////						newPair.add(i);
////						newPair.add(j);
////						res.add(newPair);
////					}
////					i++;// check larger one
//				} else {
//					// we got same pair
////					List<Integer> pair = new ArrayList<>();
////					pair.add(i);
////					pair.add(j);
////					res.add(pair);
////					
////					int idx = j - 1;
////					while (idx >= 0 && b[idx][1] == b[idx + 1][1]) {
////						// same
////						List<Integer> newPair = new ArrayList<>();
////						newPair.add(i);
////						newPair.add(j);
////						res.add(newPair);
////					}
////					
////					i++;
//					
//				}
			}
		}
		return res;
	}
	
	public static void main(String[] args) {
		int[][] a = new int[][] {
			{1, 3},
			{2, 5},
			{3, 7},
			{4, 10}
		};
		int[][] b = new int[][] {
			{1, 2},
			{2, 3},
			{4, 5},
			{3, 4}
		};
		
		new OptimalUtilization().optimalUtilization(a, b, 10);
	}
}
