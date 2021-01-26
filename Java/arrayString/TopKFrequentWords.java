package arrayString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

//Given a non-empty list of words, return the k most frequent elements.
//
//Your answer should be sorted by frequency from highest to lowest. If two words have the same frequency, then the word with the lower alphabetical order comes first.
//
//Example 1:
//Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
//Output: ["i", "love"]
//Explanation: "i" and "love" are the two most frequent words.
//    Note that "i" comes before "love" due to a lower alphabetical order.
//Example 2:
//Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
//Output: ["the", "is", "sunny", "day"]
//Explanation: "the", "is", "sunny" and "day" are the four most frequent words,
//    with the number of occurrence being 4, 3, 2 and 1 respectively.
//Note:
//You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
//Input words contain only lowercase letters.
//Follow up:
//Try to solve it in O(n log k) time and O(n) extra space.

public class TopKFrequentWords {

	public List<String> topKFrequent(String[] words, int k) {
        if (words.length == 0) return new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(new Comparator<Map.Entry<String, Integer>>(){
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                // 按题意建Max Heap, O（NlgN）  ==> 达到 O（nlgK）,需要建minHeap ==>方便每次把min弹出
            										//===> 以保证堆中只有 K个元素
            	if (a.getValue() == b.getValue())
                    return a.getKey().compareTo(b.getKey());
                else
                    return b.getValue() - a.getValue();
            	// min heap ==> nlgK
//            	if (a.getValue() == b.getValue())
//                    return b.getKey().compareTo(a.getKey());
//                else
//                    return a.getValue() - b.getValue();
            }
        });
        
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            pq.offer(entry);
//            if (pq.size() > k) {
//                pq.poll(); // 保证里面只有K个元素， 前提是建 min heap，poll出来的就是最小的
//            }
        }
        while (!pq.isEmpty()) {
            if (k-- == 0) break;
            Map.Entry<String, Integer> entry = pq.poll();
            list.add(entry.getKey());
        }
        
        // Collections.reverse(list);  ===> min heap ==> 输出的时候就得 reverse下了
        return list;
    }
}
