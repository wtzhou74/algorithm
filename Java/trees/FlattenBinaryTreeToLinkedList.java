package trees;

import java.util.Stack;

import trees.RecoverBinarySearchTree.TreeNode;

//Given a binary tree, flatten it to a linked list in-place.
//
//For example, given the following tree:
//
//    1
//   / \
//  2   5
// / \   \
//3   4   6
//The flattened tree should look like:
//
//1
// \
//  2
//   \
//    3
//     \
//      4
//       \
//        5
//         \
//          6

// a不要被例子误导， 这不是“排序”；  这实际是按 “根-左-右”（先序遍历） 构造一个链表
public class FlattenBinaryTreeToLinkedList {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	public TreeNode generate() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(5);
		root.left.left = new TreeNode(3);
		root.left.right = new TreeNode(4);
		root.right.right = new TreeNode(6);
		return root;
	}
	
	TreeNode dump = null;
    public void flatten(TreeNode root) {
        if (root == null) return;
        
        //dump.right = root;
        helper(root);
        System.out.println();
    }
    
    // "a反常规的" DFS， 从右子树开始再左；  借助一个辅助节点dump， 修改 “已访问的”节点的右孩子，并把其 左孩子=null
    // a注意修改节点右孩子 与 递归的顺序
    public void helper(TreeNode node) {
        if (node == null) {
            return;
        }
//        dump.right = node;
//        dump = node; // 上述两个语句将使 原node.right指针丢失  ==》 最右边会放在最后一个，反过来 先处理右边节点
//        helper(node.left);
//        helper(node.right); // 这个node.right不正确，因为被之前的代码修改掉了
//        node.left = null;
        helper(node.right);
//        node.right = dump;
//        dump = node; // 在递归左子树之前添加，其结果中的左子树会因为 node.left出栈以及node.left=null被切掉
        helper(node.left);
        // 对“已访问过的”节点修改 指针， 不能修改未访问的节点
        node.right = dump;
        dump = node;
        node.left = null; // 结果是一个 unbalanced 只有右子树的数， 所以 左孩子一定要 null
    }
    
    public void iterativeSol(TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        //stack.push(root);
//        TreeNode curr = root;
//        TreeNode dump = null;
//        while (curr != null || !stack.isEmpty()) {
//            while (curr != null) {
//                stack.push(curr);
//                curr = curr.right;
//            }
//            TreeNode temp = stack.peek();
//            if (temp.left != null) { // a这块已 visited的点会再处理，导致无限循环
//                curr = temp.left;
//            } else {
//                TreeNode node = stack.pop();
//                node.right = dump;
//                dump = node;
//                node.left = null;
//            }
//        }
        
        
        Stack<TreeNode> helper = new Stack<>();
        stack.push(root);
        //TreeNode curr = root;
        TreeNode dump = null;
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            helper.push(node);
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
            
            // a不可以直接在这里处理， 否则 下面的语句会把 root下的子树都砍掉，导致最后结果只留有一个 root值
            // a遍历的顺序是没有问题的，因为节点 已经提前放到 stack 了， 但这里是要 处理 inplace 的
//            node.right = dump;
//            dump = node;
//            node.left = null;
        }
        // s所以我们借助一个辅助 stack， 把所有 按  根-左-右 遍历的顺序放好， 然后按照这个顺序， "从后往前"，借助 dump节点， 把这是节点按指定顺序连接起来
        // a当然此处也可以用 queue
        while (!helper.isEmpty()) {
            TreeNode temp = helper.pop();
            temp.right = dump;
            dump = temp;
            temp.left = null;
        }
        
        // a借助 Queue ==> 按当前顺序链接
//        dump = helper.poll();
//        dump.left = null;
//        while (!helper.isEmpty()) {
//            TreeNode node = helper.poll();
//            dump.right = node;
//            dump = node;
//            dump.left = null;
//        }
        
        // a不借助额外 辅助的 stack        
//        TreeNode dump = new TreeNode(-1);
//        while (!stack.isEmpty()) {
//            TreeNode node = stack.pop();
//            dump.right = node; // 开始链接 每个 经  root-left-right 遍历的结果
//            if (node.right != null)
//                stack.push(node.right);
//            if (node.left != null) {                
//                stack.push(node.left);
//                node.left = null; // 所有有左孩子的在处理完之后 就设置为 null， 这是最终链表要的
//            }
//            dump = node;
//        }
    }
    
    
    public static void main(String[] args) {
    	FlattenBinaryTreeToLinkedList flat = new FlattenBinaryTreeToLinkedList();
    	TreeNode root = flat.generate();
    	flat.iterativeSol(root);
    }
}
