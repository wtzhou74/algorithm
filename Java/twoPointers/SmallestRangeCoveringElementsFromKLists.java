package twoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

//You have k lists of sorted integers in ascending order. Find the smallest range that includes at least one number from each of the k lists.
//
//We define the range [a,b] is smaller than range [c,d] if b-a < d-c or a < c if b-a == d-c.
//
// 
//
//Example 1:
//
//Input: [[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
//Output: [20,24]
//Explanation: 
//List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
//List 2: [0, 9, 12, 20], 20 is in range [20,24].
//List 3: [5, 18, 22, 30], 22 is in range [20,24].
// 
//
//Note:
//
//The given list may contain duplicates, so ascending order means >= here.
//1 <= k <= 3500
//-105 <= value of elements <= 105.

public class SmallestRangeCoveringElementsFromKLists {

	// 从 [0,0,0,...]开始， 得到一个区间 [min, max]，以此从左往右
	// 为了减少区间， 可以 1）向右移动 min, 2)向左移动max; 但是 向左移动max,会使 max所在的列不被包括 （当前的max,是其所在列最“小”的）
	// ==> 如何找到需要移动的索引 （及对应的list）  ==> int[] aux = new int[nums.size()] 记录当前索引下每个列表中的值
	// Time: O(n * m) n* m = total number of elements
	// Space: O(n)
	public int[] smallestRange(List<List<Integer>> nums) {
        if (nums.size() == 1) 
            return new int[]{nums.get(0).get(0), nums.get(0).get(0)};
        int n = nums.size();
        int minRange = 0, maxRange = Integer.MAX_VALUE;
        int[] aux = new int[n]; // aux的索引对应着列表，而aux[i]值对应该列表上的第几个元素
        //int[] next = new int[2];
        int move = 0;
        //boolean complete = false;
        while (true) {
        	int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {           	
                if (nums.get(i).get(aux[i]) < min) {
                    min = nums.get(i).get(aux[i]);
                    move = i;
                    //next[1] = aux[i];
                }
                if (nums.get(i).get(aux[i]) > max) {
                    max = nums.get(i).get(aux[i]);
                }
            }
            // 不能放在这里，否则 aux[len - 1] 就被跳过不处理了
//            if (aux[move] + 1 >= nums.get(move).size())
//                break;
            if (max - min < maxRange - minRange ||
            		(max - min == maxRange - minRange && min < minRange)) {
            	minRange = min;
            	maxRange = max;
            }
            aux[move] = aux[move] + 1;
            if (aux[move] >= nums.get(move).size())
                break;
        }
        
        return new int[]{minRange, maxRange};
    }
	
	
	// 
	public int[] smallestRangeAlert(List<List<Integer>> nums) {
        if (nums.size() == 1) 
            return new int[]{nums.get(0).get(0), nums.get(0).get(0)};
        int n = nums.size();
        int minRange = 0, maxRange = Integer.MAX_VALUE;
        int[] aux = new int[n];
        int next = 0;  
        while (true) {
        	PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
            for (int i = 0; i < n; i++) {
                pq.offer(nums.get(i).get(aux[i]));
            }
            int min = pq.poll(), max = Integer.MIN_VALUE;
            for (int i = 0; i < aux.length; i++) {
                if (nums.get(i).get(aux[i]) == min) {
                    next = i;
                }
                max = Math.max(max, nums.get(i).get(aux[i]));
            }
            if (max - min < maxRange - minRange ||
            		(max - min == maxRange - minRange && min < minRange)) {
            	minRange = min;
            	maxRange = max;
            }
            aux[next] = aux[next] + 1;
            if (aux[next] >= nums.get(next).size())
                break;
        }
        
        return new int[]{minRange, maxRange};
    }
	
	// 充分发挥 PriorityQueue的作用
	// Time: O(n * lgM); m refers to the total number of lists
	public int[] smallestRangeOptimized(List<List<Integer>> nums) {
        if (nums.size() == 1) 
            return new int[]{nums.get(0).get(0), nums.get(0).get(0)};
        int n = nums.size();
        int minRange = 0, maxRange = Integer.MAX_VALUE;
        int[] aux = new int[n];
        // PriorityQueue<int[]> pq = new PriorityQueue<>((i, j) -> 
        //                                              i[0] - i[1]);
        
        // ！！！！！用于放含"当前最小"的list的索引
        PriorityQueue<Integer> pq = new PriorityQueue<>((i, j) ->
                                                       nums.get(i).get(aux[i]) - nums.get(j).get(aux[j]));
        
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < aux.length; i++) {
            pq.offer(i);
            max = Math.max(max, nums.get(i).get(0));
        }
        boolean flag = true;
        // 双层循环这里本身并没太多意义，只是为了确保每个数都能处理到，如果需要的话
        for (int i = 0; i < aux.length && flag; i++) {
            for (int j = 0; j < nums.get(i).size() && flag; j++) {
                int minList = pq.poll();
                if (maxRange - minRange > max - nums.get(minList).get(aux[minList])) {
                    minRange = nums.get(minList).get(aux[minList]);
                    maxRange = max;
                }
                aux[minList]++;
                if (aux[minList] == nums.get(minList).size()) {
                    flag = false;
                    break;
                }
                pq.offer(minList);
                
                max = Math.max(nums.get(minList).get(aux[minList]), max);
            }
        }
        
        return new int[]{minRange, maxRange};
    }
	
	public static void main(String[] args) {
		List<List<Integer>> nums = new ArrayList<>();
		List<Integer> num = Arrays.asList(4,10,15,24,26);
		List<Integer> num1 = Arrays.asList(0,9,12,20);
		List<Integer> num2 = Arrays.asList(5,18,22,30);
		nums.add(num);
		nums.add(num1);
		nums.add(num2);
		new SmallestRangeCoveringElementsFromKLists().smallestRangeOptimized(nums);
	}
}
