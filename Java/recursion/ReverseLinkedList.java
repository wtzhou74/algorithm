package recursion;

import recursion.SwapPairs.ListNode;

/*Reverse a singly linked list.

Example:

Input: 1->2->3->4->5->NULL
Output: 5->4->3->2->1->NULL
Follow up:

A linked list can be reversed either iteratively or recursively. Could you implement both?
*/

public class ReverseLinkedList {

	public static class ListNode{
		int val;
		ListNode next;
		ListNode(int x){
			val = x;
		}
	}
	
	//change its previous
	// 1->2->3->null => null <- 1<-2<-3
	// Time: O(n); n is the length of linkedlist
	// Space: O(1)
	public ListNode reverseList(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		ListNode curr = head;
		ListNode prev = null;
		while (curr != null) {
			ListNode nxt = curr.next;
			curr.next = prev;
			prev = curr;
			curr = nxt;
		}
		
		return prev; // not curr, it can be null
		
	}
	
	// recursive solution
	// TOP DOWN
	// Time: O(n)
	// Space: O(n); the extra space comes from implicit stack space due to recursion, 
	//             the recursion could go to n-levels deep.
	public ListNode reverseListRecu(ListNode head) {
		if (head == null || head.next == null) 
			return head;
		ListNode p = reverseListRecu(head.next); // RECURRENCE RELATION
		/*
		 * Assume from node nk+1 to nm had been reversed and you are at node nk. n1 → …
		 * → nk-1 → Nk → nk+1 ← … ← nm We want nk+1’s next node to point to nk. So,
		 * nk.next.next = nk;
		 */
		head.next.next = head; //BASE CASE
		head.next = null; // otherwise, it will goes to infinite loop
		
		return p;
	}
	
	public static void main(String[] args) {
		ListNode node = new ListNode(1);
		node.next = new ListNode(2);
		node.next.next = new ListNode(3);
	    node.next.next.next = new ListNode(4);
	    node.next.next.next.next = new ListNode(5);
	    node.next.next.next.next.next = new ListNode(6);
	    
		ReverseLinkedList r = new ReverseLinkedList();
		ListNode head = r.reverseListRecu(node);
		while (head != null) {
			System.out.println(head.val);
			head = head.next;
		}
	}
}
