package twoPointers;
//
//Given a linked list, determine if it has a cycle in it.
//
//To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.
//
// 
//
//Example 1:
//
//Input: head = [3,2,0,-4], pos = 1
//Output: true
//Explanation: There is a cycle in the linked list, where tail connects to the second node.
//
//
//Example 2:
//
//Input: head = [1,2], pos = 0
//Output: true
//Explanation: There is a cycle in the linked list, where tail connects to the first node.
//
//
//Example 3:
//
//Input: head = [1], pos = -1
//Output: false
//Explanation: There is no cycle in the linked list.
public class LinkedListCycle {

	public class ListNode {
		int val;
		ListNode next;
		public ListNode(int val) {
			this.val = val;
			this.next = null;
		}
	}
	public boolean hasCycle(ListNode head) {
        if(head == null) return false;
        ListNode slow = head; // 两个人要在同一起跑线
        ListNode fast = head;
        // a如果没有环， fast的肯定先到最后一个点，所以 while中用fast, 同时也解决了 fast.next.next的判断
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }
}
