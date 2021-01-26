package binaryTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/*//Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.
//
//Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.
//
//Example: 
//
//You may serialize the following tree:
//
//    1
//   / \
//  2   3
//     / \
//    4   5
//
//as "[1,2,3,null,null,4,5]"
//Clarification: The above format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.
//
//Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.
*/
public class SerializeAndDeserializeBinaryTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		
		TreeNode(int x) {
			val = x;
		}
	}
	
	private static final String NULL = "X";
	
	public static void main(String[] args) throws Exception {
		TreeNode root = new TreeNode(1);
		TreeNode two = new TreeNode(2);
		TreeNode three = new TreeNode(3);
	    TreeNode four = new TreeNode(4);
	    TreeNode five = new TreeNode(5);
	    TreeNode six = new TreeNode(6);
	    TreeNode seven = new TreeNode(7);
	    TreeNode eight = new TreeNode(8);
	    TreeNode nine = new TreeNode(9);
	    TreeNode ten = new TreeNode(10);
	    
	    root.left = null;
	    root.right = two;
	    two.left = three;
	    three.left = four;
	    four.right = five;
	    five.right = six;
	    six.left = seven;
	    seven.left = eight;
	    eight.right = nine;
	    nine.right = ten;
	    
	    String serializedStr = new SerializeAndDeserializeBinaryTree().serialize(root);
	    TreeNode result = new SerializeAndDeserializeBinaryTree().deserialize(serializedStr);
	    System.out.println("Hello");
	}
	
	// Encodes a tree to a single string
	public String serialize(TreeNode root) {
		if (root == null) return null;
		List<String> list = new ArrayList<>();
		encode(root, list);
		// convert list to String
		StringBuffer sb = new StringBuffer();
		sb.append(list.get(0));
		for (int i = 1; i < list.size(); i++) {
			sb.append(",").append(list.get(i));
		}
		return sb.toString();
	}
	
	//level-order tree
	public void encode(TreeNode root, List<String> list) {
		// if node is null, the string should include NULL value
		if (root == null) list.add(NULL);
		else {
			list.add(String.valueOf(root.val));
			encode(root.left, list);
			encode(root.right, list);
		}
	}
	
	public TreeNode deserialize(String str) {
		// str is the result of level-order traversal
		if (str == null || str.isEmpty()) {
			return null;
		}
		// break a string into tokens
		StringTokenizer st = new StringTokenizer(str, ",");
		Queue<String> queue = new ArrayDeque<>();
		while (st.hasMoreElements()) queue.offer(st.nextToken());
		return decode(queue);
	}
	
	public TreeNode decode(Queue<String> queue) {
		String val = queue.poll();
		if (val.equals(NULL)) return null;
		TreeNode node = new TreeNode(Integer.parseInt(val));
		node.left = decode(queue);
		node.right = decode(queue);
		return node;	
	}
}
