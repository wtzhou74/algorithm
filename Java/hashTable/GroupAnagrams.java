package hashTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given an array of strings, group anagrams together.
 * Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
   Output:
   [
     ["ate","eat","tea"], // EACH GROUP HAVE SAME "KEY"
     ["nat","tan"],
     ["bat"]
   ]
 * */
public class GroupAnagrams {

	/*
	 * REDESIGN KEY
	 * */
	// 1) 对 anagram，sorted之后，所有字符串就都一样了 ！！！ ===> sorted
    // 2) 另外一种设计Key的方式，由于 anagram所有字符及数目都一样，那就设计key为： a*b*c*...,*代表个数
	public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0)
            return new ArrayList<>();
        List<List<String>> res = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            char[] cs = strs[i].toCharArray();
            Arrays.sort(cs); // 对 anagram，sorted之后，所有字符串就都一样了 ！！！
            //		还有另外一种设计Key的方式，由于 anagram所有字符及数目都一样，那就设计key未： a*b*c*...
            String key = String.valueOf(cs);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(strs[i]);
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            res.add(entry.getValue());
        }
        return res;
    }
	
	
	// 一个个遍历未访问的点，把是anagram的放到一起
	public List<List<String>> groupAnagrams1(String[] strs) {
        if (strs == null || strs.length == 0)
            return new ArrayList<>();
        boolean[] visited = new boolean[strs.length];
        List<List<String>> res = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            if (visited[i]) continue;
            int j = i;
            List<String> list = new ArrayList<>();
            // 从当前未访问的点开始
            while (j < strs.length) {
                if (list.size() == 0) {
                    list.add(strs[j]);
                    visited[j] = true;
                    j++;
                    continue;
                }
                if (!visited[j] && 
                    isAnagram(list.get(list.size() - 1), strs[j])) {
                    list.add(strs[j]);
                    visited[j] = true;
                }
                j++;
            }
            res.add(list);
        }
        return res;
    }
    
    public boolean isAnagram(String a, String b) {
        if (a.length() != b.length())
            return false;
        // 看是否是anagram，不能简单看字符是否在两个字符串中都有，还要看个数 ！！！！， 比如： aab, abb 就不是anagram
        // for (int i = 0; i < a.length(); i++) {
        //     if (b.indexOf(a.charAt(i)) < 0 
        //         || a.indexOf(b.charAt(i)) < 0)
        //         return false;
        // }
        Map<Character, Integer> map = new HashMap<>();
        for (char c : a.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        for (char c : b.toCharArray()) {
            if (!map.containsKey(c)) return false;
            int count = map.get(c) - 1;
            if (count < 0) return false;
            if (count == 0) map.remove(c);
            else map.put(c, count);
        }
        return map.size() == 0 ? true : false;
    }
}
