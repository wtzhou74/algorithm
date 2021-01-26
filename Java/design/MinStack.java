package design;

/*Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

push(x) -- Push element x onto stack.
pop() -- Removes the element on top of the stack.
top() -- Get the top element.
getMin() -- Retrieve the minimum element in the stack.
 

Example:

MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   --> Returns -3.
minStack.pop();
minStack.top();      --> Returns 0.
minStack.getMin();   --> Returns -2.
*/
public class MinStack {

	// List<Integer> list;
    public class Node {
        int min;
        Node pre;
        int val;
        
        public Node(int val) {
            this.val = val;
            this.min = val;
        }
    }
    
    Node head = null;
    /** initialize your data structure here. */
    public MinStack() {
        // this.list = new ArrayList<>();
    }
    
    public void push(int x) {
        //list.add(x);
        if (head == null) {
            Node node = new Node(x);
            head = node;
            
        } else {
            Node node = new Node(x);
            node.pre = head;
            node.min = Math.min(head.min, x);
            head = node;
            head.min = node.min;
        }
        
    }
    
    public void pop() {
        // if (list.size() == 0) return;
        // list.remove(list.size() - 1);
        // return;

        head = head.pre;
        
    }
    
    public int top() {
        // return list.get(list.size() - 1);
        return head.val;
    }
    
    public int getMin() {
        // int min = Integer.MAX_VALUE;
        // for (int i = 0; i < list.size(); i++) {
        //     min = Math.min(min, list.get(i));
        // }
        // return min;
        if (head == null) {
            return 0;
        }
        return head.min;
    }
}
