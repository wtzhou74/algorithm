package trees;

import java.util.Stack;

import trees.IncreasingOrderSearchTree.TreeNode;

//Given a binary tree, each node has value 0 or 1.  Each root-to-leaf path represents a binary number starting with the most significant bit.  For example, if the path is 0 -> 1 -> 1 -> 0 -> 1, then this could represent 01101 in binary, which is 13.
//
//For all leaves in the tree, consider the numbers represented by the path from the root to that leaf.
//
//Return the sum of these numbers.
//
// 
//
//Example 1:
//
//
//
//Input: [1,0,1,0,1,0,1]
//Output: 22
//Explanation: (100) + (101) + (110) + (111) = 4 + 5 + 6 + 7 = 22
// 
//
//Note:
//
//The number of nodes in the tree is between 1 and 1000.
//node.val is 0 or 1.
//The answer will not exceed 2^31 - 1.

public class SumOfRootToLeafBinaryNumbers {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int val) {
			this.val = val;
		}
	}
	public int sumRootToLeaf(TreeNode root) {
        if (root == null) return 0;
        dfs(root, "");
        return total;
    }
    int total = 0;
    public void dfs(TreeNode node, String sol) {
    	// 下面会导致叶子节点会被跑两次，比如 100， 100， 因为当node.left==null, return 后， 由于node.right也==null,
    	//	过来又得到一个同样的solution, 进而得到重复的
//        if (node == null) {
//            total += Integer.parseInt(sol, 2);
//            return;
//        }
    	if (node == null) return; //加上，比如 root只有left, 当node.right的时候就是null; 或者下面修改为判读 left是否为空
    	// 左右孩子都没有的时候，就是一条路径的结束，不用再往下了
        if (node.left == null && node.right == null) {
            total += Integer.parseInt(sol + node.val, 2);
            return;
        }
        sol += node.val;
        dfs(node.left, sol);
        dfs(node.right, sol);
        
        // 如果上面没有 if( node== null )return； 就用下面语句
//        if (node.left != null)
//            dfs(node.left, sol);
//        if (node.right != null)
//            dfs(node.right, sol);
    }
    
    // 借助公式  val * 基数（2） + node.val  (dfs从上往下，最高位开始)
    public void dfs(TreeNode node, int val) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            val = val * 2 + node.val;
            total += val;
            return;
        }
        val = val * 2 + node.val;//计算公式
        dfs(node.left, val);
        dfs(node.right, val);
    }
    
    
    // Iterative ==> Stack 实现每层只取一个（递归）
    public int sumRootToLeafIterative(TreeNode root) {
        if (root == null) return 0;
        //List<String> list = new ArrayList<>();
        
        Stack<Integer> s1 = new Stack<>();
        Stack<TreeNode> s2 = new Stack<>();
        s1.push(root.val);
        s2.push(root);
        int total = 0;
        while (!s2.isEmpty()) {
            int value = s1.pop();
            TreeNode node = s2.pop();
            if (node.left == null && node.right == null) {
                System.out.println(value);
                total += value;
                //total += value * 2 + node.val; //不对了，在入栈的时候已经处理过了，这里直接取值出来用
            }
                
            if (node.left != null) {
                s2.push(node.left);
                s1.push(value * 2 + node.left.val);//不是node.val, 是其左孩子的值
            }
            if (node.right != null) {
                s2.push(node.right);
                s1.push(value * 2 + node.right.val);//是右孩子的值
            }
        }
        return total;
    }
    
    public TreeNode buildTree() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(0);
		root.right = new TreeNode(1);
		root.left.left  = new TreeNode(0);
		root.left.right = new TreeNode(1);
		root.right.left = new TreeNode(0);
		root.right.right = new TreeNode(1);
		
		return root;
	}
    
    public static void main(String[] args) {
    	TreeNode root = new SumOfRootToLeafBinaryNumbers().buildTree();
    	new SumOfRootToLeafBinaryNumbers().sumRootToLeafIterative(root);
    }
}
