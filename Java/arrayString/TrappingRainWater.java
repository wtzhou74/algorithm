package arrayString;

public class TrappingRainWater {
	// KEY: LEFT_MAX and RIGHT_MAX for each elem ===> 考虑某个具体点试试
	
	// Two pointers  ===> See slides on https://leetcode.com/explore/interview/card/top-interview-questions-hard/124/others/875/
	public int trap(int[] height) {
		if (height.length <= 2) return 0;
        int total = 0;
        int i = 0, j = height.length - 1;
        int leftMax = 0, rightMax = 0;
        while (i < j) {
            leftMax = Math.max(leftMax, height[i]);
            rightMax = Math.max(rightMax, height[j]);
            total += (leftMax - height[i]);
            total += (rightMax - height[j]);
            if (height[i] < height[j]) {
                i++;
                
            } else {
                j--;
            }
            
//            if (height[i] < height[j]) {
//                leftMax = Math.max(leftMax, height[i]);
//                total += (leftMax - height[i]);
//                i++;
//                
//            } else {
//                rightMax = Math.max(rightMax, height[j]);
//                total += (rightMax - height[j]);
//                j--;
//            }
        }
        
        return total;
    }
	
	// Scanning/ Dynamic solution   
	// find LEFT_MAX and RIGHT_MAX for each element
	public int dp(int[] height) {
        if (height.length <= 2) return 0;
        int total = 0;
        
        int[] leftMax = new int[height.length];
        int[] rightMax = new int[height.length];
        
        // scan from left to right to get leftMax
        leftMax[0] = height[0];
        int temp = leftMax[0];
        for (int i = 0; i < height.length; i++) {
            if (temp < height[i])
                temp = height[i];
            leftMax[i] = temp;
        }
        
        // scan from right to left
        //rightMax[height.length - 1] = height[height.length - 1];
        temp = height[height.length - 1];
        for (int i = height.length - 1; i >= 0; i--) {
            if (temp < height[i]) {
                temp = height[i];
            }
            rightMax[i] = temp;
            //total += (Math.min(temp, leftMax[i]) - height[i]);
        }
        
        for (int i = 0; i < height.length; i++) {
            int diff = Math.min(leftMax[i], rightMax[i]) - height[i];
            if (diff > 0) {
                total += diff;
            }
        }
        return total;
    }
	
	public static void main(String[] args) {
		int[] height = new int[] {5, 4, 1, 2};
		new TrappingRainWater().trap(height);
	}
}
