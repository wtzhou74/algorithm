package design;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

//Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.
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

public class SerializeAndDeserializeBinaryTree {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	// 	Time: O(n) , both , since we need visit ALL NODES
	// Space: O(N)
	public String serializeTest(TreeNode root)
    {
        String res = "";
        serializeTestHelper(root, res);
        return res.substring(0, res.length() - 1);
    }

	// !!!!! 注意一定要用特殊字符分割各个点，以及 null 
    private void serializeTestHelper(TreeNode root, String res)
    {
        if(root == null)
        {
            res = "*";
            return;
        }
        
        // WRONG !!! ==> root.val here is DFS (preorder)
        // EACH RECURSION is a resolution of SUBPROBLEM, so, see serialize solution
        res = res + root.val + ",";
        serializeTestHelper(root.left, res);
        serializeTestHelper(root.right, res);

        //return root.val + "," + left + "," + right;

    }
    
	
	// Encodes a tree to a single string.
    public String serialize(TreeNode root)
    {
        return serializeHelper(root);
    }

    // BFS have to be ALIGHT with deserializer BFS solution
    public String serializeIteration(TreeNode root)
    {
        if (root == null) return "";
        //String res = "";
        StringBuffer res = new StringBuffer();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        //int size = 0;
        while (!queue.isEmpty()) {
            //size = queue.size();
            //while (size > 0) { // No need using level/size!!!
                TreeNode node = queue.poll();
                if (node == null) {
                	res.append("*").append(",");
                    //size--;
                    continue;
                }
                else res.append(node.val).append(",");
//                if (node.left != null) {
//                    queue.offer(node.left);
//                } else queue.offer(null);
//                if (node.right != null) {
//                    queue.offer(node.right);
//                } else  queue.offer(null);
                queue.offer(node.left);
                queue.offer(node.right);
                //size--;
            //}
            
        }
        return res.substring(0, res.length() - 1);
    }
    
    // Recursive solution
    private String serializeHelper(TreeNode root)
    {
        if(root == null)
            return "*";

        String left = serializeHelper(root.left);
        String right = serializeHelper(root.right);

        // a一定要注意用分隔符分开
        // return root.val + left + right; // WRONG!!!!! "-1" throws error for input string "-" when doing split("") below
        return root.val + "," + left + "," + right;
        
        // same below
//        String s = "";
//        s += root.val + ",";
//        s += serialize(root.left) + ",";
//        s += serialize(root.right);
//        return s;
    }
	
    
    
//	public TreeNode deserializer(String data) {
//		Queue<String> queue = new LinkedList<>();
//		// for (String c : data.split("") ) {  // see above
//		for (String c : data.split(",") ) {
//			queue.offer(c);
//		}
//		// 这个不是用于 BFS serialize 后的结果
//		return deserializerHelper(queue);
//	}
//	public TreeNode deserializerHelper(Queue<String> queue){
//        if (queue.isEmpty()) return null;
//        String c = queue.poll();
//        if (c.equals("*")) return null;
//        TreeNode root = new TreeNode(Integer.valueOf(c));
//        System.out.println(root.val);
//        root.left = deserializerHelper(queue);
//        root.right = deserializerHelper(queue);
//        
//        return root;
//    }
	
	private int i = 0; // MUST BE A GLOBAL variable, just like applying Queue, the item of String will be checked one by one by left, right
	// There will get WRONG/INCOMPLETE answer if treating i as LOCAL variable, since the i will be CHANGED by BOTH left AND right
	// a因为把 i放在括号中，在递归往回的时候，i是会走回到原来的值
	public TreeNode deserializerHelper2(String[] strs){
        if (i >= strs.length || strs[i].equals("*")) {
            i++;
            return null;
        }
        TreeNode root = new TreeNode(Integer.valueOf(Integer.valueOf(strs[i])));
        i++;
        root.left = deserializerHelper2(strs);
        root.right = deserializerHelper2(strs);
        
        return root;
    }
	
	public TreeNode deserialize(String data) {
        if (data.equals("*"))
            return null;
        return deserializerHelper2(data.split(","));
    }
	
	// BFS deserialize
	public TreeNode deserializeBFS(String data) {
		if (data.length() == 0) return null;
        String[] strs = data.split(",");
        TreeNode root = new TreeNode(Integer.valueOf(strs[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int i = 1;
        while (i < strs.length) {
            TreeNode node = queue.poll();
            String ltemp = strs[i++];  // NOTE i value !!!!!
            if (!ltemp.equals("*")) {
                TreeNode left = new TreeNode(Integer.valueOf(ltemp));
                node.left = left;
                queue.offer(left);
                //i++;
            }
            if (i >= strs.length) break;
            String rtemp = strs[i++];
            if (!rtemp.equals("*")) {
                TreeNode right = new TreeNode(Integer.valueOf(rtemp));
                node.right = right;
                queue.offer(right);
                //i++;
            }
        }
        return root;
    }
	
	public static void main(String[] args) {
		String s = "1,2,3,*,*,4,5";
		TreeNode root = new SerializeAndDeserializeBinaryTree().deserialize(s);
		System.out.println(root);
		String res = new SerializeAndDeserializeBinaryTree().serialize(root);
		System.out.println(res);
	}
	
	// Encodes a tree to a single string.
    public String serializeT(TreeNode root)
    {
        if (root == null) return "";
        Queue<TreeNode> queue = new LinkedList<>();
        StringBuffer sb = new StringBuffer();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                TreeNode curr = queue.poll();
                if (curr == null) sb.append("*" + ",");
                else{
                    sb.append(curr.val + ",");                
                    queue.offer(curr.left);
                    queue.offer(curr.right);
                }
                size--;
            }
        }
        return sb.toString();
    }

    
	public TreeNode deserializeT(String data) {
		if (data == null || data.length() == 0) return null;
        String[] s = data.split(",");
        int i = 0;
        TreeNode root = new TreeNode(Integer.valueOf(s[i++]));
        Queue<TreeNode> res = new LinkedList<>();
        res.offer(root);
        while (!res.isEmpty() && i < s.length) {
        	TreeNode node = res.poll();
            String l = s[i++];
            if (!l.equals("*")) {
                node.left = new TreeNode(Integer.valueOf(l));
                res.offer(node.left);
            }
            String r = s[i++];
            if (!r.equals("*")) {
                node.right = new TreeNode(Integer.valueOf(r));
                res.offer(node.right);
            }
        }
        return root;
    }
	
	
	
}
