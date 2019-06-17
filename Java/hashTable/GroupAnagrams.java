package hashTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
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
	public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return null;
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            char[] chars = strs[i].toCharArray();
            
            Arrays.sort(chars);//return null   // sort characters, besides integers
            String temp = String.valueOf(chars);
            if (map.containsKey(temp)) {
                map.get(temp).add(strs[i]);
            } else {
                List<String> list = new ArrayList<>();
                list.add(strs[i]);
                map.put(temp, list);
            }
        }
        
        List<List<String>> result = new ArrayList<>();
        for(Map.Entry<String, List<String>> entry : map.entrySet()) {
            result.add(entry.getValue());
        }
        
        return result;
    }
}
