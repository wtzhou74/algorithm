package treesAndGraphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

//There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you. You receive a list of non-empty words from the dictionary, where words are sorted lexicographically by the rules of this new language. Derive the order of letters in this language.
//
//Example 1:
//
//Input:
//[
//  "wrt",
//  "wrf",
//  "er",
//  "ett",
//  "rftt"
//]
//
//Output: "wertf"
//Example 2:
//
//Input:
//[
//  "z",
//  "x"
//]
//
//Output: "zx"
//Example 3:
//
//Input:
//[
//  "z",
//  "x",
//  "z"
//] 
//
//Output: "" 
//
//Explanation: The order is invalid, so return "".
//Note:
//
//You may assume all letters are in lowercase.
//You may assume that if a is a prefix of b, then a must appear before b in the given dictionary.
//If the order is invalid, return an empty string.
//There may be multiple valid order of letters, return any one of them is fine.

public class AlianDictionary {

	// Apply better Data Structure to get better performance
	public String ds(String[] words) {
        if (words.length == 1) return words[0];
        Map<Character, Integer> indegree = new HashMap<>();
        // List<Character>[] adj = new ArrayList[128];
        Map<Character, Set<Character>> adj = new HashMap<>();// Without Assign 128, and most of them are useless in same cases, and it is better for subsequent processing, e.g. contains()
        // initialize indegree and graph for each character
        for (int i = 0; i < words.length; i++) {
            for (char c : words[i].toCharArray()) {
                indegree.putIfAbsent(c, 0);// initial degree
                //adj[c] = new ArrayList<>();// initial graph
                adj.putIfAbsent(c, new HashSet<>());
            }
        }
        for (int i = 0; i < words.length - 1; i++) {
        	String word1 = words[i];
        	String word2 = words[i + 1];
        	int minLen = Math.min(word1.length(), word2.length()); // e.g. "abc", "abeg"
        	for (int j = 0; j < minLen; j++) {
        		char c1 = word1.charAt(j);
                char c2 = word2.charAt(j);
                if (c1 != c2) {
                    if (!adj.get(c1).contains(c2)) {
                    	adj.get(c1).add(c2);
                    	indegree.put(c2, indegree.get(c2) + 1);
                    } 
                    break;
                } 
        	}
        }
        Queue<Character> queue = new ArrayDeque<>();
        for (Map.Entry<Character, Integer> entry : indegree.entrySet()) {
            if (entry.getValue() == 0) queue.offer(entry.getKey());
        }
        String res = "";
        int count = 0;
        while (!queue.isEmpty()) {
            Character c = queue.poll();
            count++;
            res += c;
//             for (char i = 'a'; i <= 'z'; i++) {
//                 if (adj[c] != null && adj[c].contains(i))
//                 {
//                     if (indegree.get(i) - 1 == 0) queue.offer(i);
//                     indegree.put(i, indegree.get(i) - 1);
                    
//                 }
//             }
            for (char i : adj.get(c))
            {
                if (indegree.get(i) - 1 == 0) queue.offer(i);
                indegree.put(i, indegree.get(i) - 1);
            }
            
        }
        if (count != indegree.size()) return "";
        else return res;
                        
    }
	
	// 这里隐含假设是 一个字符串的 prefix一定要排在前面，即 ab 一定得在 abc 前面才合法 !!!!!!
	public String alienOrder2(String[] words) {
        if (words.length == 0)
            return "";
        Map<Character, List<Character>> graph =
            new HashMap<>();
        Map<Character, Integer> indegree = new HashMap<>();
        
        // 一个字符串的 prefix一定得排在前面，否则不合法， 比如 [abc, ab] 就是 invalid
        // 如果没在这里单独处理，结论会出错，因为下面的逻辑不会处理  'c' 和 ‘’ 空字符
        for (int i = 0; i < words.length - 1; i++) {
            if (words[i].length() > words[i + 1].length() 
                && words[i].startsWith(words[i + 1]))
                return "";
        }
        for (int i = 0; i < words.length; i++) {            
            for (char c : words[i].toCharArray()) {
                indegree.put(c, 0);
            }
        }
        buildGraph(words, graph, indegree);
        
        Queue<Character> queue = new LinkedList<>();
        for (Map.Entry<Character, Integer> entry :
            indegree.entrySet()) {
            if (entry.getValue() == 0)
                queue.offer(entry.getKey());
        }
        if (queue.size() == 0)
            return "";
        StringBuffer sb = new StringBuffer();
        while (!queue.isEmpty()) {
            int size = queue.size();
            char c = queue.poll();
            sb.append(c);
            for (char adj : graph.getOrDefault(c, new ArrayList<>())) {
                int d = indegree.get(adj) - 1;
                if (d == 0){
                    queue.offer(adj);
                }
                indegree.put(adj, d);
            }
        }
        for (Character key : indegree.keySet()) {
            if (indegree.get(key) != 0)
                return "";
        }
        return sb.toString();
    }
    
    public void buildGraph(String[] words, 
                           Map<Character, List<Character>> graph,
                          Map<Character, Integer> indegree) {
        for (int i = 0; i < words.length - 1; i++) {
            String w1 = words[i];
            String w2 = words[i + 1];
            int j = 0;
            while (j < w1.length() && j < w2.length()) {
                char c1 = w1.charAt(j);
                char c2 = w2.charAt(j);
                if (c1 != c2) {
                    indegree.put(c1, indegree.getOrDefault(c1, 0));
                    indegree.put(c2, indegree.getOrDefault(c2, 0) + 1);
                    graph.computeIfAbsent(c1, 
                                          k -> new ArrayList<>()).add(c2);
                    break;
                } 
                j++;
            }
        }
    }
	
	// Time: O(m * n)  m: words‘ length , n - average length of word
	// Space: O(m * n)
	// ！！！！ 这里没有判断 ["abc", "ab"] 的情况，，看上面解法
	// 这里隐含假设是 一个字符串的 prefix一定要排在前面，即 ab 一定得在 abc 前面才合法
	public String alienOrder(String[] words) {
		if (words.length == 1) return words[0];
        Map<Character, Integer> indegree = new HashMap<>();
        List<Character>[] adj = new ArrayList[128];
        //Map<Character, List<Character>> graph = new HashMap<>();
        //Arrays.fill(indegree, -1);
        Set<Character> set = new HashSet<>();
        // initialize indegree and graph for each character
        for (int i = 0; i < words.length; i++) {
            for (char c : words[i].toCharArray()) {
                indegree.putIfAbsent(c, 0);// initial degree
                adj[c] = new ArrayList<>();// initial graph                
            }
        }
        for (int i = 0; i < words.length - 1; i++) {
        	String word1 = words[i];
        	String word2 = words[i + 1];
        	int minLen = Math.min(word1.length(), word2.length()); // e.g. "abc", "abeg"
        	for (int j = 0; j < minLen; j++) {
        		char c1 = word1.charAt(j);
                char c2 = word2.charAt(j);
                if (c1 != c2) {
                	// For each list, the REST of nodes are directed connect to the HEAD, but it doesn't mean there is a path between non-head nodes
                	// That is why we WONT add DUPLICATE to the same list, e.g. [a, b], b wont be added again since the relation between a and b has been created
                    if (!adj[c1].contains(c2)) {
                    	adj[c1].add(c2);
                    	indegree.put(c2, indegree.get(c2) + 1);
                    }                    
                    break; 
                    // WHY break 1) 如果一样，没有谁前谁后  2） 如果不一样，对两个单词，只能知道这一对字符的先后顺序，后面不可知，因为lexicographically先按前面排
                } 
        	}
        }
        Queue<Character> queue = new ArrayDeque<>();
        for (Map.Entry<Character, Integer> entry : indegree.entrySet()) {
            if (entry.getValue() == 0) queue.offer(entry.getKey());
        }
        String res = "";
        int count = 0;
        while (!queue.isEmpty()) {
            Character c = queue.poll();
            count++;
            res += c;
            for (char i = 'a'; i <= 'z'; i++) {
                if (adj[c] != null && adj[c].contains(i))
                {
                    if (indegree.get(i) - 1 == 0) queue.offer(i);
                    indegree.put(i, indegree.get(i) - 1);
                    
                }
            }
        }
        if (count != indegree.size()) return "";
        else return res;
                        
    }
	
	public String test(String[] words) {
		if (words.length == 1) return words[0];
        int[] indegree = new int[128]; // Num of Nodes is unknown, Map is better than Array
        List<Character>[] adj = new ArrayList[128];
        //Map<Character, List<Character>> graph = new HashMap<>();
        Arrays.fill(indegree, -1);
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < words.length - 1; i++) {
        	String word1 = words[i];
        	String word2 = words[i + 1];
        	int minLen = Math.min(word1.length(), word2.length()); // e.g. "abc", "abeg"
        	for (int j = 0; j < minLen; j++) {
        		if (word1.charAt(j) == word2.charAt(j)) {
        			set.add(word1.charAt(j));
        			if (indegree[word1.charAt(j)] < 0) indegree[word1.charAt(j)] = 0;
        			if (adj[word1.charAt(j)] == null) {
        				adj[word1.charAt(j)] = new ArrayList<>();
        			}
        		} else {
        			if (adj[word1.charAt(j)] == null) {
        				adj[word1.charAt(j)] = new ArrayList<>();        				
        			}
        			if (!adj[word1.charAt(j)].contains(word2.charAt(j)))
        				adj[word1.charAt(j)].add(word2.charAt(j));
        			set.add(word1.charAt(j));
        			set.add(word2.charAt(j));
        			if (indegree[word2.charAt(j)] < 0) indegree[word2.charAt(j)] = 1;
        			else indegree[word2.charAt(j)]++;
        			if (indegree[word1.charAt(j)] < 0) indegree[word1.charAt(j)] = 0;
        		}
        	}
        }
        
        Queue<Character> queue = new ArrayDeque<>();
        for (int i = 0; i < 128; i++) {
            if (indegree[i] == -1) continue;
            if (indegree[i] == 0) queue.offer((char)i);
        }
        String res = "";
        while (!queue.isEmpty()) {
            Character c = queue.poll();
            set.remove(c);
            res += c;
            for (char i = 'a'; i <= 'z'; i++) {
                if (adj[c] != null && adj[c].contains(i))
                {
                    indegree[i]--;
                    if (indegree[i] == 0) queue.offer(i);
                }
            }
        }
        if (set.size() > 0) return "";
        else return res;
                        
    }
	
	public static void main(String[] args) {
		String[] words = new String[]{"abc","ab"};
		new AlianDictionary().alienOrder(words);
	}
}
