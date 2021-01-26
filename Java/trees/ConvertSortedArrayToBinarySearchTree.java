package trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
//
//For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
//
//Example:
//
//Given the sorted array: [-10,-3,0,5,9],
//
//One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:
//
//      0
//     / \
//   -3   9
//   /   /
// -10  5


//// BST ===> SORTED when INORDER TRAVERSE   ====> BALANCED  ===> Starting from MID
public class ConvertSortedArrayToBinarySearchTree {

	public class TreeNode {
		int val;
		TreeNode left, right;
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	public class Node {
		TreeNode node;
		int left;
		int right;
		
		public Node (TreeNode node, int left, int right) {
			this.left = left;
			this.right = right;
			this.node = node;
		}
	}
	
	
	////// BFS solution
	public TreeNode sortedArrayToBST(int[] nums) {
		if (nums == null || nums.length == 0)
			return null;
		int l = 0, r = nums.length - 1;
		TreeNode root = new TreeNode(nums[l + (r - l) / 2]);
		Node node = new Node(root, l, r);
		Queue<Node> queue = new LinkedList<>();
		queue.offer(node);
		
		while (queue.size() > 0) {
			int size = queue.size();
			while (size > 0) {
				Node t = queue.poll();
				int mid = t.left + (t.right - t.left) / 2;
				
				if (t.left < mid) {
					t.node.left = new TreeNode(nums[t.left + (mid - 1 - t.left) / 2]);
					queue.offer(new Node(t.node.left, t.left, mid - 1));
				}
				
				if (t.right > mid) {
					t.node.right = new TreeNode(nums[mid  + 1 + (t.right - mid - 1) / 2]);
					queue.offer(new Node(t.node.right, mid + 1, t.right));
					
				}
				size--;
			}

		}
		return root;
	}
	
	
	/////////////////////////////////////////////////DFS
	public TreeNode dfs(int[] nums) {
        if (nums == null || nums.length == 0)
			return null;
		int l = 0, r = nums.length - 1;
		TreeNode root = new TreeNode(nums[l + (r - l) / 2]);
		Node node = new Node(root, l, r);
		Stack<Node> stack = new Stack<>();
		stack.push(node);
        
        while (stack.size() > 0) {
            Node t = stack.pop();
            int mid = t.left + (t.right - t.left) / 2;
            t.node.val = nums[mid];  //ALL NODEs' value will be set here
            
            if (t.left < mid) {
                t.node.left = new TreeNode(0); // so, we do not set node.val here
                stack.push(new Node(t.node.left, t.left, mid - 1));
            }
            if (t.right > mid) {
                t.node.right = new TreeNode(0);
                stack.push(new Node(t.node.right, mid + 1, t.right));
            }
        }
		
		
		return root;
    }
	
	///////////////////Recursive solution
	// Time: O(N)  since we need to visit all nodes
	// Space: O(N) for the output, and O(lgN) for recursion
//	public TreeNode sortedArrayToBST(int[] nums) {
//        if (nums == null) return null;
//        return helper(0, nums.length - 1, nums);
//    }
//    
//    public TreeNode helper(int l, int r, int[] nums) {
//        if (l > r)
//            return null;
//        int mid = l + (r - l) / 2;
//        TreeNode node = new TreeNode(nums[mid]);
//        node.left = helper(l, mid - 1, nums);
//        node.right = helper(mid + 1, r, nums);
//        
//        return node;
//    }
	
	public static void main(String[] args) {
		new ConvertSortedArrayToBinarySearchTree().sortedArrayToBST(new int[] {-10, -3, 0, 5, 9});
	}
}
