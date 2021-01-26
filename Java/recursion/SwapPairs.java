package recursion;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class SwapPairs {

	public static class ListNode {
		int val;
		ListNode next;
		ListNode(int x) {val = x;}
	}
	
	public ListNode swapPairsSol(ListNode head) {
		if (head == null || head.next == null) return head;
		ListNode res = head.next;
		helper(head, null);
		return res;
	}
	
	public void helper(ListNode node, ListNode preNode) {
		if (node == null || node.next == null) return;
		ListNode cur = node;
		ListNode pre = preNode;
		ListNode nxt = node.next.next;
		node = node.next;
		node.next = cur;
		node.next.next = nxt;
		if (pre != null) pre.next = node;
		helper(nxt, cur);		
	}
	
	public static void main(String[] args) throws Exception {
		ListNode node = new ListNode(1);
		node.next = new ListNode(2);
		node.next.next = new ListNode(3);
	    node.next.next.next = new ListNode(4);
	    node.next.next.next.next = new ListNode(5);
	    node.next.next.next.next.next = new ListNode(6);
	    
	    //ListNode head = new SwapPairs().swapPairsSol(node);
	    ListNode head = new SwapPairs().sol2(node);
	    while ( head != null) {
	    	System.out.println(head.val);
	    	head = head.next;
	    }
	}
	
	public ListNode sol2(ListNode head) {
		if (head == null || head.next == null) return head;
		ListNode newHead = head.next;
		
		ListNode curr = head.next;
		ListNode pre = head;
		ListNode prevPrev = new ListNode(-1); //dummy node
		while (curr != null) {
			pre.next = curr.next;
			curr.next = pre;
			prevPrev.next = curr;
			// reset curr, prev, prevPrev
			if (pre.next != null) {
				curr = pre.next.next;
				pre = pre.next;
				prevPrev = prevPrev.next.next;
			} else {
				curr = null;
			}
			
		}
		return newHead;
	}
}
