package trees;

//Given the root node of a binary search tree (BST) and a value. You need to find the node in the BST that the node's value equals the given value. Return the subtree rooted with that node. If such node doesn't exist, you should return NULL.
//
//For example, 
//
//Given the tree:
//        4
//       / \
//      2   7
//     / \
//    1   3
//
//And the value to search: 2
//You should return this subtree:
//
//      2     
//     / \   
//    1   3
//In the example above, if we want to search the value 5, since there is no node with value 5, we should return NULL.
//
//Note that an empty tree is represented by NULL, therefore you would see the expected output (serialized tree format) as [], not null.
public class SearchInABinarySearchTree {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	// 根据BST的特性   ==> 左子树 < root.val < 右子树
	public TreeNode searchBSTConcise(TreeNode root, int val) {
        if (root == null || root.val == val) return root;
        if (root.val > val)
            return searchBSTConcise(root.left, val);
        else return searchBSTConcise(root.right, val);
    }
	
	// 顺着BST，从root往下走，依据root.val ?= val, 左移/右移
	public TreeNode searchBST1(TreeNode root, int val) {
        while(root != null && val != root.val) {
            if (val > root.val)
                root = root.right;
            else {
                root = root.left;
            }
        }
        return root;
    }
	
	public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) return null;
        preorder(root, val);
        return newRoot;
    }
    
    TreeNode newRoot = null;
    public void preorder(TreeNode root, int val) {
        if (root == null)
            return;
        searchBST(root.left, val);
        if (val == root.val) {
            newRoot = root;
            return;
        }
        searchBST(root.right, val);  
    }
}
