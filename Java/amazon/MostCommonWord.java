package amazon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

//Given a paragraph and a list of banned words, return the most frequent word that is not in the list of banned words.  It is guaranteed there is at least one word that isn't banned, and that the answer is unique.
//
//Words in the list of banned words are given in lowercase, and free of punctuation.  Words in the paragraph are not case sensitive.  The answer is in lowercase.
//
// 
//
//Example:
//
//Input: 
//paragraph = "Bob hit a ball, the hit BALL flew far after it was hit."
//banned = ["hit"]
//Output: "ball"
//Explanation: 
//"hit" occurs 3 times, but it is a banned word.
//"ball" occurs twice (and no other word does), so it is the most frequent non-banned word in the paragraph. 
//Note that words in the paragraph are not case sensitive,
//that punctuation is ignored (even if adjacent to words, such as "ball,"), 
//and that "hit" isn't the answer even though it occurs more because it is banned.
// 
//
//Note:
//
//1 <= paragraph.length <= 1000.
//0 <= banned.length <= 100.
//1 <= banned[i].length <= 10.
//The answer is unique, and written in lowercase (even if its occurrences in paragraph may have uppercase symbols, and even if it is a proper noun.)
//paragraph only consists of letters, spaces, or the punctuation symbols !?',;.
//There are no hyphens or hyphenated words.
//Words only consist of letters, never apostrophes or other punctuation symbols.
public class MostCommonWord {

	// 按Map的value 排序
	// Collections.sort的时间复杂度 是 O（NlgN）
	public String mostCommonWord(String paragraph, String[] banned) {
        Map<String, Integer> map = new HashMap<>();
        String[] words = paragraph.split("\\W+");
        Set<String> set = new HashSet<>();
        for (String word : banned) {
            set.add(word.toLowerCase());
        }
        for (String word : words) {
            word = word.toLowerCase(); // !!!!!NOT case sensitive 
            					// ==> 原paragraph的word有大小写，要统一处理成low/upper case
            if (set.contains(word)) continue;
            
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        map = sortByValue(map);
        return map.keySet().iterator().next();
    }
    
    public Map<String, Integer> sortByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> aux = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            aux.add(entry);
        }
        Collections.sort(aux, (a, b) -> b.getValue() - a.getValue());
        Map<String, Integer> newMap = new LinkedHashMap<>();
        					/// 这里一定要用 LinkedHashMap, 否则入到Map的元素顺序是任意的
        for (Map.Entry<String, Integer> entry : aux) {
            newMap.put(entry.getKey(), entry.getValue());
        }
        return newMap;
    }
    
    // PriorityQueue
    // PQ 的sort的是时间复杂度是 O（lgN）
    public String mostCommonWordPQ(String paragraph, String[] banned) {
        PriorityQueue<Map.Entry<String, Integer>> pq 
        	= new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());//min heap，方便后面达到O（1）的排序
        Map<String, Integer> map = new HashMap<>();
        String[] words = paragraph.split("\\W+");
        Set<String> set = new HashSet<>();
        for (String word : banned) {
            set.add(word.toLowerCase());
        }
        for (String word : words) {
            word = word.toLowerCase();
            if (set.contains(word)) continue;
            
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            pq.offer(entry);
            if (pq.size() > 1) //我们上面建的是min Heap，所以出来的第一个就是更小的，
            			// 这里 PQ里面就可以始终维护2个元素的排序
                pq.poll();
        }
        
        return pq.poll().getKey();
    }
}
