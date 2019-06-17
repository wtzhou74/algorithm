package n_aryTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class N_aryLevelTraversal {

	// Iterative solution
	public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int size = queue.size();// use queue size to split each level
            // process current level
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                level.add(node.val);
                
                for (Node child : node.children) {
                    queue.offer(child);
                }
            }
            result.add(level);
        }
        return result;
    }
	
	// traversal solution
	public void helper(Node node, List<List<Integer>> result, int level) {
        if (node == null) return;
        // create a new level (list), e.g. root level == 0
        if (result.size() == level) {
            result.add(new ArrayList<>());     
        }
        // for size >= level, add val to current level
        List<Integer> list = result.get(level);
        list.add(node.val);
        // process next level (SAME IDEA AS ITERATIVE SOLUTION)
        for (Node child : node.children) {
            helper(child, result, level + 1);
        }
    }
}
