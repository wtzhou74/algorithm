package binaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PostorderTraversal {

	public List<Integer> postorderTraversalSol(TreeNode root)
	{
		List<Integer> result = new ArrayList<>();
		// recursive solution
//		if (root == null) return result;
//		recursiveTraverse(root, result);
		
		// non-recursive
		traverseWithoutStack(root, result);
		
		return result;
		
		
	}
	
	public void recursiveTraverse (TreeNode node, List<Integer> result)
    {
        if (node == null) return;
        
        recursiveTraverse(node.left, result);
        recursiveTraverse(node.right, result);
        result.add(node.val);
    }
	
	// Comparing with Preorder Traversal
	// REMEMBER STACK IS FILO
	public void traverseWithoutStack (TreeNode root, List<Integer> result)
    {
        Stack<TreeNode> stack = new Stack<>();
        Stack<Integer> helper = new Stack<>();// store elements in root-right-left 
        
        stack.push (root);
        while (!stack.isEmpty())
        {
            TreeNode node = stack.pop();
            helper.add(node.val);// root will the last one to pop
            // can be replaced with list.add(0, node.val)
            
            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);// right will accessed before left
            
        }
        
        while (!helper.isEmpty()) result.add(helper.pop());// pop elements from stack to get correct order of postorder
    }
}
