package twoPointers;

//Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines, which together with x-axis forms a container, such that the container contains the most water.
//
//Note: You may not slant the container and n is at least 2.
//
// 
//
//
//
//The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
//
// 
//
//Example:
//
//Input: [1,8,6,2,5,4,8,3,7]
//Output: 49
public class ContainerWithMostWater {

	public int maxArea(int[] height) {
        if (height.length < 2) return -1;
        int i = 0, j = height.length - 1; // 长度越长，其面积（可能）越大，取决于两边的最短高度
        int max = Integer.MIN_VALUE;
        while (i < j) { //a移动时， 先移“短的”，只有留下“长”的，在缩短间距时才有可能得到更大的面积，否则只会越来越小，
        	// a因为高度是取决于短的, 而往里移动的时候，长度在减小
            max = Math.max(max, (j - i) * Math.min(height[i], height[j]));
            if (height[i] >= height[j]) {
                j--;
            } else {
                i++;
            }
        }
        return max;
    }
}
