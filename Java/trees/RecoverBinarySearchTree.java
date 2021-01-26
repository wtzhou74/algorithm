package trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import trees.SameTree.TreeNode;

//Two elements of a binary search tree (BST) are swapped by mistake.
//
//Recover the tree without changing its structure.
//
//Example 1:
//
//Input: [1,3,null,null,2]
//
//   1
//  /
// 3
//  \
//   2
//
//Output: [3,1,null,null,2]
//
//   3
//  /
// 1
//  \
//   2
//Example 2:
//
//Input: [3,1,4,null,null,2]
//
//  3
// / \
//1   4
//   /
//  2
//
//Output: [2,1,4,null,null,3]
//
//  2
// / \
//1   4
//   /
//  3
//Follow up:
//
//A solution using O(n) space is pretty straight forward.
//Could you devise a constant space solution?  ==> Moris算法，可以获得 constant space

//BST, inorder正常是有序的 ====》 问题就转换为 从 inorder 中找到需要换的两个元素， 然后更新节点的值以维持结构
// Step 1）: inorder遍历  2） 找到需要 swap的两个值； 3） 更新节点中的两个值
public class RecoverBinarySearchTree {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode (int x) {
			this.val = x;
		}
	}
	
	// a合并 step 1 & 2 即在 inorder遍历的时候就确定出需要 swap的两个数
	public void recoverTreeOptimize(TreeNode root) {
        if (root == null) return;
        TreeNode[] swap = new TreeNode[2];
        //pre = root;
        helper(root, swap);
        int temp = swap[0].val;
        swap[0].val = swap[1].val;
        swap[1].val = temp;
    }
    
    // private TreeNode[] swap = new TreeNode[2];
    private TreeNode pre = null; // 不能初始化为 root, 这是 inorder顺序的 第一个
    // Time: O(n)
    // Space: O(H)
    public void helper(TreeNode node, TreeNode[] swap) {
        if (node == null) return;
        helper(node.left, swap);
        // a找到需要交换的两个数
        if (pre != null && pre.val > node.val) {
            if (swap[0] == null)
                swap[0] = pre;
            swap[1] = node;
        } 
        pre = node;
        helper(node.right, swap);
    }
    
    
    // iterative solution
    public void iterativeSol(TreeNode root) {
        if (root == null) return;
        TreeNode[] swap = new TreeNode[2];
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        stack.push(root);
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) { // curr != null 这里是为了 curr = curr.right
            while (curr != null) {                    
                    stack.push(curr.left);
                    curr = curr.left;
            }
            curr = stack.pop();
            if (pre != null && pre.val > curr.val) {
                if (swap[0] == null) {
                    swap[0] = pre;
                }
                swap[1] = curr;
            }
            pre = curr;
            curr = curr.right;
        }
        int temp = swap[0].val;
        swap[0].val = swap[1].val;
        swap[1].val = temp;
    }
	
    
    // Morris 算法实现 space-O（1）   ===》 按 inorder, 把这个各个点通过 “right” 节点给链接起来
    public void moriesSol(TreeNode root) {
        if (root == null) return;
        TreeNode[] swap = new TreeNode[2];
        TreeNode preNode = null;
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left == null) { // 遍历到当前子树的 最左孩子
                if (preNode != null && preNode.val > curr.val) {
                    if (swap[0] == null) {
                        swap[0] = preNode;
                    }
                    swap[1] = curr;
                }
                preNode = curr;
                
                curr = curr.right; // 按照right回溯，此时的right被改造了，实际上 right 就是它的 direct predecessor, 开始处理 父节点 （第二轮遍历）. 
            } else {
                // find its predecessor
                TreeNode pre = findPredecessor(curr); // 找到 左孩子的“最”右孩子， 此时该店就是当前的直接父节点
                if (pre.right == null) {
                    pre.right = curr; // 第一轮 Mories, 创建这个右节点，让元素按顺序连接起来
                    curr = curr.left; // 处理下一个节点
                } else { // 如果右孩子不为空， 则是第二轮的开始， 因为这个右节点是 mories算法创建的
                    if (preNode != null && preNode.val > curr.val) {
                        if (swap[0] == null) {
                            swap[0] = preNode;
                        }
                        swap[1] = curr;
                    }
                    preNode = curr;
                    curr = curr.right; // 回溯到父节点，即 该店的下一个节点， 比如按 inorder排序后的
                    pre.right = null; // 把这个右节点删除，没用了
                }
                
            }
        }
        int temp = swap[0].val;
        swap[0].val = swap[1].val;
        swap[1].val = temp;
    }
    
    public TreeNode findPredecessor(TreeNode node) {
        TreeNode pre = node;
        if (pre.left != null) { // 如果没有左孩子， 那当前点就是自己的 predecessor
            pre = node.left;
            while (pre.right != null && pre.right != node) { // pre.right != node 因为找右孩子的过程中， right 有可能是Mories算法新建的，那这个右节点就是指向当前点的，所以要去掉
                pre = pre.right; // 一直往右找 直到找到“最”右孩子
            }
        }
        return pre;
    }
    
	public void recoverTree(TreeNode root) {
        if (root == null) return;
        List<Integer> list = new ArrayList<>();
        helper(root, list);
        for (int item : list)
            System.out.println(item);
        // find the two elements swapped by mistake
        
        int[] nums = new int[] {-1, -1};
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i + 1) < list.get(i)) {
                if (nums[0] == -1) nums[0] = list.get(i);
                nums[1] = list.get(i + 1); // 1, 2, 5, 4, 3 6, 7 还要注意交换的是连续两个数，而且注意 第二个数肯定是在 i+1, 所以此处在 < 的情况下都要取 i+1 的值
            }
                
        }
        System.out.println(nums[0] + "" + nums[1]);
        // traverse the tree to recovery the tree
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node.val == nums[0])
            {
                node.val = nums[1];
            } else if (node.val == nums[1])
                node.val = nums[0];
            if (node.left != null)
                q.offer(node.left);
            if (node.right != null)
                q.offer(node.right);
        }
    }
    
	// recursive inorder
	// Time: O(N)
	// Space: O(H)
    public void helper(TreeNode node, List<Integer> list) {
        if (node == null) return;
        helper(node.left, list);
        list.add(node.val);
        helper(node.right, list);
    }
    
    public TreeNode generate() {
    	TreeNode root = new TreeNode(2);
    	root.left = new TreeNode(3);
    	//root.right = new TreeNode(4);
    	root.left.left = new TreeNode(1);
    	return root;
    }
    
    public static void main(String[] args) {
    	RecoverBinarySearchTree o = new RecoverBinarySearchTree();
    	TreeNode root = o.generate();
    	o.iterativeSol(root);
    }
}
