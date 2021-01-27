package amazon;

import recursionII.ValidateBinarySearchTree.TreeNode;

//Given a binary tree, find the largest subtree which is a Binary Search Tree (BST), where largest means subtree with largest number of nodes in it.
//
//Note:
//A subtree must include all of its descendants.
//
//Example:
//
//Input: [10,5,15,1,8,null,7]
//
//   10 
//   / \ 
//  5  15 
// / \   \ 
//1   8   7
//
//Output: 3
//Explanation: The Largest BST Subtree in this case is the highlighted one.
//             The return value is the subtree's size, which is 3.
//Follow up:
//Can you figure out ways to solve it with O(n) time complexity?
public class LargestBSTSubtree {

	public static class TreeNode{
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) {
			val = x;
		}
	}
	
	// 这里一个直观的解法就是对每个node, 以此为root，先验证其是否为 BST，是的话就计算这颗子树的节点个数 => O(n^2)
	int max = Integer.MIN_VALUE;
    public int largestBSTSubtree(TreeNode root) {
        if (root == null)
            return 0;
        //int num = 0;
        helper(root);
        return max;
        
    }
    
    public void helper(TreeNode root) {
        if (root == null)
            return;
        int[] count = new int[1];//这里不能用 int,否则函数返回回来后当前int变量值还是0！！！
        if (isValidateBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE, count)) { 
            //int count = calculate(root);
            max = Math.max(count[0], max);
        } 
        helper(root.left);
        helper(root.right);
    }
    
    // 这里我们校验的时候就统计该子树的个数, 就不用下面的 calculate（）方法了
    public boolean isValidateBST(TreeNode root, int min, int max, int[] count) {
        if (root == null)
            return true;
        if (root.val <= min || root.val >= max)
            return false;
        count[0]++;
        boolean left = isValidateBST(root.left, min, root.val, count);
        boolean right = isValidateBST(root.right, root.val, max, count);
        
        return left && right;
    }
    
    
    public int calculate(TreeNode root) {
        if (root == null)
            return 0;
        // int num = 0;
        // num += calculate(root.left) + 1;
        // num += calculate(root.right) + 1; // 这里会多加 1
        int left = calculate(root.left);
        int right = calculate(root.right);
        
        //return num;
        return left + right + 1; //左右子树 + 根
    }
    
    
    // 这里进一步优化，只遍历一次树  O（N）  ==> 就需要同时记录左右子树的最小值，最大值以及当前节点的个数 ==> int[]； // 注意MIN/MAX的初始值！！！！
    public int largestBSTSubtree2(TreeNode root) {
        if (root == null)
            return 0;
        //int num = 0;
        int[] result = helper1(root);
        return result[2];
    }
    
    public int[] helper1(TreeNode root) {
        if (root == null)
            return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0}; // 这里初始值的设置是要让下面的if 通过
        // 一直往下递归只到叶子节点，再递归往回
        int[] left = helper1(root.left);
        int[] right = helper1(root.right);
        if (root.val > left[1] && root.val < right[0]) { // 如果root.val比左子树的左节点的最大值大且比右子树的最小值小，符合条件，更新min,max，以及个数
        	int min = root.left != null ? left[0] : root.val;
            int max = root.right != null ? right[1] : root.val;
            //return new int[] {left[0], Math.max(root.val, right[1]), left[2] + right[2] + 1}; // 这个是上面的两句合并的结果
            return new int[] {min, max, left[2] + right[2] + 1};
        } else {
            return new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE, Math.max(left[2], right[2])}; //不符合，重新初始化， 但之前找到的BST的节点个数要带上
        }
    }
}
