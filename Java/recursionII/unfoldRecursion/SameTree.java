package recursionII.unfoldRecursion;

import java.util.ArrayDeque;
import java.util.Stack;

/*Given two binary trees, write a function to check if they are the same or not.

Two binary trees are considered the same if they are structurally identical and the nodes have the same value.

Example 1:

Input:     1         1
          / \       / \
         2   3     2   3

        [1,2,3],   [1,2,3]

Output: true
Example 2:

Input:     1         1
          /           \
         2             2

        [1,2],     [1,null,2]

Output: false
Example 3:

Input:     1         1
          / \       / \
         2   1     1   2

        [1,2,1],   [1,1,2]

Output: false
*/
public class SameTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) {
			val = x;
		}
	}
	
	public boolean isSameTree(TreeNode p, TreeNode q) {
		if (p == null && q == null) return true; // both are null
		if (p == null || q == null) return false; // either is null
		if (p.val != q.val) return false; // both are not null, check value; if true, then check their children
		// check SINGLE node, no need to do BELOW
//		if (p.left == null && p.right == null 
//				&& q.left == null && q.right == null && p.val == q.val) return true;
		// start to check their children
		return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
	}
	
	public boolean iterateSol(TreeNode p, TreeNode q) {
		if (!check(p, q)) return false;
		ArrayDeque<TreeNode> dp = new ArrayDeque<>(); // ArrayDeque can be treated as stack, but more efficient
		ArrayDeque<TreeNode> dq = new ArrayDeque<>();
		dp.addLast(p);
		dq.addLast(q);
		
		while (!dp.isEmpty() && !dq.isEmpty()) {
			TreeNode pn = dp.removeFirst();
			TreeNode qn = dq.removeLast();
			if (!check(pn, qn)) return false;
			if (pn != null) { // no need to check qn since check() has done
				// in Java nulls are not allowed in Deque
		        if (!check(p.left, q.left)) return false;
		        if (p.left != null) {
		          dp.addLast(p.left);
		          dq.addLast(q.left);
		        }
		        if (!check(p.right, q.right)) return false;
		        if (p.right != null) {
		          dp.addLast(p.right);
		          dq.addLast(q.right);
		        }
			}			
		}
//		if (dp.isEmpty() && dq.isEmpty()) return true; // No needed for check () has done
//		else return false;
		return true;
	}
	
	public boolean iterateSolWithStack(TreeNode p, TreeNode q) {
		if (!check(p, q)) return false;
        Stack<TreeNode> sp = new Stack<>();
        Stack<TreeNode> sq = new Stack<>();
        sp.push(p);
        sq.push(q);
        
        while (!sp.isEmpty() && !sq.isEmpty()) { // Step 1: using LOOP to replace invocation of RECURSION
            TreeNode tp = sp.pop();
            TreeNode tq = sq.pop();
            if (!check(tp, tq)) return false;
            if (tp != null) {			 // Step 2: PUSH THE PARAMETERS into STACK instead of invoking a RECUSION.
                sp.push(tp.left);
                sp.push(tp.right);
            }
            if (tp != null) {
                sq.push(tq.left);
                sq.push(tq.right);
            }
        }
        return true;
        // if (sp.isEmpty() && sq.isEmpty()) return true;
        // else return false;
	}
	
	public boolean check(TreeNode p, TreeNode q) {
		if (p == null && q == null) return true;
		if (p == null || q == null) return false;
		if (p.val != q.val) return false;
		return true;
	}
}
