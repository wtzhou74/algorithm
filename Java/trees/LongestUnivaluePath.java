package trees;


//Given a binary tree, find the length of the longest path where each node in the path has the same value. This path may or may not pass through the root.
//
//The length of path between two nodes is represented by the number of edges between them.
//
// 
//
//Example 1:
//
//Input:
//
//              5
//             / \
//            4   5
//           / \   \
//          1   1   5
//Output: 2
//
// 
//
//Example 2:
//
//Input:
//
//              1
//             / \
//            4   5
//           / \   \
//          4   4   5
//Output: 2
//
// 
//
//Note: The given binary tree has not more than 10000 nodes. The height of the tree is not more than 1000.

// Post-order的遍历是最好也是最直接的处理 ==> 典型的bottom-up
public class LongestUnivaluePath {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode (int val) {
			this.val = val;
		}
	}
	public int longestUnivaluePath(TreeNode root) {
        if (root == null)
            return 0;
        dfs(root);
        return max;
    }
    
    //int max = Integer.MIN_VALUE;
    public int dfs(TreeNode node) {
    	if (node == null)
            return 0;
        int left = 0;
        int right = 0;
        // top-down， 不好预知后续的点的情况，考虑并需要处理的比较多，且抽象，所以这样方式比较麻烦
        // wrong solution below， right solution referring to topdown(root)
        if (node.left != null) {
            if (node.val == node.left.val)
                left = dfs(node.left) + 1;
            else
                left = dfs(node.left);
        }
        if (node.right != null) {
            if (node.val == node.right.val) {
                right = dfs(node.right) + 1;
            } else {
                right = dfs(node.right);
            }
        }
        if (node.left != null && node.right != null 
        		&& node.val == node.left.val && node.val == node.right.val)
        	max = Math.max(max, left + right); // 这种方式的后果是 某个root的左子树会加上右子树，即便两边 val不一样
        else
        	max = Math.max(left, right);
        return Math.max(left, right);
    }
    
    public int topdown(TreeNode node) {
        if (node == null)
            return 0;
        int left = 0, right = 0;
        int total = 0, ret = 0;
        if (node.left != null) {
            left = dfs(node.left); //a先递归处理（所有点都要处理）， 但不着急 +1, 分情况， 只有符合条件的才能 +1, 否则length不变，是0还是0
            if (node.left.val == node.val) {// 如果两值相等
                total += left + 1; // left+1, 同时放入 curTotal, 这个变量也会记录right,所以接下来不需要再 left+right了
                ret = Math.max(ret, left + 1);// 同时考虑到return的是left,right中的大值，所以在这里就记录下
                							// a因为left这里本身不 +1,所以不能直接再最后 用return max(left, right)
            }
        }
        //a处理右子树与左子树一样
        if (node.right != null) {
            right = dfs(node.right);
            if (node.right.val == node.val) {
                total += right + 1;
                ret = Math.max(ret, right + 1);
            }
        }
        max = Math.max(max, total); //z这里只需要total,不再是 left + right
        return ret;// ret之前以及处理后了， 因为有的是 left， 有的是 left + 1, 所以之前就放好比较方便
    }
    
    	
    
    // Time: O(n)
    // Space: O(h)
    // bottom-up ==> 走到最低，得到 left, right, 然后返回根据 val的情况累加求length
	// a这实际上是一个 post-order的过程， 见下面 postOrder（root）
    public int longestUnivaluePathRecursive(TreeNode root) {
        if (root == null)
            return 0;
        dfs(root);
        // postOrder(root);
        return max;
    }
    
    int max = Integer.MIN_VALUE;
    // bottom-up, 分别处理左右子树
    public int recursive(TreeNode node) {
        if (node == null)
            return 0;
        int left = dfs(node.left);
        int right = dfs(node.right);
        int l = 0, r = 0; // 初始值从0 开始
        if (node.left != null && node.left.val == node.val) {
            l += left + 1;//a往左边走，一样的话 +1
        }
        if (node.right != null && node.right.val == node.val) {
            r += right + 1;//a往右边走，一样的话 +1
        }
        max = Math.max(max, l + r);// bottom-up往回， max就是直接相加，如果root,left,right三者中不是都一样，left,right有一个为0
        return Math.max(l, r); // a往回走时，其当前的length是left，right中的大值，再根据root.val再做处理
    }
    
    public int postOrder(TreeNode node) {
        if (node == null)
            return 0;
        int left = postOrder(node.left);
        int right = postOrder(node.right);
        
        if (node.left != null && node.val == node.left.val) {
            left += 1;//a一样就可以 +1, 以此往回
        } else {
            left = 0; //a不一样，当前值0
        }
        if (node.right != null && node.val == node.right.val) {
            right += 1;
        } else {
            right = 0;
        }
        max = Math.max(max, left + right); //a上面的结论，这里相加就很明显了
        return Math.max(left, right);
    }
    
    public  TreeNode buildTree() {
    	TreeNode root = new TreeNode(4);
    	TreeNode left = new TreeNode(4);
    	TreeNode right = new TreeNode(5);
    	root.left = left;
    	root.right = right;
    	root.left.left = new TreeNode(4);
    	root.left.right = new TreeNode(4);
    	root.right.right = new TreeNode(5);
    	root.left.left.left = new TreeNode(4);
    	root.left.left.right = new TreeNode(4);
    	return root;
    }
    
    public static void main(String[] args) {
    	TreeNode root = new LongestUnivaluePath().buildTree();
    	new LongestUnivaluePath().longestUnivaluePath(root);
    }
}
