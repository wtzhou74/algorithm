package binaryTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class PreorderTraversal {

	public List<Integer> preorderTraversal(TreeNode root)
	{
		List<Integer> result = new ArrayList<Integer>();
		
		if (root == null) return result;
		// DFS solution
		// recursiveSol(root, result);
		
		// With Stack
//		Stack<TreeNode> stack = new Stack<TreeNode>();
//		stack.push(root); // push root first
//		dfsWithStack(root, result, stack);
		
		
		return result;
	}
	
	// recursion / DFS
	public void recursiveSol(TreeNode root, List<Integer> result)
	{
		if (root == null) return;
		result.add(root.val);
		recursiveSol (root.left, result);
		recursiveSol (root.right, result);
	}
	
	// DFS with stack
	// FILO
	public void dfsWithStack (List<Integer> result, Stack<TreeNode> stack)
	{
		while (!stack.isEmpty())
		{
			// record root
			TreeNode node = stack.peek();
			result.add(node.val);
			
			// pop current node
			stack.pop();
			
			// push right first for FILO
			if (node.right != null)
				stack.push(node.right);
			
			if (node.left != null)
				stack.push(node.left);
		}
		
	}
	
//	public void bfs(TreeNode root, List<Integer> result)
//	{
//		Queue<TreeNode> queue = new LinkedList<TreeNode>();
//        queue.offer(root);
//        while(!queue.isEmpty())
//        {
//            TreeNode node = queue.poll();
//            result.add(node.val);
//            if (node.left != null) queue.offer(node.left);
//            if (node.right != null) queue.offer(node.right);
//        }
//	}
}
