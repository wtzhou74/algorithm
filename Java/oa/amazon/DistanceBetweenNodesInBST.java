package oa.amazon;

//Given a list of unique integers nums, construct a BST from it (you need to insert nodes one-by-one with the given order to get the BST) and find the distance between two nodes node1 and node2. Distance is the number of edges between two nodes. If any of the given nodes does not appear in the BST, return -1.
//
//Example 1:
//
//Input: nums = [2, 1, 3], node1 = 1, node2 = 3
//Output: 2
//Explanation:
//     2
//   /   \
//  1     3

// 问题转换为： 
// 		1) 构建BST
//		2）  找到p, q点的公共ancestor
//		3） 分别算出 p，q 到 ancestor的距离
public class DistanceBetweenNodesInBST {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	// 问题拆分成多个子问题
	public int distanceBetweenNodesInBST (int[] nums, int n1, int n2) {
		if (nums.length == 0) return 0;
		if (n1 == n2) return 0;
		//构建BST
		TreeNode root = buildBST(nums, n1, n2);
		if (root == null) return -1;
		
		//找到两个点的最低公共祖先 （LCA）
		TreeNode lca = lca(root, n1, n2);
		// 分别计算p, q 到 lca点的距离，再相加，结果就是 p 到 q 的距离
		return getDistance(lca, n1) + getDistance(lca, n2);
		
	}
	
	// n1, n2带进来是要在构建的过程中 n1, n2是否都在tree里面，不在就没distance了
	public TreeNode buildBST (int[] nums, int n1, int n2) {
		boolean found1 = false;
		boolean found2 = false;
		TreeNode root = new TreeNode(nums[0]);
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] == n1) found1 = true;
			if (nums[i] == n2) found2 = true;
			root = buildProcess(root, nums[i]);
		}
		if (!found1 || !found2) return null;
		return root;
	}
	
	// 递归实现构建过程
	public TreeNode buildProcess(TreeNode root, int val) {
		if (root == null)
			return new TreeNode(val);
		if (root.val > val)
			root.left = buildProcess(root.left, val);
		else
			root.right = buildProcess(root.right, val);
		return root;
	}
	
	public TreeNode lca(TreeNode root, int n1, int n2) {
		if (root.val >= n1 && root.val <= n2
           || root.val >= n2 && root.val <= n1)
            return root;
        if (root.val > n1 && root.val > n2)
            return lca(root.left, n1, n2);
        else 
            return lca(root.right, n1, n2);
	}
	
	public int getDistance(TreeNode s, int d) {
		if (s.val == d)
			return 0;
//		int left = getDistance(s.left, d) + 1;
//		int right = getDistance(s.right, d) + 1;
		
		// 根据BST的特性进行递归
		TreeNode node = s.left;
		if (s.val < d)
			node = s.right;
		
		//return Math.min(left, right);
		return getDistance(node, d) + 1;
	}
}
