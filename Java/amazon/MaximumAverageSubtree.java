package amazon;

//Given the root of a binary tree, find the maximum average value of any subtree of that tree.
//
//(A subtree of a tree is any node of that tree plus all its descendants. The average value of a tree is the sum of its values, divided by the number of nodes.)
//
// 
//
//Example 1:
//
//
//
//Input: [5,6,1]
//Output: 6.00000
//Explanation: 
//For the node with value = 5 we have an average of (5 + 6 + 1) / 3 = 4.
//For the node with value = 6 we have an average of 6 / 1 = 6.
//For the node with value = 1 we have an average of 1 / 1 = 1.
//So the answer is 6 which is the maximum.
// 
//
//Note:
//
//The number of nodes in the tree is between 1 and 5000.
//Each node will have a value between 0 and 100000.
//Answers will be accepted as correct if they are within 10^-5 of the correct answer.
public class MaximumAverageSubtree {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	//a这道题本质是个post-order 遍历
	// a关键是考虑两个因素，在递归的时候， 1） 统计sum， 2） 经过的节点个数 count
	// 						==> a新建一种 class(sum, count); 或者用一个二维数组存放这两个值
	// ===> bottom-up, 一直递归到叶子层， 开始返回，途径每个点都计算一次结果，因为这些都是subtree的root
	public double maximumAverageSubtree(TreeNode root) {
		if (root == null)
            return 0;
        if (root.left == null && root.right == null) {
            return root.val;
        }
        dfs(root);
        return max;
    }
    
	double max = Double.MIN_VALUE;
    public Cell dfs(TreeNode node) {
        if (node == null)
            return new Cell(0, 0);
        // if (node.left == null && node.right == null) {
        //     return new Cell(node.val, 1);
        // }
        
        Cell curr = new Cell(node.val, 1);
        Cell left = dfs(node.left);
        Cell right = dfs(node.right);
        
        double sum = left.sum + right.sum + node.val;
        int count = left.count + right.count + 1;
        max = Math.max(max, sum / count);
        curr.sum = sum;
        curr.count = count;
        
        return curr;
        
    }
    
    class Cell {
        int count;
        double sum;
        public Cell(int count, double sum) {
            this.count = count;
            this.sum = sum;
        }
    }
    
    public TreeNode buildTree() {
    	TreeNode root = new TreeNode(5);
    	root.left = new TreeNode(6);
    	root.right = new TreeNode(1);
    	return root;
    }
    
    public static void main(String[] args) {
    	MaximumAverageSubtree obj = new MaximumAverageSubtree();
    	TreeNode root = obj.buildTree();
    	obj.maximumAverageSubtree(root);
    }
}
