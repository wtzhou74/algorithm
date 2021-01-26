package trees;

import trees.MaximumBinaryTree.TreeNode;

public class TrimABinarySearchTree {

	public class TreeNode {
		int val;
		TreeNode right;
		TreeNode left;
		
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	// Time: O(N) visit each node at most once
	// Space: O(N) for stack of recursion
	public TreeNode trimBST(TreeNode root, int L, int R) {
        if (root == null)
            return null;
        if (root.val > R) {
            return trimBST(root.left, L, R); // 去掉右子树，只处理左子树
        } else if (root.val < L) {
            return trimBST(root.right, L, R);
        } else {
        	// root在这范围内， 把left, right连接到  处理后的 子树上， 所以要 root.left = ''; 因为此时 left, right 有可能要变，需要重新连接
            root.left = trimBST(root.left, L, R);
            root.right = trimBST(root.right, L, R);
        }
        return root;
    }
}
