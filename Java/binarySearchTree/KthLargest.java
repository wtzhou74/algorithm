package binarySearchTree;

import java.util.PriorityQueue;

/**
 * Design a class to find the kth largest element in a stream. Note that it is the kth largest (THE LARGEST ONE is the 1ST LARGEST element)
 *  element in the sorted order, not the kth distinct element.
 * */
public class KthLargest {

	PriorityQueue<Integer> q;// priorityQueue is a special queue will sort elements automatically by natural ordering. Comparator will be defined for complex comparison.
    int k;
    public KthLargest(int k, int[] nums) {
        this.k = k;
        q = new PriorityQueue<Integer>(k);//the capacity is 11 by default
        // the size will be incremented by 2x.
        for (int a :nums) {
            q.add(a);// call the below add()
        }
    }
    
    
    public int add(int val) {
        q.offer(val);
        if (q.size() > k) {
            q.poll();// remove the k+1th largest element
        }
     
        
        return q.peek();
    }
}
