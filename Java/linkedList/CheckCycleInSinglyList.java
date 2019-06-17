package linkedList;

import linkedList.MyLinkedList.Node;

public class CheckCycleInSinglyList {
	
	class ListNode {
		      int val;
		      ListNode next;
		      ListNode(int x) {
		          val = x;
		         next = null;
		      }
	}

	// TWO Pointer technique
	// Fast-slow pointer, the fast pointer will meet with slow pointer finally if there is a cycle
	public boolean hasCycle(ListNode head) {
/*        if (head == null || head.next == null)
        { return false;}*/
        
        ListNode slowP = head, fastP = slowP;
        while (fastP != null && fastP.next != null)
        {
        	if (fastP.next == null)
        	{return false;}
            slowP = slowP.next;
            fastP = fastP.next.next;
            if (fastP == slowP){
                return true;
            }
        }
        
        return false;
    }
	
	
	public static boolean hasCycle(Node head) {
		Node p = head,pre = head;
    	while(p != null && p.next != null){
    		if (p.next == head) return true;
    		p = p.next;
    		// set next of all visited nodes to HEAD
    		pre.next = head;
    		pre = p;
    	}
        return false;
    }
	
}
