package hashTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given a binary tree, return all duplicate subtrees. For each kind of duplicate subtrees, 
 * you only need to return the root node of any one of them.
 * Two trees are duplicate if they have the same structure with same node values.
 * 
 * 
 * 1. typical REDESIGN KEY FOR HASHMAP， and then group the items with same key
 * 2. Encoding SUBTREE, like [left+right+val]
 * */
public class FindDuplicateSubtrees {

	public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        //if (root == null)  {return null;}
        Map<String, List<TreeNode>> subTreeMap = new HashMap<>();
        List<TreeNode> result = new ArrayList<>();
        helper(root, subTreeMap);
        // TRAVERSE EACH GROUP, the items within same group are the same subtrees
        for (List<TreeNode> group : subTreeMap.values()) {
            if (group.size() > 1) {
                result.add(group.get(0));
            }
        }
        
        return result;
    }
    
	// serialize the subtrees
    public String helper(TreeNode root, Map<String, List<TreeNode>> subTreeMap) {
        if (root == null) return "#";
        String encoding = "[" + helper(root.left, subTreeMap) + helper(root.right, subTreeMap) + root.val + "]";
//        if (!subTreeMap.containsKey(encoding)) {
//            List<TreeNode> list = new ArrayList<>();
//            list.add(root);
//            subTreeMap.put(encoding, list);
//        } else subTreeMap.get(encoding).add(root);
        // equals the following code
        if (!subTreeMap.containsKey(encoding)) 
        	subTreeMap.put(encoding, new ArrayList<TreeNode>());// put encoding with EMPTY LIST
        	subTreeMap.get(encoding).add(root);// add item WITHOUT ELSE
        
        
        return encoding;
    }
}
