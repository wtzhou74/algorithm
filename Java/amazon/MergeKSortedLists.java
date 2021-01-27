package amazon;

import java.util.Arrays;
import java.util.PriorityQueue;

//Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
//
//Example:
//
//Input:
//[
//  1->4->5,
//  1->3->4,
//  2->6
//]
//Output: 1->1->2->3->4->4->5->6
public class MergeKSortedLists {

	class ListNode{
		int val;
		ListNode next;
		public ListNode(int val) {
			this.val = val;
		}
	}
	
	// Divide and Conquer
	// Top-Down : O(nlgN)
	// 1） 先按半递归divide, 直到最小单元可以直接处理  2） 处理最小单元   3） 返回最小单元结果，回到上一层
	public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) return null;
        if (lists.length <= 1)
            return lists[0];
        int mid = lists.length / 2; // 每次分半， 递归一直往下，直到 length = 1为止，这样就是对2个ListNode进行合并了
        ListNode left = mergeKLists(Arrays.copyOfRange(lists, 0, mid));
        ListNode right = mergeKLists(Arrays.copyOfRange(lists, mid, lists.length));
        
        return mergeTwoListNodes(left, right); // return到这里的时候，就可以对针对只有 2 个ListNode串进行合并了，求得的结果
        			// return 回去，出栈，回到上一层继续处理
    }
    
	// 合并两个
    public ListNode mergeTwoListNodes(ListNode left, ListNode right) {
        if (left == null && right == null) return null;
        if (left == null) return right;
        if (right == null) return left;
        if (left.val < right.val) {
            left.next = mergeTwoListNodes(left.next, right);
            return left;
        } else {
            right.next = mergeTwoListNodes(left, right.next);
            return right;
        }
    }
    
    
    // 直接两个两个一合并  ==> O(k*n)
    public ListNode mergeKLists1(ListNode[] lists) {
        if (lists.length == 0) return null;
        if (lists.length <= 1)
            return lists[0];
        for (int i = 0; i < lists.length - 1; i++) {
            lists[i + 1] = merge(lists[i], lists[i + 1]);
        }
        return lists[lists.length - 1];
    }
    
    public ListNode merge(ListNode left, ListNode right) {
        if (left == null && right == null) return null;
        if (left == null) return right;
        if (right == null) return left;
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;
        while (left != null && right != null) {
            if (left.val < right.val) {
                curr.next = left;
                left = left.next;
            } else {
                curr.next = right;
                right = right.next;
            }
            curr = curr.next;
        }
        curr.next = left == null ? right : left;
        
        return dummy.next;
    }
    
    
    // 借助PriorityQueue, 出Q的时候按顺序 new ListNode，并一个个连上
    public ListNode mergeKListsPq(ListNode[] lists) {
        if (lists.length == 0) return null;
        if (lists.length <= 1)
            return lists[0];
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < lists.length; i++) {
            ListNode curr = lists[i];
            while (curr != null) {
                pq.offer(curr.val);
                curr = curr.next;
            }
        }
        ListNode dummy = new ListNode(-1);
        ListNode aux = dummy;
        while (!pq.isEmpty()) {
            int val = pq.poll();
            aux.next = new ListNode(val);
            aux = aux.next;
        }
        return dummy.next;
        
//        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
//        for (int i = 0; i < lists.length; i++) {
//            ListNode head = lists[i];
//            ListNode curr = head;            
//            while (curr != null) {                
//                pq.offer(curr);
//                curr = curr.next;
//            }
//        }
//        ListNode dummy = new ListNode(-1);
//        ListNode temp = dummy;
//        while (!pq.isEmpty()) {
//            temp.next = pq.poll();
//            temp = temp.next;
//        }
//        temp.next = null; //一定要加！！！！， 否则对最后一个node,其next没变，会指向原来的next node,从而出现cycle
//        return dummy.next;
    }
}
