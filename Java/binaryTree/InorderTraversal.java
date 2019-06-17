package binaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class InorderTraversal {

	public List<Integer> inorderTraverseSol(TreeNode root)
	{
		List<Integer> result = new ArrayList<Integer>();
		
		if (root == null) return result;
		
		// recursive solution / dfs
		// recursiveSol (root, result);
		
		// traverse without recursion
		traverseWithoutRecursion(root, result);
		return result;
		
	}
	
	public void recursiveSol(TreeNode node, List<Integer> result)
	{
		if (node == null) return;
		
		recursiveSol(node.left, result);
		result.add(node.val);
		recursiveSol(node.right, result);
	}
	
	public void traverseWithoutRecursion(TreeNode node, List<Integer> result)
	{
		Stack<TreeNode> stack = new Stack<>();
		while (node != null || !stack.isEmpty())
		{
			// traverse left tree
			while (node != null){
				stack.push(node);
				node = node.left;
			}
			
			// record the most left element
			node = stack.pop();
			result.add(node.val);
			
			// check right tree, then trace back to go through the same process as left tree
			node = node.right;
			
		}
	}
}
