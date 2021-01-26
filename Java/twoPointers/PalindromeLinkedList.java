package twoPointers;

import java.util.ArrayList;
import java.util.List;

//Given a singly linked list, determine if it is a palindrome.
//
//Example 1:
//
//Input: 1->2
//Output: false
//Example 2:
//
//Input: 1->2->2->1
//Output: true
//Follow up:
//Could you do it in O(n) time and O(1) space?
public class PalindromeLinkedList {
	
	public class ListNode {
		int val;
		ListNode next;
		public ListNode(int val) {
			this.val = val;
		}
	}
	public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;
        List<Integer> list = new ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            list.add(curr.val);
            curr = curr.next;
            
        }
        int i = 0, j = list.size() - 1;
        while (i < j) {
        	// !!!!! Integer 不能直接用 ==, which performs REFERENCE IDENTITY comparison
            if (Integer.compare(list.get(i), list.get(j)) != 0) {
            	// list.get(i).intValue()
            	// list.get(i).equals(list.get(j));
            	// list.get(i).compareTo(list.get(j)); == 0 or > 0 or < 0
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
	
	
	// Space: O(1)
	public boolean O1Space(ListNode head) {
        if (head == null || head.next == null) return true;
        int len = 0;
        ListNode curr = head;
        while (curr != null) {
            len++;
            curr = curr.next;
        }
        // 找中间节点，用 fast/slow双指针，两个距离相差 1， 则当 fast到最后一个， slow到第一半的最后一个
//        ListNode slow = head, fast = head;
//        ListNode firstCurr = head, nextCurr = head;
//        int count = 0;
//        while (fast.next != null && fast.next.next != null) {
//            slow = slow.next;
//            fast = fast.next.next;
//        }
        // 用 奇/偶数 判断
        int i = 0, j = len % 2 == 0 ? len / 2 : len / 2 + 1;
        ListNode firstCurr = head, nextCurr = head;
        int count = 0;
        while (count < j) {
            count++;
            nextCurr = nextCurr.next;
        }
        
        nextCurr = reverse(nextCurr);
        while (nextCurr != null) {
            if (firstCurr.val != nextCurr.val) {
                return false;
            }
            firstCurr = firstCurr.next;
            nextCurr = nextCurr.next;
        }
        return true;
    }
    
    public ListNode reverse(ListNode node) {
        ListNode pre = null;
        ListNode curr = node;
        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = pre;
            pre = curr;
            curr = temp;
        }
        return pre;
    }
    
    ///  recursive solution
    
    private ListNode frontNode;
    public boolean recursiveSol(ListNode head) {
        if (head == null || head.next == null) return true;
        frontNode = head;
        ListNode curr = head;
        return checkPalindrome(curr);
    }
    
    public boolean checkPalindrome(ListNode curr) {
        if (curr == null) return true;
        if (!checkPalindrome(curr.next)) return false;
        if (curr.val != frontNode.val) return false;
        frontNode = frontNode.next;
        
        return true;
    }
}
