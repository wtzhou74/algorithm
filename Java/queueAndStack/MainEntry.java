package queueAndStack;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MainEntry {

    public static void main(String[] args) {
    	/***********************Queue**********/
    	// BUILT-IN lib
    	// 1. Initialize a queue.
//        Queue<Integer> q = new LinkedList();
//        // 2. Get the first element - return null if queue is empty.
//        System.out.println("The first element is: " + q.peek());
//        // 3. Push new element.
//        q.offer(5);
//        q.offer(13);
//        q.offer(8);
//        q.offer(6);
//        // 4. Pop an element.
//        q.poll();
//        // 5. Get the first element.
//        System.out.println("The first element is: " + q.peek());
//        // 7. Get the size of the queue.
//        System.out.println("The size is: " + q.size());
    	
//        MyQueue q = new MyQueue();
//        q.enQueue(5);
//        q.enQueue(3);
//        if (q.isEmpty() == false) {
//            System.out.println(q.Front());
//        }
//        q.deQueue();
//        if (q.isEmpty() == false) {
//            System.out.println(q.Front());
//        }
//        q.deQueue();
//        if (q.isEmpty() == false) {
//            System.out.println(q.Front());
//        }
    	
    	// traverse of 2D => BFS/DFS, recursive solution
    	// NumberOfIslands.numberOfIslandsSol({{1,2}, {2,3}});
    	
    	// Treated the process as TREE TRAVERSE, the problem will be transferred to find the shortest path from 0000 to target.
    	// TWO DIRECTIONAL BFS???
//    	String[] s = {"8888"};
//    	OpenLock.openLockSol(s, "0009");
    	
    	// find the least number of perfect squares for given digit
    	// PerfectSquare.leastNumOfPerfectSquare(12);
    	
    	/*******************stack*************/
    	// built-in stack lib
    	// 1. Initialize a stack.
//        Stack<Integer> s = new Stack<>();
//        // 2. Push new element.
//        s.push(5);
//        s.push(13);
//        s.push(8);
//        s.push(6);
//        // 3. Check if stack is empty.
//        if (s.empty() == true) {
//            System.out.println("Stack is empty!");
//            return;
//        }
//        // 4. Pop an element.
//        s.pop();
//        // 5. Get the top element.
//        System.out.println("The top element is: " + s.peek());
//        // 6. Get the size of the stack.
//        System.out.println("The size is: " + s.size());
    	
    	//MinStack()// Record the min after doing pop();
    	
    	// ValidParentheses.validParentheseSol("(]");
    	
    	// divided by zero, OPERATOR2/OPERATOR1 when using stack.pop()
    	// String - equals; char/int - == ; Character.getNumericValue; Integer.ParseInt()
//    	String[] expression = {"4","13","5","/","+"};
//    	ReversePolishNotation.rpnSol(expression);
    	
    	// Notice the DEFINITION of NODE, loop neighbors => RECUSION (DFS)
    	// UndirectedGraphClone.cloneGraph(node);
    	
    	// BinaryTreeInorderTraversal.standardIterativeSolution(root);//standard DFS to inorder traverse binary tree
    	// BinaryTreeInorderTraversal.recursiveSolution(root); // recursive inorder traverse binary tree
    	
    	// IMPLEMENT QUEUE USING STACK
    	// ImpQueueUsingStack();
    	
    	// IMPLEMENT STACK USING QUEUE
//    	ImpStackUsingQueue s = new ImpStackUsingQueue();
//    	s.push(1);
//    	s.push(2);
//    	s.push(3);
//    	s.top();
    	
    	// the sequence of characters after poping and pushing !!!!
    	// recursion ???
    	// TODO optimized
    	// DecodeString.decodeStr("100[bc]");
    	// DecodeString.containDigit("a3bb");
    }
    
	
}
