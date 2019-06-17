package queueAndStack;

import java.util.ArrayList;
import java.util.List;

public class MyStack {

	private List<Integer> data;
	public MyStack()
	{
		data = new ArrayList<Integer>();
	}
	
	// insert an element into the stack
	public void push(int x)
	{
		data.add(x);
	}
	
	// check whether the stack is empty or not
	public boolean isEmpty()
	{
		return data.isEmpty();
	}
	
	// get the top item from the stack
	public int top()
	{
		return data.get(data.size() - 1);
	}
	
	// delete an element from stack
	public boolean pop()
	{
		if(isEmpty())
		{return false;}
		data.remove(data.size() - 1);
		return true;
	}
}
