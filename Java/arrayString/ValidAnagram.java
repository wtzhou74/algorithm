package arrayString;

import java.util.HashMap;
import java.util.Map;

/**
 * Two words are said to be anagrams, if both the words 
 * contain SAME SET of characters with all original letters exactly once
 * */

//  WAYS TO DELTE THE SAME CHARs => map.remove(key); sb.deleteCharAt(int)
public class ValidAnagram {
	public boolean isAnagram(String s, String t) {
        // if (s == null || t == null) return false;
        // if (s.length() != t.length()) return false;
        // StringBuilder str = new StringBuilder(t);
        // char[] chars = s.toCharArray();
        // for (char c : chars) {
        //     if (str.indexOf("" + c) == -1) return false;
        //     else {
		//			// StringBuilder indexOf param is String, not character
        //         str.deleteCharAt(str.indexOf("" + c));
        //     }
        // }
        // return str.toString().isEmpty();
        
        if (s == null || t == null) return false;
        if (s.length() != t.length()) return false;
        int[] bucket = new int[128];
        
        // place each character onto specific position
        for (char c : s.toCharArray()) bucket[c - 'a']++;// THE DEFAULT VALUE OF ITEMs within INT[] is 0
        // remove character(s) from the specific position
        for (char c : t.toCharArray()) bucket[c - 'a']--;
        for (int n : bucket) if (n > 0) return false;
        return true;
        
        // solution 3
        // Arrays.sorted(chars)
        // Arrays.equals(chars1, chars2)
    }
	
	public boolean sol2(String s, String t) {
		if (s.length() != t.length()) return false;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
        	// fetch previous value
        	// Integer val = map.get(s.charAt(i));
            Integer temp = map.putIfAbsent(s.charAt(i), 1);
            if (temp != null) map.put(s.charAt(i), temp + 1);
        }
        for (int j = 0; j < t.length(); j++) {
            if (map.get(t.charAt(j)) != null) {
                int temp = map.get(t.charAt(j)) - 1;
                if (temp == 0) map.remove(t.charAt(j));
                else map.put(t.charAt(j), temp);
            } else return false;
        }
        return map.size() > 0 ? false : true;
	}
	
	public static void main(String[] args) {
		new ValidAnagram().isAnagram("anagram", "nagaram");
	}
}
