package recursion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.

Example:

Input: 3
Output:
[
  [1,null,3,2],
  [3,2,null,1],
  [3,1,null,null,2],
  [2,1,3],
  [1,null,2,null,3]
]
Explanation:
The above output corresponds to the 5 unique BST's shown below:

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3*/

public class UniqueBinarySearchTreesII {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) {val = x;}
	}
	
	public List<TreeNode> generateTrees(int n) {
		if (n == 0) return new ArrayList<>();
		
		// MEMOIZATION
		Map<String, List<TreeNode>> cache = new HashMap<>();
		return helper(1, n, cache);
		
	}
	
	// construct tree from l to r
	public List<TreeNode> helper(int l, int r, Map<String, List<TreeNode>> cache) {
		List<TreeNode> nodes = new ArrayList<>();
		if (l > r || l < 0) nodes.add(null);
		if (l == r) {
			nodes.add(new TreeNode(l));//node which does not have l, r child.
			return nodes;
		}
		String key = String.valueOf(l) + " " + String.valueOf(r);
		if (cache.containsKey(key)) return cache.get(key);
		for (int i = l; i <= r; i++) {
			// i is the root, and left subtree is less than right subtree
			List<TreeNode> left = helper(l, i - 1, cache);// i is the root
			List<TreeNode> right = helper(i + 1, r, cache);
			
			// COMBINATION => l:[1]  r:[2,3]
			for (TreeNode lnode: left) {
				for (TreeNode rnode: right) {
					TreeNode root = new TreeNode(i);// root node
					root.left = lnode;
					root.right = rnode;
					
					nodes.add(root);
				}
			}
			
		}
		cache.put(key, nodes);
		
		return nodes;
		
	}
	
	public static void main(String[] args) {
		List<TreeNode> res = new UniqueBinarySearchTreesII().generateTrees(3);
		for (TreeNode node:res) {
			System.out.println(node.val);
		}
	}
}
