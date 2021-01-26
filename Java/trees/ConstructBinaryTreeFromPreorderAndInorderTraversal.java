package trees;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

//Given preorder and inorder traversal of a tree, construct the binary tree.
//
//Note:
//You may assume that duplicates do not exist in the tree.
//
//For example, given
//
//preorder = [3,9,20,15,7]
//inorder = [9,3,15,20,7]
//Return the following binary tree:
//
//    3
//   / \
//  9  20
//    /  \
//   15   7

// Time: O(n)
// Space: O(n)
public class ConstructBinaryTreeFromPreorderAndInorderTraversal {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	// recursive solution
	public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null)
            return null;
        Map<Integer, Integer> map = new HashMap<>();// map to record index of each element
        int j = 0;
        for (int i : inorder) {
            map.put(i, j);
            j++;
        }
        return helper(preorder, 0, preorder.length - 1, map);
    }
    
    private int k = 0;
    public TreeNode helper(int[] preorder, int start, int end, Map<Integer, Integer> map) {
        if (start > end) {
            return null;
        }
        int val = preorder[k++];
        TreeNode node = new TreeNode(val);
        int idx = map.get(val);
        node.left = helper(preorder, start, idx - 1, map);
        node.right = helper(preorder, idx + 1, end, map);
        
        return node;
    }
    
    // WRONG， BS is based on sorted list
    public int findIdx(int[] inorder, int target) {
        int l = 0, r = inorder.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (inorder[mid] >= target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
    
    // iterative solution
    // preorder ===> 当前节点之后相邻的节点若排在该点之前（在inorder中）， 那该点一定是这个点的左孩子 (如果左邻接点不在左边，那就没有左孩子)， 反之， 其排在右边的点可以是“该点”的右孩子，也可以是该点“祖先”的右节点，但是，祖先的右节点一定排在该点右节点的后面 ，
    // a所以这里我们需要个一个stack, 先判断孩子的后判断祖先，因为preorder一定是祖先排在前面
    public TreeNode iterativeSol(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null
                || preorder.length == 0 || inorder.length == 0)
                 return null;
             Map<Integer, Integer> map = new HashMap<>();
             int j = 0;
             for (int item : inorder) {
                 map.put(item, j);
                 j++;
             }
             
             Stack<TreeNode> stack = new Stack<>();
             int value = preorder[0];
             TreeNode root = new TreeNode(value);
             stack.push(root);
             for (int i = 1; i < preorder.length; i++) {
            	 value = preorder[i];
            	 TreeNode node = new TreeNode(value);
            	 // check if it is the left child of node
            	 if (map.get(value) < map.get(stack.peek().val)) {
            		 stack.peek().left = node;// a不能pop, 否则stack最上面的点的右节点可能就丢失了
            	 } else {
            		 // find its right child if any
            		 TreeNode parent = null;
            		 while (!stack.isEmpty() && map.get(value) > map.get(stack.peek().val)) {
            			 parent = stack.pop();// a当它是节点的右孩子的时候，如果stack还有值，这个点一定是stack中点的左孩子，所以，该循环自然跳出，如果继续循环，说明它是当前点的祖先的父节点，是祖先的父节点当然在该点祖先的右子树上
            			 // !!!!!a对preorder, 排前面的一定是祖先！！！， 该循环实现了先找孩子的右节点，再回到祖先， 因为孩子的右节点同样是祖先的左节点
            		 }
            		 parent.right = node;// the parent can be the direct parent of node, or it can be one of the ancestor of stack.peek()
            	 }
            	 
            	 stack.push(node);
             }
             return root;
         }
    public static void main(String[] args) {
    	int[] preorder = new int[] {3, 1, 2, 4};
    	int[] inorder = new int[] {1, 2, 3, 4};
    	new ConstructBinaryTreeFromPreorderAndInorderTraversal().iterativeSol(preorder, inorder);
    }
    
}
