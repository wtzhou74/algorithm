package queueAndStack;

import java.util.Stack;

public class ImpQueueUsingStack {

	private Stack<Integer> main;//original order of items
	private Stack<Integer> helper;//reverse order to fetch elements in the way of Queue
	/** Initialize your data structure here. */
    public ImpQueueUsingStack() {
        main = new Stack<>();
        helper = new Stack<>();
    }
    
    /** Push element x to the back of queue. */
    public void push(int x) {
        main.push(x);
    }
    
    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
    	int x = 0;
        while(!main.isEmpty())
        {
        	helper.push(main.pop());
        }
        // Queue is FIFO, while Stack is LIFO
        if (!helper.isEmpty())
        {
        	x = helper.pop();
        }
        // recover original order
        while (!helper.isEmpty())
        {
        	main.push(helper.pop());
        }
        return x;
    }
    
    /** Get the front element. */
    public int peek() {
    	int x = 0;
    	while(!main.isEmpty())
        {
        	helper.push(main.pop());
        }
        if (!helper.isEmpty())
        {
        	x = helper.peek();
        }
        while (!helper.isEmpty())
        {
        	main.push(helper.pop());
        }
        return x;
    }
    
    /** Returns whether the queue is empty. */
    public boolean empty() {
        return main.isEmpty();
    }
}
