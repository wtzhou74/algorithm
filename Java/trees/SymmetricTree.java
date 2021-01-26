package trees;

import trees.SameTree.TreeNode;

public class SymmetricTree {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode (int x) {
			this.val = x;
		}
	}
	public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return helper(root.left, root.right);
        
    }
	
	// 1) root is SAME
	// 2) LEFT is the mirror of RIGHT subtree
    public boolean helper(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        return left.val == right.val // root is same
            && helper(left.left, right.right) // mirror
            && helper(left.right, right.left);
    }
}
