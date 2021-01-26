package twoPointers;

import java.util.HashMap;
import java.util.Map;

//Given a string, find the length of the longest substring without repeating characters.
//
//Example 1:
//
//Input: "abcabcbb"
//Output: 3 
//Explanation: The answer is "abc", with the length of 3. 
//Example 2:
//
//Input: "bbbbb"
//Output: 1
//Explanation: The answer is "b", with the length of 1.
//Example 3:
//
//Input: "pwwkew"
//Output: 3
//Explanation: The answer is "wke", with the length of 3. 
//             Note that the answer must be a substring, "pwke" is a subsequence and not a substring.

public class LongestSubstringWithoutRepeatingCharacters {

	// Brute force - Time: O(n^3), 其中 O（n）用于检查 [i, j]之间的repeat
	
	// Note: 1) 改变 i; 2) 再找到重复时注意与其重复的字符之前出现在哪里，是否是无效重复 （本质是需要remove掉 i 之前的）
	public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        int i = 0, j = 0;
        int max = Integer.MIN_VALUE;
        Map<Character, Integer> map = new HashMap<>();
        while (j < s.length()) {
            if (map.containsKey(s.charAt(j)) // 因为map内没做 remove, 所以 map.get会取出比 i小的值，但该值没用了
            		&& map.get(s.charAt(j)) >= i) {// 如果当前字符是在 i 之前的无效； 实际上在改变i时，要把 i之前的记录清掉，不管i之前的字符了
                //System.out.println(j + " " + i);
                max = Math.max(max, j - i);
                i = map.get(s.charAt(j)) + 1; // !!!找到重复了，就要更新 i; map.get()出的是第一次出现该字符的index
                //map.put(s.charAt(j), j);// ！！！！更新 已有字符的索引，否则还是在前面
            } //else {
                //map.put(s.charAt(j), j);
            //}
            map.put(s.charAt(j), j);//每次把最新的索引放进去
            j++;
        }
        return Math.max(max, j - i);
    }
	
	// 借用字符数组， 应用相同的逻辑
	public int lengthOfLongestSubstring2(String s) {
		if (s == null || s.length() == 0) return 0;
        int i = 0, j = 0;
        int max = Integer.MIN_VALUE;
        int[] index = new int[128]; // ASCII， 如果是 extended ASCII 是 [256]
        while (j < s.length()) {
            if (index[s.charAt(j)] > 0 && index[s.charAt(j)] >= i) {
                max = Math.max(max, j - i);
                i = index[s.charAt(j)];
            }
            index[s.charAt(j)] = j + 1; // +1 是因为数组初始值为0， 而上面的 if 判断 > 0
            j++;  
        }
        return Math.max(max, j - i);
    }
	
	
	public static void main(String[] args) {
		new LongestSubstringWithoutRepeatingCharacters()
			.lengthOfLongestSubstring2("abbbcab");
	}
}
