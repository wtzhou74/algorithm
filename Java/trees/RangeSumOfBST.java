package trees;

import java.util.Stack;

public class RangeSumOfBST {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		
		public TreeNode(int val) {
			this.val = val;
		}
	}
	int sum = 0;
	//a对BST，preorder的结果是有序的，但本题并不需要，所以pre/in/post-order都可以，因为它们都要遍历每一个节点
	// Time: O(N); need to check all nodes
	// Space: O(H), for stack
    public int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null) {
            return 0;
        }
        preTraverse(root, L, R);
        return sum;
    }
    
    // Stack, 画图理解该过程
    public void preTraverse(TreeNode root, int L, int R) {
        if (root == null)
            return;
        preTraverse(root.left, L, R); //a左子树 到底
        if (root.val >= L && root.val <= R) {// 从right返回的时候是先到 root,因为 root先于right但晚于left
            sum += root.val;
        }
        preTraverse(root.right, L, R); //a处理每个根节点下的右子树，完了回到根节点（即dfs左子树入栈要出的点）
    }
    
    // BFS
    public int rangeSumBSTBFS(TreeNode root, int L, int R) {
        if (root == null) {
            return 0;
        }
        Stack<TreeNode> stack = new Stack<>();
        //stack.push(root.val);
        TreeNode curr = root;
        int sum = 0;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (curr.val >= L && curr.val <= R)
                sum += curr.val;
            //if (curr.right != null)
            curr = curr.right;
        }
        return sum;
    }
    public  TreeNode buildTree() {
    	TreeNode root = new TreeNode(10);
    	TreeNode left = new TreeNode(5);
    	TreeNode right = new TreeNode(15);
    	root.left = left;
    	root.right = right;
    	root.left.left = new TreeNode(3);
    	root.left.right = new TreeNode(7);
    	root.right.right = new TreeNode(18);
    	return root;
    }
    
    public static void main(String[] args) {
    	TreeNode root = new RangeSumOfBST().buildTree();
    	new RangeSumOfBST().rangeSumBSTBFS(root, 7, 15);
    }
}
