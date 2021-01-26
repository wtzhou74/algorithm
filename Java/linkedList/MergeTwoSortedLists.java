package linkedList;

import java.util.Comparator;
import java.util.PriorityQueue;

//Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.
//
//Example:
//
//Input: 1->2->4, 1->3->4
//Output: 1->1->2->3->4->4
public class MergeTwoSortedLists {
	
	public ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) return null;
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode dummy = new ListNode(-1);
        ListNode c = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                c.next = l2;
                l2 = l2.next;
            } else {
                c.next = l1;
                l1 = l1.next;
            }
            c = c.next;
        }
        c.next = l1 == null ? l2 : l1; // 直接把剩下的拼接上，不用再while一个个判读了
        return dummy.next;
    }
	

	public class ListNode {
		 int val;
		 ListNode next;
		 ListNode(int x) { val = x; }
	}
	
	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null && l2 == null) return null;
        if (l1 == null || l2 == null) return l1 == null ? l2 : l1;
        
        PriorityQueue<Integer> heap = new PriorityQueue<>(new Comparator<Integer>(){
            public int compare(Integer val1, Integer val2) {
                return val1 - val2;
            }
        });
        
        while (l1 != null) {
            heap.offer(l1.val);
            l1 = l1.next;
        }
        while (l2 != null) {
            heap.offer(l2.val);
            l2 = l2.next;
        }
        // 构建了新链表，而不是在原有的链表上通过修改指针实现
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        while (!heap.isEmpty()) {
            ListNode node = new ListNode(heap.poll());
            current.next = node;
            current = node;
        }
        return dummy.next;
    }
	
	// recursive solution
	public ListNode recursiveSol(ListNode l1, ListNode l2) {
		if (l1 == null || l2 == null) return l1 == null ? l2 : l1;
		
		if (l1.val > l2.val) {
			l2.next = recursiveSol(l1, l2.next);// connect the next of current l2 to the result of calling function (can be from listnode1 or listnode2),
												// so, l2.next = recursiveSol();
			return l2;
		} else {
			l1.next = recursiveSol(l1.next, l2);
			return l1;
		}
	}
}
