package arrayString;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

//Given a string, find the length of the longest substring T that contains at most k distinct characters.
//
//Example 1:
//
//Input: s = "eceba", k = 2
//Output: 3
//Explanation: T is "ece" which its length is 3.
//Example 2:
//
//Input: s = "aa", k = 1
//Output: 2
//Explanation: T is "aa" which its length is 2.
public class LongestSubstringWithAtMostKDistinctCharacters {

	// TIME: O(N^2)   ===> TLE
	public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s.length() == 0 || k == 0) return 0;
        // if (s.length() < k) return 0; // WRONG, since T contains AT MOST K which can be less than k
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < s.length(); i++) {
            if (s.length() - i < max) { // No need to check if the length of remaining String is less than max
                break;
            }
            Set<Character> set = new HashSet<>();
            int temp = 1;
            set.add(s.charAt(i));
            for (int j = i + 1; j < s.length(); j++) {
                set.add(s.charAt(j));
                if (set.size() > k) {
                    max = Math.max(temp, max);
                    break;
                }
                temp++;
            }            
            max = Math.max(temp, max);// The max() within for loop might be not executed, e.g. "a"
        }
        return max == Integer.MIN_VALUE ? 0 : max;
    }
	
	// Sliding Window : [LEFT, RIGHT], and using HashMap to keep the SIZE of the window
	// Time: O(k * n)
	// Space: O(k)
	public int sol2(String s, int k) {
        if (s.length() == 0 || k == 0) return 0;
        int max = Integer.MIN_VALUE;
        Map<Character, Integer> map = new HashMap<>();
        int i = 0, j = 0;// sliding window, left and right
        while (i <= j && j < s.length()) {
            if (!map.containsKey(s.charAt(j)) && map.size() >= k) { // next element WILL BE ADDED if it is a duplicate, even though the size is larger than k
//                int tempLen = 0;
//                for (Map.Entry<Character, Integer> entry : map.entrySet()) {
//                    tempLen += entry.getValue();
//                }
                max = Math.max(j - i, max);
                int val = map.get(s.charAt(i));
                if (val - 1 == 0) map.remove(s.charAt(i)); // Do duplicate processing [e, c, e, a, b], e will be processed two times, [e, c], [c, e] !!!! See optimized solution below
                else map.put(s.charAt(i), val - 1);
                i++;
                continue;
            }            
            Integer currentVal = map.putIfAbsent(s.charAt(j), 1);
            if (currentVal != null) map.put(s.charAt(j), currentVal + 1);
            j++;
        }
        // In case that the inner MAP traversal WASNOT executed.
//        int tempLen = 0;
//        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
//            tempLen += entry.getValue();
//        }
        return Math.max(max, j - i);
    }
	
	// Sliding window. Compared to previous solution, it was optimized by removing extra duplicated execution.
	// Time: O(k * n)
	// Space: o(k)
	public int optimizedSlidingWindow(String s, int k) {
		if (s.length() == 0 || k == 0) return 0;
        int max = Integer.MIN_VALUE;
        Map<Character, Integer> map = new HashMap<>();
        int i = 0, j = 0;
        while (j < s.length()) {
            map.put(s.charAt(j), j);// j used to get the del_ind
            if (map.size() > k) {
                int del_ind = Collections.min(map.values());// value of map cannot be duplicate;  ==> time: O(k) ==> Applying LinkedHashMap(), see below solution
             //index of same char was increased，说明后面有duplicate， 所以找到索引最小的即是“leftmost”, “real leftmost” 因为后面有重复的，在这被忽略， 因为其不影响找下一个最长
                map.remove(s.charAt(del_ind)); 
                max = Math.max(max, j - i);
                i = del_ind + 1;
            }
            j++;
        }
        return Math.max(max, j - i);
	}
	
	// Sliding window
	// Time: O(n)
	// Space: O(k)
	public int optimizedSol2(String s, int k) {
		if (s.length() == 0 || k == 0) return 0;
        int max = Integer.MIN_VALUE;
        LinkedHashMap<Character, Integer> map = new LinkedHashMap<>();
        int i = 0, j = 0;
        while (j < s.length()) {
        	if (map.containsKey(s.charAt(j))) { // For linkedHashMap, this will remove the previous duplicate and keep the deleted-one always in the LEFTMOST position
                map.remove(s.charAt(j));
            }
            map.put(s.charAt(j), j);
            if (map.size() > k) {
                //int del_ind = Collections.min(map.values());
                //map.remove(s.charAt(del_ind));
                Map.Entry<Character, Integer> entry = map.entrySet().iterator().next();
                map.remove(entry.getKey());
                //max = Math.max(max, j - i);
                i = entry.getValue() + 1;
            }
            j++;
            max = Math.max(max, j - i); // each solution might be the final max
        }
        //return Math.max(max, j - i);
        return max;
	}
	
	//Time: O(k*n)
	public int withList(String s, int k) {
		
		if (s.length() == 0 || k == 0) return 0;
        int max = Integer.MIN_VALUE;
        int[] aux = new int[256];
        int left = 0, right = 0;
        int windowSize = 0;
        while (right < s.length()) {
            char c = s.charAt(right); // aux[c] = 1 means the value of index = 96 is 1 since (int)a = 97
            if (++aux[c] == 1) {// first appearance, window-size + 1, otherwise, it is duplicate which WONT affect window size
                windowSize++;
            }
            if (windowSize > k) {
                // remove element affecting window size
                while (--aux[s.charAt(left)] > 0) {// its current value == 1
                    left++;
                }
                left++;
                windowSize--;
            }
            right++;
            max = Math.max(max, right - left);
        }
        return max;
	    
	}
	
	public static void main(String[] args ) {
		new LongestSubstringWithAtMostKDistinctCharacters().withList("abaccc", 2);
	}
}
