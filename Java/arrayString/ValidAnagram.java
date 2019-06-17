package arrayString;

/**
 * Two words are said to be anagrams, if both the words 
 * contain SAME SET of characters with all original letters exactly once
 * */
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
        for (char c : s.toCharArray()) bucket[c - 'a']++;
        // remove character(s) from the specific position
        for (char c : t.toCharArray()) bucket[c - 'a']--;
        for (int n : bucket) if (n > 0) return false;
        return true;
        
        // solution 3
        // Arrays.sorted(chars)
        // Arrays.equals(chars1, chars2)
    }
}
