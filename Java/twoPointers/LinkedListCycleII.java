package twoPointers;

import java.util.HashSet;
import java.util.Set;

//Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
//
//To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.
//
//Note: Do not modify the linked list.
//
// 
//
//Example 1:
//
//Input: head = [3,2,0,-4], pos = 1
//Output: tail connects to node index 1
//Explanation: There is a cycle in the linked list, where tail connects to the second node.
//
//
//Example 2:
//
//Input: head = [1,2], pos = 0
//Output: tail connects to node index 0
//Explanation: There is a cycle in the linked list, where tail connects to the first node.
//
//
//Example 3:
//
//Input: head = [1], pos = -1
//Output: no cycle
//Explanation: There is no cycle in the linked list.
//
//
// 
//
//Follow-up:
//Can you solve it without using extra space?
public class LinkedListCycleII {

	class ListNode {
		int val;
		ListNode next;
		public ListNode(int val) {
			this.val = val;
			this.next = null;
		}
	}
	public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) return null;
        ListNode slow = head;
        ListNode fast = head;//从同一起跑线开始赛跑
        while (fast != null && fast.next != null) {
            // if (slow != fast) {
            //     slow = slow.next;
            //     fast = fast.next.next;
            // } else {
            //     break;
            // }
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;
            
        }
        if (fast == null || fast.next == null) return null;
        fast = head;
        while (fast != slow) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
	
	// Space: O(n)
	public ListNode detectCycle1(ListNode head) {
        if (head == null || head.next == null) return null;
        Set<ListNode> set = new HashSet<>(); // contains方法判断node是否已被处理过
        ListNode node = head;
        while (node != null) {
            if (set.contains(node)) {
                return node;
            }
            set.add(node);
            node = node.next;
        }
        return null;
    }
}
