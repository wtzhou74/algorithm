package arrayString;

/**
 * Given an input string, reverse the string word by word.
 * Note:
 * A word is defined as a sequence of non-space characters.
 * Input string may contain leading or trailing spaces. However, 
 * your reversed string should not contain leading or trailing spaces.
 * You need to reduce multiple spaces between two words to a single space in the reversed string.
 * */
public class ReverseWords1 {

	public static String reverseWords1Sol(String s) {
		
		String[] strs = s.trim().split("\\s+");
		int i = 0, j = strs.length;
		while (i < j) {
			String temp = strs[j];
			strs[j] = strs[i];
			strs[i] = temp;
			i++;
			j--;
		}
		
		StringBuilder strBui = new StringBuilder();
		for (String str : strs) {
			strBui.append(str + " ");
		}
		
		return strBui.toString().trim();
	}
	
	public static String reverseWords1SolOptimized(String s) {
		String[] strs = s.trim().split("\\s+");
		StringBuilder strBuilder= new StringBuilder();
		for (int i = strs.length - 1; i >=0 ; i--) {
			strBuilder.append(strs[i] + " ");
		}
		return strBuilder.toString().trim();
	}
	
	// s.lastIndexOf(String s, int fromIndex)
}
