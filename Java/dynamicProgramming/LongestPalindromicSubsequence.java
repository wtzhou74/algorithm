package dynamicProgramming;

//Given a string s, find the longest palindromic subsequence's length in s. You may assume that the maximum length of s is 1000.
//
//Example 1:
//Input:
//
//"bbbab"
//Output:
//4
//One possible longest palindromic subsequence is "bbbb".
// 
//
//Example 2:
//Input:
//
//"cbbd"
//Output:
//2
//One possible longest palindromic subsequence is "bb".
// 
//
//Constraints:
//
//1 <= s.length <= 1000
//s consists only of lowercase English letters.
public class LongestPalindromicSubsequence {

	// palindrom，==> 左右两边一样 ， 但这里要的 sequence，不能直接用常规 palindromic的解法
	//			==> 既然两边一样，求一共长度，那么把原来s reverse， 就变成了求 s, rs 的 LCS了
	public int longestPalindromeSubseq(String s) {
        if (s.length() <= 1) return 1;
        //int mid = s.length() / 2;
        String rs = "";
        StringBuffer sb = new StringBuffer(s);
        rs = sb.reverse().toString();  // ====> 转变成为 LCS了
        int[][] dp = new int[s.length() + 1][s.length() + 1];
        for (int i = 1; i <= s.length(); i++) 
            for (int j = 1; j <= s.length(); j++) {
                char c1 = s.charAt(i - 1);
                char c2 = rs.charAt(j - 1);
                if (c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1] + 1; 
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
                
            }
        return dp[s.length()][s.length()];
    }
}
