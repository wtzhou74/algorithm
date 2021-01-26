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
    
    public class Pair {
        boolean visited;
        Node node;
        
        public Pair(boolean visited, Node node) {
            this.visited = visited;
            this.node = node;
        }
    }
    
    // For post-order, when track back, we should recognize whether the node has been VISITED OR NOT, => define PAIR, MAP is not good option here
    // Reference to POST-ORDER of BINARY TREE SEARCH
    public List<Integer> sol3(Node root) {
        if (root == null) return new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        // recursive solution
        //helper(root, res);
        
        // Map<boolean, Node> map = new HashMap<>();
        Stack<Pair> stack = new Stack<>();
        Pair pair = new Pair(false, root);
        stack.push(pair);
        while (!stack.isEmpty()) {
            Pair temp = stack.peek();
            if (!temp.visited) {
                Node node = temp.node;
                if (node.children != null) {
                    for (int i = node.children.size() - 1; i >= 0; i--) {
                        Pair child = new Pair(false, node.children.get(i));
                        stack.push(child);
                    }
                }
                temp.visited = true;
            } else {
                Pair p = stack.pop();
                res.add(p.node.val);
            }    
            
            
        }
        return res;
    } 
}
