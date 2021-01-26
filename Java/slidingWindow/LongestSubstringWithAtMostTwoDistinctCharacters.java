package slidingWindow;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//Given a string s , find the length of the longest substring t  that contains at most 2 distinct characters.
//
//Example 1:
//
//Input: "eceba"
//Output: 3
//Explanation: t is "ece" which its length is 3.
//Example 2:
//
//Input: "ccaabbb"
//Output: 5
//Explanation: t is "aabbb" which its length is 5.
public class LongestSubstringWithAtMostTwoDistinctCharacters {

	// a典型的Sliding Window的问题
	public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s == null || s.length() == 0) return 0;
        if (s.length() < 3) return s.length();
        int i = 0, j = 0;
        int max = Integer.MIN_VALUE;
        Map<Character, Integer> map = new HashMap<>();
        Set<Character> distinct = new HashSet<>(); // 不需要额外的Set, Map的Size就可以，因为Key是唯一的
        //map.put(s.charAt(0), 1);
        //distinct.add(i);
        while (j < s.length()) {
            distinct.add(s.charAt(j));
            if (distinct.size() <= 2 ){
                Integer currentNum = map.putIfAbsent(s.charAt(j), 1);
                if (currentNum != null) map.put(s.charAt(j), currentNum + 1);
                j++;
                
            } else if (distinct.size() > 2) {
                max = Math.max(max, j - i);
                int val = map.get(s.charAt(i));
                map.put(s.charAt(i), val - 1);
                if (val - 1 == 0) distinct.remove(s.charAt(i));
                i++;
            }
        }
        max = Math.max(max, j - i);
        return max;
    }
	
	// a去掉额外的 Set, 用Map的size
	// a不去记录重复多少次， 而是去找需要删除的索引，这里需要删除的是 重复值的最后一个，由于是重复，i一直往前不影响长度
	public int lengthOfLongestSubstringTwoDistinct1(String s) {
		if (s == null || s.length() == 0) return 0;
        if (s.length() < 3) return s.length();
        int i = 0, j = 0;
        int max = Integer.MIN_VALUE;
        Map<Character, Integer> map = new HashMap<>();
        while (j < s.length()) {
            if (map.size() < 3){
                map.put(s.charAt(j), j);
                
                j++;
                
            } 
            if (map.size() == 3) {
                max = Math.max(max, j - i - 1); // 因为这里是 3为条件，所以要 -1
                int del_ind = Collections.min(map.values()); //a最小的那个数是第一块重复的最大值，删除它会影响长度
                //e.g.  abbbbbcccdd, i(b), j(d), > 3; 此时删除的idx是最后一个b,这样删除有意义（影响长度），否则按第一种方法，i一样要循环到最后一个b的位置
                map.remove(s.charAt(del_ind));
                i = del_ind + 1;
                
            } 
            
            
        }
        max = Math.max(max, j - i);
        return max;
    }
}
