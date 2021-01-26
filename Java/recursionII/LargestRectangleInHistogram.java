package recursionII;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/*Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.

Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].

The largest rectangle is shown in the shaded area, which has area = 10 unit.

Example:

Input: [2,1,5,6,2,3]
Output: 10
*/
public class LargestRectangleInHistogram {

	// TIME: O(n)
	public int largestRectangleArea(int[] heights) {
		int n = heights.length;
		if (n == 0) return 0;
		if (n == 1) return heights[0];
		Deque<Integer> s = new ArrayDeque<>();
		int max = 0; // area is always positive
		int i = 0;
		while (i < n) {
			if (s.isEmpty() || heights[i] >= heights[s.peek()]) // current bar value is BIGGER than the TOP item of stack
			{
				s.push(i++); // always keep the bars in stack in ASCENDING ORDER, there is a possible MAX AREA.
			} else {
				// Calculate ALL AREA within this SUBAREA
				int top = s.pop();
				int leftToRight = i - s.peek() - 1;
				// if s.isEmpty, then the TOP is the SMALLEST value within this subarea, then the area is the heights[top] * i
				// REFERENCE TO: https://blog.csdn.net/weixin_40017590/article/details/80522433
				int area = heights[top] * (s.isEmpty() ? i : leftToRight);
				max = Math.max(max, area);
			}
		}
		// if all remaining elements are sorted in ASCENDING order
		while (!s.isEmpty()) {
			int top = s.pop();
			int area = heights[top] * (s.isEmpty() ? i : i - s.peek() - 1);
			max = Math.max(max, area);
		}
		
        return max;
    }
	
	// Given CURRENT i, FIND BARs in ascending order FROM BOTH SIDEs, then I is the lowest bar within this SUB-AREA
	public int bruteForce(int[] heights) {
		int max = 0;
		if (heights.length == 0) return 0;
		if (heights.length == 1) return heights[0];
		
		for (int i = 0; i < heights.length; i++) {
			int left = i;
			// starting from CURRENT bar, find the LEFTMOST bar in ascending order
			while (left >= 0 && heights[left] >= heights[i]) {
				left--;
			}
			// starting from CURRENT bar, find the RIGHTMOST bar in ascending order
			int right = i;
			while (right <= heights.length - 1 && heights[right] > heights[i]) {
				right++;
			}
			// THEN i IS THE SMALLEST BAR WITHIN THIS SUB-AREA
			max = Math.max(heights[i] * (right - left - 1), max);
			
		}
		return max;
	}
	public static void main(String[] args) {
		int[] heights = new int[] {2, 1, 4, 5, 6, 2, 3};
		new LargestRectangleInHistogram().largestRectangleArea(heights);
	}
}
