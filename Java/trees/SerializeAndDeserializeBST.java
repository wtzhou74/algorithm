package trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.
//
//Design an algorithm to serialize and deserialize a binary search tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary search tree can be serialized to a string and this string can be deserialized to the original tree structure.
//
//The encoded string should be as compact as possible.
//
//Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.
public class SerializeAndDeserializeBST {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		
		public TreeNode(int val) {
			this.val = val;
		}
	}
	// Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) return "";
        // recursive way
        // deCode(root);
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        String res = "";
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node == null) {
                res += "*,";
                continue;
            }
            res += node.val + ",";
            stack.push(node.right);
            stack.push(node.left);
        }
        return res.substring(0, res.length());
    }
    
    String s = "";
    public void deCode(TreeNode node) {
        if (node == null) {
            s += "*,";
            return;
        }
        s += node.val + ",";
        deCode(node.left);
        deCode(node.right);
        
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.length() == 0) return null;
        Queue<Integer> q = new LinkedList<>();
        for (String c : data.split(",")) {
            if (c.equals("*"))
                q.offer(null);
            else {
                q.offer(Integer.valueOf(c));
            }
        }
        
        return decode(q);
    
    }
    
    public TreeNode decode(Queue<Integer> queue) {
        if (queue.isEmpty()) {
            return null;
        }
        Integer val = queue.poll();
        if (val == null) return null;
        TreeNode node = new TreeNode(val);
        node.left = decode(queue);
        node.right = decode(queue);
        
        return node;
    }
    
    
    //   ONLY FOR BST
    public String serializebBST(TreeNode root) {
        if (root == null) return "";
        //String res = "";
        
        deCodeBST(root);
        System.out.println(s);
        return s.substring(0, s.length());
    }
    
    public void deCodeBST(TreeNode node) {
        if (node == null) {
            //res += "*,"; // 利用BST 的特性， queue.peek() < min || queue.peek() > max; 这里对null的不需要特殊处理
            return;
        }
        s += node.val + ",";
        deCode(node.left);
        deCode(node.right);
        
    }

    // Decodes your encoded data to tree.
    public TreeNode deserializeBST(String data) {
        if (data.length() == 0) return null;
        Queue<Integer> q = new LinkedList<>();
        for (String c : data.split(",")) {
            // if (c.equals("*"))
            //     q.offer(null);
            // else {
                q.offer(Integer.valueOf(c));
            //}
        }
        
        // while (!queue.isEmpty()) {
        //     TreeNode node = new TreeNode(stack.poll());
        //     if (node == null) continue;
        //     node.left = queue.poll();
        // }
        return decodeBST(q, Integer.MIN_VALUE, Integer.MAX_VALUE);
    
    }
    
    public TreeNode decodeBST(Queue<Integer> queue, int min, int max) {
        if (queue.isEmpty()) {
            return null;
        }
        if (queue.peek() < min || queue.peek() > max)  { // 利用BST 的特性
            return null;
        }
        Integer val = queue.poll();
        //if (val == null) return null;
        TreeNode node = new TreeNode(val);
        node.left = decodeBST(queue, min, val);
        node.right = decodeBST(queue, val, max);
        
        return node;
    }
}
