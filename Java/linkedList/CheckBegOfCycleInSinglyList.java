package linkedList;

import java.util.HashMap;
import java.util.Map;

import linkedList.MyLinkedList.Node;

public class CheckBegOfCycleInSinglyList {

	public static Node detectCycle(Node head)
	{
		Node slow = head;
		Node fast = head;
		
		//Detect if there is a cycle
        while(fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) {
                break;
            }
        }
        
        //no cycle
        
        if(fast == null || fast.next == null) {
            return null;
        }
        
        Node detect = head;
        
        /**
         * Detect node will meet with slow node at the beginning of cycle !!!!
         * **/
        while(detect != slow) {
            slow = slow.next;
            detect = detect.next;
        }
        return detect;
	}
	
	// relatively time-consuming for extra space
	public static Node hasCycle(Node head) {
		Map<Node, Boolean> visitedNodes = new HashMap<Node, Boolean>();
		Node p = head;
        while (p != null && p.next != null)
        {
            boolean visited = true;
            visitedNodes.put(p, visited);
            p = p.next;
            if (visitedNodes.get(p) != null && visitedNodes.get(p))
            {
                return p;
            }
        }
        
        return null;
	}
}
