package queueAndStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class BinaryTreeInorderTraversal {
	
	public List<Integer> inorderTraversal(TreeNode root) {
        
		List<Integer> list = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        while (root != null || !stack.empty()) {
            if (root != null) {  
                stack.push(root);
                root = root.left;  
            } else {                              
                list.add(stack.peek().val);
                root = stack.pop().right;
            }
        }
        return list;
    }

	public static List<Integer> explicitDfsSolution (TreeNode root)
	{
		List<Integer> result = new ArrayList<>();
		if (root == null)
		{
			return result;
		}
		
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		
		while (!stack.isEmpty())
		{
			TreeNode node = stack.pop();
			
			if (node.left == null && node.right == null)
			{
				result.add(node.val);
				continue;
			}
			// push right
			if (node.right != null)
			{
				stack.push(node);
			}
			
			// push root
			stack.push(node);
			
			// push left
			if (node.left != null)
			{
				stack.push(node);
			}
			
			node.left = null;
			node.right = null;
			
			
		}
		
		return result;
	}
	public static List<Integer> standardIterativeSolution(TreeNode root)
	{
		Stack<TreeNode> stack = new Stack<>();
		List<Integer> result = new ArrayList<>();
		
		if (root == null) return result;
		
		// iterate left path of the root
		while (root != null)
		{
			if (root.left != null)
			{
				// push left node to stack
				stack.push(root.left);
				root = root.left;
			}
			result.add(root.val);
			// traverse right path when left path is done, and traverse back if there is no right
			while (root.right == null)
			{
				if (!stack.isEmpty())
				{
					result.add(stack.pop().val);
				} else {
					return result;//processed all nodes
				}
				
			}
			root = root.right;
		}
		
		return result;
	}
	
	public static List<Integer> recursiveSolution(TreeNode root)
	{
		List<Integer> result = new ArrayList<>();
        if (root == null)
		{
			return result;//cannot be NULL, or recursion will throw nullexception
		}
        
		result.addAll(recursiveSolution(root.left));
		result.add(root.val);
		result.addAll(recursiveSolution(root.right));
		
		return result;
	}
}

class TreeNode{
	int val;
	TreeNode left;
	TreeNode right;
	TreeNode(int x){
		val = x;
	}
}