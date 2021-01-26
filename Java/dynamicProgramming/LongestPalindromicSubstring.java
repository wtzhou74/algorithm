package dynamicProgramming;

//Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
//
//Example 1:
//
//Input: "babad"
//Output: "bab"
//Note: "aba" is also a valid answer.
//Example 2:
//
//Input: "cbbd"
//Output: "bb"


// NOTE:
// The KEY here is Starting from CENTER then expanding to both direction one by one
// When finding the center, there are 2*len in total, rather than len
public class LongestPalindromicSubstring {
	
	// DP的关系  <== 跟从中间两边往外扩是一样的   ===> **baab*** ==> b分别在 i,j的位置，
	// 												==> baab 是，那么 [i+1][j-1]的 aa 就必须是
	public String longestPalindromeDP2(String s) {
        if (s.length() <= 1) {
            return s;
        }
        String res = "";
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = true; // 对自己到自己，就字符本身的结果肯定是 true
        }
        for (int l = 1; l <= n; l++) { // 子字符串的长度 （可能palindrome的长度） // 这个是跨度，即里面的每一轮，先按跨度是1，即相邻的两个字符一判断，之后按跨度
        								// 为2的来判断，即每三个字符一判断，此时里面每两个的已得到结果了
            for (int i = 0; i < n - l; i++) { // i 可取的值此时只能是 0 到 n -l, 之后剩下 l, 
            							///  不能再取了， 否则 j 就溢出了   // 剩下的就不能超过 n - l的了，否则 j + (n - l) + l就会溢出了
                int j = i + l;  // 在区间 l 下， 此时 j 相对于 i 的值
                if (s.charAt(i) == s.charAt(j) 
                    && (l == 1 || dp[i+1][j-1])) { // ！！！l == 1的情况，如果前后[i,j]两个字符一样
                							// 此时不管 dp[i+1][j-1]了，因为此时 i+1 < j-1的，还没值呢，
                							// 默认是false，所以直接用该值会导致结果错误
                    dp[i][j] = true;
                    if (j - i + 1 > res.length())
                        res = s.substring(i, j + 1);
                }
            }
        }
        return res.length() == 0 ? s.charAt(0) + "" : res;// 上面结论最长是2，所以对最后res="",最终结果
        			// 取第一个字符就好， 因为对palindrom，至少会有一个值（长度为1），‘abc', 'a'就是结果
        			// 或者在上面处理时候，不去取res, 记录 i(startIdx)，以及对应的 l 就好，初始化 maxLen = 1
        			// 最后取  substring(startIdx, startIdx + maxLen)
        //return res;
    }
	
	// TIME: O(N^2)
	// SPACE: O(N^2)
	// DP的关系  <== 跟从中间两边往外扩是一样的   ===> **baab*** ==> b分别在 i,j的位置，
	// 												==> baab 是，那么 [i+1][j-1]的 aa 就必须是
	public String dpSol(String s) {
		if (s == null || s.length() == 0) return "";
		String res = "";
		int len = s.length();
		boolean[][] dp = new boolean[len][len]; // each subString => goal
		for (int i = len - 1; i >= 0; i--) { // !!!!!!! NOT (int i = 0; i < len; i++), otherwise, dp[i+1][j-1] will pick the DEFAULT value of previous solution.
			for (int j = i; j < len; j++) {
				// RELATION between DP[I] and DP[i-1], "baab" => "aa" => b+aa+b
				// j-i here will cover BASE CASE, e.g. dp[0][0] = true
				dp[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 3 || dp[i+1][j-1]);
				if (dp[i][j] && res.length() < j - i + 1) {
					res = s.substring(i, j + 1);
				}
			}
		}
		return res;
	}
	
	
	
	// 从中间往两边扩
	// TIME O(N^2)
	// SPACE O(1)
	// INDEX can be the one between two letters; abc => 2*6 in total  ==> but remember to get its corresponding INDEX in string.
		public String sol2(String s) {
			if (s.length() == 1) return s;
			int max = 0;
			String res = "";
			for (int i = 1; i < 2 * s.length(); i++) {
	            int index = 0, left = 0, right = 0;
	            
	            if (i % 2 == 0) {
	                index = i / 2 - 1;//left
	                // GET its corresponding index in String to get subString
	                left = index;   // 类似判断2个（偶数个）字符的palindrome)，从“中间”的两个开始往外扩
	                right = index + 1;
	            } else {
	                index = (i - 1) / 2; //middle
	                // GET its corresponding index in String to get subString
	                left = index;  // 奇数个的，从“中间”开始
	                right = index;
	            }
	            //String subString = "" + s.charAt(i);
	            while (left >= 0 && right < s.length()) {
	                String subString = s.substring(left, right + 1);
	                if (isPalindrome(subString)) {
	                    if (right - left + 1 > max) {
	                        max = right - left + 1;
	                        res = subString;
	                    }
	                    left--;
	                    right++;
	                }
	                else break;
	            }
	        }
	        return res;
		}

	// TLE
	public String longestPalindrome(String s) {
        if (s.length() == 1) return s;
        int max = 0;
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                String subString = s.substring(i, j);
//                String subString = "";
//                for (int k = i; k <= j; k++) {
//                    subString += s.charAt(k);
//                }
                if (isPalindrome(subString)) {
                    if (j - i + 1> max) {
                        max = j - i;
                        res = subString;
                    }
                }
            }
        }
        return res;
    }
	
	
    public boolean isPalindrome(String s) {
        if (s.length() == 1) return true;
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
    
    public static void main(String[] args) {
    	new LongestPalindromicSubstring().longestPalindromeDP2("ababd");
    }
}
