package twoPointers;

//Given a linked list, remove the n-th node from the end of list and return its head.
//
//Example:
//
//Given linked list: 1->2->3->4->5, and n = 2.
//
//After removing the second node from the end, the linked list becomes 1->2->3->5.
//Note:
//
//Given n will always be valid.
//
//Follow up:
//
//Could you do this in one pass?
public class RemoveNthNodeFromEndOfList {

	public class ListNode {
		int val;
		ListNode next;
		ListNode() {}
		ListNode(int val) {
			this.val = val;
		}
		ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}
	public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) return null;
        int size = 0;
        ListNode curr = head;
        while (curr != null) { //这个步骤可通过 利用 n 值来简化，如如下方法2
            size++;
            curr = curr.next;
        }
        if (size == n) return head.next;
        int i = 0;
        ListNode pre = new ListNode(-1, head);
        curr = head;
        while (i <= size - n) {
            if (i == size - n) {
                pre.next = curr.next;
                break;
            } else {
                pre = curr;
                curr = curr.next;
            }
            i++;
        }
        return head;
    }
	
	// One pass ===> 让前后指针始终保持 n 的距离，这样当前面的指针到最后，后面的指针.next就是要删的元素 （画图看）
	// Two-pointers
	public ListNode removeNthFromEnd1(ListNode head, int n) {
        if (head == null) return null;
        //int size = 0;
        //ListNode curr = head;
        ListNode dummy1 = new ListNode(-1, head);
        ListNode dummy2 = new ListNode(-1, head);
        int i = 0;
        while (i <= n) {
            dummy1 = dummy1.next;
            i++;
        }
        if (dummy1 == null) return head.next;
        while (dummy1 != null) {
            dummy1 = dummy1.next;
            dummy2 = dummy2.next;
        }
        dummy2.next = dummy2.next.next;
        
        return head;
    }
}
