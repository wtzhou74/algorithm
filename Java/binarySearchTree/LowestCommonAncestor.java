package binarySearchTree;

/**
 * Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between 
 * two nodes p and q as the lowest node in T that has both p and q as descendants
 *  (where we allow a node to be a descendant of itself).”
 * */
public class LowestCommonAncestor {

	/**
	 * Definition for a binary tree node.
	 * public class TreeNode {
	 *     int val;
	 *     TreeNode left;
	 *     TreeNode right;
	 *     TreeNode(int x) { val = x; }
	 * }
	 */
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return root;
        }
        if (root == p || root == q) {
            return root;
        }
        // "Bottom up"
        TreeNode left = lowestCommonAncestor (root.left, p, q);
        TreeNode right = lowestCommonAncestor (root.right, p, q);
        if (left != null && right != null) {
            return root;
        } else if (left != null) return left;
        else if (right != null) return right;
        
        return null;
    }
	
	// Since it is a BINARY SEARCH TREE, according to its definition
	public TreeNode lowestCommonAncestorWithBinarySearch(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return root;
        }
        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor(root.right, p, q);
        } else if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        
        return root;
    }
}
