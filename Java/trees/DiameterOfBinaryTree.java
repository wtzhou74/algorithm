package trees;

//Given a binary tree, you need to compute the length of the diameter of the tree. The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.
//
//Example:
//Given a binary tree
//          1
//         / \
//        2   3
//       / \     
//      4   5    
//Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].
//
//Note: The length of path between two nodes is represented by the number of edges between them.
public class DiameterOfBinaryTree {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		
		public TreeNode(int val) {
			this.val = val;
		}
	}
	int max = Integer.MIN_VALUE;
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null)
            return 0;
        
        helper(root);
        return max;
    }
    
    // DFS - Bottom Up
    public int helper(TreeNode node) {
        if (node == null)
            return 0;
        //count++;
//        int left = 0, right = 0;
//        if (node.left != null) 
//            left = helper(node.left) + 1; // a只有接下来还有节点时候才+1
//        if (node.right != null)
//            right = helper(node.right) + 1;
//
//        max = Math.max(left + right, max);
//        
//        return Math.max(left, right);
        
        // a另一种写法
        int left = helper(node.left);
        int right = helper(node.right);
        
        max = Math.max(left + right, max);
        
        return Math.max(left, right) + 1;
    }
    
    public TreeNode generate() {
    	TreeNode root = new TreeNode(1);
    	root.left = new TreeNode(2);
    	root.right = new TreeNode(3);
    	root.left.left = new TreeNode(4);
    	root.left.right = new TreeNode(5);
    	return root;
    }
    
    public static void main(String[] args) {
    	DiameterOfBinaryTree diameter = new DiameterOfBinaryTree();
    	TreeNode root = diameter.generate();
    	diameter.diameterOfBinaryTree(root);
    }
}
