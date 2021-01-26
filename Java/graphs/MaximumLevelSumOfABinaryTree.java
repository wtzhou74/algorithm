package graphs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

//Given the root of a binary tree, the level of its root is 1, the level of its children is 2, and so on.
//
//Return the smallest level X such that the sum of all the values of nodes at level X is maximal.
//
// 
//
//Example 1:
//
//
//
//Input: [1,7,0,7,-8,null,null]
//Output: 2
//Explanation: 
//Level 1 sum = 1.
//Level 2 sum = 7 + 0 = 7.
//Level 3 sum = 7 + -8 = -1.
//So we return the level with the maximum sum which is level 2.
// 
//
//Note:
//
//The number of nodes in the given tree is between 1 and 10^4.
//-10^5 <= node.val <= 10^5
public class MaximumLevelSumOfABinaryTree {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	// BFS
	public int maxLevelSum(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int level = 0, res = 1;
        int max = root.val;
        while (!q.isEmpty()) {
            int size = q.size();
            level++;
            int sum = 0;
            while (size > 0) {
                TreeNode node = q.poll();
                sum += node.val;
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
                size--;
            }
            if (sum > max) {
                res = level;
                max = sum;
            }
        }
        return res;
    }
	
	Map<Integer, Integer> map = new HashMap<>();
	// DFS
    public int maxLevelSumdfs(TreeNode root) {
        if (root == null) return 0;
        helper(root, 1);
        int res = 1, max = root.val;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            //System.out.println(entry.getKey() + " " + entry.getValue());
            if (entry.getValue() > max) {
                max = entry.getValue();
                res = entry.getKey();
            }
        }
        return res;
    }
    
    // a遍历每个点，in/pre/postorder 都可以，但在处理的时候要分 level, 所以这里用了一个 map
    public void helper(TreeNode node, int level) {
        if (node == null) {
            return;
        }
        Integer curr = map.putIfAbsent(level, node.val);
        if (curr != null) map.put(level, curr + node.val);
        helper(node.left, level + 1); // 记得处理 level
        helper(node.right, level + 1);
    }
}
