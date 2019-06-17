package arrayString;

import java.util.ArrayList;
import java.util.List;

/**
 * Write a function to find the longest common prefix string amongst an array of strings.
 * */
public class LongestCommonPrefix {

	public static String longestCommonPrefixSol(String[] strs)
	{
		if (strs.length == 0) {
			return "";
		}
		String shortest = strs[0];
        //List<String> remainings = new ArrayList<String>();
        int shortestIndex = 0;
        for (int i = 1; i < strs.length; i++) {
            int len = strs[i].length();
            if (len == 0) {
                return "";
            }
            if (len < shortest.length()) {
                shortest = strs[i];
                shortestIndex = i;
            }
        }
        
        
        StringBuilder strBuilder = new StringBuilder();
        char[] chars = shortest.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            boolean matchFlag = true;
            for (int i = 0; i < strs.length; i++) {
            	if (i == shortestIndex) continue;
                if (strs[i].charAt(j) != chars[j]) {
                    matchFlag = false;
                    break;
                }  
            }
            if (matchFlag) strBuilder.append(chars[j]);
            if (!matchFlag) break;
        }
        
        return strBuilder.toString();
	}
}
