package binaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class InorderTraversal {

	
	public List<Integer> inorderTraverseSol(TreeNode root)
	{
		List<Integer> result = new ArrayList<Integer>();
		
		if (root == null) return result;
		
		// recursive solution / dfs
		// recursiveSol (root, result);
		
		// traverse without recursion
		traverseWithoutRecursion(root, result);
		return result;
		
	}
	
	public void recursiveSol(TreeNode node, List<Integer> result)
	{
		if (node == null) return;
		
		recursiveSol(node.left, result);
		result.add(node.val);
		recursiveSol(node.right, result);
	}
	
	// PREORDER
	public void traverseWithoutRecursion(TreeNode node, List<Integer> result)
	{
		Stack<TreeNode> stack = new Stack<>();
		while (node != null || !stack.isEmpty())
		{
			// traverse left tree
			while (node != null){
				stack.push(node);
				node = node.left;
			}
			
			// record the most left element
			node = stack.pop();
			result.add(node.val);
			
			// check right tree, then trace back to go through the same process as left tree
			node = node.right;
			
		}
//		
//		stack.push(node);
//        TreeNode curr = node;
//        while (!stack.isEmpty()) {
//            while (curr.left != null) {
//                stack.push(curr.left);
//                curr = curr.left;
//            }
//            curr = stack.pop();
//            list.add(curr);
//            if (curr.right != null) {
//                curr = curr.right;
//                stack.push(curr);
//            }
//            
//        }
	}
	
	public List<Integer> morries(TreeNode root) {
        if (root == null)
            return new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left == null) { // 当前子树左边的左孩子
                list.add(curr.val);
                curr = curr.right; // 处理下一个节点， 这有两种情况：  1） 该节点本身有右孩子， 根据 “左根右”， 自然而然要处理右孩子了
                				// 2） 这个右节点是 被 Morris算法新建的，这个右指针指向的 就是当前节点的下一个节点， 那么这个语句就是用于处理下一个节点的  （出现在第二轮遍历）
            } else {
                // find its predecessor
                TreeNode pre = findPredecessor(curr); // a找到直接前驱，即按inorder的直接父节点
                if (pre.right != null) {// （第二轮） 如果这个点已有右节点了，那这个右节点一定是新创建的， 否则 在 findPredecessor()会被处理； 这个 pre.right 指向当前点 自己
                    list.add(curr.val); // 此时就可以把当前点的值放入结果列表中
                    curr = curr.right; // 与上一个 curr = curr.right一样，但这次的这个 right 肯定是新建的， 处理下一个节点
                    pre.right = null; // 把新建的 右指针删掉， 恢复原结构
                } else {
                    pre.right = curr;// （第一轮） morris, 创建右指针，指向它按 inorder顺序的下一个节点
                    curr = curr.left; // 处理下一个未处理的节点
                }
                
            }
            
        }
        return list;
    }
    
    public TreeNode findPredecessor(TreeNode node) {
        TreeNode pre = node;
        if (pre.left != null) {
            pre = pre.left;
            while (pre.right != null && pre.right != node) {
                pre = pre.right;
            }
        }
        return pre;
    }
    
}
