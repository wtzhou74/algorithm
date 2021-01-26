package linkedList;

import linkedList.MyLinkedList.Node;

public class RemoveNthFromEnd {
	
	public static Node optimizedRemoveNthFromEndSol(Node head, int n)
	{
		Node fast = head, slow = head;
        int count = 0;
        
        // the distance between fast and slow is the N. And the slow.next is the removed node
        while (fast.next != null){
            fast = fast.next;
            if (count == n){
                slow = slow.next;
            } else count++;
        }
        
        // remove the first one !!!!!!
        if (count < n) return head.next == null ? null : head.next;
        
        slow.next = slow.next.next == null ? null : slow.next.next;
        
        return head;
	}

	public static Node removeNthFromEndSol(Node head, int n) {
		int length = getLength(head);
        Node p = head, pre = head;
        if (p.next == null)
        {
            return null;
        }
        int i = 0;
        // the value of Lengh - n is the index of removed node
        while(i < length - n)
        {
        	pre = p;
            p = p.next;
            i++;
        }
        // remove the first one
        if (i == 0)
        {
        	return pre.next == null ? null : pre.next;
        } else {
        	pre.next = pre.next == null ? null : pre.next.next;
        }
        
        return head;
    }
    
    public static int getLength(Node head)
    {
        int length = 0;
        Node n = head;
        while(n != null)
        {
            n = n.next;
            length++;
        }
        return length;
    }
    
    public Node sol3(Node head, int n) {
        int len = 0;
        Node temp = head;
        while (temp != null) {
            len++;
            temp = temp.next;
        }
        if (n == len) head = head.next;
        else {
            // need to remove (len - n + 1)th element
            temp = head;
            int i = 0;
            while (i < len - n - 1) {
                temp = temp.next;
                i++;
            }
            temp.next = temp.next.next;
        }
                
        return head;
    }
    
}
