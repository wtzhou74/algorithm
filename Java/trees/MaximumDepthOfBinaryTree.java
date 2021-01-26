package trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//Given a binary tree, find its maximum depth.
//
//The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
//
//Note: A leaf is a node with no children.
//
//Example:
//
//Given binary tree [3,9,20,null,null,15,7],
//
//    3
//   / \
//  9  20
//    /  \
//   15   7
//return its depth = 3.
public class MaximumDepthOfBinaryTree {

	public class TreeNode {
		int val;
		TreeNode left, right;
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	// Time: O(N) n: the number of tree
	// Space: worst case for unbalanced tree : O(n), best case: O(lgN) balanced tree
	public int recursiveSol(TreeNode root) {
        if (root == null)
            return 0;
        //int max = 0;
        int max_left = recursiveSol(root.left) + 1;
        int max_right = recursiveSol(root.right) + 1;
        
        return Math.max(max_left, max_right);
    }
	
	
	/// Iterative => stack
	public int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        Stack<TreeNode> stack = new Stack<>();
        Stack<Integer> depths = new Stack<>();
        stack.push(root);
        depths.push(1);
        
        int currDepth = 0, depth = 0;
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            currDepth = depths.pop();
            if (curr != null) {
                depth = Math.max(depth, currDepth);
                stack.push(curr.left);
                stack.push(curr.right);
                depths.push(currDepth + 1);
                depths.push(currDepth + 1);
            }
        }
        
        return depth;
    }
	
	// BFS
	public int bfs(TreeNode root) {
        if (root == null)
            return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            level++;
            while (size > 0) {
                TreeNode temp = queue.poll();
                if (temp.left != null)
                    queue.offer(temp.left);
                if (temp.right != null)
                    queue.offer(temp.right);
                size--;
            }
        }
        return level;
    }
}
