package amazon;

//Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
//
//Example 1:
//
//Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
//Output: true
//Example 2:
//
//Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
//Output: false
public class InterleavingString {

	// 本质跟 LongestCommonSubsequence是一样的
	// 只是这里在字符相同时候，要分两种情况，1） 移动idx1, 2)移动idx2
	// 这里的子问题同样是 （idx1, idx2）的时候，其两个字串是否是 idx3的时候的interleaving string
	
	public boolean isInterleave(String s1, String s2, String s3) {
        if (s1 == null && s2 == null)
            return false;
        if (s1.length() + s2.length() != s3.length())
            return false;
        Boolean[][] memo = new Boolean[s1.length()][s2.length()];
        return dp(s1, s2, s3, 0, 0, 0, memo);
    }
    
	// Time: O(m * n), 有 m * n 个子问题
    public boolean dp(String s1, String s2, String s3, 
                      int idx1, int idx2, int idx3, Boolean[][] memo) {
        if (idx1 == s1.length() && idx2 == s2.length() 
           && idx3 == s3.length())
            return true;
        if (idx1 >= s1.length()) // 当某一个串遍历完了，其结果就是看剩下那个串的子串是否和s3剩下的子串一样
             return s2.substring(idx2, s2.length()).equals(s3.substring(idx3, s3.length()));
        if (idx2 >= s2.length())
            return s1.substring(idx1, s1.length()).equals(s3.substring(idx3, s3.length()));
        
        if (memo[idx1][idx2] != null)
            return memo[idx1][idx2];
        
        char c1 = s1.charAt(idx1);
        char c2 = s2.charAt(idx2);
        char c3 = s3.charAt(idx3);
        
        if (c1 == c2 && c1 == c3) {
            if (dp(s1, s2, s3, idx1 + 1, idx2, idx3 + 1, memo)
               || dp(s1, s2, s3, idx1, idx2 + 1, idx3 + 1, memo)) {
                memo[idx1][idx2] = true;
                return true;
            }
        } else if (c1 != c2 && c1 == c3) {
            if (dp(s1, s2, s3, idx1 + 1, idx2, idx3 + 1, memo)) {
                memo[idx1][idx2] = true;
                return true;
            }
        } else if (c1 != c2 && c2 == c3) {
            if (dp(s1, s2, s3, idx1, idx2 + 1, idx3 + 1, memo)) {
                memo[idx1][idx2] = true;
                return true;
            }
        }
        
        memo[idx1][idx2] = false;
        
        return false;
    }
    
    // 2D table
    // 画一个类似求两个单词之间距离的table
    public boolean isInterleave2(String s1, String s2, String s3) {
        if (s1 == null && s2 == null)
            return false;
        if (s1.length() + s2.length() != s3.length())
            return false;
        boolean[][] dp = new boolean[s1.length() + 1][s2.length() + 1];
        dp[0][0] = true;
        // 初始化, 其中一个是0的时候，即只看另外一个string， 跟s3比较
        for (int i = 1; i <= s1.length(); i++) {
            if(s1.charAt(i - 1) == s3.charAt(i - 1))
                dp[i][0] = true;
            else
                break; // 这里一定要break， 当碰到两个不一样了，后面就不用比较了，肯定都是false
            	// 比如 （aabc, aadc）, 符合条件的（true）只能到 aa 为止
        }
        for (int i = 1; i <= s2.length(); i++) {
            //dp[0][i] = s2.charAt(i - 1) == s3.charAt(i - 1);
            if (s2.charAt(i - 1) == s3.charAt(i - 1))
                dp[0][i] = true;
            else
                break;
        }
        //int k = 0;
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
            	// 此时其关系就是，如果当前字符一样了，就看之前的子串是否满足
            	//  比如 （aa|cde, aac|bf, aaaac|...）这里要看s2的c了，发现s3位置上也是c，就看
            	//		当s2前面一个时候，即a,[2][2]的位置时，其是不是interleaving string, 
                if (s1.charAt(i - 1) == s3.charAt(i + j - 1))
                    if (dp[i - 1][j])
                        dp[i][j] = true;
                    //dp[i][j] = dp[i - 1][j];
                if (s2.charAt(j - 1) == s3.charAt(i + j - 1))
                    if (dp[i][j - 1])
                        dp[i][j] = true;
                    //dp[i][j] = dp[i][j - 1];
            }
        }
        return dp[s1.length()][s2.length()];
    }
    
}
