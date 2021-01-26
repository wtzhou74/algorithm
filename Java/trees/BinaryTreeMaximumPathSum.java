package trees;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeMaximumPathSum {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int val) {
			this.val = val;
		}
	}
	public int maxPathSum(TreeNode root) {
        if (root.left == null && root.right == null) return root.val;
        int[] max = new int[] {Integer.MIN_VALUE};
        helper(root, max); // REFERENCE Parameter, can be replaced by max = Integer.MIN_VALUE;
        return max[0];
    }
    //int max = Integer.MIN_VALUE;
    public int helper(TreeNode node, int[] max) {
        if (node == null) return 0;
        //if (node.left == null && node.right == null) return node.val;  // NODE: THIS IS NOT BASE, CANNOT RETURN HERE  !!!!!!!!!!!!!!!!!!!!
        
        int maxLeft = helper(node.left, max) + node.val; // Then we can RETURN 0 when node == null
        int maxRight = helper(node.right, max) + node.val;
        
        //int rootVal = node.val;
//        if (max < rootVal) max = rootVal;
//        if (max < maxLeft) max = maxLeft;
//        if (max < maxRight) max = maxRight;
//        if (max < rootVal + maxLeft + maxRight) max = rootVal + maxLeft + maxRight;
//        if (max < rootVal + maxLeft) max = rootVal + maxLeft;
//        if (max < rootVal + maxRight) max = rootVal + maxRight;
        
        // return max; // NOT RETURN max, should return the max(left, right)
        
        // Focus on the LARGER one
        int localMax = maxLeft > maxRight ? Math.max(maxLeft, node.val) : Math.max(maxRight, node.val); // left + root / right + root ? root
        int mergedMax = maxLeft + maxRight - node.val; // left + right + root
        
        max[0] = Math.max(localMax, max[0]);
        max[0] = Math.max(mergedMax,  max[0]);
        
        return localMax; // NOT RETURN MAX, return MAX of LEFT SUBTREE and RIGHT SUBTREE (CURRENT LEVEL), then process subtree recursively
        
    }
    
    private int max = Integer.MIN_VALUE;
    public int helper2(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = Math.max(helper2(node.left), 0);// By default, comparing with 0, if it is minus, left is 0 which means we wont add this (left) child
        int right = Math.max(helper2(node.right), 0);
        int currMax = node.val + left + right;
        max = Math.max(max, currMax);
        
        return node.val + Math.max(left, right);// For each recursion, trace the larger path
    }
    
    public TreeNode buildTree(String s) {
    	TreeNode head = new TreeNode(-30);
    	head.left = new TreeNode(9);
    	head.right = new TreeNode(20);
    	
//    	head.left.left = new TreeNode(1);
//    	head.left.right = new TreeNode(3);
    	
    	head.right.left = new TreeNode(15);
    	
    	head.right.right = new TreeNode(7);
    	
    	return head;
    	
//    	String[] strs = s.split(",");
//    	Queue<String> queue = new ArrayDeque<>();
//    	//int size = strs.length;
//    	for (int i = 0; i < strs.length; i++) {
//    		queue.offer(strs[i]);
//    	}
//    	
//    	return decode(queue);
    }
    
    public TreeNode decode(Queue<String> queue) {
    	String val = queue.poll();
    	if (val == null || val.equals("null")) return null;
    	TreeNode node = new TreeNode(Integer.valueOf(val));
    	node.left = decode(queue);
    	node.right = decode(queue);
    	
    	return node;
    }
    
    public static void main(String[] args) {
    	TreeNode head = new BinaryTreeMaximumPathSum().buildTree("1,-2,-3,1,3,-2,null,-1");
    	new BinaryTreeMaximumPathSum().helper2(head);
    }
}
