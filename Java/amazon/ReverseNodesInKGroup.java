package amazon;

//Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
//
//k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
//
//Example:
//
//Given this linked list: 1->2->3->4->5
//
//For k = 2, you should return: 2->1->4->3->5
//
//For k = 3, you should return: 3->2->1->4->5
//
//Note:
//
//Only constant extra memory is allowed.
//You may not alter the values in the list's nodes, only nodes itself may be changed.
public class ReverseNodesInKGroup {

	public class ListNode {
		int val;
		ListNode next;
		public ListNode(int val) {
			this.val = val;
		}
	}
	public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null)
            return null;
        ListNode curr = head;
        if (k == 1)
        	return head;
        int i = 0;
        ListNode auxHead = head;
        ListNode newHead = null;
        boolean firstPre = false;
        ListNode pre = null;
        // 注意 i这里是 <= k, 这里的k是K个元素
        while (i <= k) { // curr == null还要进行最后一次， 比如 [1,2,|3,4], 到 [3,4], 
        							// curr此时==null,但还要处理 【3，4】， 但在是否进行下一轮时，i =? 0时再break,
        							//  所以这里不能添加 && curr!=null; 主要看下面的逻辑，curr是group的最后一个元素还是最后一个元素的next
        							// 这里 curr是每组最后一个元素的next
            if (i == k) {
                ListNode temp = curr;
                ListNode rHead = reverse(auxHead, curr);
                auxHead.next = temp;
                
                if (pre == null) { 
                    pre = auxHead;
                } else {
                    pre.next = rHead; // 记住上一轮的最后一个item,在处理完下一轮后，把上一轮的最后一个item
            		/// 链接到下一轮
                    pre = auxHead;// 更新pre
                }
                auxHead = temp;
                curr = temp;
                if (!firstPre) {
                    firstPre = true;
                    newHead = rHead;
                }
                if (temp == null) // 说明此时list的所有元素都处理过了，不需要开始下一轮
                	break;
                i = 0;
            } else {
            	if (curr == null) // 最后一轮开始，但不满足 k个，不处理
            		break;
                curr = curr.next;
                i++;
            }
        }
        return newHead;
    }
	
	// K个一重复也是个递归问题
	public ListNode helper(ListNode head, int k) {
        int count = 0;
        ListNode curr = head;
        while (count < k && curr != null) {
            curr = curr.next;
            count++;
        }
        
        if (count == k) {
            ListNode reverseHead = reverse(head, curr); // 反转这K个元素
            
            head.next = helper(curr, k); // 当前head的next就是下一轮回来的reverseHead
            return reverseHead; // 这边return的是reverseHead，因为这个作为(每个group)新的list的head
        }
        
        return head; // 对于 count < k 保持原样，直接return当前head就好
    }
	
	public ListNode reverseKGroup2(ListNode head, int k) {
        if (head == null || k == 1) return head;
        ListNode curr = head;
        //int count = 0;
        boolean headFound = false;
        ListNode pre = null;
        while (curr != null) {
            ListNode aux = curr;
            int count = 0;
            while (count < k && aux != null) {
                aux = aux.next;
                count++;
            }
            if (count == k) {
                ListNode reverseHead = reverse(curr, aux);                
                if (!headFound) {
                    pre = head;
                    head = reverseHead;
                    curr.next = aux;
                    headFound = true;
                } else {
                    pre.next = reverseHead;
                    curr.next = aux;
                    pre = curr;
                }
                    
            } else {
                break;
            }
            curr = aux;
        }
        
        return head;
    }
    
	// 这里返回后，tail是 null, 因为初始值 pre=null
    public ListNode reverse(ListNode head, ListNode end) {
//        ListNode pre = null;
//        ListNode curr = head;
//        while (curr != end) {
//            ListNode temp = curr.next;
//            curr.next = pre;
//            pre = curr;
//            curr = temp;
//        }
//        return pre;
        
        // 递归实现
        if (head.next == end)
            return head;
        ListNode aux = reverse(head.next, end); // 这里aux只是用来放最后一个元素， 否则return null就没了，之后没用
        head.next.next = head;  // 比如： 【a, b, c, d】 此时 递归return回来后， 
        				    //此时，head = c, c.next.next (就是 d.next) = head(c), c,d就反转了，再递归返回，head=b
        head.next = null; // 当前反转后
        return aux; //a两个两个一换，返回最后一个aux，即reverse后的head
    }
    
    public ListNode buildList() {
    	ListNode head = new ListNode(1);
    	head.next = new ListNode(2);
    	head.next.next = new ListNode(3);
    	head.next.next.next = new ListNode(4);
    	head.next.next.next.next = new ListNode(5);
    	return head;
    }
    
    public static void main(String[] args) {
    	ReverseNodesInKGroup r = new ReverseNodesInKGroup();
    	ListNode head = r.buildList();
    	r.helper(head, 4);
    }
}
