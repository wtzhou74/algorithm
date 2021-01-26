package recursion;

import recursion.RangeSumOfBST.TreeNode;

//Given a binary tree, find the length of the longest path where each node in the path has the same value. This path may or may not pass through the root.
//
//The length of path between two nodes is represented by the number of edges between them.
//
// 
//
//Example 1:
//
//Input:
//
//              5
//             / \
//            4   5
//           / \   \
//          1   1   5
//Output: 2
//
// 
//
//Example 2:
//
//Input:
//
//              1
//             / \
//            4   5
//           / \   \
//          4   4   5
//Output: 2
//
// 
//
//Note: The given binary tree has not more than 10000 nodes. The height of the tree is not more than 1000.
public class LongestUnivaluePath {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode (int val) {
			this.val = val;
		}
	}
	public int longestUnivaluePath(TreeNode root) {
        if (root == null)
            return 0;
        dfs(root);
        return max;
    }
    
    //int max = Integer.MIN_VALUE;
    public int dfs(TreeNode node) {
    	if (node == null)
            return 0;
        int left = 0;
        int right = 0;
        // top-down， 不好预知后续的点的情况，所以这样方式比较麻烦
        // wrong solution below
        if (node.left != null) {
            if (node.val == node.left.val)
                left = dfs(node.left) + 1;
            else
                left = dfs(node.left);
        }
        if (node.right != null) {
            if (node.val == node.right.val) {
                right = dfs(node.right) + 1;
            } else {
                right = dfs(node.right);
            }
        }
        if (node.left != null && node.right != null 
        		&& node.val == node.left.val && node.val == node.right.val)
        	max = Math.max(max, left + right); // 这种方式的后果是 某个root的左子树会加上右子树，即便两边 val不一样
        else
        	max = Math.max(left, right);
        return Math.max(left, right);
    }
    
    // Time: O(n)
    // Space: O(h)
    // bottom-up ==> 走到最低，得到 left, right, 然后返回根据 val的情况累加求length
    public int longestUnivaluePathRecursive(TreeNode root) {
        if (root == null)
            return 0;
        dfs(root);
        return max;
    }
    
    int max = Integer.MIN_VALUE;
    // bottom-up, 分别处理左右子树
    public int recursive(TreeNode node) {
        if (node == null)
            return 0;
        int left = dfs(node.left);
        int right = dfs(node.right);
        int l = 0, r = 0; // 初始值从0 开始
        if (node.left != null && node.left.val == node.val) {
            l += left + 1;//a往左边走，一样的话 +1
        }
        if (node.right != null && node.right.val == node.val) {
            r += right + 1;//a往右边走，一样的话 +1
        }
        max = Math.max(max, l + r);// bottom-up往回， max就是直接相加，如果root,left,right三者中不是都一样，left,right有一个为0
        return Math.max(l, r); // a往回走时，其当前的length是left，right中的大值，再根据root.val再做处理
    }
    
    public  TreeNode buildTree() {
    	TreeNode root = new TreeNode(4);
    	TreeNode left = new TreeNode(4);
    	TreeNode right = new TreeNode(5);
    	root.left = left;
    	root.right = right;
    	root.left.left = new TreeNode(4);
    	root.left.right = new TreeNode(4);
    	root.right.right = new TreeNode(5);
    	root.left.left.left = new TreeNode(4);
    	root.left.left.right = new TreeNode(4);
    	return root;
    }
    
    public static void main(String[] args) {
    	TreeNode root = new LongestUnivaluePath().buildTree();
    	new LongestUnivaluePath().longestUnivaluePath(root);
    }
}
