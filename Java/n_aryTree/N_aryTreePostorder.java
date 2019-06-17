package n_aryTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class N_aryTreePostorder {

	// recursive solution
	public List<Integer> postorder(Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        helper(root, result);
        //s1 : result.add(root.val);
        return result;
    }
    public void helper(Node node, List<Integer> list) {
        if (node == null) return;
        for (Node child : node.children) {
            helper(child, list);
            //s1:
            //list.add(child.val); // then previous S1 should be added since it starts from the children of root
        }
        list.add(node.val);// it starts from root, so the previous S1 is not needed
    }
    
    // Iterative solution
    public List<Integer> postorder2(Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        //helper(root, result);
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            Node node = stack.pop();
            result.add(node.val);
            for (Node child : node.children) {
                stack.push(child);
            }
        }
        // ROOT-RIGHT-LEFT => goal: L-RIGHT-ROOT
        Collections.reverse(result);
        return result;
    }
}
