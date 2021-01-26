package linkedList;

import java.util.Comparator;
import java.util.PriorityQueue;

//Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
//
//Example:
//
//Input:
//[
//  1->4->5,
//  1->3->4,
//  2->6
//]
//Output: 1->1->2->3->4->4->5->6
public class MergeKSortedLists {

	public class ListNode {
		int val;
		ListNode next;
		
		public ListNode (int val) {
			this.val = val;
		}
	}
	
	// Time: O(N * k)
	public ListNode mergeKLists(ListNode[] lists) {
		if (lists.length == 0) return null;
        if (lists.length == 1) return lists[0];
        PriorityQueue<ListNode> heap = new PriorityQueue<>(new Comparator<ListNode>(){
            public int compare(ListNode node1, ListNode node2) {
                return node1.val - node2.val;
            }
        });
        
        // ALTERNATIVE  ==> STORE ITS VALUE
//        PriorityQueue<Integer> heap = new PriorityQueue<>(new Comparator<Integer>(){
//            public int compare(Integer val1, Integer val2) {
//                return val1 - val2;
//            }
//        });
        
        // The PREVIOUS CONNECTION-RELATION WAS NOT DESTROYED, since the heap store the whole ListNode object, its val, next are still the same!!!!!!
        // That is why we need to CREATE NEW LIST NODEs when doing heap traversal below
        for (ListNode node : lists) {
            while (node != null) {
                heap.offer(node);
                node = node.next;
            }
        }
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;
        while (!heap.isEmpty()) {
            ListNode node = new ListNode(heap.poll().val); // create new node since we are going to build a new linkedList
            current.next = node;
            current = current.next;
        }
        return dummy.next;
    }
	
	
	// Time: O(N*k)
	// Space: O(1)
	public ListNode recurisveSol(ListNode[] lists) {
		if (lists.length == 0) return null;
        if (lists.length == 1) return lists[0];
        
        ListNode first = lists[0];
        // The following can be optimized by applying D&Q solution
        for (int i = 1; i < lists.length; i++) {
            first = helper(first, lists[i]);
        }
        return first;
	}
	
	public ListNode helper(ListNode node1, ListNode node2) {
        if (node1 == null || node2 == null) return node1 == null ? node2 : node1;
        if (node1.val < node2.val) {
            node1.next = helper(node1.next, node2);
            return node1;
        } else {
            node2.next = helper(node1, node2.next);
            return node2;
        }
    }
	
	public ListNode[] buildLinkedLists() {
		ListNode[] lists = new ListNode[2];
		ListNode head1 = new ListNode(-2);
		head1.next = new ListNode(-1);
		head1.next.next = new ListNode(-1);
		head1.next.next.next = new ListNode(-1);
		ListNode head2 = null;
		lists[0] = head1;
		lists[1] = head2;
		return lists;
		
	}
	
	public static void main(String[] args) {
		ListNode[] lists = new MergeKSortedLists().buildLinkedLists();
		new MergeKSortedLists().mergeKLists(lists);
	}
}
