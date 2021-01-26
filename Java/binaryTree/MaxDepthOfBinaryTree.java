package binaryTree;

public class MaxDepthOfBinaryTree {

	public int MaxDepthBottomUp(TreeNode root)
	{
		// BASE CASE
		if (root == null) return 0;
		
		// RECURRENCE RELATION
		// known left, right child
		int left = MaxDepthBottomUp(root.left);
		int right = MaxDepthBottomUp(root.right);
		
		// calculate root/ top layer
		return Math.max(left, right) + 1;
	}
	
	int answer;
	public int maxDepthTopDown (TreeNode root)
	{
		if (root == null) return 0;
		// depth starts from 1
		topDown(root, 1);
		
		return answer;
	}
	
	private void topDown(TreeNode node, int depth) {
		if (node == null) return;
		// if root is left node
		if (node.left == null && node.right == null)
			answer = Math.max(answer, depth);
		// known top node, then calculate children by passing parameter depth
		topDown(node.left, depth + 1);
		topDown(node.right, depth + 1);
	}
}
