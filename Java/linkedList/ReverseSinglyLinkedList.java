package linkedList;

import linkedList.MyLinkedList.Node;

public class ReverseSinglyLinkedList {

	// iterative solution
	public static Node reverseList(Node head)
	{
		if (head == null)
        {
            return null;
        }
		Node p = head, h = head;
        while (p.next != null)
        {
        	h = p.next;
        	p.next = p.next.next;
        	h.next = head;
        	
        	head = h;
        }
        
        return head;
	}
	
	public static Node conciseSolOfReverseList(Node head)
	{
		if (head == null)
		{
			return null;
		}
		
		Node pre = null;
		Node current = head;
		Node nxt = current;
		
		while(current != null)
		{
			nxt = current.next;
			current.next = pre;
			pre = current;
			current = nxt;
		}
		
		return pre;
		
	}
	
	// recursive solution
	public static Node reverseListRecursively (Node head) {
        if (head == null) {
            //makes it easier to deal with the last node.
            return head;
        }
       return reverse_rec(head);
    }
    
    private static Node reverse_rec(Node head) {
        if (head.next == null) {
            //Found the end.
            return head;
        }
        //Recurse all the way to the end of the list.
        // Pay attention the parameter - head.next
        Node temp = reverse_rec(head.next);
        //The last node is always head.next since it will  be at the end of the  returned list.
        head.next.next = head;
		//Set a null to the end of the current node.
        //head.next = null;
        return temp;
    }
}
