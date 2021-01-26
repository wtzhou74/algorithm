package linkedList;

import java.util.HashMap;
import java.util.Map;

//
//A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.
//
//Return a deep copy of the list.
//
// 
//
//Example 1:
//
//
//
//Input:
//{"$id":"1","next":{"$id":"2","next":null,"random":{"$ref":"2"},"val":2},"random":{"$ref":"2"},"val":1}
//
//Explanation:
//Node 1's value is 1, both of its next and random pointer points to Node 2.
//Node 2's value is 2, its next pointer points to null and its random pointer points to itself.
// 
//
//Note:
//
//You must return the copy of the given head as a reference to the cloned list.
public class CopyListWithRandomPointer {

	public class Node {
		public int val;
		public Node next;
		public Node random;
		
		public Node() {}
		
		public Node (int val, Node next, Node random) {
			this.val = val;
			this.next = next;
			this.random = random;
		}
	}
	
	// Keep record of OldNode-NewNode mapping !!!!!
	// Process NEXT and RANDOM separately
	// Time: O(N)
	// Space: O(N) ==> map
	public Node copyRandomList(Node head) {
        if (head == null) return null; // !!!Edge case
        Map<Node, Node> map = new HashMap<>(); // oldNode - newNode mapping ==> when handling next/random of original list, we know which corresponding newNode is. see the second while below
        Node newHead = new Node(head.val, null, null);
        map.put(head, newHead); // 为了在 random时候能快速找到node,借用map, 因为old linkedList的random node是已知的，这样
        						// 的话当知道 原列表中 random连的node,通过map就可以快速找到在新列表中对应的node了
        Node current = head;
        Node newCurrent = newHead;
        while (current.next != null) {
            Node node = new Node(current.next.val, null, null);//create newNode, for now, we donot know the next and random, so, null were here
            map.put(current.next, node);
            newCurrent.next = node;
            current = current.next;
            newCurrent = newCurrent.next;
        }
        current = head;
        newCurrent = newHead;
        while (current != null) {
            if (current.random != null) {
                Node node = map.get(current.random);// Find its corresponding newNode based on the oldNode, then we can random the correct newNode
                newCurrent.random = node;
            }
            current = current.next;
            newCurrent = newCurrent.next;
        }
        return newHead;
    }
	
	
	// RECURISVE solution ===> can be seen as a GRAPH
	// Time: O(N)
	// Space: O(N) ==> recursive stack
	Map<Node, Node> map = new HashMap<>();
    public Node recurisveSol(Node head) {
        if (head == null) return null;
        if (map.containsKey(head)) 
            return map.get(head);
        // If the node was not visited and does not exist, create a new one
        Node node = new Node(head.val, null, null);
        map.put(head, node);
        // recursive the remaining nodes in linkedlist
        node.next = copyRandomList(head.next);
        node.random = copyRandomList(head.random);
        
        return node;
    }
    
    
    // Alternative way to MAINTAIN nodes' RELATION  
    //		==>Copy one by one and ATTACHED to the next of existing node, hence to MAINTAIN the relationship between NEW/OLD and OLD/OLD nodes
    // ==> Space: O(1)
    public Node interweavingNodes(Node head) {
    	if (head == null) return null;
        Node current = head;
        //Node newHead = new Node(head.val, null, null);
        //Node newCurrent = newHead;
        while (current != null) { // Copy one by one and ATTACHED to the next of existing node, hence to MAINTAIN the relationship between NEW/OLD and OLD/OLD nodes
            Node node = new Node(current.val, null, null);
            Node temp = current.next;
            current.next = node;
            node.next = temp;
            current = node.next;
            
        }
        current = head;
        while (current != null) {
            if (current.random != null) {
                current.next.random = current.random.next;
            }
            current = current.next.next;
            //newCurrent = newCurrent.next.next;
        }
        current = head;
        Node newHead = current.next;
        Node newCurrent = newHead;
        while (newCurrent.next != null) {
        	// Recovering original LinkedList, we CANNOT MODIFY original LinkedList. !!!!!
        	current.next = newCurrent.next;
            current = current.next;
            
            // Get a Copy of new LinkedList
            Node temp = newCurrent.next.next;
            newCurrent.next = temp;
            newCurrent = newCurrent.next;
        }
        current.next = newCurrent.next;// the last node, current is always before newCurrent, so, here it should be set to null or newCurrent.next
        return newHead;
    }
    
    public Node buildLinkedList() {
    	Node head = new Node(1, null, null);
    	Node node = new Node(2, null, null);
    	head.random = node;
    	head.next = node;
    	node.random = node;
    	
    	return head;
    }
    
    
    public static void main(String[] args) {
    	Node head = new CopyListWithRandomPointer().buildLinkedList();
    	new CopyListWithRandomPointer().interweavingNodes(head);
    }
}
