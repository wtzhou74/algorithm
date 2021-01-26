package trees;

import java.util.LinkedList;
import java.util.Queue;

//You are given a perfect binary tree where all leaves are on the same level, and every parent has two children. The binary tree has the following definition:
//
//struct Node {
//  int val;
//  Node *left;
//  Node *right;
//  Node *next;
//}
//Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
//
//Initially, all next pointers are set to NULL.
//
// 
//
//Follow up:
//
//You may only use constant extra space.
//Recursive approach is fine, you may assume implicit stack space does not count as extra space for this problem.
// 
//
//Example 1:
//
//
//
//Input: root = [1,2,3,4,5,6,7]
//Output: [1,#,2,3,#,4,5,6,7,#]
//Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.

//Constraints:
//
//The number of nodes in the given tree is less than 4096.
//-1000 <= node.val <= 1000
public class PopulatingNextRightPointersInEachNode {

	public class Node {
		Node left;
		Node right;
		Node next;
		int val;
		
		public Node (int val) {
			this.val = val;
		}
	}
	public Node connect(Node root) {
        if (root == null) return null;
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            while (size > 0) {
                Node node = q.poll();
                if (size == 1) {
                    node.next = null;
                } else {
                    node.next = q.peek();
                }
                if (node.left != null)
                    q.offer(node.left);
                if (node.right != null)
                    q.offer(node.right);
                size--;
            }
        }
        return root;
    }
	
	
	public Node recursiveSol(Node root) {
        if (root == null) return null;
        helper(root);
        return root;
    }
    
	// Up-down
	// Space: O(1) without considering implicit stack for recursion
    public void helper(Node node) {
        if (node == null) return;
        if (node.left != null)
            node.left.next = node.right;
        if (node.right != null)
            if (node.next != null) {
            	// a借助（上层的）next,处理下层
                node.right.next = node.next.left;// node.next这里是node并排的右子树了
            } else {
                node.right.next = null;
            }
        //right.next = null;
        // a再递归往下
        helper(node.left);
        helper(node.right);
        
    }
    
    // iterative solution for O(1) space
    public Node iterativeSol(Node root) {
        if (root == null) return null;
        Node curr = root;
        curr.next = null;
        while (curr.left != null) { // left存在才有必要往下走 （PERFECT BINARY TREE）
            
            Node head = curr; // 判断每一层
            while (head != null) { // 从左往右， 利用 next 完成循环； 看next在图上的结构
                head.left.next = head.right;
                if (head.next != null) {
                    head.right.next = head.next.left;
                } else {
                    head.right.next = null;
                }
                head = head.next;
            }
            
            curr = curr.left;
        }
        
        return root;
    }
}
