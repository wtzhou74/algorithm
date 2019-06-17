package binaryTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given a binary tree, return the level order traversal of its
 *  nodes' values. (ie, from left to right, level by level).
 *  [
 *		[3],
 *		[9,20],
 *		[15,7]
 *  ]
 *  
 * */
public class LevelOrderTraversal {

	public List<List<Integer>> LevelOrderSol(TreeNode root)
	{
		List<List<Integer>> levels = new ArrayList<List<Integer>> ();
		if (root == null) return levels;
		
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		
		queue.offer(root);
		while (!queue.isEmpty()){
			List<Integer> level = new ArrayList<Integer>();
			int size = queue.size();// number of elements of each level
			// process each element for each level
			for (int i = 0; i < size; i++)
			{
				TreeNode node = queue.poll();
				level.add(node.val);
				
				if (node.left != null) queue.offer(node.left);
				if (node.right != null) queue.offer(node.right);
				
			}
			
			levels.add(level);
		}
		
		return levels;
	}
	
	// DFS / recursion
	public List<List<Integer>> dfs(TreeNode root)
	{
		List<List<Integer>> levels = new ArrayList<List<Integer>>();
		if (root == null) return levels;
		
		dfsTraversal(root, levels, 0);
		
		return levels;
	}
	
	public void dfsTraversal (TreeNode node, List<List<Integer>> levels, int depth)
	{
		if (node == null) return;
		if (depth >= levels.size()) levels.add(new LinkedList<Integer>());
		
		levels.get(depth).add(node.val);
		
		dfsTraversal(node.left, levels, depth+1);
		dfsTraversal(node.right, levels, depth+1);
		
		// TOP-DOWN : find maximum depth of path
	}
}
