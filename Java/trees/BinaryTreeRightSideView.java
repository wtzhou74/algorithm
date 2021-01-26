package trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import trees.BalancedBinaryTree.TreeNode;


//Given a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.
//
//Example:
//
//Input: [1,2,3,null,5,null,4]
//Output: [1, 3, 4]
//Explanation:
//
//   1            <---
// /   \
//2     3         <---
// \     \
//  5     4       <---
public class BinaryTreeRightSideView {

	// BFS ==> 每一层的最后一个元素
	public List<Integer> rightSideView(TreeNode root) {
        if (root == null) return new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            while (size > 0) {
                TreeNode node = q.poll();
                if (size == 1) { // record the last elem of each level
                    list.add(node.val);
                }
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
                size--;
            }
        }
        return list;
    }
	
	public List<Integer> dfs(TreeNode root) {
        if (root == null) return new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        helper(root, list, 1);
        return list;
    }
    public void helper(TreeNode node, List<Integer> list, int level) {
        if (node == null) return;
        if (level > list.size()) { // list只存每个level的最右边孩子， 所以 list.size 跟 level 有关了
            list.add(node.val);
        }
        // 每次取最右边， 所以从右孩子还是递归
        helper(node.right, list, level + 1); // 每递归一次，层次向下加 1
        helper(node.left, list, level + 1);
    }
}
