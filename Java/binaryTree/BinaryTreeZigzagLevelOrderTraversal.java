package binaryTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).
//
//For example:
//Given binary tree [3,9,20,null,null,15,7],
//    3
//   / \
//  9  20
//    /  \
//   15   7
//return its zigzag level order traversal as:
//[
//  [3],
//  [20,9],
//  [15,7]
//]

public class BinaryTreeZigzagLevelOrderTraversal {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	// recursively traversal
	public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        helper(root, res, 0);
        return res;
	}
	public void helper(TreeNode node, List<List<Integer>> res, int level) {
        if (node == null) return;
        if (level >= res.size()) {
            res.add(new ArrayList<>());
        }
        // ODD or EVEN determines the SEQUENCE of adding elements
        if (level % 2 != 0) {
            res.get(level).add(0, node.val); // always add item to the head of list (RIGHT to LEFT)
        } else {
            res.get(level).add(node.val);
        }
        level++;
        helper(node.left, res, level);
        helper(node.right, res, level);
    }
	
	public List<List<Integer>> iterativeSol(TreeNode root) {
		 List<List<Integer>> res = new ArrayList<>();
	        if (root == null) return res;
	        Queue<TreeNode> queue = new LinkedList<>();
	        queue.offer(root);
	        int layerNumber = 1;
	        while (!queue.isEmpty()) {
	            int size = queue.size();
	            List<Integer> layer = new ArrayList<>();
	            while (size > 0) {
	                TreeNode node = queue.poll();
	                if (node.left != null) queue.offer(node.left);
	                if (node.right != null) queue.offer(node.right);
	                
	                size--;
	                // SEQUENCE of adding new item
	                if (layerNumber % 2 == 0) {
	                    layer.add(0, node.val);
	                } else layer.add(node.val);
	            }
	            res.add(layer);
	            layerNumber++;
	        }
	        return res;
	}
}
