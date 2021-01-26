package binarySearchTree;

import java.util.Stack;

//Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.
//
//Note:
//You may assume k is always valid, 1 ≤ k ≤ BST's total elements.
//
//Example 1:
//
//Input: root = [3,1,4,null,2], k = 1
//   3
//  / \
// 1   4
//  \
//   2
//Output: 1
//Example 2:
//
//Input: root = [5,3,6,2,4,null,null,1], k = 3
//       5
//      / \
//     3   6
//    / \
//   2   4
//  /
// 1
//Output: 3
//Follow up:
//What if the BST is modified (insert/delete operations) often and you need to find the kth smallest frequently? 
//How would you optimize the kthSmallest routine?
//======> Design Problem => Keep BST here with a DOUBLE LINKED LIST
public class KthSmallestElementInABST {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int val) {
			this.val = val;
		}
	} 
	
	public int kthsmallest(TreeNode root, int k) {
		TreeNode curr = root;
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		int i = 0;
		while (!stack.isEmpty()) {
			while (curr != null && curr.left != null) {
				curr = curr.left;
				stack.push(curr);
			}
			TreeNode temp = stack.pop();			
			i++;
			if (i == k) {
				return temp.val;
			}
			curr = temp.right;
			if (curr != null) {
				stack.push(temp);
			}
		}
		return -1;
	}
}
