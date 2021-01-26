package trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//Implement an iterator over a binary search tree (BST). Your iterator will be initialized with the root node of a BST.
//
//Calling next() will return the next smallest number in the BST.
//
// 
//
//Example:
//
//
//
//BSTIterator iterator = new BSTIterator(root);
//iterator.next();    // return 3
//iterator.next();    // return 7
//iterator.hasNext(); // return true
//iterator.next();    // return 9
//iterator.hasNext(); // return true
//iterator.next();    // return 15
//iterator.hasNext(); // return true
//iterator.next();    // return 20
//iterator.hasNext(); // return false
// 
//
//Note:
//
//next() and hasNext() should run in average O(1) time and uses O(h) memory, where h is the height of the tree.
//You may assume that next() call will always be valid, that is, there will be at least a next smallest number in the BST when next() is called.
public class BinarySearchTreeIterator {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}
	
	//////////////////////////////////////// Space: O(H) h is the height of tree  as required
	private Stack<TreeNode> stack;
    public BinarySearchTreeIterator(TreeNode root) {
        this.stack = new Stack<>();
        pushStack(root);
    }
    
    public void pushStack(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }
    
    /** @return the next smallest number */
    public int next() {
        if (stack.size() == 0)
            return -1;
        else {
            TreeNode node = stack.pop();
            if (node.right != null) { // ALWAYS keep H nodes in the stack, H is the height which is the longest distance from root to leaf
                pushStack(node.right); // On average, time is O(1), while in worst case, it is o(n) (iterate over a bunch of nodes)
            } 
            return node.val;
        }
    }
    
    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        if (stack.isEmpty())
            return false;
        
        return true;
    }
	
	////////////////////////////////////// Space is O(n) , N is number of nodes

//	
//	private Queue<Integer> queue;
//    public BinarySearchTreeIterator(TreeNode root) {
//        this.queue = new LinkedList<>();
//        traverseTree(root);
//    }
//    
//    
//    public void traverseTree(TreeNode root) {
//        if (root == null)
//            return;
//        traverseTree(root.left);
//        queue.offer(root.val);
//        traverseTree(root.right);
//    }
//    
//    /** @return the next smallest number */
//    public int next() {
//        if (queue.isEmpty()) return -1;
//        return queue.poll();
//    }
//    
//    /** @return whether we have a next smallest number */
//    public boolean hasNext() {
//        return queue.isEmpty() ? false : true;
//    }
}
