package trees;

//A binary tree is univalued if every node in the tree has the same value.
//
//Return true if and only if the given tree is univalued.
//
// 
//
//Example 1:
//
//
//Input: [1,1,1,1,1,null,1]
//Output: true
//Example 2:
//
//
//Input: [2,2,2,5,2]
//Output: false
// 
//
//Note:
//
//The number of nodes in the given tree will be in the range [1, 100].
//Each node's value will be an integer in the range [0, 99].

public class UnivaluedBinaryTree {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		
		public TreeNode(int val) {
			this.val = val;
		}
	}
	public boolean isUnivalTree(TreeNode root) {
        
        if (root == null) return true;
        boolean left = true, right = true; //初始值为true， 因为当root.left为null， root.right为null,return true;
        if (root.left != null)
        	// 当前的 boolean结果布尔运算于后面递归的的结果
            left = (root.val == root.left.val) && isUnivalTree(root.left);
        if (root.right != null)
            right = (root.val == root.right.val) && isUnivalTree(root.right);
        
        return left && right;
    }
}
