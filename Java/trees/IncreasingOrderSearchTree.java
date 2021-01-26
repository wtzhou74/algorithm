package trees;

import java.util.Stack;

//Given a binary search tree, rearrange the tree in in-order so that the leftmost node in the tree is now the root of the tree, and every node has no left child and only 1 right child.
//
//Example 1:
//Input: [5,3,6,2,4,null,8,1,null,null,null,7,9]
//
//       5
//      / \
//    3    6
//   / \    \
//  2   4    8
// /        / \ 
//1        7   9
//
//Output: [1,null,2,null,3,null,4,null,5,null,6,null,7,null,8,null,9]
//
// 1
//  \
//   2
//    \
//     3
//      \
//       4
//        \
//         5
//          \
//           6
//            \
//             7
//              \
//               8
//                \
//                 9  
// 
//
//Constraints:
//
//The number of nodes in the given tree will be between 1 and 100.
//Each node will have a unique integer value from 0 to 1000.

// Time O(N)
// Space O(H)
// ！！！！辅助节点
public class IncreasingOrderSearchTree {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode (int val) {
			this.val = val;
		}
	}
	public TreeNode increasingBST(TreeNode root) {
        if (root == null)
            return null;
        Stack<TreeNode> stack = new Stack<>();
//        TreeNode ans = new TreeNode(0);
//        cur = ans; // 或者借助新建一个辅助节点，同时用 cur = ans, 最后 return cur.right; 达到同样效果
        TreeNode curr = root;
        TreeNode pre = null;//需要借助额外的辅助节点，但这里不是新建节点构造新树
        TreeNode newRoot = null;
        while (!stack.isEmpty() || curr != null) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (pre == null) {
                pre = curr;
                newRoot = pre;// pre需要移动的，所以最后pre不在root位置
            } else {
                pre.right = curr;
                pre = pre.right;//移动pre,以处理下一个节点
                pre.left = null; //一定要left置null, 因为 pre.right=curr，curr本来可以有left，所以，pre就可能会有left，这是不对的
                				//而且，这时候到这里，由于是 inorder,此时的curr.left已经没有用了
                				//当pre连到最后一个node,不需要再额外处理，因为最后一个node,它的left,right肯定都是null,否则while还会继续下去
            }
            curr = curr.right;
        }
        
        return newRoot;
    }
	
	// DFS==> 跟上面一个思路，只是换 stack为递归
	//需要辅助节点 ！！！
	public TreeNode increasingBSTDFS(TreeNode root) {
        if (root == null)
            return null;
//        dfs(root);
//        return newRoot;
        
        //用这种辅助节点方式
        TreeNode aux = new TreeNode(0);
        pre = aux;
        return aux.right;
    }
    TreeNode pre = null;
   // TreeNode newRoot = null;
    public void dfs(TreeNode root) {
        if (root == null)
            return;
        dfs(root.left);
//        if (pre == null) {           
//            pre = root;
//            newRoot = pre;
//        }
//        else {
            pre.right = root;
            pre = pre.right;
            pre.left = null;
        //}
        dfs(root.right);
    }
	
	public TreeNode buildTree() {
		TreeNode root = new TreeNode(5);
		root.left = new TreeNode(3);
		root.right = new TreeNode(6);
		root.left.left  = new TreeNode(2);
		root.left.right = new TreeNode(4);
		root.right.right = new TreeNode(8);
		
		return root;
	}
	
	public static void main(String[] args) {
		TreeNode root = new IncreasingOrderSearchTree().buildTree();
		TreeNode n = new IncreasingOrderSearchTree().increasingBST(root);
		System.out.println();
	}
}
