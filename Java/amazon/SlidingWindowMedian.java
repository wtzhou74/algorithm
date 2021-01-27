package amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

//Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.
//
//Examples:
//[2,3,4] , the median is 3
//
//[2,3], the median is (2 + 3) / 2 = 2.5
//
//Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Your job is to output the median array for each window in the original array.
//
//For example,
//Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
//
//Window position                Median
//---------------               -----
//[1  3  -1] -3  5  3  6  7       1
// 1 [3  -1  -3] 5  3  6  7       -1
// 1  3 [-1  -3  5] 3  6  7       -1
// 1  3  -1 [-3  5  3] 6  7       3
// 1  3  -1  -3 [5  3  6] 7       5
// 1  3  -1  -3  5 [3  6  7]      6
//Therefore, return the median sliding window as [1,-1,-1,3,5,6].
//
//Note:
//You may assume k is always valid, ie: k is always smaller than input array's size for non-empty array.
//Answers within 10^-5 of the actual value will be accepted as correct.
public class SlidingWindowMedian {

	public double[] medianSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0)
            return new double[0];
        int i = 0, j = 0;
        List<Double> list = new LinkedList<>();
        while (j < nums.length) {
            if (j - i + 1 < k) {
                j++;
                continue;
            }
            // 这个地方可以优化，在遍历window里面的元素，边遍历边add to auxList, 这样就不需要额外的 copy了
            //  如下
            list.add(findMedian(Arrays.copyOfRange(nums, i, j + 1)));
            i++;
            j++;
        }
        
        return list.stream().mapToDouble(x -> x).toArray();
    }
    
	// 遍历windows的同时，记录元素到aux list
	// Time: O(n*m*lgm)  ==> 对于求Median 可以继续优化
	public double[] medianSlidingWindow1(int[] nums, int k) {
        if (nums == null || nums.length == 0)
            return new double[0];
        int i = 0, j = 0;
        List<Double> list = new LinkedList<>();
        List<Integer> aux = new ArrayList<>();
        while (j < nums.length) {
            if (j - i + 1 < k) {
                aux.add(nums[j]); // 记录起来
                j++;
                continue;
            } 
            aux.add(nums[j]); // 加上window最后一个元素
            Collections.sort(aux); // 排序
            list.add(findMedian(aux)); // 求median
            aux.remove((Integer)nums[i]); // 删除 i 所在的元素， 不是sort之后的第一个，用以新加入一个
            i++;
            j++;
        }
        
        return list.stream().mapToDouble(x -> x).toArray();
    }
    
    public double findMedian(List<Integer> subNums) {
        int size = subNums.size();
        if (size % 2 == 0) {
            return ((double)subNums.get(size / 2 - 1) + (double)subNums.get(size / 2)) / 2;
        } else {
            return subNums.get(size / 2);
        }
    }
    
    public double findMedian(int[] subNums) {
        int size = subNums.length;
        Arrays.sort(subNums);
        if (size % 2 == 0) {
        	 // 做加法，一定要注意是否会 超过 Integer ！！！
            return ((double)subNums[size / 2 - 1] + (double)subNums[size / 2]) / 2;
        } else {
            return subNums[size / 2];
        }
    }
    
    
    // 一定要注意 dataType， + - 运算一定要考虑  Integer.MAX_VALUE!!!!!!!!!
    // Time: O(nlgk)
    public double[] medianSlidingWindowByStacks(int[] nums, int k) {
    	if (nums == null || nums.length == 0)
            return new double[0];
        int i = 0, j = 0;
        List<Double> list = new LinkedList<>();
        //TreeMap<Integer, Double> map = new TreeMap<>();
        List<Integer> aux = new ArrayList<>();
        int idx = 0;
        PriorityQueue<Double> minHeap = new PriorityQueue<>();
        PriorityQueue<Double> maxHeap = new PriorityQueue<>((a, b) -> Double.compare(b, a));
//        PriorityQueue<Double> maxHeap = new PriorityQueue<>(new Comparator<Double>() {
//        	public int compare(Double d1, Double d2) {
//        		return Double.compare(d1, d2);
//        	}
//        });
        while (j < nums.length) {
            if (j - i + 1 < k) {
                //aux.add(nums[j]);
            	findMedianPQ(minHeap, maxHeap, new Double(nums[j])); // 边遍历的时候边让它入stack, 这样就不要再取 window内的元素去求median
                j++;
                continue;
            } 
            double median = findMedianPQ(minHeap, maxHeap, new Double(nums[j]));
            list.add(median);
            
            //remove  // 删除 stack中 nums[i]的元素！！！
            if (minHeap.contains((double)nums[i])) {
                if (minHeap.size() >= maxHeap.size())
                    minHeap.remove((double)nums[i]);
                else {
                    minHeap.remove((double)nums[i]);
                    minHeap.offer(maxHeap.poll());
                }
            } else if (maxHeap.contains((double)nums[i])) {
                if (minHeap.size() > maxHeap.size()) {
                    maxHeap.remove((double)nums[i]);
                    maxHeap.offer(minHeap.poll());
                } else {
                    maxHeap.remove((double)nums[i]);
                } 
            }
            i++;
            j++;
        }
        
        return list.stream().mapToDouble(x -> x).toArray();
    }
    
    public double findMedianPQ(PriorityQueue<Double> minHeap, 
                             PriorityQueue<Double> maxHeap, double num) {
    	double median = 0;
        if (minHeap.size() == 0 && maxHeap.size() == 0) {
            minHeap.offer(num);
            median = num;
        } else if (minHeap.size() > maxHeap.size()) {
            if (num >= minHeap.peek()) {
                maxHeap.offer(minHeap.poll());
                minHeap.offer(num);
            } else {
                maxHeap.offer(num);
            }
            median = (minHeap.peek() + maxHeap.peek()) / 2;
        } else if (minHeap.size() < maxHeap.size()) {
            if (num <= maxHeap.peek()) {
                minHeap.offer(maxHeap.poll());
                maxHeap.offer(num);
            } else {
                minHeap.offer(num);
            }
            median = (minHeap.peek() + maxHeap.peek()) / 2;
        } else {
            if (num <= minHeap.peek()) {
                maxHeap.offer(num);
                median = maxHeap.peek();
            }
            else {
                //maxHeap.offer(minHeap.poll());
                minHeap.offer(num);
                median = minHeap.peek();
            }

        } 
        //System.out.println(median);
        return median;
    }
    
    public static void main(String[] args) {
    	int[] nums = new int[] {1,3,-1,-3,5,3,6,7};
    	new SlidingWindowMedian().medianSlidingWindowByStacks(nums, 3);
    }
}
