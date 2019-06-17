package linkedList;

/**
 * HEAD ITSELF IS A POINTER POINTING TO THE FIRST ELEMENT, NOT A NODE!!!
 * 
 * NODE is a user-defined data structure, inclduing value, and reference, like built-in data type, int, String, etc.
 * HEAD is just a Node pointer/reference pointing to the first node of linked list
 * NEXT - a pointer pointing to the next element
 * VAL(value) - value field, the value of current node
 * LENGTH
 * */
public class MyLinkedList {
	
//	/**
//	 * Initialize your data structure here
//	 * */
//	int length;
//	// head is just a Node pointer pointing to the first node
//	Node head;// Each element is an entity of NODE
//	// define NODE (user-defined data structure, not build-in, like int, String, etc.)
//	class Node {
//		int val;
//		Node next;
//		Node (int val) {
//			this.val = val;
//		}
//	}
//	
//	public MyLinkedList() {
//		this.length = 0;
//		this.head = null;
//	}
//	
//	/**
//	 * Get the value of the index-th node in the linked list. If the index is invalid, return -1
//	 * */
//	public int get(int index) {
//		if (index < 0 || index >= this.length) {
//			return -1;
//		} else {
//			int counter = 0;
//			// REMEMBER each element is a NODE
//			Node cur = head;
//			while(counter != index) {
//				cur = cur.next;
//				counter++;
//			}
//			return cur.val;
//		}
//	}
//	
//	/**
//	 * Add a node of value val before the first element of the linked list, after the insertion, the new node will
//	 * be the first element of linked list
//	 * */
//	public void addAtHead(int val) {
//		Node newNode = new Node(val);
//		//1. link the new node to the original this.head
//		newNode.next = this.head;
//		//2. link this.head to the new node
//		this.head = newNode;
//		//3. add 1 to the length
//		this.length++;
//	}
//	
//	/**
//	 * Append a node of value val to the last element of the linked list
//	 * */
//	public void addAtTail(int val) {
//		if (this.length == 0) {
//			addAtHead(val);
//			return;
//		}
//		Node newTail = new Node(val);
//		// newTail.next = null;
//		Node temp = head;
//		while (temp.next != null) {
//			temp = temp.next;
//		}
//		temp.next = newTail;
//		newTail.next = null;
//		// REMEMBER TO ADD ONE TO the length
//		this.length++;
//		
//	}
//	
//	/*
//	 * Add a node of value val before the index-th node in the linked list. If index equals to the length of 
//	 * the linked list, the node will be appended to the end of linked list. If the index is invalid, the node
//	 * will not be inserted
//	 * **/
//	public void addAtIndex (int index, int val) {
//		if (index < 0 || index > this.length) {
//			return;
//		}
//		if (index == this.length) {
//			addAtTail(val);
//			return;
//		}
//		Node node = new Node(val);
//		// The traverse process always starts from HEAD
//		Node cur = this.head;
//		Node pre = null;
//		int counter = 0;
//		while (counter < index) {
//			pre = cur;
//			cur = cur.next;
//			counter++;
//		}
//		node.next = cur;
//		pre.next = node;
//		this.length++;
//	}
//	
//	/**
//	 * Delete the index-th node in the linked list, if the index is valid.
//	 * */
//	public void deleteAtIndex(int index) {
//		if (index < 0 || index >= this.length) {
//			return;
//		}
//		Node cur = this.head;
//		if (index == 0) {
//			this.head = cur.next;
//			this.length--;
//			return;
//		}
//		int counter = 0;
//		Node pre = null;
//		while(counter != index) {
//			pre = cur;
//			cur = cur.next;
//			counter++;
//			
//		}
//		if (index == this.length - 1) {
//			pre.next = null;
//		} else {
//			pre.next = cur.next;
//		}
//		this.length--;
//		return;
//		
//	}
	
	// Define a node
	static class Node{
		int val;
		Node next;
		public Node(int val) {
			this.val = val;
		}
	}
	

	// Head node
	Node head = null;
	// length of linked list
	private int length = 0;
	public int length() {
		return length;
	}
	
	public MyLinkedList() {}
	
	// get the index-th Node
	public Node findAtIndex(int index) {
		if (length == 0 || index >= length) {
			return null;
		}
//		if (index <= 0) {
//			return head;
//		}
		int counter = 0;
		Node cur = head;
		while(counter < index) {
			cur = cur.next;
			counter++;
		}
		return cur;
	}
	
	// get index-th element
	public int get(int index) {
		Node cur = findAtIndex(index);
//		if (cur != null) {
//			return cur.val;
//		}
//		else return -1;
		return cur != null ? cur.val : -1;
	}
	
	// add at the index-th of the linked list
	public void addAtIndex(int index, int val) {
		if (index > length) {
			return;
		}
		// initialize a new node
		Node newNode = new Node(val);
		Node preIndexNode = findAtIndex(index - 1);
		if (preIndexNode == null) {
			// Initial value of Head is null
			this.head = newNode;
			this.length++;
			return;
		}
		if (index == 0) {
			newNode.next = this.head;//not head.next, since head is the pointer pointing the first node of linkedlist
			this.head = newNode;
			
		} 
		else {
			newNode.next = preIndexNode.next;
			preIndexNode.next = newNode;
		}
		this.length++;
		return;
	}
	
	// add one element to the head
	public void addAtHead(int val) {
		addAtIndex(0, val);
		return;
	}
	
	// add one element to the tail
	public void addAtTail(int val) {
		addAtIndex(this.length, val);
		return;
	}
	
	// delete the index-th node in LinkedList
	public void deleteAtIndex(int index) {
		if (length == 0 || index < 0 || index > length - 1) {
			return;
		}
		if (index == 0) {
			this.head = this.head.next;
		}
		Node node = findAtIndex(index - 1);
		if (node == null) {
			return;
		} else {
			node.next = node.next.next;
		}
		this.length--;
	}
}
