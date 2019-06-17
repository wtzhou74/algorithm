package n_aryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Given an n-ary tree, return the PREORDER traversal of its nodes' values.
 * */
public class N_aryTreePreorderTraversal {

	// RECURSIVE SOLUTION
    List<Integer> result = new ArrayList<>();
    public List<Integer> preorder(Node root) {
        if (root == null) return result;
        result.add(root.val);
        for (Node child : root.children) {
            preorder(child);
        }
        return result;
        
    }
    
    //ITERATIVE SOLUTION
    // dfs
    public List<Integer> preorder1(Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            Node node = stack.pop();
            result.add(node.val);
            // The most right node should be pushed first for FILO of Stack
            for (int i = node.children.size() - 1; i >= 0; i--) {
                stack.push(node.children.get(i));
            }
        }
        
        return result;
        
    }
	
}
