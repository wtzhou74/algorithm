package linkedList;

import java.util.ArrayList;
import java.util.List;

//You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
//
//You may assume the two numbers do not contain any leading zero, except the number 0 itself.
//
//Example:
//
//Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
//Output: 7 -> 0 -> 8
//Explanation: 342 + 465 = 807.


// ！！！！注意对最后一个carry是否是1的的判断， 如果是1， 就要额外增加1位了， 比如 99 + 1 = 100
// 		另外，对数学运算，是从低位到高位的， 正好是数字方向的reverse order，要这里做加法不需要直接从最右边一位位往左边加就是了
public class AddTwoNumbers {

	public class ListNode {
		int val;
		ListNode next;
		
		public ListNode(int val) {
			this.val = val;
		}
	}
	
	public ListNode getL1() {
		ListNode head = null;
		ListNode node = new ListNode(0);
		head = node;
		return head;
	}
	public ListNode getL2() {
		ListNode head = null;
		ListNode node = new ListNode(9);
		head = node;
		ListNode node1 = new ListNode(1);
		head.next = node1;
		ListNode node2 = new ListNode(6);
		head.next.next = node2;
		return head;
	}
	
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) return null;
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode c1 = l1;
        ListNode c2 = l2;
        ListNode dummy = new ListNode(-1);
        ListNode c = dummy;
        int carry = 0;
        while (c1 != null && c2 != null) {
            int sum = c1.val + c2.val + carry;
            if (sum > 9) {
                carry = 1;
                c.next = new ListNode(sum % 10);
            } else {
                carry = 0;
                c.next = new ListNode(sum);
            }
            c = c.next;
            c1 = c1.next;
            c2 = c2.next;
        }
        while (c1 != null) {
            int sum = c1.val + carry;
            if (sum > 9) {
                carry = 1;
                c.next = new ListNode(sum % 10);
            } else {
                carry = 0;
                c.next = new ListNode(sum);
            }
            c = c.next;
            c1 = c1.next;
        }
        while (c2 != null) {
            int sum = c2.val + carry;
            if (sum > 9) {
                carry = 1;
                c.next = new ListNode(sum % 10);
            } else {
                carry = 0;
                c.next = new ListNode(sum);
            }
            c = c.next;
            c2 = c2.next;
        }
        if (carry == 1)
            c.next = new ListNode(1);
        return dummy.next;
    }
	
//	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//		if (l1 == null && l2 == null) return null;
//        if (l1 == null) return l2;
//        if (l2 == null) return l1;
//        ListNode curr1 = l1;
//        ListNode curr2 = l2;
//        int carry = 0;
//        ListNode head = null;
//        ListNode curr = null;
//        boolean isHead = true;
//        while (curr1 != null && curr2 != null) {
//            int total = curr1.val + curr2.val + carry;
//            carry = total / 10;
//            int remainder = total % 10;
//            ListNode node = new ListNode(remainder);
//            if (isHead) {
//                head = node;
//                curr = node;
//                isHead = false;
//            } else {
//                curr.next = node;
//                curr = node;
//            }
//            curr1 = curr1.next;
//            curr2 = curr2.next;
//            
//        }
//        
//        while (curr1 != null) {
//            if (carry != 1) {
//                curr.next = curr1;
//                curr = curr1;
//                curr1 = curr1.next;
//            } else {
//                int total = curr1.val + carry;
//                carry = total / 10;
//                int remainder = total % 10;
//                ListNode node = new ListNode(remainder);
//                curr.next = node;
//                curr = node;
//                curr1 = curr1.next;
//            }
//            
//        }
//        while (curr2 != null) {
//            if (carry == 0) {
//                curr.next = curr2;
//                curr = curr2;
//                curr2 = curr2.next;
//            } else {
//                int total = curr2.val + carry;
//                carry = total / 10;
//                int remainder = total % 10;
//                ListNode node = new ListNode(remainder);
//                curr.next = node;
//                curr = node;
//                curr2 = curr2.next;
//            }
//        }
//        if (curr1 == null && curr2 == null && carry >= 1) {
//            ListNode node = new ListNode(carry);
//            curr.next = node;
//        }
//        return head;
//    }
	
	// 把 Linked list 放到list对象了 （但这里没必要，直接按 linked list顺序一个个加就好）
	public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) return null;
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        ListNode c1 = l1;
        ListNode c2 = l2;
        while (c1 != null) {
            list1.add(c1.val);
            c1 = c1.next;
        }
        while (c2 != null) {
            list2.add(c2.val);
            c2 = c2.next;
        }
        // 不需要 reverse, 数学运算从低位到高位，正好是题目中的顺序，不是正常数字的顺序！！！
        //Collections.reverse(list1);
        //Collections.reverse(list2);
        int i = 0;
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;
        int carry = 0;
        while (i < list1.size() && i < list2.size()) {
            int add = list1.get(i) + list2.get(i) + carry;
            if (add > 9) {
                carry = 1;
                curr.next = new ListNode(add % 10);
            } else {
                carry = 0;
                curr.next = new ListNode(add);
            }
            curr = curr.next;
            i++;
        }
        if (carry == 1 && list1.size() == list2.size()) {
            curr.next = new ListNode(1);
            return dummy.next;
        }
        while (i < list1.size()) {
            if (carry == 1) {
                int add = list1.get(i) + 1;
                if (add > 9) {
                    curr.next = new ListNode(0);
                    carry = 1;
                } else {
                    curr.next = new ListNode(add);
                    carry = 0;
                }
                    
            } else {
                curr.next = new ListNode(list1.get(i));
            }
            
            curr = curr.next;
            i++;
        }
        while (i < list2.size()) {
            if (carry == 1) {
                int add = list2.get(i) + 1;
                if (add > 9) {
                    curr.next = new ListNode(0);
                    carry = 1;
                } else {
                    curr.next = new ListNode(add);
                    carry = 0;
                }
                    
            } else {
                curr.next = new ListNode(list2.get(i));
            }
            
            curr = curr.next;
            i++;
        }
        // 注意最后一个 carry 是否是1的的判断， 是1的话要增加1位！！！！
        if (carry == 1)
            curr.next = new ListNode(1);
        return dummy.next;
    }
	
	public static void main(String[] args) {
		ListNode l1 = new AddTwoNumbers().getL1();
		ListNode l2 = new AddTwoNumbers().getL2();
		new AddTwoNumbers().addTwoNumbers(l1, l2);
	}
}
