package binarySearchTree;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

//
//Given a binary search tree and a node in it, find the in-order successor of that node in the BST.
//
//The successor of a node p is the node with the smallest key greater than p.val.
public class InorderSuccessorInBST {

	//a根据BST 的特性， successor 只能在两个地方
	// 1） 如果有右孩子的情况，其successor 就在右子树的最左边的点
	// 2） 如果没有右孩子，其successor 就是他的父节点
	public TreeNode inorderSuccessor1(TreeNode root, TreeNode p) {
        if (root == null)
            return null;
        if (p.right != null) {
            TreeNode temp = p.right;
            while (temp.left != null) {
                temp = temp.left;
            }
            return temp;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (p.val == curr.val) {
                return stack.isEmpty() ? null : stack.pop();
            }
            curr = curr.right;
        }
        return null;
    }
	
	
	public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        List<TreeNode> nodes = new LinkedList<>();
        if (root == null) return null;
        inOrder(root, nodes);
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).val > p.val) {
                return nodes.get(i);
            }
        }
        return null;
    }
    
    public void inOrder (TreeNode node, List<TreeNode> list) {
        if (node == null)
            return;
        // inOrder(node.left, list);
        // list.add(node);
        // inOrder(node.right, list);
        Stack<TreeNode> stack = new Stack<>();
        stack.push(node);
        TreeNode curr = node;
        while (!stack.isEmpty()) {
            while (curr.left != null) {
                stack.push(curr.left);
                curr = curr.left;
            }
            curr = stack.pop();
            list.add(curr);
            if (curr.right != null) {
                curr = curr.right;
                stack.push(curr);
            }
            
        }
    }
}
