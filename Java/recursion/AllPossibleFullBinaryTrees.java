package recursion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//A full binary tree is a binary tree where each node has exactly 0 or 2 children.
//
//Return a list of all possible full binary trees with N nodes.  Each element of the answer is the root node of one possible tree.
//
//Each node of each tree in the answer must have node.val = 0.
//
//You may return the final list of trees in any order.
//
// 
//
//Example 1:
//
//Input: 7
//Output: [[0,0,0,null,null,0,0,null,null,0,0],[0,0,0,null,null,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,null,null,null,null,0,0],[0,0,0,0,0,null,null,0,0]]
//Explanation:
//
// 
//
//Note:
//
//1 <= N <= 20
public class AllPossibleFullBinaryTrees {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		
		public TreeNode(int val) {
			this.val = val;
		}
		
		public TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}
	
	Map<Integer, List<TreeNode>> memo = new HashMap<>();
    public List<TreeNode> allPossibleFBT(int N) {
        if (N % 2 == 0) return new ArrayList<>();
        List<TreeNode> list = new ArrayList<>();
        if (N == 1) {
            TreeNode node = new TreeNode(0);
            list.add(node);
            return list;
        }
//        if (memo.containsKey(N))
//            return memo.get(N);
        // 当 树root分别在 1， 3， 5...的位置形成的树，之后对 在比如 idx=1, 那左边[0,1)就是该根的左子树，(1, N]就是右子树，
        // 递归往下直到N==1; 完事往回开始建树（点）
        // 同理对 树root=3开始...
        for (int i = 1; i < N; i += 2) { // 递归过来，每一棵子树或右子树都可以从 i =1, 3,...开始
            List<TreeNode> leftNodes = allPossibleFBT(i);// i为分界点cut-off point, 处理左子树
            List<TreeNode> rightNodes = allPossibleFBT(N - i - 1); // 处理右子树
            
            // 往回新建点并把左右子树相连接起来！！！！！！！这里之所以需要双层循环，就是当 i = 1,其可以有两种右子树（形成两种树）， 一种是对剩下的点 i = 1, 或者 i=3
            for (TreeNode left : leftNodes) {//这里是full binary tree, left在前或righ都可以，但这里right在前速度更快
                for (TreeNode right : rightNodes) {
                    TreeNode root = new TreeNode(i, left, right); //递归往回的时候，新建“root”,并连接左右子树，之前这个并不在没新建，建的只是N==1的时候，
                    											// refer to AllPossibleFullBinaryTrees.png
                    list.add(root);
                }
            }
        }
        
        //memo.put(N, list);
        
        return list;
    }
    
    public static void main(String[] args) {
    	new AllPossibleFullBinaryTrees().allPossibleFBT(7);
    }
}
