package arrayString;

//Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). n vertical lines are drawn such that the two endpoints
//of line i is at (i, ai) and (i, 0). Find two lines, which together with x-axis forms a container, such that the container contains the most water.
//
//Note: You may not slant the container and n is at least 2.
//
//The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
//
//Example:
//
//Input: [1,8,6,2,5,4,8,3,7]
//Output: 49

public class ContainerWithMostWater {

	// Brute-force 
	// Time: O(n^2)
	public int maxArea(int[] height) {
        if (height.length == 2) return height[1] - height[0];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int currentMax = Math.min(height[i], height[j]) * (j - i);
                max = Math.max(currentMax, max);
            }
        }
        return max;
    }
	
	// Min(height[i], height[j]) * distance ==> TWO-POINTERs => Starting from both side
	public int sol(int[] height) {
		int left = 0, right = height.length - 1;
		int max = Integer.MIN_VALUE;
        while (left < right) {
            max = Math.max(Math.min(height[left], height[right]) * (right - left), max);
            if(height[left] - height[right] >= 0)
            {
               right--; // NO NEED to check left++, since that value MUST BE LESS than this one BASEED ON FORMULA, since the distance is same.
            } else left++;
        }
        return max;
	}
	public static void main(String[] args) {
		new ContainerWithMostWater().maxArea(new int[] {1,8,6,2,5,4,8,3,7});
	}
}
