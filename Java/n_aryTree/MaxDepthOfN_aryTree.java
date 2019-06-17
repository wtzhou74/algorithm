package n_aryTree;

public class MaxDepthOfN_aryTree {

	// similar idea as N-level
	int result = 0;
    public int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }
        //int result = 0;
        helper(root, 1);
        return result;
    }
    public void helper(Node root, int level) {
        if (root == null) return ;
        result = Math.max(level, result);
        for (Node child : root.children) {
            helper(child, level + 1);
        }
    }
    
    // solution 2 - more concise
    public int maxDepth2(Node root) {
        if (root == null) {
            return 0;
        }
        int maxDepth = 1;
        for (Node child : root.children) {
            maxDepth = Math.max(maxDepth, maxDepth(child) + 1);
        }
        return maxDepth;
    }
}
