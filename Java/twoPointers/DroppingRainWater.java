package twoPointers;

import java.util.Stack;

//Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.
//
//
//The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped. Thanks Marcos for contributing this image!
//
//Example:
//
//Input: [0,1,0,2,1,0,1,3,2,1,2,1]
//Output: 6
public class DroppingRainWater {

	// ！！！！！画图分析
	// Time: O(n)
	// Space: O(1)
	public int trap(int[] height) {
        if (height.length <= 2) return 0;
        int left = 0, right = height.length - 1;
        int count = 0;
        while (left < right) {
            if (height[left] <= height[right]) {
                if (height[left] == height[right] && height[left] == 0) {
                    left++;
                    right--;
                } else if (height[left] == 0) 
                    left++;
                else {
                    int temp = left;
                    left++;
                    while (left < right) {
                        if (height[left] < height[temp]) {
                            count += (height[temp] - height[left]);
                            left++;
                        } else break; // >=的话之间不会有能存水的 area,所以跳出
                    }
                }
            } else {
                if (height[right] == 0) right--;
                else {
                    int temp = right;
                    right--;
                    while (left < right) {
                        if (height[right] < height[temp]) {
                            count += (height[temp] - height[right]);
                            right--;
                        } else break;
                    }
                }
            }
        }
        return count;
    }
	
	// 借助 stack, 里面只放 比如 [2,1,0,0, 1, 5] 这样的元素，（从开始变大的第一个元素开始）然后三个或三个以上（有相等元素）一比较	
	public int trapStack(int[] height) {
        if (height.length <= 2) return 0;
        int count = 0;
        int curr = 0;
        Stack<Integer> stack = new Stack<>();
        while (curr < height.length) {
        	// 这里需要while, 是因为 [1,0,1]这样的序列会在pop后多次出现，比如 [3,2,1,0,0,2] 接着 5进来，就开始 pop了，while处理后，最终计算 [3, -- 5]的area.
            // while 里面，比如 [2, 1, 0, 2],先处理的是 [1, 0, 2] （只会计算矮的部分）,然后 再处理高的部分 [2, 1, 2] 计算结果只会是 1 以上的
        	while (!stack.isEmpty() // 这里不是用 size() >= 2, 这样的话stack最后会留下2个元素，在后面的元素进来后会影响结果
               && height[curr] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.empty()) break; // 有了这个语句，就不用担心 size()是否 >= 2, 如果 [1,2]的元素序列出现，1被pop,2被加入，成为第一个元素，以此类推，比如 [1,2,3]
                int distance = curr - stack.peek() - 1;
                int area = distance * (Math.min(height[curr], height[stack.peek()]) - height[top]);
                count += area;
            }
            stack.push(curr++); // 把它放这里，是每一个 curr都要校验后再看是否放入，和其前一个比较
        }
        return count;
    }
	
	public static void main(String[] args) {
		new DroppingRainWater().trapStack(new int[] {1, 2, 3, 4, 2, 1, 0, 2, 5, 4});
	}
}
