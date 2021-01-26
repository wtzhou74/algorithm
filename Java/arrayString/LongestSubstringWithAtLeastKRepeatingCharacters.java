package arrayString;

import java.util.Arrays;

//Find the length of the longest substring T of a given string (consists of lowercase letters only) such that every character in T appears no less than k times.
//
//Example 1:
//
//Input:
//s = "aaabb", k = 3
//
//Output:
//3
//
//The longest substring is "aaa", as 'a' is repeated 3 times.
//Example 2:
//
//Input:
//s = "ababbc", k = 2
//
//Output:
//5
//
//The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
public class LongestSubstringWithAtLeastKRepeatingCharacters {

	//a 找到分割点
	public int divideConque(String s, int k) {
		if (s.length() == 0 || k > s.length()) return 0;
        return helper(s, 0, s.length() - 1, k);
	}
	
	// Time: O(nLgN) for best case;  worst case - O(n^2) 每次分 1，和剩余 n-1个
	// Space: 
	public int helper(String s, int start, int end, int k) {
        if (start > end) {
            return 0;
        }
        int[] count = new int[26];
        for (int i = start; i <= end; i++) {
            count[s.charAt(i) - 'a']++; // 统计在（start, end）之间每个字符出现的频数
        }
        
        for (int i = 0; i < 26; i++) {
        	// a找到某个字符其在字符串中，但个数不到k, 此时， 答案会在其左边或者右边， 所以D&Q 算法
            if (count[i] < k && count[i] > 0 ) {
                int pos = s.indexOf((char)('a' + i), start); // 找到该字符在原字符串中的位置
                // Process its LEFT side and RIGHT side
                return Math.max(helper(s, start, pos - 1, k), helper(s, pos + 1, end, k));
            }
        }
        
        return end - start + 1;
    }
	
	public int optimizedHelper(String s, int start, int end, int k) {
        if (end - start + 1 < k) {
            return 0;
        }
        int[] count = new int[26];
        for (int i = start; i <= end; i++) {
            count[s.charAt(i) - 'a']++;
        }
        
        int preStart = start;
        int res = 0;
        boolean noSplit = true;// to check if need to divide
        // a不再每次都做二分
        for (int i = start; i <= end; i++) {
            if (count[s.charAt(i) - 'a'] < k && count[s.charAt(i) - 'a'] > 0 ) {
                noSplit = false;//need to split
                res = Math.max(res, helper(s, preStart, i - 1, k));
                preStart = i + 1; // 下半部分从 i+1开始
            }
        }
        
        if (noSplit) return end - start + 1;
        return Math.max(res, helper(s, preStart, end, k)); //c处理另一半部分
    }
	
	// Sliding Window
	public int slidingWindow(String s, int k) {
        int [] counts = new int[26];
        int max = 0;
        // a对满足条件的字串，其包含的 unique chars 数目可以是 1 - 26, 因为该字符串只包含 小写字母
        // a遍历当unique = 1, 2, ...;a满足条件的 字串， 看哪个最大
        for(int u=1; u<=26;++u){
            Arrays.fill(counts,0);
            int left = 0;
            int right = 0;
            int unique=0;
            int kOrMore = 0;// 当前字符出现的次数
            // a分别对unique 是 1， 2， ... 做 sliding window 处理
            while(right<s.length()){
            	// unique字符没到数， 继续往右边走，加新的字符
                if(unique<=u){
                    char c = s.charAt(right);
                    int idx = (int)c-(int)'a';
                    counts[idx]++;
                    if(counts[idx]==1){
                        ++unique;
                    }
                    if(counts[idx]==k){
                        ++kOrMore;// 当前字符在字串中满足出现K或以上次数的要求
                    }
                    ++right;
                }
                else{// unique字符超过数目了， 需要处理left, 减少新字符
                    char o = s.charAt(left);
                    int idx = (int)o-(int)'a';
                    counts[idx]--;
                    if(counts[idx]==0){
                        --unique;
                    }
                    if(counts[idx]==k-1){
                        --kOrMore;
                    }
                    ++left;
                }
                // unique的字符数达到要求， 同时 其每个字unique字符的出现次数都达到k或以上，即 kOrMore == unique
                if(unique==u && kOrMore==unique){
                    max=Math.max(max,right-left);//每种满足情况的子串 中取最大
                }
            }
        }
        return max;
    }
	
	
	public int longestSubstring(String s, int k) {
        if (s.length() == 0 || k > s.length()) return 0;
        int i = 0;
        int max = Integer.MIN_VALUE;
        while (i + k <= s.length()) {
            int[] aux = new int[26];
            for (int j = i; j < s.length(); j++) {
                int idx = s.charAt(j) - 'a';
                aux[idx]++;
                
                boolean existed = true;
                for (int m = 0; m < 26; m++) {
                    if (aux[m] >= 1 && aux[m] < k) {
                        existed = false;
                        break;
                    }
                }
                if (existed)
                    max = Math.max(max, j - i + 1);
            }
            i++;
        }
        
        return max == Integer.MIN_VALUE ? 0 : max;
    }
	
	public static void main(String[] args) {
		new LongestSubstringWithAtLeastKRepeatingCharacters().slidingWindow("aabcde", 1);
	}
}
