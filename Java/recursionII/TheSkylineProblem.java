package recursionII;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

// https://leetcode.com/explore/learn/card/recursion-ii/507/beyond-recursion/3006/
//A city's skyline is the outer contour of the silhouette formed by all the buildings in 
//that city when viewed from a distance. Now suppose you are given the locations and height
//of all the buildings as shown on a cityscape photo (Figure A), write a program to output 
//the skyline formed by these buildings collectively (Figure B).
//
//Buildings Skyline Contour
//The geometric information of each building is represented by a triplet of integers 
//[Li, Ri, Hi], where Li and Ri are the x coordinates of the left and right edge of 
//the ith building, respectively, and Hi is its height. It is guaranteed that 0 ≤ Li, 
//Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0. You may assume all buildings are perfect
//rectangles grounded on an absolutely flat surface at height 0.
//
//For instance, the dimensions of all buildings in Figure A are recorded as: [ [2 9 10], [3 7 15], 
//[5 12 12], [15 20 10], [19 24 8] ] .
//
//The output is a list of "key points" (red dots in Figure B) in the format of 
//[ [x1,y1], [x2, y2], [x3, y3], ... ] 
//that uniquely defines a skyline. A key point is the left endpoint of a horizontal 
//line segment. Note that the last key point, where the rightmost building ends, 
//is merely used to mark the termination of the skyline, and always has zero height. 
//Also, the ground in between any two adjacent buildings should be considered part of the skyline contour.
//
//For instance, the skyline in Figure B should be represented as:
//[ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ].
//
//Notes:
//
//The number of buildings in any input list is guaranteed to be in the range [0, 10000].
//The input list is already sorted in ascending order by the left x position Li.
//The output list must be sorted by the x position.
//There must be no consecutive horizontal lines of equal height in the output skyline. 
//For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable; 
//the three lines of height 5 should be merged into one in the final output as such: 
//[...[2 3], [4 5], [12 7], ...]

// https://www.cnblogs.com/TinyBobo/p/4592061.html

// 1:  HEIGHTS LEFTMOST BOUND => Heap => Priority Queue
// 2:  Record ALL BAR sorted by X-axle and Height => Comparator => Compare()

public class TheSkylineProblem {

	// Max-heap
	public class MaxCom implements Comparator<Integer> {
		public int compare(Integer a, Integer b) {
			return b - a; // descending order. Bigger will be on the top of heap
		}
	}
	
	// Comparator for Array
	public class ArrayCom implements Comparator<int[]> {
		public int compare(int[] a, int[] b) {
			if (a[0] != b[0]) return a[0] - b[0]; // sorted by LEFT-BOUND in ascending order
			return b[1] - a[1]; // then sorted by HEIGHT in descending order which means if they are OVERLAP, then the higher will be ahead.
		}
	}
	public List<List<Integer>> getSkyline(int[][] buildings) {
        if (buildings.length == 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        // Default initial capacity of priority queue is 11
        //PriorityQueue<Integer> maxHeap = new PriorityQueue<>(11, new MaxCom()); // Initial a priority queue whose initial size is 11 with user-defined compartor
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new Comparator<Integer> () {
        	public int compare(Integer a, Integer b) {
        		return b - a;
        	}
        });
        List<int[]> ver = new ArrayList<>();// record ALL Y-axis
        for (int i = 0; i < buildings.length; i++) {
        	int[] temp = buildings[i]; // For each building, it has TWO boundaries. 
        	// SET RIGHT BOUND TO MINUS left-bound to distinguish LEFT FROM RIGH, since their value are the same.
        	ver.add(new int[]{temp[0], temp[2]}); // left bound 
        	ver.add(new int[] {temp[1], -temp[2]}); // right bound
        }
        Collections.sort(ver, new ArrayCom());
        
        // traverse boundaries
        int cur = 0, pre = 0;
        for (int i = 0; i < ver.size(); i++) {
        	int[] temp = ver.get(i);
        	if (temp[1] > 0) { // LEFT BOUND
        		maxHeap.offer(temp[1]); // PUSH the current HEIGHT to the Heap
        		cur = maxHeap.peek(); // current highest building
        	} else {
        		// RIGHT BOUND
        		maxHeap.remove(-temp[1]); // REMOVE THE HEIGHT from heap
        		cur = (maxHeap.peek() == null ? 0 : maxHeap.peek()); // If the RIGHT BOUND is the last one, then set current height to 0, otherwise update it
        	}
        	if (cur != pre) {
        		List<Integer> s = new ArrayList<>();
        		s.add(temp[0]);
        		s.add(cur);
        		res.add(s);
        		pre = cur; //
        	}
        }
        return res;
    }
	
	public static void main(String[] args) {
		int[][] buildings = new int[][] {{2, 9, 10}, {2, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}};
		new TheSkylineProblem().getSkyline(buildings);
	}
 }
