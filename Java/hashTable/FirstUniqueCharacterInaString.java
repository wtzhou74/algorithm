package hashTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirstUniqueCharacterInaString {

	// need to know INDEX and VALUE => HashMap
	// need to know the SEQUENCE => list
	public int firstUniqChar(String s) {
        if (s == null || s.length() == 0) return -1;
        Map<Character, Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < s.length(); i++) {
            if (!map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), i);
                list.add(i);
            } else {
                list.remove(map.get(s.charAt(i)));
            }
        }
        
        return list.size() == 0 ? -1 : list.get(0);
        
    }
	
	public int firstUniqCharSol(String s) {
        if (s == null || s.length() == 0) return -1;
        
        int result = Integer.MAX_VALUE;
        // NOTE: each character should be within (a,z)
        // now for this String, we just need to focus on English character (a - z)
        for (char c = 'a'; c <= 'z'; c++) {
            int curInd = s.indexOf(c);
            // curInd != s.lastIndexOf(c) means DUPLICATION
            if (curInd != -1 && curInd == s.lastIndexOf(c) && curInd < result) {
                result = curInd;
            }
        }
        
        return result == Integer.MAX_VALUE ? -1 : result;
        
    }
    
	
	public static void main(String[] args) {
		//firstUniqChar("aadadaad");
	}
}
