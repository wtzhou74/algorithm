package trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//Given two binary trees and imagine that when you put one of them to cover the other, some nodes of the two trees are overlapped while the others are not.
//
//You need to merge them into a new binary tree. The merge rule is that if two nodes overlap, then sum node values up as the new value of the merged node. Otherwise, the NOT null node will be used as the node of new tree.
//
//Example 1:
//
//Input: 
//	Tree 1                     Tree 2                  
//          1                         2                             
//         / \                       / \                            
//        3   2                     1   3                        
//       /                           \   \                      
//      5                             4   7                  
//Output: 
//Merged tree:
//	     3
//	    / \
//	   4   5
//	  / \   \ 
//	 5   4   7

public class MergeTwoBinaryTrees {


	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
		TreeNode() {}
	}
	
	// Without creating new nodes
	public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return null;
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        t1.val += t2.val;
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        
        return t1;
    }
	
	
	// Creating a new tree  ==> 主要在于当一个子树为空的时候，不能像修改已有树的方法，直接接过去，而是要继续放入Queue/Stack/或递归继续下去
	public TreeNode mergeTrees1(TreeNode t1, TreeNode t2) {
        TreeNode node;
        if (t1 == null && t2 == null) return null;//由于不管子树是否有空都要递归往下，所以这里两个可能都为null

        if (t1 == null) { // WRONG if return new TreeNode(t2.val) since starting from this node, it will continue to process its children if any !!!!!
            node = new TreeNode(t2.val);
            node.left = mergeTrees(null, t2.left);
            node.right = mergeTrees(null, t2.right);
        } else if (t2 == null) {
            node = new TreeNode(t1.val);
            node.left = mergeTrees(t1.left, null);
            node.right = mergeTrees(t1.right, null);
        } else {
            node = new TreeNode(t1.val + t2.val); 
            node.left = mergeTrees(t1.left, t2.left);
            node.right = mergeTrees(t1.right, t2.right);
        }
        
        return node;
    }
	
	
	// iterative Solution  - BFS
	public TreeNode iterativeSol(TreeNode t1, TreeNode t2) {
        if (t1 == null) return t2;
        if (t2 == null) return t1;
//        
//        Queue<TreeNode> queue1 = new LinkedList<>();
//        queue1.offer(t1);
//        Queue<TreeNode> queue2 = new LinkedList<>();
//        queue2.offer(t2);
//        while (!queue1.isEmpty() || !queue2.isEmpty()) {
//            TreeNode node1 = queue1.poll();
//            TreeNode node2 = queue2.poll();
//            
//            // Based on the logic, NEITHER node1 nor node2 are null !!!!
//            node1.val += node2.val;
//            
//            // Check their children
//            if (node1.left == null || node2.left == null) {
//            	node1.left = node1.left == null ? node2.left : node1.left; // One of is NULL, attach the (RIGHT)subtree to (LEFT) current node WITHOUT offering to QUEUE again.
//            } else if (node1.left != null && node2.left != null) { // ONLY CHECK non-null node
//            	queue1.offer(node1.left);
//            	queue2.offer(node2.left);
//            }
//            
//            if (node1.right == null || node2.right == null) {
//            	node1.right = node1.right == null ? node2.right : node1.right;
//            } else if (node1.right != null && node2.right != null) {
//            	queue1.offer(node1.right);
//            	queue2.offer(node2.right);
//            }
//           
//                
//        }
        
        Queue<TreeNode> q1 = new LinkedList<>();
        Queue<TreeNode> q2 = new LinkedList<>();
        q1.offer(t1);
        q2.offer(t2);
        
        while (!q1.isEmpty() || !q2.isEmpty()) {
        	// 因为在 offer() 的时候，q1,q2一直是成对的，即便进去的是null
            TreeNode n1 = q1.poll();
            TreeNode n2 = q2.poll();
            
            // n1,n2不会同时为空，那样的话 在之前 null,是不入queue的，所以从Queue出来的不会同时为null
            if (n1 == null || n2 == null) { // n2 是空时没必要处理了
                continue;//有一个为空没必要处理了，因为下面（之前）做了  比如 n1==null, n1.left = n2.left这样的操作
                			//这个写法不涉及真正意义的new Tree, 而是更新已有的Tree
            }
            
            n1.val += n2.val; // 只要有值就相加，不管是否相等
            
            // 处理其左孩子
            if (n1.left == null) {
                n1.left = n2.left;//直接把子树拼接过去就好，且不用入queue，因为接过来这部分已经完成了
            } else {
                q1.offer(n1.left);
                q2.offer(n2.left);
            }
            
            //处理其右孩子
            if (n1.right == null) {
                n1.right = n2.right;
            } else {
                q1.offer(n1.right);
                q2.offer(n2.right);
            }
        }
        return t1;
    }
	
	
	// Iterative - DFS
	public TreeNode dfs(TreeNode t1, TreeNode t2) {
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        
        Stack<TreeNode[]> stack = new Stack<>(); // instead declaring two stacks
        stack.push(new TreeNode[] {t1, t2});
        
        while (!stack.isEmpty()) {
            TreeNode[] nodes = stack.pop();
            // Based on the below logic, NEITHER node0 NOR node1 are null
            // if node0 CANNOT be null, there is no need to do even though node1 is null
            if (nodes[0] == null || nodes[1] == null) {
                continue;
            }
            nodes[0].val += nodes[1].val;
            
            if (nodes[0].left == null ) {
                nodes[0].left = nodes[1].left;// attach (right) subtree to current node
            } else {
                stack.push(new TreeNode[] {nodes[0].left, nodes[1].left});
            }
            
            if (nodes[0].right == null) {
                nodes[0].right = nodes[1].right;
            } else {
                stack.push(new TreeNode[] {nodes[0].right, nodes[1].right});
            }
        }
        return t1;
    }
	
	/// BFS creating new Tree  ==> 碰到其中一个为空的，继续放入 Queue/Stack
	public TreeNode mergeTreesNewTree(TreeNode t1, TreeNode t2) {
		if (t1 == null && t2 == null) return null;
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        
        Queue<TreeNode[]> q = new LinkedList<>(); // TreeNode[] 这样就不需要新建多个 Queue了
        TreeNode n = new TreeNode();
        q.offer(new TreeNode[] {t1, t2, n}); //带着要新建的点一起，这样好处理左，右子树
        while (!q.isEmpty()) {
            TreeNode[] nodes = q.poll();
            // ！！！！！这里nodes[0],node[1], 不会同时为null
            // 如何入 Queue让其不会同时为 null!!!!!!
            if (nodes[0] == null) { // 既然两个不能同时为null, 那就把它统一挪到 n[0]上，如果 n[1]为null的话
            						// 也方便后面if的处理
                nodes[0] = nodes[1];
                nodes[1] = null;
            }
            if (nodes[1] == null) {
                nodes[2].val = nodes[0].val;
            } else {
                nodes[2].val = nodes[0].val + nodes[1].val;
            }
            
            // 这里不能仅 if nodes[0]==null || nodes[1] == null处理， 接下来是要处理左右孩子
            if (nodes[0].left != null ||
                (nodes[1] != null && nodes[1].left != null)) { //限制了进去的两个left不会同时为null
            				//同时为 null的话不会进到 if 里面
                TreeNode left = new TreeNode();
                nodes[2].left = left;
                q.offer(new TreeNode[]{
                    nodes[0].left,
                    nodes[1] == null ? null : nodes[1].left,
                    left
                });
            }
            
            if (nodes[0].right != null ||
                (nodes[1] != null && nodes[1].right != null)) {
                TreeNode right = new TreeNode();
                nodes[2].right = right;
                q.offer(new TreeNode[]{
                    nodes[0].right,
                    nodes[1] == null ? null : nodes[1].right,
                    right
                });
            }
        }
        return n;
        
    }
	
	// creating new Tree
	public TreeNode mergeTreesStack(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return null;
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        
        Stack<TreeNode[]> stack = new Stack<>();
        TreeNode n = new TreeNode();
        stack.push(new TreeNode[] {t1, t2, n});
        while (!stack.isEmpty()) {
            TreeNode[] nodes = stack.pop();
            if (nodes[0] == null) {
                nodes[0] = nodes[1];
                nodes[1] = null;
            }
            if (nodes[1] == null) {
                nodes[2].val = nodes[0].val;
            } else {
                nodes[2].val = nodes[0].val + nodes[1].val;
            }
            
            if (nodes[0].left != null ||
                (nodes[1] != null && nodes[1].left != null)) {
                TreeNode left = new TreeNode();
                nodes[2].left = left;
                stack.push(new TreeNode[]{
                    nodes[0].left,
                    nodes[1] == null ? null : nodes[1].left,
                    left
                });
            }
            
            if (nodes[0].right != null ||
                (nodes[1] != null && nodes[1].right != null)) {
                TreeNode right = new TreeNode();
                nodes[2].right = right;
                stack.push(new TreeNode[]{
                    nodes[0].right,
                    nodes[1] == null ? null : nodes[1].right,
                    right
                });
            }
        }
        return n;
        
    }
	
	public TreeNode generate1() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(3);
		root.right = new TreeNode(2);
		
//		root.left.left = new TreeNode(5);
		
		return root;
	}
	
	public TreeNode generate2() {
		TreeNode root = new TreeNode(2);
		root.left = new TreeNode(1);
		root.right = new TreeNode(3);
		
//		root.left.right = new TreeNode(4);
//		root.right.right = new TreeNode(7);
//		
		return root;
	}
	
	public static void main(String[] args) {
		MergeTwoBinaryTrees merge = new MergeTwoBinaryTrees();
		TreeNode root1 = merge.generate1();
		TreeNode root2 = merge.generate2();
		
		merge.mergeTreesNewTree(root1, root2);
	}
	 
}
