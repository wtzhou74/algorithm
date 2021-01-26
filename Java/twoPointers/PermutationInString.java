package twoPointers;

import java.util.HashMap;
import java.util.Map;

//Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1. In other words, one of the first string's permutations is the substring of the second string.
//
//		 
//
//Example 1:
//
//Input: s1 = "ab" s2 = "eidbaooo"
//Output: True
//Explanation: s2 contains one permutation of s1 ("ba").
//Example 2:
//
//Input:s1= "ab" s2 = "eidboaoo"
//Output: False
// 
//
//Note:
//
//The input strings only contain lower case letters.
//The length of both given strings is in range [1, 10,000].
public class PermutationInString {

	// a虽然是Permutation, 但实际是看 S2是否有含有S1的连续字串 (permutation是字符一样，但无顺序限制)  ===> 不需要列出所有Permutation的pairs, ==> Sliding Window
	// 由于是Permutation的结果 ==> 顺序无所谓，但字符串长度固定的  
	// =====> 1) 连续的；  2） window size 固定   ====> right - left = s1 所以我们只需要这个window内的子串是否符合条件就行
	// 需要两个 window
	public boolean checkInclusion(String s1, String s2) {
		Map<Character, Integer> map = new HashMap<>();
        int[] freq = new int[26];
        // O(l1)
        for (int i = 0; i < s1.length(); i++) {
            freq[s1.charAt(i) - 'a']++;
            map.put(s1.charAt(i), 0);
        }
        
        int i = 0, j = 0;
        // O(26 * (l2 - l1))
        while (j < s2.length()) {
            if (!map.containsKey(s2.charAt(j))) {
                //if (map.size() == 0) return true;
                j++;
                i = j; 
                ///// size固定，我们不需要从临近开始一个个判断
                for (int k = 0; k < 26; k++) {
                    if (freq[k] > 0)
                        map.put((char)(k + 'a'), 0);//重置Map
                }
                continue;
            } 
            int val = map.get(s2.charAt(j));
            if (val + 1 > freq[s2.charAt(j) - 'a']) {
                map.put(s2.charAt(i), map.get(s2.charAt(i)) - 1);
                i++;                
            } else {
                map.put(s2.charAt(j), val + 1);
                boolean isTrue = true;
                j++;
                for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                    if (freq[entry.getKey() - 'a'] != entry.getValue()) {
                        isTrue = false;
                        break;
                    }
                        
                }
                if (isTrue) return true;
            }
            
        }
        boolean res = true;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (freq[entry.getKey() - 'a'] != entry.getValue()) {
                res = false;
                break;
            }

        }
        return res;
        
    }
	
	// 1) window size 固定
	// 2) 字符是连续的
	public boolean checkInclusion1(String s1, String s2) {
        
        int[] freq1 = new int[26];
        //int[] freq2 = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            freq1[s1.charAt(i) - 'a']++;
            //freq2[s2.charAt(i) - 'a']++;
        }
        int i = 0, j = i + s1.length() - 1;
        while (j < s2.length()) { //按 指定size 移动
            String sub = s2.substring(i, j + 1);
            if (isPumutation(freq1, sub)) return true;
            i++;
            j++;
        }
        return false;
         
    }
    
	//判断window内的字符串是否满足条件
    public boolean isPumutation(int[] freq1, String s) {
        int[] freq = new int[26];
        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (freq[i] != freq1[i]) return false;
        }
        return true;
    }
    
    // 在判断windows内的字符串是否满足条件， 继续优化
    // Time: O(l1 + 26 * (l2 - l1))
    public boolean checkInclusion2(String s1, String s2) {
        
        if (s1.length() > s2.length()) return false;
        int[] freq1 = new int[26];
        int[] freq2 = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            freq1[s1.charAt(i) - 'a']++;
            freq2[s2.charAt(i) - 'a']++; // 记录第二个window内的字符
        }
        int i = 0, j = i + s1.length();
        while (j < s2.length()) {
            //String sub = s2.substring(i, j + 1);
            if (isPumutation(freq1, freq2)) return true;
            freq2[s2.charAt(j++) - 'a']++; //通过移动left, right， 改变 第二个window内的字符
            freq2[s2.charAt(i++) - 'a']--;
            // i++;
            // j++;
        }
        if (isPumutation(freq1, freq2)) return true; //判断两个window内的字符是否一样
        else return false;
         
    }
    
    public boolean isPumutation(int[] freq1, int[] freq2) {
        // int[] freq = new int[26];
        // for (int i = 0; i < s.length(); i++) {
        //     freq[s.charAt(i) - 'a']++;
        // }
        for (int i = 0; i < 26; i++) {
            if (freq2[i] != freq1[i]) return false;
        }
        return true;
    }
	
	public static void main(String[] args) {
		String s1 = "adc", s2 = "dcda";
		new PermutationInString().checkInclusion(s1, s2);
	}
}
