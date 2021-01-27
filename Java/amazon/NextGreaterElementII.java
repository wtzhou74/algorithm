package amazon;

import java.util.Arrays;
import java.util.Stack;

//Given a circular array (the next element of the last element is the first element of the array), print the Next Greater Number for every element. The Next Greater Number of a number x is the first greater number to its traversing-order next in the array, which means you could search circularly to find its next greater number. If it doesn't exist, output -1 for this number.
//
//Example 1:
//Input: [1,2,1]
//Output: [2,-1,2]
//Explanation: The first 1's next greater number is 2; 
//The number 2 can't find next greater number; 
//The second 1's next greater number needs to search circularly, which is also 2.
//Note: The length of given array won't exceed 10000.
public class NextGreaterElementII {

	public int[] nextGreaterElements(int[] nums) {
        if (nums == null || nums.length < 1)
            return new int[0];
        int i = 0;
        int n = nums.length;
        int[] result = new int[nums.length];
        Arrays.fill(result, -1); // 这样下面没有被赋值的说明没有更大的值，按题意返回-1，所以这里设置成-1，下面对没有更大值的就不用额外判断了
        while (i < n) {
            int curr = nums[i];
            int j = (i + 1) % n;
            while (j != i) { // 碰到当前值就结束
                if (nums[j] > nums[i]) {
                    result[i] = nums[j];
                    break;
                }
                j = (j + 1) % n;
            }
            i++;            
        }
        return result;
    }
	
	// Time: O(N)
	
	// 1） 从右往左遍历，因为我们要找后面当前值大的， 2） 另外是一个circle, 所以这里遍历2次
	// stack， nums[i] > peek(),就pop直到 大于nums[i] 为止，同时push当前值进去，因为这个nums[i]可能是别的更大的值
	//  pop只发生在 stack有值，同时 peek的值小于 nums[i]，我们要继续往下找，看stack中是否有合适的，所以要pop
	public int[] nextGreaterElementsStack(int[] nums) {
        if (nums == null || nums.length < 1)
            return new int[0];
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[nums.length];
        Arrays.fill(result, -1);
        for (int i = 0; i < 2; i++) {
            for (int j = nums.length - 1; j >= 0; j--) {
                if (stack.isEmpty()) { // stack本身为空，push
                    result[j] = -1;
                    stack.push(nums[j]);
                } else if (nums[j] >= stack.peek()) {
                    while (!stack.isEmpty()) { // 找到比 nums[i]大为止
                        stack.pop();
                        if (!stack.isEmpty() && nums[j] < stack.peek()) {
                            //stack.push(nums[j]);
                            result[j] = stack.peek(); // 找到了
                            break;
                        }
                    }
                    stack.push(nums[j]); // 不管找没找到，当前值nums【i】都要被push进去
                    		// 刚开始fill(-1),所以这里不用单独对没有更大值的设置 -1
                } else {
                    result[j] = stack.peek(); // 直接就找到了
                    stack.push(nums[j]); // 把当前值push进去就好
                }
            }
        }
        return result;
	}
}
