package binarySearchTree;

import java.util.Arrays;

/**
 * Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
 * For this problem, a height-balanced binary tree is defined as a binary tree 
 * in which the depth of the two subtrees of every node never differ by more than 1.
 * */
public class ConvertSortedArrayToBST {
	public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        return createTree(nums, 0, nums.length - 1);
    }
    
    public TreeNode createTree(int[] nums, int left, int right) {
        if (left > right) return null;// END CONDITION
        int mid = (left + right) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = createTree(nums, left, mid - 1);
        node.right = createTree(nums, mid + 1, right);
        
        return node;
    }
    
    // Usage of Arrays.copyOfRange(array, start, end)
    public TreeNode sortedArrayToBSTSol2(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        int i = 0, j = nums.length - 1, mid = (i + j) >>> 1;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = sortedArrayToBST(Arrays.copyOfRange(nums, i, mid));
        node.right = sortedArrayToBST(Arrays.copyOfRange(nums, mid + 1, j + 1));// end should PLUS 1
        
        return node;
        
    }
}
