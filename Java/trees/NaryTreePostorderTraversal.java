package trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import trees.NaryTreePreorderTraversal.Node;

//Given an n-ary tree, return the postorder traversal of its nodes' values.
//
//Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value (See examples).
//
// 
//
//Follow up:
//
//Recursive solution is trivial, could you do it iteratively?
//
// 
//
//Example 1:
//
//
//
//Input: root = [1,null,3,2,4,null,5,6]
//Output: [5,6,3,2,4,1]
//Example 2:
//
//
//
//Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
//Output: [2,6,14,11,7,3,12,8,4,13,9,10,5,1]
// 
//
//Constraints:
//
//The height of the n-ary tree is less than or equal to 1000
//The total number of nodes is between [0, 10^4]
public class NaryTreePostorderTraversal {

	class Node {
		int val;
		List<Node> children;
		public Node(int val) {
			this.val = val;
		}
	}
	
	public List<Integer> postorder(Node root) {
        if (root == null) return new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        dfs(root, list);
        return list;
    }
    
    public void dfs(Node root, List<Integer> list) {
        if (root.children.size() == 0) {
            list.add(root.val);
            return;
        }
        List<Node> children = root.children;
        for (Node node : children) {
            dfs(node, list);
        }
        list.add(root.val);
    }
    
    // iterative == 左右根  ==> 根右左
    public List<Integer> postorderIter(Node root) {
        if (root == null) return new ArrayList<>();
        List<Integer> list = new ArrayList<>();        
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) { // 根右左 记录
            Node node = stack.pop();
            List<Node> children = node.children;
            list.add(node.val);
            for (Node child : children) {
                stack.push(child);
            }
        }
        Collections.reverse(list); // ！！！重点在这，先按反序记录，再反序输出
        return list;
    }
}
