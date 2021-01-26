package treesAndGraphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//You are given an integer array nums and you have to return a new counts array. The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].
//
//Example:
//
//Input: [5,2,6,1]
//Output: [2,1,1,0] 
//Explanation:
//To the right of 5 there are 2 smaller elements (2 and 1).
//To the right of 2 there is only 1 smaller element (1).
//To the right of 6 there is 1 smaller element (1).
//To the right of 1 there is 0 smaller element.

public class CountOfSmallerNumbersAfterSelf {
	
	public class TreeNode {
		int val;
		int count;
		TreeNode left;
		TreeNode right;
		public TreeNode(int val, int count) {
			this.val = val;
			this.count = count;
		}
	}
	
	// Trick : Starting from right, so once we knew right number's result, move left, just need to check current and previous result
	// Time: (O(nlgn))   // build BST in lgn
	public List<Integer> countSmaller(int[] nums) {
		if (nums.length == 0) {
			return new ArrayList<>();
		}
		//List<Integer> res = new ArrayList<>();
		TreeNode root = new TreeNode(nums[nums.length - 1], 0);// right-most item
		Integer[] temp = new Integer[nums.length];
        Arrays.fill(temp, 0);
		for (int i = nums.length - 2; i >= 0; i--) {
			// build BST and calculate the smaller number of right
			//res.add(buildAndCal(root, nums[i]));// reverse
			temp[i] = buildAndCal(root, nums[i]);
		}
		return Arrays.asList(temp);
	}
	
	public int buildAndCal(TreeNode node, int num) {
		int count = 0;// record the number of smaller right items
		while (true) {
			if (node.val > num) {
				node.count++;
				if (node.left == null) {
					//node.count++;// REQUIRED, for the remaining elements
					node.left = new TreeNode(num, 0);
					//return node.count;//WRONG, since node.count++; it is used to calculate the subsequent LARGER items
					return count;// the smaller number of right items is same as node's
				} else {
					//node.count++;// since the num is smaller than node
					node = node.left;
				}				
			} else {
				count += node.count;
				if (num != node.val) count++;// if different, add a new count
				if (node.right == null) {
					//count += node.count;
					node.right = new TreeNode(num, 0);
					return count;// add node.val itself since it is also smaller than num
				} else {
					//count += node.count;
					node = node.right;
				}
			}
		}
	}
	
//	SAME AS ABOVE
//	public int buildAndCal(TreeNode node, int num) {
//		int count = 0;// record the number of smaller right items
//		while (true) {
//			if (node.val > num) {
//				node.count++;
//				if (node.left == null) {
//					//node.count++;// REQUIRED, for the remaining elements
//					node.left = new TreeNode(num, 0);
//					return count;// the smaller number of right items is same as node's
//				} else {
//					//node.count++;// since the num is smaller than node
//					node = node.left;
//				}				
//			} else {
//				count += node.count;
//				if (num != node.val) count++;// if different, add a new count
//				if (node.right == null) {
//					//count += node.count;
//					node.right = new TreeNode(num, 0);
//					return count;// add node.val itself since it is also smaller than num
//				} else {
//					//count += node.count;
//					node = node.right;
//				}
//			}
//		}
//	}
	// Brute force
	// Time: O(n^2)
	public List<Integer> trivialSol(int[] nums) {
        if (nums.length == 0) return new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int count = 0;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[i]) count++;
            }
            list.add(count);
        }
        return list;
    }
}
