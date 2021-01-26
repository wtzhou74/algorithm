package recursionII;

import java.util.Stack;

public class ValidateBinarySearchTree {

	public static class TreeNode{
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) {
			val = x;
		}
	}
	//TIME: O(n)
	//SPACE: O(n) for stack
	public boolean isValidateBST(TreeNode root) {
		if (root == null) return true;
		// Divide =>> subtree => single Node => if it is a BST => LEFT / RIGHT VALUE
		return helper(root, Long.MIN_VALUE, Long.MAX_VALUE);// 这里要用long, 因为node.val可以是Integer.min/max
								// 而且这里的MIN_VALUE对应下面的min, 因为刚开始，要让一开始node.val <= min 肯定是不成立
		
	}
	public boolean helper(TreeNode node, long min, long max) {
		// min: left value, max: right value; and the default is the MIN and MAX
		if (node == null) {
			return true;// BASE CASE
		} 
		// RELATION
		if ( node.val <= min || node.val >= max) { //比最小的小或者比最大的大
			return false;
		}
		
		// 光这样只对直接孩子节点的值判断是不够的，因为 root.val要比所有右子树的节点的值都大，不光是right.child
		//  ==> 所以这里我们要记录最小，最大值
//		if (root.left != null && root.right != null 
//				&& !(root.val > root.left.val && root.val < root.right.val))
		// DIVIDE
		boolean left = helper(node.left, min, node.val); // max becomes the "root".val
		boolean right = helper(node.right, node.val, max); // min/max是相对root.val以及左/右子树
		
		// COMBINE
		return left && right;
	}
	
	
	
	// ITERATIVE solution
	// INORDER
	// TIME: O(n): visit each node one time
	public boolean isBST(TreeNode root) {
		if (root == null) return true;
		Stack<TreeNode> s = new Stack<>();
		s.push(root);
		TreeNode curr = root;
		long min = Long.MIN_VALUE;
		while (!s.isEmpty()) {
			while (curr.left != null) {				
				s.push(curr.left);
				curr = curr.left;
			}
			TreeNode tmp = s.pop();
			if (tmp.val <= min) return false;// INORDER of BST is in ASCENDING order, LEFT->ROOT->RIGHT
			if (tmp.right != null) {
				s.push(tmp.right);
				curr = tmp.right;// HAVE TO RESET curr, if there is NO right, then CURR is still the leaf of LEFT subtree
			}
		}
		return true;
		
	}
}
