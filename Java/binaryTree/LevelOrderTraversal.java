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
			int size = queue.size();// number of elements of current level
			// process each element for current level
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
		if (depth >= levels.size()) levels.add(new LinkedList<Integer>()); // CREATE A NEW LEVEL
		
		levels.get(depth).add(node.val);
		
		// The following equals the above two elements
//		List<Integer> levelNum ;
//        if (depth == levels.size()) {
//            levelNum = new ArrayList<>();
//            levels.add(levelNum);
//        } else {
//            levelNum = list.get(level);
//        }
//        
//        levelNum.add(node.val);
		///----------end
		
//		dfsTraversal(node.left, levels, depth+1);
//		dfsTraversal(node.right, levels, depth+1);
		// OR
		depth++;
		dfsTraversal(node.left, levels, depth);
		dfsTraversal(node.right, levels, depth);
		
		// TOP-DOWN : find maximum depth of path
		
	}
	
	public TreeNode generate() {
		TreeNode root = new TreeNode(3);
		root.left = new TreeNode(9);
		root.right = new TreeNode(20);
		root.right.left = new TreeNode(15);
		root.right.right = new TreeNode(7);
		return root;
	}
	
	public static void main(String[] args) {
		LevelOrderTraversal levelT = new LevelOrderTraversal();
		TreeNode root = levelT.generate();
		levelT.dfs(root);
	}
}
