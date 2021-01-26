package trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.
//
//Example:
//
//Input: 3
//Output:
//[
//  [1,null,3,2],
//  [3,2,null,1],
//  [3,1,null,null,2],
//  [2,1,3],
//  [1,null,2,null,3]
//]
//Explanation:
//The above output corresponds to the 5 unique BST's shown below:
//
//   1         3     3      2      1
//    \       /     /      / \      \
//     3     2     1      1   3      2
//    /     /       \                 \
//   2     1         2                 3
public class UniqueBinarySearchTreesII {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		
		public TreeNode(int val) {
			this.val = val;
		}
	}
	public List<TreeNode> generateTrees(int n) {
        if (n == 0)
            return new ArrayList<>();
        return helper(1, n);
    }
    
    public List<TreeNode> helper(int start, int end) {
        List<TreeNode> tree = new LinkedList<>(); // local变量 是纵向的， 是用于存储一整个solution所得的所有值
        if (start > end) {
            tree.add(null);
            return tree;
        }
        
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftTree = helper(start, i - 1);
            List<TreeNode> rightTree = helper(i + 1, end);
            
            // connecting left and right trees to the root i
            for (TreeNode l : leftTree) {
                for (TreeNode r : rightTree) {
                    TreeNode node = new TreeNode(i);
                    node.right = r;
                    node.left = l;
                    
                    tree.add(node);
                }
            }
        }
        return tree;
    }
}
