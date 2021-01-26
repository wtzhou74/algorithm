package trees;

import java.util.ArrayList;
import java.util.List;

//In a binary tree, a lonely node is a node that is the only child of its parent node. The root of the tree is not lonely because it does not have a parent node.
//
//Given the root of a binary tree, return an array containing the values of all lonely nodes in the tree. Return the list in any order.
//
// 
//
//Example 1:
//
//
//Input: root = [1,2,3,null,4]
//Output: [4]
//Explanation: Light blue node is the only lonely node.
//Node 1 is the root and is not lonely.
//Nodes 2 and 3 have the same parent and are not lonely.
//Example 2:
//
//
//Input: root = [7,1,4,6,null,5,3,null,null,null,null,null,2]
//Output: [6,2]
//Explanation: Light blue nodes are lonely nodes.
//Please remember that order doesn't matter, [2,6] is also an acceptable answer.
//Example 3:
//
//
//
//Input: root = [11,99,88,77,null,null,66,55,null,null,44,33,null,null,22]
//Output: [77,55,33,66,44,22]
//Explanation: Nodes 99 and 88 share the same parent. Node 11 is the root.
//All other nodes are lonely.
//Example 4:
//
//Input: root = [197]
//Output: []
//Example 5:
//
//Input: root = [31,null,78,null,28]
//Output: [78,28]
// 
//
//Constraints:
//
//The number of nodes in the tree is in the range [1, 1000].
//Each node's value is between [1, 10^6].


public class FindAllTheLonelyNodes {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	public List<Integer> getLonelyNodes(TreeNode root) {
        if (root == null || 
            (root.left == null && root.right == null)) return new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        recursive(root, res);
        return res;
    }
    
	// 递归的是， 找只有一个孩子节点的这个孩子  ==> 分别递归左子树，右子树，即找完左的，找右
    public void recursive(TreeNode node, List<Integer> list) {
        if (node == null)
            return;
        if (node.left == null && node.right == null) {
            return;
        }
        // 符合条件的点，放入list
        if (node.left == null) {
            list.add(node.right.val);
            //recursive(node.right, list); //不能放这里，放这里的话就只有 node.left==null才会去递归右子树
            					// 而实际还有别的情况，比如左右子树都不为 null的时候
        }
        if (node.right == null) {
            list.add(node.left.val);
            //recursive(node.left, list);
        }
        // 递归左子树，右子树
        recursive(node.right, list);
        recursive(node.left, list);
    }
}
