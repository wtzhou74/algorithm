package dynamicProgramming;

//Given a string, your task is to count how many palindromic substrings in this string.
//
//The substrings with different start indexes or end indexes are counted as different substrings even they consist of same characters.
//
//Example 1:
//
//Input: "abc"
//Output: 3
//Explanation: Three palindromic strings: "a", "b", "c".
// 
//
//Example 2:
//
//Input: "aaa"
//Output: 6
//Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
// 
//
//Note:
//
//The input string length won't exceed 1000.
public class PalindromicSubstrings {

	// 思路： 从“假设”palindrome的中间出发， 往外扩，如果其左右两边字符一样，那它还是个Palindrom
	//		1) "a" 也是 palindrome
	//		2） 任何一个位置都可以是palindrome的center ==> abc ==> 2*3-1 个位置中任何一个[0,2n-1	], a,b之间也是一个
	//      3） left, right 要注意了， left = center/2, right = left + center % 2 
	//					不是简单的 center+1 或 -1， left,right位置必须在字符上，center可以在字符之间
	public int countSubstrings(String s) {
        if (s.length() == 1)
            return 1;
        int n = s.length();
        int count = 0;
        for (int center = 0; center <= 2*n - 1; center++) {
            int left = center / 2;
            int right = left + center % 2;
            while (left >= 0 && right < n
                   && s.charAt(left) == s.charAt(right)) {
                count++;
                left--;
                right++;
            }
        }
        return count;
    }
	
	// DP 解法
	public int countSubstringsDP(String s) {
        if (s.length() == 1)
            return 1;
        int n = s.length();
        int count = 0;
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
            count += 1;  // 把所有单个字符都计算一遍 ！！！
        }
        for (int l = 1; l <= n; l++){
            for (int i = 0; i < n - l; i++) {
                int j = i + l;
                if (s.charAt(i) == s.charAt(j)
                		&& (l == 1 || dp[i + 1][j - 1])) { // 注意对 l = 1 的情况
                    dp[i][j] = true;
                    count += 1;
                }
            }
        }
        return count;
    }
}
