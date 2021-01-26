package twoPointers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//You are given a string, s, and a list of words, words, that are all of the same length. Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening characters.
//
//
//
//Example 1:
//
//Input:
//  s = "barfoothefoobarman",
//  words = ["foo","bar"]
//Output: [0,9]
//Explanation: Substrings starting at index 0 and 9 are "barfoo" and "foobar" respectively.
//The output order does not matter, returning [9,0] is fine too.
//Example 2:
//
//Input:
//  s = "wordgoodgoodgoodbestword",
//  words = ["word","good","best","word"]
//Output: []
public class SubstringWithConcatenationOfAllWords {

	public List<Integer> findSubstring(String s, String[] words) {
		if (words.length ==0) return new ArrayList<>();
        if (s.length() < words.length * words[0].length()) return new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> aux = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
            aux.put(word, aux.getOrDefault(word, 0) + 1);
        }
        int i = 0, j = i + words[0].length();
        List<Integer> res = new ArrayList<>();
        while (j <= s.length()) { //a这里 j 一直用于 substring(), 可以 ==length;
            String sub = s.substring(i, j);
            if (!aux.containsKey(sub)) {
                i++;
                j++;
            } else {
                int temp = i;
                while (j <= s.length()) { // a需要等于, 用于 substring()
                    if (!aux.containsKey(s.substring(temp, j))) {
                    	// reset aux with original map
                        for (Map.Entry<String, Integer> entry : map.entrySet()) {
                            aux.put(entry.getKey(), entry.getValue());
                        }
                        i++;
                        j = i + words[0].length();
                        break;
                    }
                    aux.put(s.substring(temp, j), aux.get(s.substring(temp, j)) - 1);
                    if (aux.get(s.substring(temp, j)) == 0)
                        aux.remove(s.substring(temp, j));
                    if (aux.size() == 0) {
                    	// reset aux with original map
                        for (Map.Entry<String, Integer> entry : map.entrySet()) {
                            aux.put(entry.getKey(), entry.getValue());
                        }
                        res.add(i);
                        //i = j; //a当找到 solution后，下一个起始点是 i + 1, 不是 solution的下一个点
                        		// a比如 “AAA”, ["AA"]
                        i = i + 1; //a每个点都要作为 起点 去校验
                        j = i + words[0].length();
                        break;
                    }
                    temp = j;
                    j = temp + words[0].length();
                }
            }
        }
        return res;
    }
	
	// a思路跟 MinimumWindowSubstring.java一样  ==> 只是这里以word为单位，且window.size==words.len * wordLen
	// aTricky是只需要校验前 wordLen就行， 不需要 s.len - wordLen; 这样会有重复
	public List<Integer> findSubstringOptimized(String s, String[] words) {
        if (s == null || s.length() == 0 || words.length == 0)
            return new ArrayList<>();
        int wordLen = words[0].length();
        //int count = 0;
        List<Integer> res = new ArrayList<>();
        // a从i=0开始，这样内部才能以 word为单位，否则会错过一些solution
        // a但不能check到 i < s.length() - wordLen, 这样会有重复
        for (int i = 0; i < wordLen; i++) { // 从每个 i 开始检验，这样在内部，left就可以每 wordLen一跳，不是 ++; 
        									// a而且这里实际只需 [0, wordLen]就够，这样后面的处理能包含所有的solution
            Map<String, Integer> map = new HashMap<>();
            //a每次开始都需要初始化map, 及 counter, 相当于重新找
            for (String word : words) {
                map.put(word, map.getOrDefault(word, 0) + 1);
            }
            int left = i, right = i, count = 0;
            // a按step=wordLen找
            while (right + wordLen <= s.length()) {
                String sub = s.substring(right, right + wordLen);
                if (map.containsKey(sub) && map.get(sub) > 0)
                    count++;// a找到要的word, count++；
                map.put(sub, map.getOrDefault(sub, 0) - 1);// 这里都减1，不管这个word是不是在map中，
                											// a这样不在map中的值会变负数，不会影响后面的处理
                right += wordLen;
                
                while (count == words.length) {// 如果找足够的words， 但此时不一定是 solution "ABCDEFG",["AB", "EF"]
                    if (right - left == words.length * wordLen) {
                        res.add(left); // 如果是，那把起点放进去
                    }
                    // a这里的removeStr包括原本在 map的以及后续加进去的
                    String removeStr = s.substring(left, left + wordLen);// 以word为单位remove掉前面的word
                    if (map.get(removeStr) >= 0) // 如果是map中的，count--, 说明我们需要这个word,且需要跳出循环去找了
                        count--;
                    map.put(removeStr, map.get(removeStr) + 1);// 并把word恢复回去，对原本不在map中的word,其值最大只能到0
                    left += wordLen;
                }
            }
        }
        return res;
    }
	
	public static void main(String[] args) {
		String s = "AAAA";
		String[] words = new String[] {
				"AA"
		};
		new SubstringWithConcatenationOfAllWords().findSubstringOptimized(s, words);
	}
}
