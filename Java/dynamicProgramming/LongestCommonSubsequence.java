package dynamicProgramming;

import java.util.Arrays;

//Given two strings text1 and text2, return the length of their longest common subsequence.
//
//A subsequence of a string is a new string generated from the original string with some characters(can be none) deleted without changing the relative order of the remaining characters. (eg, "ace" is a subsequence of "abcde" while "aec" is not). A common subsequence of two strings is a subsequence that is common to both strings.
//
// 
//
//If there is no common subsequence, return 0.
//
// 
//
//Example 1:
//
//Input: text1 = "abcde", text2 = "ace" 
//Output: 3  
//Explanation: The longest common subsequence is "ace" and its length is 3.
//Example 2:
//
//Input: text1 = "abc", text2 = "abc"
//Output: 3
//Explanation: The longest common subsequence is "abc" and its length is 3.
//Example 3:
//
//Input: text1 = "abc", text2 = "def"
//Output: 0
//Explanation: There is no such common subsequence, so the result is 0.
// 
//
//Constraints:
//
//1 <= text1.length <= 1000
//1 <= text2.length <= 1000
//The input strings consist of lowercase English characters only.
public class LongestCommonSubsequence {

	// top -down
	public int longestCommonSubsequence(String text1, String text2) {
        if (text1.length() == 0 || text2.length() == 0)
            return 0;
        // 子问题： s1字串，s2的字串， 比如： s1: "abcde", "bcd" ==> ([a],[b]), ([abc],[bc]) 等
        //		==> 对不同子问题，其对应的结果, 一共会有 s1.len * s2.len个子问题
        int[][] memo = new int[text1.length()][text2.length()];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        
        return dp(0, 0, text1, text2, memo);
    }
    
	// Time : O(m*n * n), 两个字符串，有 m * n pairs, 同时对（每个pair）每个在s1种的字符，都要到 s2中去看存不存在，n次比较
    public int dp(int idx1, int idx2, String text1, String text2, int[][] memo) {
        if (idx1 >= text1.length() || idx2 >= text2.length()) {
            return 0;
        }
        if (memo[idx1][idx2] != -1) {
            return memo[idx1][idx2];
        }
        
        char c = text1.charAt(idx1);
        // 两种情况： 1） 当前字符不是solution中的一个， 此时 idx1 + 1, idx2不动，判断下一个
        //		2） 如果当前字符是 solution中的一个， 此时 idx1, idx2 都同时往前移动一个， 对剩下的字符进行判断
        
        // if c is not a character of solution
        int s1 = dp(idx1 + 1, idx2, text1, text2, memo);
        int s2 = 0;
        int idx = text2.indexOf(c, idx2);
        if (idx != -1) { // 如果当前字符不在第二个字符中，没必要判断了
            s2 = 1 + dp(idx1 + 1, idx + 1, text1, text2, memo);
        }
        
        // 上面部分可以优化成  ===> 这样我们只需做一个串的长度的递归  ===> Time: O(m * n) 共有 m * n subproblems
//        int s = 0;
//			// 当前两个字符一样，同时往后移
//        if (text1.charAt(idx1) == text2.charAt(idx2)) {
//            s = 1 + dp(idx1 + 1, idx2 + 1, text1, text2, memo);
//        } else {
//			// 不一样，有两种情况 1） 移动idx1, 2) 移动idx2， 取大值   =====> 对应的也是iterative solution
//            s = Math.max(dp(idx1 + 1, idx2, text1, text2, memo), (这里不能+1,不一样，该字符就不在这长度内)
//                        dp(idx1, idx2 + 1, text1, text2, memo));
//        }
        
        memo[idx1][idx2] = Math.max(s1, s2); // 取两种情况下大的结果
        return memo[idx1][idx2];
    }
    
    
    // bottom- up ===> Time(O(m * n))
    public int longestCommonSubsequence2(String text1, String text2) {
        if (text1.length() == 0 || text2.length() == 0)
            return 0;
        int[][] dp = new int[text1.length() + 1][text2.length() + 1]; //因为子问题是子串字串的比较
        										// 且下面有 -1 操作
        // for (int[] row : memo) {
        //     Arrays.fill(row, -1);
        // }
        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                char c1 = text1.charAt(i - 1); // 因为这里是从1开始的，字符的位置，取它需要换成索引
                char c2 = text2.charAt(j - 1);
                if (c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1] + 1; // 所以不能从0开始
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            } 
        }
        return dp[text1.length()][text2.length()];
    }
}
