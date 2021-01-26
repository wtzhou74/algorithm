package twoPointers;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

//Given a string s that consists of only uppercase English letters, you can perform at most k operations on that string.
//
//In one operation, you can choose any character of the string and change it to any other uppercase English character.
//
//Find the length of the longest sub-string containing all repeating letters you can get after performing the above operations.
//
//Note:
//Both the string's length and k will not exceed 104.
//
//Example 1:
//
//Input:
//s = "ABAB", k = 2
//
//Output:
//4
//
//Explanation:
//Replace the two 'A's with two 'B's or vice versa.
// 
//
//Example 2:
//
//Input:
//s = "AABABBA", k = 1
//
//Output:
//4
//
//Explanation:
//Replace the one 'A' in the middle with 'B' and form "AABBBBA".
//The substring "BBBB" has the longest repeating letters, which is 4.


// Note: 
// 		1) 数据结构的选择  ==> 涉及到排序，记录原字符子串中重复最多的元素及重复次数  ==> Map, Sort, PriorityQueue, int[] 等
//		2) 典型的Sliding Window题目，但是关键是 - 在什么条件下移动Window的两个边界
//		3) 如何移动时，由于在计算长度时候，可改变K次，那么在保证最长，首先要“留下当前最长的字串”， 剩下的都需要修改
//				所以，需要记录最长的连续的相同字符，同时在该字串下，剩下的字符即为需要修改的，那么这个值就需要和K做比较
//		4) 在修改最长连续字符及个数的时候，需要考虑在同一字串中有“多个字符有同样的频数”，此时如果 i 移动时，并不改变当前最大的连续的字符个数！！！
public class LongestRepeatingCharacterReplacement {

//	public LinkedHashMap<Character, Integer> sortByValue(Map<Character, Integer> map) {
//		LinkedList<Map.Entry<Character, Integer>> list = new LinkedList<>(map.entrySet());
//		//list.sort(Map.Entry.comparingByValue());
//		Collections.sort(list, (a, b) -> a.getValue() - b.getValue());
//		LinkedHashMap<Character, Integer> newMap = new LinkedHashMap<>();
//		for (Map.Entry<Character, Integer> item : list) {
//			newMap.put(item.getKey(), item.getValue());
//		}
//		return newMap;
//	}
	
//	public int characterReplacement(String s, int k) {
//        if (s.length() - 1 <= k) return s.length();
//        int maxSame = 0;
//        Set<Character> maxChars = new HashSet<>();
//        int i = 0, j = 0;
//        Map<Character, Integer> map = new HashMap<>();
//        int max = Integer.MIN_VALUE;
//        while (j < s.length()) {
//            if (map.size() - maxSame < k) {
//            	Integer fre = map.putIfAbsent(s.charAt(j), 1);
//            	if (fre != null) map.put(s.charAt(j), fre + 1);
//            	// update max same
//            	if (map.get(s.charAt(j)) > maxSame) {
//            		maxChars.clear();
//            		maxChars.add(s.charAt(j));
//            		maxSame = map.get(s.charAt(j));
//            	} else if (map.get(s.charAt(j)) == maxSame) {
//            		maxChars.add(s.charAt(j));
//            	}
//                j++;
//            }
//            if (j == s.length()) return Math.max(max, j - i);
//            if (map.size() - maxSame >= k) {
////            	// 判断除最大外剩余的那些字符的个数是否大于K， 
					// 因为在当前子串下，那些字符是需要修改的，而不是简单地判断当前字符的频率，比如 AABA (AABABBA), 当加A时候还是可以的，因为可以通过改变B
////                map = sortByValue(map);
//                //int min = map.keySet().stream().findFirst().get();
//            	if (maxChars.contains(s.charAt(j))) {
//                    map.put(s.charAt(j), map.get(s.charAt(j)) + 1);
//                    maxChars.clear();
//                    maxChars.add(s.charAt(j));
//                    maxSame = map.get(s.charAt(j));
//                    j++;
//                } else {
//                    max = Math.max(max, j - i);
//                    int val = map.get(s.charAt(i));
//                    if (maxChars.contains(s.charAt(i))) {
//                    	maxChars.remove(s.charAt(i));
//                    	if 
//                    }
//                    //map.put(s.charAt(i), map.get(s.charAt(i)) - 1);
//                    i++;
//                }
//                
//            }
//        }
//        max = Math.max(max, j - i);
//        return max;
//    }
	
	public int characterReplacement(String s, int k) {
        if (s.length() - 1 <= k) return s.length();
        int[] freq = new int[26];
        int i = 0, j = 0;
        //Map<Character, Integer> map = new HashMap<>();
        int maxSize = 0;
        int maxRepeat = 0;
        int max = Integer.MIN_VALUE;
        while (j < s.length()) {
            
            if (maxSize - maxRepeat < k) {
            	freq[s.charAt(j) - 'A'] = freq[s.charAt(j) - 'A'] + 1;
            	maxSize++;
            	maxRepeat = Math.max(maxRepeat, freq[s.charAt(j) - 'A']);
            	j++;
            }
            if (j == s.length()) 
            	return Math.max(max, j - i);
            if (maxSize - maxRepeat >= k) {
            	if (maxRepeat == freq[s.charAt(j) - 'A']) {
            		freq[s.charAt(j) - 'A'] = freq[s.charAt(j) - 'A'] + 1;
            		maxRepeat++;
            		maxSize++;
            		j++;
            	} else {
            		max = Math.max(max, j - i);
            		System.out.println(max);
            		if (freq[s.charAt(i) - 'A'] == maxRepeat) {
            			//maxRepeat--;//多个字符对应同样的Repeat; 尤其是MaxRepeat = 1，再减就变成0， 而实际还是 1
            			// 看看是否还有别的元素有MaxRepeat
            			boolean multiMax = false;
            			for (int l = 0; l < 26; l++) {
            				if (l != s.charAt(i) - 'A' && freq[l] == maxRepeat) {
            					multiMax = true;
            					break;
            				}
            			}
            			if (multiMax) {
            				freq[s.charAt(i) - 'A'] = freq[s.charAt(i) - 'A'] - 1;
            			} else {
            				maxRepeat--;
            				freq[s.charAt(i) - 'A'] = maxRepeat;
            			}
            			
            		} else {
            			freq[s.charAt(i) - 'A'] = freq[s.charAt(i) - 'A'] - 1;
            		}
            		maxSize--;
            		i++;
            	}
            }
        }
        max = Math.max(max, j - i);
        return max;
    }
	
	public static void main(String[] args) {
		String s = "DOBDPDPFP";
		new LongestRepeatingCharacterReplacement().characterReplacement(s, 2);
	}
	
}
