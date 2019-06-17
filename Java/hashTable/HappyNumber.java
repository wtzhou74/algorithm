package hashTable;

import java.util.HashSet;
import java.util.Set;

/**
 * A HAPPY NUMBER is a number defined by the following process: Starting with any positive integer,
 *  replace the number by the sum of the squares of its digits, and repeat the process until the number
 *   equals 1 (where it will stay), or it loops endlessly in a cycle
 *  which does not include 1. Those numbers for which this process ends in 1 are happy numbers.
 * 
 * */
public class HappyNumber {

	Set<Integer> set = new HashSet<>();
    public boolean isHappy(int n) {
        int result = 0;
        while (n > 0) {
            result += (n % 10 ) * (n % 10);
            
            n = n / 10;
        }
        // last digit
        //result += (n % 10 ) * (n % 10);// % - get last digit  / - get remaining digits
        
        if (result == 1) return true;
        else if (set.contains(result)) return false;// check cycle
        else  set.add(result);
        
        return isHappy(result);// recursion
        
    }
}
