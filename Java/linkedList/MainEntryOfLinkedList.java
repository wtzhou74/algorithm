package linkedList;

import java.util.Scanner;

import linkedList.MyLinkedList.Node;

public class MainEntryOfLinkedList {
	
	public static void main(String[] args)
	{
		// KEEP IN MIND what is HEAD, NEXT, VALUE, POINTER/REFERENCE, etc.
//		MyLinkedList myLinkedList = new MyLinkedList();
//		myLinkedList.addAtHead(1);
//		myLinkedList.addAtTail(3);
//		myLinkedList.addAtIndex(1, 2);
//		myLinkedList.get(1);
//		myLinkedList.deleteAtIndex(1);
//		myLinkedList.get(1);
		
		//TWO POINTER technique
		//CheckCycleInSinglyList.hasCycle(constructCycleLinkedList().head);
		//constructLinkedList();
		
		//Find the beginning of cycle, detect will meet with slow at the beginning of cycle
		//CheckBegOfCycleInSinglyList.detectCycle(constructCycleLinkedList().head);
		
		//RemoveNthFromEnd.removeNthFromEndSol(constructSinglyList().head, 1);
		// Implemented it in one pass // the distance between fast and slow is the N.
		// RemoveNthFromEnd.optimizedRemoveNthFromEndSol(constructSinglyList().head, 3);
		
		// reverse singly linked list
		// ReverseSinglyLinkedList.reverseList(constructSinglyList().head);// iterative solution
		// ReverseSinglyLinkedList.conciseSolOfReverseList(constructSinglyList().head);// concise iterative solution
		// ReverseSinglyLinkedList.reverseListRecursively(constructSinglyList().head);//recursive solution
	
	}
	
	public static MyLinkedList constructCycleLinkedList()
	{
		MyLinkedList myLinkedList = new MyLinkedList();
		myLinkedList.addAtHead(0);
		myLinkedList.addAtHead(1);
		myLinkedList.addAtHead(2);
		myLinkedList.addAtHead(3);
		myLinkedList.addAtHead(4);
		myLinkedList.addAtHead(5);
		
		Node p = myLinkedList.head;
		Node q = myLinkedList.head;
		q = q.next.next;
		while (p.next != null)
		{
			p = p.next;
		}
		p.next = q;
		
		return myLinkedList;
		/*Node r = myLinkedList.head;
		while (r != null)
		{
			System.out.println(r.val);
			r = r.next;
		}*/
	}
	
	public static MyLinkedList constructSinglyLinkedList()
	{
		MyLinkedList myLinkedList = new MyLinkedList();
		myLinkedList.addAtHead(0);
		myLinkedList.addAtHead(1);
		//myLinkedList.addAtHead(2);
		//myLinkedList.addAtHead(3);
		
		/*Node p = myLinkedList.head;
		while (p != null)
		{
			System.out.println(p.val);
			p = p.next;
		}*/
		return myLinkedList;
		
		
	}
	
	public static MyLinkedList constructSinglyList()
	{
		MyLinkedList myLinkedList = new MyLinkedList();
		myLinkedList.addAtHead(5);
		myLinkedList.addAtHead(4);
		myLinkedList.addAtHead(3);
		myLinkedList.addAtHead(2);
		myLinkedList.addAtHead(1);
		
		Node p = myLinkedList.head;
		
		return myLinkedList;
		
		
	}
	
	public static void printArray(int[] result)
	{
		for(int i : result) {
			System.out.println(i);
		}
	}
	
	// Type array
	public static int[] typeArray() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter an array: ");
		String line = scan.nextLine();
		String[] items = line.split(",");
		int[] nums = new int[items.length];
		for (int i = 0; i < items.length; i++)
		{
			nums[i] = Integer.parseInt(items[i].trim());
		}
		
		return nums;
	}
	
	// Type String
	public static String typeString() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter an String: ");
		String line = scan.nextLine();
		return line;
	}
	
	// Type int
	public static int typeInt() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter an Int: ");
		int input = scan.nextInt();
		return input;
	}
}
