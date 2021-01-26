package twoPointers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//You are given a circular array nums of positive and negative integers. If a number k at an index is positive, then move forward k steps. Conversely, if it's negative (-k), move backward k steps. Since the array is circular, you may assume that the last element's next element is the first element, and the first element's previous element is the last element.
//
//Determine if there is a loop (or a cycle) in nums. A cycle must start and end at the same index and the cycle's length > 1. Furthermore, movements in a cycle must all follow a single direction. In other words, a cycle must not consist of both forward and backward movements.
//
// 
//
//Example 1:
//
//Input: [2,-1,1,2,2]
//Output: true
//Explanation: There is a cycle, from index 0 -> 2 -> 3 -> 0. The cycle's length is 3.
//Example 2:
//
//Input: [-1,2]
//Output: false
//Explanation: The movement from index 1 -> 1 -> 1 ... is not a cycle, because the cycle's length is 1. By definition the cycle's length must be greater than 1.
//Example 3:
//
//Input: [-2,1,-1,-2,-2]
//Output: false
//Explanation: The movement from index 1 -> 2 -> 1 -> ... is not a cycle, because movement from index 1 -> 2 is a forward movement, but movement from index 2 -> 1 is a backward movement. All movements in a cycle must follow a single direction.
// 
//
//Note:
//
//-1000 ≤ nums[i] ≤ 1000
//nums[i] ≠ 0
//1 ≤ nums.length ≤ 5000
// 
//
//Follow up:
//
//Could you solve it in O(n) time complexity and O(1) extra space complexity?
public class CircularArrayLoop {

	// Note: 1) 不是总从nums[0], 开始的，比如 [3,1,2]; cycle存在； cycle的出现可以从任何一个点开始 (for i; visited[])
	//		 2）  注意方向，+ / - ==> 即找next点， %运算
	// Wrong Solution below
	public boolean circularArrayLoop0(int[] nums) {
        if (nums.length <= 1) return false;
        int i = 0;
        while (i < nums.length) {
            if (nums[i] == nums.length)
                i++;
            else 
                break;
        }
        List<Integer> list = new ArrayList<>();
        int count = 0;
        while (count < 2 * nums.length) {
            if (list.contains(i)) {
                int res = 0;
                int temp = 0;
                boolean next = false;
                for (int j = 0; j < list.size();j++) {
                    if (list.get(j) == i) {
                        res = list.size() - j;
                        temp = j;
                    }
                    if (res == 1) return false;
                    //System.out.println("res" + res);
                    if (res > 1) {
                        if (nums[list.get(temp)] > 0) {
                            if (nums[list.get(j)] < 0) {
                                if (nums.length - list.get(list.size() - 1) - 1 >= 2) {
                                    i = list.get(list.size() - 1) + 1;
                                    System.out.println(i);
                                    next = true;
                                    break;
                                }   
                                else 
                                    return false;
                            }
                        } else {
                            if (nums[list.get(j)] > 0) {
                                if (nums.length - list.get(list.size() - 1) - 1 >= 2) {
                                    i = list.get(list.size() - 1) + 1;
                                    System.out.println(i);
                                    next = true;
                                    break;
                                }   
                                else 
                                    return false;
                            }
                        }
                    }
                    
                }
                if (!next && res > 1) return true;
            }
            list.add(i);
            //System.out.println(i);
            count++;
            if (nums[i] > 0) {
                i = (i + nums[i]) % nums.length;
            } else {
                int temp = i - Math.abs(nums[i]) % nums.length;
                if (temp >= 0) i = temp;
                else i = nums.length + temp;
            }
        }
        return false;
    }
	
	public boolean circularArrayLoop(int[] nums) {
		if (nums.length <= 1) return false;
        int n = nums.length;
        // 优化： 这里可以把 visited的num设置为 0 （nums不能为0）， 这样就不需要 visited[]数组了
        // 但是赋值0之前要记得保存下当前值; 如下 circularArrayLoopWithoutVisited()
        boolean[] visited = new boolean[n]; 
        // 可从任何点出发，找到 cycle ===> 就如 2d Matrix， 有时候我们需要考虑从任何点出发
        // 为了避免重复从某个点出发， ====> visited[]
        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            List<Integer> list = new ArrayList<>();
            list.add(i);
            int cur = i; // 从该点开始
            while (true) {
                int next = -1;
                if (nums[cur] > 0)
                    next = (cur + nums[cur]) % n; // 正数情况，下面负数的表达式同样适用于正数，可以合并
                else {
                    next = ((cur + nums[cur]) % n + n) % n;
                }
                // 如果回到上一个点，说明 cycle size = 1
                // 如果前后两个点乘积 < 0, 说明方向不一致
                if (next == cur || nums[next] * nums[cur] < 0) //利用前后两个数的乘积的符号判断方向是否一致
                    break; // 此两种情况说明从 i 点出发没有满足条件的 cycle, break, 看从下一个 i开始的情况，所以此时不能 return
                if (list.contains(next)) return true;// 找到了，之后就不用再判断了
                list.add(next);
                cur = next; // 判断下一个邻接点
                visited[next] = true;
            }
        }
        return false;
    }
	
	public boolean circularArrayLoopWithoutVisited(int[] nums) {
        if (nums.length <= 1) return false;
        int n = nums.length;
        //boolean[] visited = new boolean[n];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) continue;
            //nums[i] = 0;
            List<Integer> list = new ArrayList<>();
            list.add(i);
            int cur = i;
            int aux = nums[cur];
            nums[cur] = 0;
            while (true) {
                int next = -1;
                if (aux > 0)
                    next = (cur + aux) % n;
                else {
                    next = ((cur + aux) % n + n) % n;
                }
                if (next == cur || nums[next] * aux < 0)
                    break;
                if (list.contains(next)) return true;
                list.add(next);
            
                cur = next;
                aux = nums[next];
                nums[next] = 0;
            }
        }
        return false;
    }
	
	// SLOW-FAST pointer
	// 1) slow-fast要同向；  2） visited设置
	// 与传统的check cycle不同的是 如何取 next
	public boolean slowFast(int[] nums) {
		if (nums.length <= 1) return false;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) continue;
            int slow = i, fast = i;
            // 这里选择跟 fast的值相乘，因为 slow肯定要走 fast走过的点，所以判断后面的更快
            while (nums[i] * nums[fast] > 0
                  && nums[i] * nums[getNext(nums, fast)] > 0) {
                slow = getNext(nums, slow);
                
                fast = getNext(nums, getNext(nums, fast));
                if (slow == fast) { // 这个 if要移动上面，如果 fast初始值为 slow.next
                    if (slow == getNext(nums, slow)) break; // 判断长度为 1 的循环， 同样不能return, 看下一个起点是否有循环
                    return true;
                }
            }
            int cur = i; // 对slow走过的点置0， 此时这里出发的，slow-fast不可能相遇，否则上面就return了
            // 同向即可
            while (nums[i] * nums[cur] > 0) { //！！！ visited 标识 0 不能在上面的while中处理，因为如果直接对slow过的置0，fast回来的时候值就变了                                         
            	int next = getNext(nums, cur); //！！！ 根据上一个while的循环，如果同向，会一直走，走到不同向或者 slow==fast但cycle长度只有1，否则不会到这来
                nums[cur] = 0; // 处理后才能重置，否则值变了，结果也变了
                cur = next;
            }
            
        }
        return false;
    }
    
    public int getNext(int[] nums, int s) {
        int n = nums.length;
        if (nums[s] > 0)
            return (s + nums[s]) % n;
        else {
            return ((s + nums[s]) % n + n) % n;
        }
    }
	public static void main(String[] args) {
		new CircularArrayLoop().slowFast(new int[] {2,1,2});
	}
	
}
