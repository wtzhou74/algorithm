package queueAndStack;

import java.util.LinkedList;
import java.util.Queue;

public class ImpStackUsingQueue {

	private Queue<Integer> mainQueue;
	/** Initialize your data structure here. */
    public ImpStackUsingQueue() {
    	mainQueue = new LinkedList<>();
    }
    
    /** Push element x onto stack. */
    public void push(int x) {
        mainQueue.offer(x);
    }
    
    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        Queue<Integer> helper = new LinkedList<>();
        int x;
        // The following code is not working since FIFO, you cannot reverse the order by
        // assign it to helper queue like impQueueUsingStack which is FILO.
        // using SIZE instead
//        while(!mainQueue.isEmpty())
//        {
//        	helper.offer(mainQueue.poll());
//        }
//        if (helper.isEmpty())
//        {
//        	x = helper.poll();
//        }
//        while (!helper.isEmpty())
//        {
//        	mainQueue.offer(helper.poll());
//        }
        int size = mainQueue.size();
        for(int i = 0; i < size - 1; i++)
        {
        	helper.offer(mainQueue.poll());
        }
        x = mainQueue.poll();
        while (!helper.isEmpty())
        {
        	mainQueue.offer(helper.poll());
        }
        return x;
    }
    
    /** Get the top element. */
    public int top() {
    	Queue<Integer> helper = new LinkedList<>();
        int x = 0;
        int size = mainQueue.size();// !!!!!!! donot code like following commented since mainqueue size changes as mainQueue.poll()
        // for(int i = 0; i < mainQueue.size() - 1; i++)
        for (int i = 0; i < size - 1; i++)
        {
        	helper.offer(mainQueue.poll());
        }
        x = mainQueue.poll();
        while (!helper.isEmpty())
        {
        	mainQueue.offer(helper.poll());
        }
        mainQueue.offer(x);
        return x;
    }
    
    /** Returns whether the stack is empty. */
    public boolean empty() {
        return mainQueue.isEmpty();
    }
}
