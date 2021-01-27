package amazon;

import java.util.HashMap;
import java.util.Map;

//You are given a binary tree in which each node contains an integer value.
//
//Find the number of paths that sum to a given value.
//
//The path does not need to start or end at the root or a leaf, but it must go downwards (traveling only from parent nodes to child nodes).
//
//The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.
//
//Example:
//
//root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
//
//      10
//     /  \
//    5   -3
//   / \    \
//  3   2   11
// / \   \
//3  -2   1
//
//Return 3. The paths that sum to 8 are:
//
//1.  5 -> 3
//2.  5 -> 2 -> 1
//3. -3 -> 11
public class PathSumIII {

	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		
		public TreeNode(int val) {
			this.val = val;
		}
	}
	
	// 参考 SubarraySum.java 只是这里换成了Tree, 那我们就 preorder一个个元素看有几个能符合条件
	// 核心是记住每个 accumulative sum, 通过 currSum - k 是否在(在map中，存preSums)且有几个计算总的几个符合条件
	// 但在这里由于是DFS， 我们需要递归往回，这时候需要删除currSum在map中的个数
	public int pathSum(TreeNode root, int sum) {
        if (root == null)
            return 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1); // 这个要加上，否则下面 sum==target then count++就得加上，否则就会漏掉 ==的情况
        helper(root, sum, 0, map);
        return count;
    }
    int count = 0;
    Map<Integer, Integer> map = new HashMap<>();

    public void helper(TreeNode root, int target, int sum, Map<Integer, Integer> map) {
        if (root == null) {
            return;
        }
        sum += root.val;
        // if (sum == target)
        //     count++;
        count += map.getOrDefault(sum - target, 0);
        map.put(sum, map.getOrDefault(sum, 0) + 1);
        // if (sum == target)
        //     count++;
        helper(root.left, target, sum, map);
        helper(root.right, target, sum, map);
        
        map.put(sum, map.get(sum) - 1); // 删除当前sum, 因为递归往回会走向另一分支
        //sum -= root.val;
    }
}
