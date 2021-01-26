package recursion;

import java.util.List;

// also refer to arrayString -> ReverseString.java
public class ReverseString {

	/*
	 * Write a function that reverses a string. The input string is given as an
	 * array of characters char[].
	 * 
	 * Do not allocate extra space for another array, you must do this by modifying
	 * the input array in-place with O(1) extra memory.
	 * 
	 * You may assume all the characters consist of printable ascii characters.
	 * 
	 * Example 1:

		Input: ["h","e","l","l","o"]
		Output: ["o","l","l","e","h"]
	Example 2:

		Input: ["H","a","n","n","a","h"]
		Output: ["h","a","n","n","a","H"]
	 */
	
	public void reverseString(char[] s) {
		if (s.length == 0) return;
		int i = 0, j = s.length - 1;
		while (i < j) {
			char temp = s[i];
			s[i] = s[j];
			s[j] = temp;
			
			i++;
			j--;
		}
	}
	
	public void iterativeSol(char[] s) {
		helper(0, s);
	}
	public void helper(int i, char[] s) {
		if (s == null || i >= s.length) return;
		// RECURRENCE RELATION
		// stringReverse(n) = stringReverse(s[1...n]) + print(0)
		helper(i + 1, s);
		// list.add(String.valueOf(s[i]));
		System.out.println(s[i]);
	}
	public static void main(String[] args) {
		char[] input = {'H','a','n','n','a','h'} ;
		new ReverseString().iterativeSol(input);
	}
	
}
