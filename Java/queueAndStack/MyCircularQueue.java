package queueAndStack;

public class MyCircularQueue {

	int size = 0;//the number of elements
	int front = -1;//index starts from 0
	int rear = -1;
	int[] elements;
	
	public MyCircularQueue(int k)
	{
		// indicate how many elements the queue can store
		elements = new int[k];
	}
	
	/** Insert an element into the circular queue. Return true if the operation is successful. */
	public boolean enQueue(int value)
	{
		if (!isFull())
		{
			// update the index of rear
			// enqueue from rear
			rear = (rear + 1) % elements.length;
			size++;
			elements[rear] = value;
			
			return true;
		}
		return false;
		
	}
	
	/** Delete an element from the circular queue. Return true if the operation is successful. */
    public boolean deQueue() {
        if (!isEmpty())
        {
        	// dequeue from start
        	front = (front + 1) % elements.length;
        	size--;
        	return true;
        }
        return false;
    }
    
    /** Get the front item from the queue. */
    public int Front() {
    	// since the front starting from -1
        return isEmpty() ? -1 : elements[front + 1];
    }
    
    /** Get the last item from the queue. */
    public int Rear() {
        return isEmpty() ? -1 : elements[rear];
    }
    
    /** Checks whether the circular queue is empty or not. */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /** Checks whether the circular queue is full or not. */
    public boolean isFull() {
    	return size == elements.length;
    	// or return ((tail + 1) % size) == head
    }
	
	
}

// For reference
class CircularQueueForReference
{
	private int[] data;
    private int head;
    private int tail;
    private int size;

    /** Initialize your data structure here. Set the size of the queue to be k. */
    public CircularQueueForReference(int k) {
        data = new int[k];
        head = -1;
        tail = -1;
        size = k;
    }
    
    /** Insert an element into the circular queue. Return true if the operation is successful. */
    public boolean enQueue(int value) {
        if (isFull() == true) {
            return false;
        }
        if (isEmpty() == true) {
            head = 0;
        }
        tail = (tail + 1) % size;
        data[tail] = value;
        return true;
    }
    
    /** Delete an element from the circular queue. Return true if the operation is successful. */
    public boolean deQueue() {
        if (isEmpty() == true) {
            return false;
        }
        if (head == tail) {
            head = -1;
            tail = -1;
            return true;
        }
        head = (head + 1) % size;
        return true;
    }
    
    /** Get the front item from the queue. */
    public int Front() {
        if (isEmpty() == true) {
            return -1;
        }
        return data[head];
    }
    
    /** Get the last item from the queue. */
    public int Rear() {
        if (isEmpty() == true) {
            return -1;
        }
        return data[tail];
    }
    
    /** Checks whether the circular queue is empty or not. */
    public boolean isEmpty() {
        return head == -1;
    }
    
    /** Checks whether the circular queue is full or not. */
    public boolean isFull() {
        return ((tail + 1) % size) == head;
    }
}
