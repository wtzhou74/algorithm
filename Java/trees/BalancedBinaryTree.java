package trees;

import java.util.LinkedList;
import java.util.Queue;

//Given a binary tree, determine if it is height-balanced.
//
//For this problem, a height-balanced binary tree is defined as:
//
//a binary tree in which the left and right subtrees of every node differ in height by no more than 1.
//
// 
//
//Example 1:
//
//Given the following tree [3,9,20,null,null,15,7]:
//
//    3
//   / \
//  9  20
//    /  \
//   15   7
//Return true.
//
//Example 2:
//
//Given the following tree [1,2,2,3,3,null,null,4,4]:
//
//       1
//      / \
//     2   2
//    / \
//   3   3
//  / \
// 4   4
//Return false.
public class BalancedBinaryTree {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	// recursive solution
	public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        // check the diff between LEFT/RIGHT subtree of current node
        if (Math.abs(helper(root.left) - helper(root.right)) > 1)
            return false;
        else 
            return isBalanced(root.left) && isBalanced(root.right);
    }
    
    public int helper(TreeNode node) {
        if (node == null)
            return 0;
        int left = helper(node.left) + 1;
        int right = helper(node.right) + 1;
        
        return Math.max(left, right);
    }
    
    // iterative solution
    public boolean iterativeSol(TreeNode root) {
        if (root == null) {
            return true;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            int diff = Math.abs(calHeight(temp.left) - calHeight(temp.right));
            if(diff > 1) return false;
            if (temp.left != null)
                queue.offer(temp.left);
            if (temp.right != null)
                queue.offer(temp.right);
            
        }
        return true;
    }
    
    public int calHeight(TreeNode node) {
        if (node == null) return 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(node);
        int height = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            height++;
            while (size > 0) {
                TreeNode temp = q.poll();
                if (temp.left != null)
                    q.offer(temp.left);
                if (temp.right != null)
                    q.offer(temp.right);
                size--;
            }
        }
        return height;
    }
}
