package amazon;

import java.util.HashMap;
import java.util.Map;

//Given a non-empty string, encode the string such that its encoded length is the shortest.
//
//The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times.
//
//Note:
//
//k will be a positive integer and encoded string will not be empty or have extra space.
//You may assume that the input string contains only lowercase English letters. The string's length is at most 160.
//If an encoding process does not make the string shorter, then do not encode it. If there are several solutions, return any of them is fine.
// 
//
//Example 1:
//
//Input: "aaa"
//Output: "aaa"
//Explanation: There is no way to encode it such that it is shorter than the input string, so we do not encode it.
// 
//
//Example 2:
//
//Input: "aaaaa"
//Output: "5[a]"
//Explanation: "5[a]" is shorter than "aaaaa" by 1 character.
// 
//
//Example 3:
//
//Input: "aaaaaaaaaa"
//Output: "10[a]"
//Explanation: "a9[a]" or "9[a]a" are also valid solutions, both of them have the same length = 5, which is the same as "10[a]".
// 
//
//Example 4:
//
//Input: "aabcaabcd"
//Output: "2[aabc]d"
//Explanation: "aabc" occurs twice, so one answer can be "2[aabc]d".
// 
//
//Example 5:
//
//Input: "abbbabbbcabbbabbbc"
//Output: "2[2[abbb]c]"
//Explanation: "abbbabbbc" occurs twice, but "abbbabbbc" can also be encoded to "2[abbb]c", so one answer can be "2[2[abbb]c]".
public class EncodeStringWithShortestLength {

	// 长度区间型动态规划   ===> dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j])
	//			===> 对string (i, j),分left, right部分
	// https://blog.csdn.net/u013325815/article/details/106914037
	public String encode(String s) {
        if (s == null) return null;
        if (s.length() < 4) return s;
        Map<String, String> memo = new HashMap<>();
        return helper(s, memo);
    }
    
    public String helper(String s, Map<String, String> memo) {
        if (s == null || s.length() < 5) // 2【a】合并后最小的长度
            return s;
        String encodeString = s;
        int min = Integer.MAX_VALUE; // 用以取当前长度更小的
        for (int i = 0; i < s.length() / 2 + 1; i++) { // 分两边，取半就可
            
            if (memo.containsKey(s))
                return memo.get(s);
            // 分两半，对left, right再分两半
            String left = s.substring(0, i + 1);
            String right = s.substring(i + 1, s.length());
            // 看左半段是不是右半段的prefix
            int repeatCounter = prefixCount(left, right);
            String temp = s; // 当前默认string,就是非压缩情况下
            //System.out.println(repeatCounter);
            if (repeatCounter > 0) { // 有重复，进行compress
            	// 这里重复的left, 即右边有 repeatCounter个左半段内容，按left 压缩，同时对剩余字符串递归再处理
                temp = (repeatCounter + 1) + "[" + helper(left, memo) + "]" 
                    + helper(right.substring(repeatCounter * left.length(), right.length()), memo);
            } else {
                //temp = left + helper(s.substring(i + 1, s.length()), memo);
                
            }
            // 对于其他情况（包括没有重复的）， 直接对left, right再做递归处理
            String temp2 = helper(left, memo) + helper(right, memo);
            // ！！！！！看这两种情况结果哪个更好 1） 有重复，按左半段压缩，递归剩余部分   2） 有/没有重复都不压缩，直接分别递归left,right半段
            if (temp.length() > temp2.length())
                temp = temp2; // 选出长度更短的作为当前的压缩结果
            if (temp.length() < s.length()) {
                if (temp.length() < min) {
                    encodeString = temp;
                    min = temp.length();
                }
            }
        }
        memo.put(s, encodeString);
        return encodeString;
    }
    
    public int prefixCount(String prefix, String s) {
        int count = 0;
        int startIdx = 0;
        while (startIdx < s.length()) {
            int idx = s.indexOf(prefix, startIdx);// prefix就是left半段
            if (startIdx != idx) break;
            startIdx = idx + prefix.length();
            count++;
        }
        return count;
    }
}
