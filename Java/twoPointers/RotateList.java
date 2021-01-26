package twoPointers;

import linkedList.RotateList.ListNode;

//Given a linked list, rotate the list to the right by k places, where k is non-negative.
//
//Example 1:
//
//Input: 1->2->3->4->5->NULL, k = 2
//Output: 4->5->1->2->3->NULL
//Explanation:
//rotate 1 steps to the right: 5->1->2->3->4->NULL
//rotate 2 steps to the right: 4->5->1->2->3->NULL
//Example 2:
//
//Input: 0->1->2->NULL, k = 4
//Output: 2->0->1->NULL
//Explanation:
//rotate 1 steps to the right: 2->0->1->NULL
//rotate 2 steps to the right: 1->2->0->NULL
//rotate 3 steps to the right: 0->1->2->NULL
//rotate 4 steps to the right: 2->0->1->NULL

public class RotateList {

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
	
	// !!!! 找到新head, 同时确认 新的tail， 然后原tail连到原head
	public ListNode rotateRight(ListNode head, int k) {
		if (head == null) return null;
        if (head.next == null) return head;
        int size = 0;
        ListNode curr = head;
        while(curr != null) {
            size++;
            curr = curr.next;
        }
        int rotate = k % size;
        if (rotate == 0) return head;
        ListNode aux = head;
        //找到新的 head
        ListNode pre = new ListNode(-1, aux);
        for (int i = 0 ; i < size - rotate; i++) {
            aux = aux.next;
            pre = pre.next;// 确定新的tail
        }
        pre.next = null; // 同时找到新的 tail,直接设为 null, 切断，这样就不需要 下面注释掉的部分去找到这个tail了
        ListNode newHead = aux;
        while (aux.next != null) {
            aux = aux.next;
        }
        // 2 此时把原尾部连到原head就好
        aux.next = head; // 连到原 链表的头, 否则 当 一开始就head.next == newHead，[1,2]，此时首尾没连上
//        while (head.next != newHead) { // 遍历到新的head为止
//            head = head.next;
//            aux = aux.next;
//            aux.next = head;
//        }
//        head.next = null; // 最后要切断，因为此时是 队尾， 要设置为 null
        return newHead;
    }
}
