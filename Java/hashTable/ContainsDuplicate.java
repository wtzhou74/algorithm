package hashTable;

import java.util.HashSet;
import java.util.Set;

/**
 * USAGE OF HASHSET for DUPLICATION
 * */
public class ContainsDuplicate {

	public boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) return false;
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) {
                return true;
            }
            set.add(num);
        }
        
        return false;
    }
}
