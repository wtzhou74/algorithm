package trees;

//Given the root of a binary tree with N nodes, each node in the tree has node.val coins, and there are N coins total.
//
//In one move, we may choose two adjacent nodes and move one coin from one node to another.  (The move may be from parent to child, or from child to parent.)
//
//Return the number of moves required to make every node have exactly one coin.
//
// 
//
//Example 1:
//
//
//
//Input: [3,0,0]
//Output: 2
//Explanation: From the root of the tree, we move one coin to its left child, and one coin to its right child.
//Example 2:
//
//
//
//Input: [0,3,0]
//Output: 3
//Explanation: From the left child of the root, we move two coins to the root [taking two moves].  Then, we move one coin from the root of the tree to the right child.
//Example 3:
//
//
//
//Input: [1,0,2]
//Output: 2
//Example 4:
//
//
//
//Input: [1,0,0,null,3]
//Output: 4
// 
//
//Note:
//
//1<= N <= 100
//0 <= node.val <= N


// Note： Similar to DiameterOfBinaryTree.java
public class DistributeCoinsInBinaryTree {
	
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int val) {
			this.val = val;
		}
	}

	int total = 0;
    public int distributeCoins(TreeNode root) {
        helper(root);
        return total;
    }
    
    // DFS - Bottom up  ==> 递归到叶子节点时候， 叶子节点只需要留 1个coin，==> 上移/下移  ==> 剩余数量 excess
    // = Math.abs(node.val - 1), 假如叶子节点为0， 则需要从上往下移1位， 每次 只能移动一位，故 - 1.
    public int helper(TreeNode node) {
        if (node == null) return 0;
        int left = helper(node.left);
        int right = helper(node.right);
        
        total += Math.abs(left) + Math.abs(right);
        
        return node.val + left + right - 1;// 每次只能移动 1 位，故减去1， 剩余 的excess = node.val + left + right - 1, 然后递归往回走（up）
    
        // a另一种写法
//        if (node == null) return 0;
//        int left = 0, right = 0;
//        if (node.left != null) {
//            left = helper(node.left) - 1; // 每移动一次，先减 1，当然前提是在可移动的情况下
//        }
//        if (node.right != null) {
//            right = helper(node.right) - 1;
//        }
//        
//        total += Math.abs(left) + Math.abs(right);
//        return node.val + left + right; // 这里就不需要再 减 1 了， excess = node.val + left +right
    }
}
