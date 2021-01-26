package trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

//Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
//
//According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
//
//Given the following binary tree:  root = [3,5,1,6,2,0,8,null,null,7,4]
//
//
// 
//
//Example 1:
//
//Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
//Output: 3
//Explanation: The LCA of nodes 5 and 1 is 3.
//Example 2:
//
//Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
//Output: 5
//Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
// 
//
//Note:
//
//All of the nodes' values will be unique.
//p and q are different and both values will exist in the binary tree.

public class LowestCommonAncestorOfABinaryTree {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode (int val) {
			this.val = val;
		}
	}
	// a前提是 p , q 都在树中
	
	// recursive solution
	// Time: O(N) in the worst case, we need visit all nodes
	// Space: O(N)
	public TreeNode recursiveLCA(TreeNode root, TreeNode p, TreeNode q) {
		// base case
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        // CASE 1: 若P, Q 若在当前点的左右子树，那当前点即为 LCA
        if (left != null && right != null) {
            return root;
        }
        // CASE 2, 3: P,Q 同时在左子树， 或者右子树
        return left != null ? left : right;
    }
	
	// BFS, 记录每个节点的父节点直到找到P 和 q  =》   通过父节点可以找到父节点的父节点
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val == q.val) {
            return p;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        Map<TreeNode, TreeNode> map = new HashMap<>();
        map.put(root, null);
        while (!map.containsKey(p) || !map.containsKey(q)) {
            int size = queue.size();
            while (size > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                    map.put(node.left, node);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                    map.put(node.right, node);
                }
                size--;
            }
        }
        List<TreeNode> list = new ArrayList<>();
        while (p != null) {
            list.add(p);
            p = map.get(p);
        }
        // a找到第一个共同的节点即是 LCA
        while (!list.contains(q)) {
            q = map.get(q);
        }
        return q;
    }
}
