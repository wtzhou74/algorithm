package trees;

import java.util.LinkedList;
import java.util.Queue;

//
//Given two binary trees, write a function to check if they are the same or not.
//
//Two binary trees are considered the same if they are structurally identical and the nodes have the same value.
//
//Example 1:
//
//Input:     1         1
//          / \       / \
//         2   3     2   3
//
//        [1,2,3],   [1,2,3]
//
//Output: true
//Example 2:
//
//Input:     1         1
//          /           \
//         2             2
//
//        [1,2],     [1,null,2]
//
//Output: false
//Example 3:
//
//Input:     1         1
//          / \       / \
//         2   1     1   2
//
//        [1,2,1],   [1,1,2]
//
//Output: false
public class SameTree {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode (int x) {
			this.val = x;
		}
	}
	
	// recursive solution
	public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null)
            return false;
        if (p.val != q.val)
            return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
	
	// Time: O(n)
	// Space: O(lgN) for best case of completely BALANCED TREE, or O(N) for worst case of completely UNBALANCED TREE
	public boolean iterativeSol(TreeNode p, TreeNode q) {
		if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null)
            return false;
        Queue<TreeNode> q1 = new LinkedList<>();
        Queue<TreeNode> q2 = new LinkedList<>();
        q1.offer(p);
        q2.offer(q);
        
        while (!q1.isEmpty() && q2.isEmpty()) {
            int sizeP = q1.size();
            int sizeQ = q2.size();
            if (sizeP != sizeQ) {
                return false;
            }
            while (sizeP > 0) {
                TreeNode np = q1.poll();
                TreeNode nq = q2.poll();
                if (np == null && nq == null) {
                    sizeP--;
                    continue;
                }
                if (np == null || nq ==null) {
                    return false;
                }
                if (np.val != nq.val) {
                    return false;
                }
                q1.offer(np.left);
                q1.offer(np.right);
                q2.offer(nq.left);
                q2.offer(nq.right);
                sizeP--;
            }
        }
        return true;
	}
	
	public TreeNode generateTree(String data) {
		String[] items = data.split(",");
		Queue<TreeNode> queue = new LinkedList<>();
		TreeNode root = new TreeNode(Integer.valueOf(items[0]));
		queue.offer(root);
		int i = 1;
		while (i < items.length) {
			TreeNode node = queue.poll();
			String left = items[i++];
			if (!left.equals("null")) {
				node.left = new TreeNode(Integer.valueOf(left));
				queue.offer(node.left);
			}
			
			if (i >= items.length) break;
			String right = items[i++];
			if (!right.equals("null")) {
				node.right = new TreeNode(Integer.valueOf(right));
				queue.offer(node.right);
			} 
			
		}
		return root;
	}
	
	public static void main(String[] args) {
		SameTree tree = new SameTree();
		TreeNode p = tree.generateTree("1,2");
		TreeNode q = tree.generateTree("1,null,2");
		
		boolean res = tree.iterativeSol(p, q);
		System.out.println(res);
	}
}
