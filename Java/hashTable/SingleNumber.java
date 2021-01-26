package hashTable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

/**
 * Given a non-empty array of integers, every element appears twice except for one. 
 * Find that single one.
 * */
public class SingleNumber {
	
	public int singleNumber(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        Set<Integer> set = new HashSet<Integer>();
        for (int i : nums) {
            if (set.contains(i)) {
                set.remove(i);
                continue;
            }
            set.add(i);
        }
        
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext())
        {
            return iterator.next();
        }
        return -1;
    }
	
	//  implement it without using extra memory
	// 	XOR: 0 ^ N = N
	//       N ^ N = 0
	// XOR is commutative and associative
	public int singleNumberSol(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        int x = 0;
        for (int num : nums) {
            x ^= num;
        }
        return x;
    }
	
}
