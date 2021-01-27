package amazon;

import java.util.ArrayList;
import java.util.List;

//Given two non-empty binary trees s and t, check whether tree t has exactly the same structure and node values with a subtree of s. A subtree of s is a tree consists of a node in s and all of this node's descendants. The tree s could also be considered as a subtree of itself.
//
//Example 1:
//Given tree s:
//
//     3
//    / \
//   4   5
//  / \
// 1   2
//Given tree t:
//   4 
//  / \
// 1   2
//Return true, because t has the same structure and node values with a subtree of s.
// 
//
//Example 2:
//Given tree s:
//
//     3
//    / \
//   4   5
//  / \
// 1   2
//    /
//   0
//Given tree t:
//   4
//  / \
// 1   2
//Return false.

public class SubtreeOfAnotherTree {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode (int val) {
			this.val = val;
		}
	}
	
	// a这里其实是分两部分： 1） 在s中找到t.root的点， 2） 从这个node开始，看是否和 t一样
	// a这里也可通过比较字符串， 比如 preorder， 但对没有左/右子树的要用 null补上，然后看 t是不是 s.substring()
	public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s == null && t == null)
            return true;
        if (s == null || t == null)
            return false;
        
        // a要注意！！！树中节点的值可以重复，所以找到所有val跟t.root相同的点
        List<TreeNode> list = new ArrayList<>(); // 不用 list的话，就s树一个个判断是一样的
        findT(s, t.val, list);
        //System.out.println(newRoot.val);
        if (list.size() == 0)
            return false;
        for (TreeNode node : list) {
            if (checkSame(node, t))
                return true;
        }
        return false;
    }
    
    // find the node from s
    public void findT(TreeNode s, int val, List<TreeNode> list) {
        if (s == null)
            return ;
        if (s.val == val) {
            list.add(s);
            //return;
        }
        findT(s.left, val, list);
        findT(s.right, val, list);
    }
    
    public boolean checkSame(TreeNode s, TreeNode t) {
        if (s == null && t == null)
            return true;
        if (s == null || t == null)
            return false;
        if (s.val != t.val)
            return false;
        boolean left = checkSame(s.left, t.left);
        boolean right = checkSame(s.right, t.right);
        
        return left && right;
    }
}
