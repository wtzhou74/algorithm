package recursion;

//Given a Binary Search Tree (BST) with the root node root, return the minimum difference between the values of any two different nodes in the tree.
//
//Example :
//
//Input: root = [4,2,6,1,3,null,null]
//Output: 1
//Explanation:
//Note that root is a TreeNode object, not an array.
//
//The given tree [4,2,6,1,3,null,null] is represented by the following diagram:
//
//          4
//        /   \
//      2      6
//     / \    
//    1   3  
//
//while the minimum difference in this tree is 1, it occurs between node 1 and node 2, also between node 3 and node 2.
//Note:
//
//The size of the BST will be between 2 and 100.
//The BST is always valid, each node's value is an integer, and each node's value is different.
//This question is the same as 530: https://leetcode.com/problems/minimum-absolute-difference-in-bst/

public class MinimumDistanceBetweenBSTNodes {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	//a 借助 BST - inorder的结果是顺序的特性
	public int minDiffInBST(TreeNode root) {
        if (root == null)
            return 0;
        dfs(root);
        return min;
    }
    int min = Integer.MAX_VALUE;
    int pre = -1;
    public void dfs(TreeNode root) {
        if (root == null)
            return;
        dfs(root.left);
        if (pre != -1) {
            min = Math.min(root.val - pre, min);
        }
        pre = root.val;
        // if (pre == -1) {
        //     pre = root.val;
        // } else if (min > root.val - pre) {
        //     min = root.val - pre;
        //     pre = root.val;
        // } else {
        //     pre = root.val;
        // }
        dfs(root.right);
    }
}
