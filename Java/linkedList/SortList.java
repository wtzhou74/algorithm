package linkedList;

import java.util.Comparator;
import java.util.PriorityQueue;

import linkedList.MergeKSortedLists.ListNode;

//Sort a linked list in O(n log n) time using constant space complexity.
//
//Example 1:
//
//Input: 4->2->1->3
//Output: 1->2->3->4
//Example 2:
//
//Input: -1->5->3->4->0
//Output: -1->0->3->4->5
public class SortList {

	public class ListNode {
		int val;
		ListNode next;
		
		public ListNode(int val) {
			this.val = val;
		}
	}
	
	//Time: O(nlgN)
	//Space: O(n)
	public ListNode sortList(ListNode head) {
        if (head == null) return null;
        PriorityQueue<Integer> heap = new PriorityQueue<>(new Comparator<Integer>(){
            public int compare(Integer val1, Integer val2) {
                return val1 - val2;
            }
        });
        ListNode current = head;
        while (current != null) {
            heap.offer(current.val);
            current = current.next;
        }
        ListNode dummy = new ListNode(-1);
        ListNode temp = dummy;
        while (heap.size() > 0) {
            ListNode node = new ListNode(heap.poll());
            temp.next = node;
            temp = node;
        }
        return dummy.next;
    }
	
	
	// Time: O(nlgn)   ==> Applying Merge Sort
	// Space: O(1)
	public ListNode mergeSort(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return head;
      
        // SLOW/FAST pointer to get the middle
//        ListNode slow = head;
//        ListNode fast = head;
//        ListNode prev = null;
//        // When fast reaches to the end, the slow is in the middle of linkedList
//        while (fast != null &&  fast.next != null) {
//        	prev = slow;
//        	slow = slow.next;
//        	fast = fast.next.next;
//        }
//        
//        prev.next = null;
//        
//        
//        ListNode first = mergeSort(head);
//        ListNode second = mergeSort(slow);
        
        // ALTERNATIVE: Calculate the size of linkedlist
        ListNode temp = head;
        int size = 0;
        while (temp != null) {
            size++;
            temp = temp.next;
        }
        ListNode firstHead = head;
        ListNode secondHead = head;
        ListNode prev = null;// keep record of the END of first half, and set its next to null
        for (int i = 0; i < size / 2; i++) {
        	prev = firstHead;
            firstHead = firstHead.next;
            secondHead = secondHead.next;
        }
        // firstHead.next = null;// WRONG!!!!!
        prev.next = null;
        
        ListNode first = mergeSort(head);
        ListNode second = mergeSort(secondHead);
        
        return helper(first, second);
    }
    
    public ListNode helper(ListNode node1, ListNode node2) {
        if (node1 == null || node2 == null) return node1 == null ? node2 : node1;
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        while (node1 != null && node2 != null) {
            if (node1.val > node2.val) {
                current.next = node2;
                node2 = node2.next;
            } else {
                current.next = node1;
                node1 = node1.next;
            }
            current = current.next;
        }
        
        while (node1 != null) {
            current.next = node1;
            node1 = node1.next;
            current = current.next;
        }
        while (node2 != null) {
            current.next = node2;
            node2 = node2.next;
            current = current.next;
        }
        return dummy.next;
    }
    
    
    public ListNode buildLinkedLists() {
		ListNode head = new ListNode(4);
		head.next = new ListNode(2);
		head.next.next = new ListNode(1);
		head.next.next.next = new ListNode(3);
		return head;
		
	}
    
    
    public static void main(String[] args) {
    	ListNode head = new SortList().buildLinkedLists();
    	new SortList().mergeSort(head);
    	
    	
    }
}
