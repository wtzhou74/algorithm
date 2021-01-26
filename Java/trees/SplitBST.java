package trees;

//Given a Binary Search Tree (BST) with root node root, and a target value V, split the tree into two subtrees where one subtree has nodes that are all smaller or equal to the target value, while the other subtree has all nodes that are greater than the target value.  It's not necessarily the case that the tree contains a node with value V.
//
//Additionally, most of the structure of the original tree should remain.  Formally, for any child C with parent P in the original tree, if they are both in the same subtree after the split, then node C should still have the parent P.
//
//You should output the root TreeNode of both subtrees after splitting, in any order.
//
//Example 1:
//
//Input: root = [4,2,6,1,3,5,7], V = 2
//Output: [[2,1],[4,3,6,null,null,5,7]]
//Explanation:
//Note that root, output[0], and output[1] are TreeNode objects, not arrays.
//
//The given tree [4,2,6,1,3,5,7] is represented by the following diagram:
//
//          4
//        /   \
//      2      6
//     / \    / \
//    1   3  5   7
//
//while the diagrams for the outputs are:
//
//          4
//        /   \
//      3      6      and    2
//            / \           /
//           5   7         1
//Note:
//
//The size of the BST will not exceed 50.
//The BST is always valid and each node's value is different.
public class SplitBST {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	// 有点BS找target的味道，但要遵循树的结构，而不是BST的排序顺序，故这里不用stack,- preorder
	
	// 每次按root分半
	// 分两种情况 1） <= V; 2) > V,
	//	-- 对情况1， 左子树的root就是当前root，而右子树那边，其节点可以都还在右子树，也可以部分到左子树去，这就为什么找到root后还要继续往下，确定是否有需要连到左子树上的节点
	//  -- 对情况2， 右子树的root就是当前root， 右子树的都在右子树，但要判断原来左子树的是否有节点需要挪到新的右子树上去，故继续往下遍历
	public TreeNode[] splitBST(TreeNode root, int V) {
        //if (root == null) return null;
        TreeNode curr = root;
        TreeNode leftTemp = null;//用以记录splitted后，左子树的root
        TreeNode rightTemp = null;//右子树的root
        TreeNode[] res = new TreeNode[2];
        while (curr != null) {
            if (curr.val > V) {//比V大，说明这个点可以是右子树的root
                if (rightTemp != null) {
                    rightTemp.left = curr;//如果之前有rightTemp了，此时又发现一个，拼接孩子节点
                }
                rightTemp = curr;
                curr = curr.left;//此时再往左边走，找更小的值； 而且再进入这个if的话，说明在左子树下找到
                				//新的比 V大的点，此时对原来的rightTemp, 当前点就是它的left,所以
                				//上面的if(rightTemp!=null); 要将left指向curr, 而不是right
                rightTemp.left = null;//这块设置为 null, 是因为当它为右子树的root, 由于是BST， 其左边都比V小，属于左子树的，所以切断
                // 而且这里要继续往下直到叶子节点，即便找到了对应的root, 是因为对某个root来说，其右子树的最左边的child，再分割后是跟root直接相连的
                // 而对左子树则正好相反
            } else {// 因为 <= 的都划分到左子树
                if (leftTemp != null) {
                    leftTemp.right = curr;//链接孩子节点
                }
                leftTemp = curr;//可能是左子树的 root
                curr = curr.right;//但还是要往右子树看，看是否值更大的也满足; 再进来的时候，找到合适的是在
                					// 相对于上一个 leftTemp的右子树了，所以上面要 leftTemp.right=curr，而不是left
                leftTemp.right = null;//同样如果是左子树的root, 切断 right, 因为right的值都比它大
            }
            
            // 取第一个就是， 因为遍历过程是一层层往下，在找到第一个的时候就是其子树的root，剩下往下找是链接其孩子节点
            if (res[0] == null && leftTemp != null) {
                res[0] = leftTemp; 
            }
            if (res[1] == null && rightTemp != null) {
                res[1] = rightTemp;
            }
        }
        
        
        return res;
    }
	
	// recursive solution
	
	public TreeNode[] splitBSTRecursive(TreeNode root, int V) {
        if (root == null) 
            return new TreeNode[]{null, null};
        TreeNode[] res = new TreeNode[2];//临时变量，每一层都有一组res, 递归往回，到最开始的一组就是结果
        if (root.val > V) {
            TreeNode[] leftRes = splitBST(root.left, V);//太大了， 往左边走 
            res[0] = leftRes[0];//左子树的root就是 leftRes[0]
            res[1] = root;//此时到某一个叶子节点， 然后递归往回，其当前root就是右子树的root,因为其比V大
            root.left = leftRes[1];//同时我们要把孩子节点连接好，root的值比较大，所以 leftRes[1]是其左孩子
        } else {
            TreeNode[] rightRes = splitBST(root.right, V);
            res[0] = root; // root值刚刚好， 那root就是左子树的节点，此时右子树的节点可以都还在右子树，也可以在左子树，所以要继续往下
            res[1] = rightRes[1]; // 右子树的root就是当前rightRes[1]
            root.right = rightRes[0];// 同时连接孩子节点
        }

        return res;
    }
	
	public TreeNode buildTree() {
		TreeNode root = new TreeNode(4);
		root.left = new TreeNode(2);
		root.right = new TreeNode(7);
		
		root.left.left = new TreeNode(1);
		root.left.right = new TreeNode(3);
		
		root.right.left = new TreeNode(5);
		root.right.right = new TreeNode(8);
		
		return root;
	}
	
	public static void main(String[] args) {
		TreeNode root = new SplitBST().buildTree();
		new SplitBST().splitBST(root, 6);
	}
}
