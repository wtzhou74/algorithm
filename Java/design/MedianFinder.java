package design;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class MedianFinder {

	// TWO Heaps Solution
	// Time: O(lgN)
	// Space: O(n)
	private PriorityQueue<Integer> maxHeap;// smaller half
    private PriorityQueue<Integer> minHeap;// larger half
    private double median;
    // private int size;
    /** initialize your data structure here. */
    public MedianFinder() {
    	// 将 list分成left, right部分，左边的全部元素都小于或等于右边的全部元素
    	//		minHeap放大的部分， maxHeap放相对于maxHeap小的部分
        this.maxHeap = new PriorityQueue<>((a, b) -> b - a);
        this.minHeap = new PriorityQueue<>((a, b) -> a - b);
        this.median = 0;
    }
    
    public void addNum(int num) {
        int maxSize = maxHeap.size();
        int minSize = minHeap.size();
        // 先放min起
        if (maxSize == 0 && minSize == 0) {
            minHeap.offer(num);
            median = num;
        } else {
        	// 保证高度相差1， 另外minHeap的最小要 >= maxHeap的最大
            // BALANCING the two heaps to always keep difference between the two heaps 1
        	// by removing from min/maxHeap back and forth
            if (maxSize > minSize) {
                if (maxHeap.peek() <= num) {
                    minHeap.offer(num);
                } else {
                    minHeap.offer(maxHeap.poll());
                    maxHeap.offer(num);
                }
                median = (maxHeap.peek() + minHeap.peek()) / 2d;
                // 谁多就跟谁先比较，看是否需要移动或直接加到少的一边
            } else if (maxSize < minSize) {
                if (minHeap.peek() >= num) {
                    maxHeap.offer(num);
                } else {
                    maxHeap.offer(minHeap.poll());
                    minHeap.offer(num);
                }
                median = (maxHeap.peek() + minHeap.peek()) / 2d;
            } else {
                if (minHeap.peek() >= num) {
                    maxHeap.offer(num);
                    median = maxHeap.peek();
                } else {
                    minHeap.offer(num);
                    median = minHeap.peek();
                }
            }
        }
    }
    
    public double findMedian() {
        return median;
    }
    
    
	private List<Integer> list;
    // private int size;
    /** initialize your data structure here. */
//    public MedianFinder() {
//        //this.list = new LinkedList<>(); // TLE
//    	this.list = new ArrayList<>();
//    }
    
    public void addNum1(int num) {
        int idx = findInsertionPosition(list, num);
        list.add(idx, num);// O(N)
    }
    
    // binary search insertion position (O(lgN))
    public int findInsertionPosition(List<Integer> nums, int target) {
        if (nums.size() == 0) {
            return 0;
        }
        if (nums.get(nums.size() - 1) <= target) return nums.size();
        // The following is only valid when size >= 2, that is why we need the previous statement
        int i = 0, j = nums.size() - 1;
        while (i < j) {
            int mid = i + (j - i) / 2;
            if (nums.get(mid) >= target) {
                j = mid;
            } else {
                i = mid + 1;
            }
        }
        return i;
    }
    
    public double findMedian1() {
        if (list.size() == 0) return 0;
        double median = 0;
        //Collections.sort(list); // O(nlgn)
        if (list.size() % 2 == 0) {
            int mid = list.size() / 2;
            //System.out.println(mid);
            median = (list.get(mid - 1) + list.get(mid)) / 2d;
        } else {
            median = list.get(list.size() / 2);
        }
        return median;
    }
    
    public static void main(String[] args) {
    	MedianFinder finder = new MedianFinder();
    	finder.addNum(1);
    	finder.addNum(2);
    	System.out.println(finder.findMedian());
    	finder.addNum(3);
    	System.out.println(finder.findMedian());
    }
}
