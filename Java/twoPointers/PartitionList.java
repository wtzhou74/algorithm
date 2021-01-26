package twoPointers;

//Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.
//
//You should preserve the original relative order of the nodes in each of the two partitions.
//
//Example:
//
//Input: head = 1->4->3->2->5->2, x = 3
//Output: 1->2->2->4->3->5

// 看清题目
// 仔细重链接指针
public class PartitionList {
	public class ListNode {
		int val ;
		ListNode next;
		ListNode() {}
		ListNode(int val) {this.val = val;}
		ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}
	
	// Time: O（N）
	// Space: O(1)
	public ListNode partition(ListNode head, int x) {
        if (head == null) return null;
        ListNode aux = head;
        while (aux.next != null) {
            aux = aux.next;
        }
        ListNode curr = head;
        ListNode last = aux;
        ListNode pre = new ListNode(-1, head);
        boolean first = false;
        while (curr != last) {
            //System.out.println(curr.val);
            if (curr.val >= x) {
                ListNode temp = curr;
                pre.next = temp.next;
                aux.next = temp;
                temp.next = null; //切断原有的链接，此时该点是最后一个了
                aux = temp;
                
                curr = pre.next;
            } else {
                pre = curr;
                if (!first) { //在往后移动时，head有可能需要变化，比如 4-1-3-2-5； 3； 这里 4移动后，1应该是head了
                    head = pre;
                    first = true;
                }
                //pre = curr;
                curr = curr.next;
            }
            
        }
        if (curr == last && curr.val >= x && curr.next != null) { 
        	//如果当前点就是最后一个，就没必要做了， 否则会删了最后一个，比如  1-2-3; 3
            pre.next = curr.next;
            aux.next = curr;
            curr.next = null;
        } else {
            if (!first) head = last;// 如果此时head还没找到，那这个点就是head了
            //else 
            //head = pre;
        }
        return head;
    }
	
	// 简洁算法   ===> Two Pointers （Two Dummy Nodes） ===> 把重分ListNodes， 然后把分好的两个ListNodes连在一起
	// Time O(N)
	// Space O(1) 因为这里我们只是用了2个Dummy Node （头指针）而已
	public ListNode partition2(ListNode head, int x) {
        if (head == null) return null;
        ListNode beforeHead = new ListNode(-1, null);
        ListNode afterHead = new ListNode(-1, null);
        ListNode curr = head;
        ListNode before = beforeHead;
        ListNode after = afterHead;
        while (curr != null) {
            ListNode temp = curr.next;
            if (curr.val < x) {
                before.next = curr;
                curr.next = null;
                before = before.next;
            } else {
                after.next = curr;
                curr.next = null;
                after = after.next;
            }
            curr = temp;
        }
        if (beforeHead.next != null) {
            head = beforeHead.next;
            before.next = afterHead.next;
        }
        else head = afterHead.next;
        
        
        return head;
    }
	
	public static void main(String[] args) {
		
		//new PartitionList().partition(head, x);
	}
}
