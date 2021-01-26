package trees;

import java.util.LinkedList;
import java.util.Queue;

import trees.MaximumDepthOfBinaryTree.TreeNode;

//Invert a binary tree.
//
//Example:
//
//Input:
//
//     4
//   /   \
//  2     7
// / \   / \
//1   3 6   9
//Output:
//
//     4
//   /   \
//  7     2
// / \   / \
//9   6 3   1
public class InvertBinaryTree {

	public class TreeNode {
		int val;
		TreeNode left, right;
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	// TIME： O（N） ： n is the number of nodes
	// Space: O(h)
	public TreeNode invertTree(TreeNode root) {
        if (root == null)
            return null;
//        TreeNode node = new TreeNode(root.val);
//        node.left = invertTree(root.right);
//        node.right = invertTree(root.left);
//        
//        return node;
        
        // ALTERNATIVE: 直接在原树上调整
        TreeNode left = invertTree(root.left); // 左子树
        TreeNode right = invertTree(root.right); // 右子树
        root.right = left;//整个子树一起挪
        root.left = right;
        
        return root;
    }
	
	public TreeNode iterativeSol(TreeNode root) {
        if (root == null)
            return null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            //int size = queue.size(); // 不需要考虑具体哪个层， 无需记录 size
            TreeNode curr = queue.poll();
            // swap left, right
            TreeNode temp = curr.left;
            curr.left = curr.right;
            curr.right = temp;
            
            // process their corresponding subtrees
            if (curr.left != null) queue.add(curr.left);
            if (curr.right != null) queue.add(curr.right);
        }
        return root;
    }
}
