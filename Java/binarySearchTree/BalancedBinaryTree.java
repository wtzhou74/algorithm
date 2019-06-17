package binarySearchTree;

public class BalancedBinaryTree {

	/**
	 * Definition of Balanced Binary Tree
	 * "a binary tree in which the depth of the two subtrees of EVERY node never differ by more than 1."
	 * */
	private boolean flag = true;
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        
        // int ldepth = calDepth(root.left, 1);
        // int rdepth = calDepth(root.right,1);
        // return Math.abs(ldepth - rdepth) <= 1; // WRONG, according to the definition of BBT (EVERY NODE)
        calDepth(root, 0);
        
        return flag;
        
    }
    
    public int calDepth(TreeNode node, int depth) {
        if (node == null) return depth;
        int ld = calDepth(node.left, depth + 1);
        int rd = calDepth(node.right, depth + 1);
        
        if (flag) {
            flag = Math.abs(ld - rd) <= 1;
        }
        
        return Math.max(ld, rd);
        
        
    }
    
    public boolean isBalancedSol2(TreeNode root) {
        if (root == null) return true;
        // root node
        int lDepth = getDepth(root.left);
        int rDepth = getDepth(root.right);
        
        if (Math.abs(lDepth - rDepth) <= 1) {

            // EVERY (CHILD) NODE， according to BBT definition
            return isBalanced(root.left) && isBalanced(root.right);
        }
        
        return false;
        
    }
    
    public int getDepth(TreeNode node) {
        if (node == null) return 0;
        int ld = getDepth(node.left);
        int rd = getDepth(node.right);
        
        // start from child, PLUS 1
        return Math.max(ld, rd) + 1;
        
    }
}
