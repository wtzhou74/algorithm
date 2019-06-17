package queueAndStack;

import java.util.Stack;

/*Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

push(x) -- Push element x onto stack.
pop() -- Removes the element on top of the stack.
top() -- Get the top element.
getMin() -- Retrieve the minimum element in the stack.
*/
public class MinStack {

	public Stack<Integer> stack;
	public int min ;
	
	public MinStack()
	{
		stack = new Stack<>();
		min = Integer.MAX_VALUE;
	}
	
	public void push(int i)
	{
		if (i <= min)
		{
			stack.push(min);//record min of remaining elements when pop() is executed !!!
			min = i;
		}
		stack.push(i);
	}
	
	public void pop()
	{
		if(stack.peek() == min){
			stack.pop();
			min = stack.peek();//this is the min of remaining elements
		} else stack.pop();
	}
	
	public int top()
	{
		return stack.peek();
	}
	
	public int getMin()
	{
		return min;
	}
	
	/**
	 * Your MinStack object will be instantiated and called as such:
	 * MinStack obj = new MinStack();
	 * obj.push(x);
	 * obj.pop();
	 * int param_3 = obj.top();
	 * int param_4 = obj.getMin();
	 */
}
