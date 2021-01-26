package recursion;

/*Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.

Example:

Input: 1->2->4, 1->3->4
Output: 1->1->2->3->4->4
*/
public class MergeTwoLists {

	public static class ListNode {
		int val;
		ListNode next;
		ListNode(int x) {
			val = x;
		}
	}
	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

		if (l1 == null) return l2;
	    if (l2 == null) return l1;
	    
	    // Recurrence relation l1(i) < l2(j) => merge(i.next, j)
	    if (l1.val < l2.val) {
	    	l1.next = mergeTwoLists(l1.next, l2);// returns l1.next; new "head"
	    	return l1;
	    } else {
	    	l2.next = mergeTwoLists(l1, l2.next);
            return l2;
	    }
	}
	
	public ListNode dummyNodeSol(ListNode l1, ListNode l2) {
		ListNode res = new ListNode(-1);
        ListNode temp = res;
        //ListNode start_l1 = l1;
        //ListNode start_l2 = l2;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                res = appendList(res, l1.val);// res = l1.next ... will change l1/l2 !!!!!
                l1 = l1.next;
            } else if (l1.val > l2.val) {
            	res = appendList(res, l2.val);
                l2 = l2.next;
            } else {
            	res = appendList(appendList(res, l1.val), l1.val);
                l1 = l1.next;
                l2 = l2.next;
            }
        }
        if (l1 != null) res.next = l1;
        if (l2 != null) res.next = l2;
        
        return temp.next;
	}
	
	// Create
	public ListNode appendList(ListNode node, int val) {
		ListNode newNode = new ListNode(val);
		node.next = newNode;
		return node.next;
	}
	public static void main(String[] args) {
		ListNode head = new ListNode(1);
	    ListNode head1 = new ListNode(2);
	    ListNode head2 = new ListNode(4);

	    ListNode head3 = new ListNode(1);
	    ListNode head4 = new ListNode(3);
	    ListNode head5 = new ListNode(4);

	    head.next = head1;
	    head1.next = head2;

	    head3.next = head4;
	    head4.next = head5;
	    
	    ListNode res = new MergeTwoLists().dummyNodeSol(head, head3);
	    while (res != null) {
	    	System.out.println(res.val);
	    	res =res.next;
	    }
	}
    
}
