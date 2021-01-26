package trees;
//
//Given a complete binary tree, count the number of nodes.
//
//Note:
//
//Definition of a complete binary tree from Wikipedia:
//In a complete binary tree every level, except possibly the last, is completely filled, and all nodes in the last level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.
//
//Example:
//
//Input: 
//    1
//   / \
//  2   3
// / \  /
//4  5 6
//
//Output: 6
public class CountCompleteTreeNodes {

	public class TreeNode{
		TreeNode left;
		TreeNode right;
		int val;
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	// A两次二叉查找，用于 查找后面一层的最后一个元素
	 public int countNodesViaBS(TreeNode root) {
	        if (root == null)
	            return 0;
	        // calculate depth
	        TreeNode curr = root;
	        int d = 0;
	        while (curr.left != null) {
	            curr = curr.left;
	            d++;
	        }
	        if (d == 0) return 1;
	        int count = calLastNodeIdx(root, d);
	        return (int)(Math.pow(2, d) - 1) + count;
	        
	    }
	    
	 // a确认最后一个元素 （完全二叉树，除最后一层都满，最后一层从左往右排）
	 // A二叉查找法
	    public int calLastNodeIdx(TreeNode node, int d) {
	        int i = 1, j = (int)Math.pow(2, d);
	        while (i < j) {
	            int mid = i + (j - i) / 2;
	            if (!isExist(node, mid, d)) {// 判断当前的这个数/节点是否存在 ==> 从根开始二叉查找
	                j = mid;
	            } else {
	                i = mid + 1;
	            }
	        }
	        return i;
	    }
	    
	    // a二叉查找，从根开始， 判断当前的叶子节点是否存在， see CountCompleteTreeNodes.PNG
	    // a最后一层，前一半树正好在以root为根的左子树上，右一半树正好在右子树上，以此类推
	    public boolean isExist(TreeNode node, int target, int d) {
	        int l = 0, r = (int)Math.pow(2, d) - 1;
	        while (l < r) {
	            int mid = l + (r - l) / 2;
	            if (mid >= target) {
	                node = node.left; // a树结构，看 CountCompleteTreeNodes.PNG
	                r = mid;
	            } else {
	                node = node.right;
	                l = mid + 1;
	            }
	        }
	        return node != null;
	    }
	    
	    
	// Time: O(N)  ==> 但是没有利用 Complete树的特性
	private int count = 0;
    public int countNodes(TreeNode root) {
        if (root == null)
            return 0;
        //int count = 0;
        helper(root, count);
        return count;
        
    }
    public void helper(TreeNode node, int count) {
        if (node == null) return;
        count++;
        helper(node.left, count);
        helper(node.right, count);
    }
}
