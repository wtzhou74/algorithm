package amazon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

//We are given a binary tree (with root node root), a target node, and an integer value K.
//
//Return a list of the values of all nodes that have a distance K from the target node.  The answer can be returned in any order.
//
// 
//
//Example 1:
//
//Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, K = 2
//
//Output: [7,4,1]
//
//Explanation: 
//The nodes that are a distance 2 from the target node (with value 5)
//have values 7, 4, and 1.
//
//
//
//Note that the inputs "root" and "target" are actually TreeNodes.
//The descriptions of the inputs above are just serializations of these objects.
// 
//
//Note:
//
//The given tree is non-empty.
//Each node in the tree has unique values 0 <= node.val <= 500.
//The target node is a node in the tree.
//0 <= K <= 1000.
public class AllNodesDistanceKInBinaryTree {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode() {}
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	// 这里不将 Tree转换成Graph再BFS/DFS（如解法2）， 直接递归，分三种情况 ， 参考 AllNodesDistanceKInBinaryTree.png
	// 1） root == target , 即当递归到某点时， 正好是Target, 那它下面所有dis=K 的都是有效点
	// 2） 此时在碰到 target时候，出来收集target以下子树的点（如1所述），所以在root==target，我们要返回0，
	//   而0不是距离0， 而是表示(如果是左子树递归过来的)target在左子树上 （其他return -1 表示target不在树上）
	//   此时分两种情况：
	//			1）： target 在左子树， 同时由于是 root.left后开始算的，所以，right子树需要找的距离就是 K - L - (1 + 1)
	//			2) 同理 target在右子树，收集左子树从root到 K - R - 2的所有点
	public List<Integer> distanceK2(TreeNode root, TreeNode target, int K) {
        if (root == null)
            return new ArrayList<>();
        
        List<Integer> res = new ArrayList<>();
        dfs(root, target, K, res);
        return res;
        
    }
    
    public int dfs(TreeNode root, TreeNode target, int k, List<Integer> res) {
        if (root == null)
            return -1;
        if (root == target) {
        	// 当前点就是target, 那收集其子树下 dis == K的所有点
            collectNodes(root, k, res);
            return 0; // 表示target找到，同时如果是左子树递归过来的，那么target在左子树上，反之在右子树上
        }
        
        // 如果当前点还没到 target， 那开始左，右子树递归，直到碰到target
        int L = dfs(root.left, target, k, res);
        int R = dfs(root.right, target, k, res);
        
        // Target is in left subtree
        if (L != -1) { // 碰到target, return 0, 代表target在左子树上，否则收到 -1， 不在左子树上
            if (L + 1 == k) res.add(root.val); // 由于是 root.left过来的，所以要 L + 1 
            								// 此时意思是正好从当前的 root到target正好是距离 K
            //collect nodes from right subtree
            else  // 不是距离 K， 那需要收集右子树上符合条件的点，同理，root.right开始的，距离上也要 +1， 
            	  // 所以最终，在右子树上需要找距离是   K - L -1 - 1 的点
                collectNodes(root.right, k - L - 2, res); // 
            
            return L + 1; // 左子树上距离 + 1； 这是递归过程计算距离
        }
        if (R != -1) { // 同理看是否在右子树上
            if (R + 1 == k) res.add(root.val); // 同左子树上解释
            else
                collectNodes(root.left, k - R - 2, res);
            return R + 1;
        }
        return -1;
    }
    
    public void collectNodes (TreeNode node, int dis, List<Integer> res) {
        if (node == null) 
            return;
        if (dis == 0) {
            res.add(node.val);
            return;
        }
        collectNodes(node.left, dis - 1, res);
        collectNodes(node.right, dis - 1, res);
    }
    
	
	// 从 root到target的distance还要基于其它分支，所以不好直接判断，
	// 但是从target开始，找distance的距离就很方便，BFS/DFS
	// ==> 这里我们可以考虑基于树的结构，将 Tree转换成 undirected Graph， 然后从Target点开始进行 BFS/DFS
	// Time: O(N)
	// Space: O(N)
	public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        if (root == null)
            return new ArrayList<>();
        Map<Integer, List<TreeNode>> graph = new HashMap<>();
        buildGraph(root, graph);
        Set<Integer> visited = new HashSet<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(target);
        visited.add(target.val);
        int distance = 0;
        List<Integer> res = new ArrayList<>();
        boolean found = false;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                TreeNode node = queue.poll();
                if (distance == K) {
                    res.add(node.val);
                    found = true;
                } else {
                    //System.out.println(node.val);
                    for (TreeNode adj : graph.getOrDefault(node.val, new ArrayList<>())) {
                        if (visited.contains(adj.val)) continue;
                        visited.add(adj.val);
                        queue.offer(adj);
                    }
                }
                size--;
            }
            if (found)
                break;
            distance++;
        }
        
        return res;
        
    }
	
	// DFS 遍历 graph求距离
	public void dfs(Map<Integer, List<TreeNode>> graph, int s,
            int depth, List<Integer> res, 
            int k, Set<Integer> visited) {
		if (depth == k) {
		    res.add(s);
		    return;
		}
		// if (visited.contains(s))
		//     return;
		
		for (TreeNode adj : graph.getOrDefault(s, new ArrayList<>())) {
		    if (visited.contains(adj.val)) continue;
		    visited.add(adj.val);
		    dfs(graph, adj.val, depth + 1, res, k, visited);
		}

}
    
    public void buildGraph(TreeNode root, 
                           Map<Integer, List<TreeNode>> graph) {
        if (root == null)
            return;
        System.out.println(root.val);
        // 这个没必要，因为在如果不是只有一个节点的树，其所有点都会被记录到，因为这是个 undirected graph
        //	记录parent的节点的时候，顺便也把子节点给添加到graph中了，所以，即使到leaf节点，没有left,right,但也已经写到graph中了
//        if (!graph.containsKey(root.val))
//            graph.put(root.val, new ArrayList<>());
        TreeNode left = root.left;
        if (left != null) {
            graph.computeIfAbsent(root.val, 
                              k -> new ArrayList<>()).add(left);
            graph.computeIfAbsent(left.val, 
                              k -> new ArrayList<>()).add(root);
        }
        
        TreeNode right = root.right;
        if (right != null) {
            graph.computeIfAbsent(root.val, 
                              k -> new ArrayList<>()).add(right);
            graph.computeIfAbsent(right.val, 
                              k -> new ArrayList<>()).add(root);
        }
        
        
        buildGraph(root.left, graph);
        buildGraph(root.right, graph);
        
    }
    
    public TreeNode buildTree() {
    	TreeNode root = new TreeNode(3);
    	root.left = new TreeNode(5);
    	root.right = new TreeNode(1);
    	root.left.left = new TreeNode(6);
    	root.left.right = new TreeNode(2);
    	root.right.left = new TreeNode(0);
    	root.right.right = new TreeNode(8);
    	root.left.right.left = new TreeNode(7);
    	root.left.right.right = new TreeNode(4);
    	return root;
    }
    
    public static void main(String[] args) {
    	AllNodesDistanceKInBinaryTree all = new AllNodesDistanceKInBinaryTree();
    	TreeNode root = all.buildTree();
    	all.distanceK(root, root.left, 2);
    }
}
