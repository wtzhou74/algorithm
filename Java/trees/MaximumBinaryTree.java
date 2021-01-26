package trees;

import java.util.LinkedList;
import java.util.Queue;

public class MaximumBinaryTree {

	public class TreeNode {
		int val;
		TreeNode right;
		TreeNode left;
		
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	public class Node {
		TreeNode node;
		int left;
		int right;
		
		public Node(TreeNode node, int left, int right) {
			this.node = node;
			this.left = left;
			this.right = right;
		}
	}
		
	// recursive solution
	public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        return helper(0, nums.length - 1, nums);
    }
    
	// Time: O(NlgN) in average case, the worst case is O(N^2) for unbalanced tree
	// Space: O(N) in worst case, and O(lgN) in average case
    public TreeNode helper(int left, int right, int[] nums) {
        if (left > right) {
            return null;
        }
        int idx = idxOfMax(left, right, nums);
        TreeNode root = new TreeNode(nums[idx]);
        
        root.left = helper(left, idx - 1, nums);
        root.right = helper(idx + 1, right, nums);
        
        return root;
    }
    
    
    // iterative solution
    // Similar to ConvertSortedArrayToBinarySearchTree.java
    public TreeNode iterativeSol(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        Queue<Node> q = new LinkedList<>();
        int idx = idxOfMax(0, nums.length - 1, nums);
        TreeNode root = new TreeNode(nums[idx]);
        Node node = new Node(root, 0, nums.length - 1);
        q.offer(node);
        while (!q.isEmpty()) {
            Node currNode = q.poll();
            int l = currNode.left;
            int r = currNode.right;
            int maxIdx = idxOfMax(l, r, nums);
            if (l < maxIdx) {
                int aux = idxOfMax(l, maxIdx - 1, nums);
                System.out.println(l + " " + maxIdx + " " + aux);
                currNode.node.left = new TreeNode(nums[aux]);
                q.offer(new Node(currNode.node.left, l, maxIdx - 1));
            }
            if (r > maxIdx) {
                int aux = idxOfMax(maxIdx + 1, r, nums);
                currNode.node.right = new TreeNode(nums[aux]);
                q.offer(new Node(currNode.node.right, maxIdx + 1, r));
            }
        }
        return root;
    }
    
    
    public int idxOfMax(int left, int right, int[] nums) {
        if (left == right) return left;
        int max = nums[left];
        int idx = left;
        for (int i = left + 1; i <= right; i++) {
            if (max < nums[i]) {
                idx = i;
                max = nums[i];
            }
        }
        return idx;
    }
    
    public static void main(String[] args) {
    	new MaximumBinaryTree().iterativeSol(new int[] {3,2,1,6,0,5});
    }
}
