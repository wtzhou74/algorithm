package linkedList;

public class IntersectionOfTwoLinkedList {

	public class ListNode {
		      int val;
		      ListNode next;
		      ListNode(int x) {
		          val = x;
		          next = null;
		      }
		 }
	public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode tempA = headA;
        ListNode tempB = headB;
        
        int lenA = 0;
        int lenB = 0;
        while (tempA != null) {
            lenA++;
            tempA = tempA.next;
        }
        while (tempB != null) {
            lenB++;
            tempB = tempB.next;
        }
        
        tempA = headA;
        tempB = headB;
        while (lenA > lenB) {
            tempA = tempA.next;
            lenA--;
        }
        while (lenB > lenA) {
            tempB = tempB.next;
            lenB--;
        }
        while (tempA != tempB) {
            tempA = tempA.next;
            tempB = tempB.next;
        }
        return tempA;
    }
}
