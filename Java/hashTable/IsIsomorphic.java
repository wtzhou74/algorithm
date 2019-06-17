package hashTable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given two strings s and t, determine if they are isomorphic.
 * Two strings are isomorphic if the characters in s can be replaced to get t.
 * All occurrences of a character must be replaced with another character while 
 * preserving the order of characters. 
 * No two characters may map to the same character but a character may map to itself.
 * 
 *  s = "egg", t = "add" => true
 * */
public class IsIsomorphic {

	public boolean isIsomorphic(String s, String t) {
		char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        Set<Character> sSet = new HashSet<>();
        Set<Character> tSet = new HashSet<>();
        for (int i = 0; i < sChars.length; i++) {
            sSet.add(sChars[i]);
            tSet.add(tChars[i]);
        }
        // check two characters mapping to the same character
        if (sSet.size() != tSet.size()) return false;
        
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < sChars.length; i++) {
            if (map.containsKey(sChars[i])) {
                int preIndex = map.get(sChars[i]);
                if (tChars[i] != tChars[preIndex]) return false;
            }
            map.put(sChars[i], i);
        }
        
        return true;
    }
	
	public boolean isIsomorphicSol(String s, String t) {
        Map<Character, Integer> sMap = new HashMap<>();
        Map<Character, Integer> tMap = new HashMap<>();
        
        // Should be Integer, not int
        for (Integer i = 0; i < s.length(); i++) {
            // If the map previously contained a mapping for the key, the old value is replaced.
        	// It returns the previous value associated with Key, or null if there was no mapping
        	// for the key.
        	if (sMap.put(s.charAt(i), i) != tMap.put(t.charAt(i), i)) {// return previous value if key exisited
                return false;
            }
        }
        return true;
    }
}
