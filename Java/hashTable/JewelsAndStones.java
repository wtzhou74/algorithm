package hashTable;

import java.util.HashSet;
import java.util.Set;

public class JewelsAndStones {

	public int numJewelsInStones(String J, String S) {
        if (J == null || J.length() == 0) {
            return 0;
        }
//        Set<Character> jewelSet = new HashSet<>();
//        for (char j : J.toCharArray()) {
//            jewelSet.add(j);
//        }
//        int count = 0;
//        for (char s : S.toCharArray()) {
//            if (jewelSet.contains(s)) count++; 
//        }
        
        // alternative
        int count = 0;
        for (char s : S.toCharArray()) {
            if (J.indexOf(s) != -1) {
                count++;
            }
        }
        
        return count;
    }
	
}
