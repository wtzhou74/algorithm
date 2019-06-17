package hashTable;

import java.util.HashSet;
import java.util.Set;

/**
 * Input: "abcabcbb"
 * Output: 3 
 * Explanation: The answer is "abc", with the length of 3. 
 * */
public class LongestSubStringWithoutRepeatingCharacters {

	public int lengthOfLongestSubstring(String s) {
        int length = 0;
		int j = 0;
		String temp = "";
		for (int i = 1; i <= s.length(); i++) {
			temp = s.substring(j, i);
			if (i < s.length() && temp.indexOf(s.charAt(i)) >= 0) {
				if (length < temp.length()) {
					length = temp.length();
				}
				if (i+1 == s.length()) {
					break;
				}
				j++;
				i = j;
				temp = "";
			}
		}
		if (length < temp.length()) {
			length = temp.length();
		}
		return length;
    }
	
	/**
	 * DUPLICATIONS => set
	 * */
	public static int lengthOfLongestSubstring2(String s) {
        if (s == null || s.length() == 0) return 0;
        char[] chars = s.toCharArray();
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            Set<Character> set = new HashSet<>();
            set.add(chars[i]);
            for (int j = i + 1; j < s.length(); j++) {
                if (set.contains(chars[j])) {
                    //result = Math.max(result, count);
                    break;
                }
                else {
                    set.add(chars[j]);
                }
            }
            result = Math.max(result, set.size());
        }
        return result;
    }
	
	public static void main(String[] args) {
		String s = "pwwkew";
		lengthOfLongestSubstring2(s);
	}
}
