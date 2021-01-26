package trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//Given a n-ary tree, find its maximum depth.
//
//The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
//
//Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value (See examples).
//
// 
//
//Example 1:
//
//
//
//Input: root = [1,null,3,2,4,null,5,6]
//Output: 3
//Example 2:
//
//
//
//Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
//Output: 5
// 
//
//Constraints:
//
//The depth of the n-ary tree is less than or equal to 1000.
//The total number of nodes is between [0, 10^4].
public class MaximumDepthOfNaryTree {

	class Node {
		int val;
		List<Node> children;
		public Node(int val) {
			this.val = val;
		}
	}
	int max = Integer.MIN_VALUE;
    public int maxDepth(Node root) {
        if (root == null)
            return 0;
        dfs(root, 1);//只有一个元素深度是1，所以初始值是1
        return max;
    }
    
    
    public void dfs(Node root, int depth) {
        if (root.children.size() == 0) {
            max = Math.max(depth, max); //到最下面我们找到其中一个depth
        }
        for (int i = 0; i < root.children.size(); i++) {
            dfs(root.children.get(i), depth + 1); //这里depth是跟着深度走的，所以 +1； 不是局部变量是层次的
        }
    }
    
    // DFS的另一种写法
    public int maxDepth1(Node root) {
        if (root == null)
            return 0;
        if (root.children.size() == 0) {
            return 1;
        }
        List<Integer> depths = new ArrayList<>();
        //int depth = 0;
        for (int i = 0; i < root.children.size(); i++) {
            depths.add(maxDepth(root.children.get(i)) + 1); //计算所有子树出去的 depth
            //depth = maxDepth(root.children.get(i)) + 1;
            //max = Math.max(max, depth);
        }
        return Collections.max(depths); //这里取最大
    }
    
    // dfs2的另一种写法
    public int dfs2(Node root) {
        if (root.children.size() == 0)
            return 1;
        int depth = 1;
        for (int i = 0; i < root.children.size(); i++) {
            depth = dfs2(root.children.get(i)) + 1;
            max = Math.max(max, depth);
        }
        return depth;
    }
    
    public int maxDepthBFS(Node root) {
        if (root == null)
            return 0;
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        int count = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            count++;
            while (size > 0) {
                Node node = q.poll();
                if (node != null) //"each group of children is separated by the null value ", node有可能为null
                    for (int i = 0; i < node.children.size(); i++) {
                        q.offer(node.children.get(i));
                    }
                size--;
            }
        }
        return count;
    }
}
