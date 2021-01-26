package linkedList;

public class OddEvenLinkedList {

	public class ListNode {
		int val;
		ListNode next;
		
		public ListNode(int val) {
			this.val = val;
		}
	}
	
	public ListNode interleavingAccess(ListNode head) {
		if (head == null) return head;
        ListNode oddHead = head;
        ListNode evenHead = head.next, even = evenHead;
        while (even != null && even.next != null) {
            oddHead.next = even.next;
            oddHead = oddHead.next;
            even.next = oddHead.next;
            even = even.next;
        }
        oddHead.next = evenHead;
        
        return head;
	}
	
	
	public ListNode moveToTail(ListNode head) {
		if (head == null) return head;
        ListNode curr = head;
        ListNode tail = head;
        int size = 1;// since tail has already set to HEAD
		 while (tail.next != null) {
		     tail = tail.next;
		     size++;
		 }
		 System.out.println(size);
		 int i = 1;
		 ListNode pre = null;
		 while (curr.next != null && i <= size) {
		     if (i % 2 == 0) {
		         pre.next = curr.next;
		         tail.next = curr;
		         curr = pre.next;
		         tail = tail.next;
		         tail.next = null;// break its origial next
		     } else {
		         pre = curr;
		         curr = curr.next;
		     }
		     i++;
		 }
		 return head;
	}
	public ListNode oddEvenList(ListNode head) {
        if (head == null) return head;
        ListNode curr = head;
        ListNode oddHead = null;
        ListNode evenHead = null;
        ListNode oddCurr = null;
        ListNode evenCurr = null;
        boolean isOddHead = true;
        boolean isEvenHead = true;
        int i = 1;
        while (curr != null) {
            if (i % 2 == 0) {
                if (isEvenHead) {
                    //evenHead = curr; // !!!!!!!!WRONG!!!!! MUST CREATE NEW nodes if using extra space
                    isEvenHead = false;
                    //evenCurr = curr;
                    ListNode node = new ListNode(curr.val);
                    evenHead = node;
                    evenCurr = node;
                } else {
                    // evenCurr.next = curr;
                    // evenCurr = curr;
                    ListNode node = new ListNode(curr.val);
                    evenCurr.next = node;
                    evenCurr = node;
                }
            } else {
                if (isOddHead) {
                    //oddHead = curr;
                    isOddHead = false;
                    //oddCurr = curr;
                    ListNode node = new ListNode(curr.val);
                    oddHead = node;
                    oddCurr = node;
                } else {
                    // oddCurr.next = curr;
                    // oddCurr = curr;
                    ListNode node = new ListNode(curr.val);
                    oddCurr.next = node;
                    oddCurr = node;
                }
            }
            
            i++;
            curr = curr.next;
        }
        oddCurr.next = evenHead;
        return oddHead;
    }
}
