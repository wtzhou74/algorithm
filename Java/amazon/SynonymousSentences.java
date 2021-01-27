package amazon;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

//Given a list of pairs of equivalent words synonyms and a sentence text, Return all possible synonymous sentences sorted lexicographically.
//
//
//Example 1:
//
//Input:
//synonyms = [["happy","joy"],["sad","sorrow"],["joy","cheerful"]],
//text = "I am happy today but was sad yesterday"
//Output:
//["I am cheerful today but was sad yesterday",
//​​​​​​​"I am cheerful today but was sorrow yesterday",
//"I am happy today but was sad yesterday",
//"I am happy today but was sorrow yesterday",
//"I am joy today but was sad yesterday",
//"I am joy today but was sorrow yesterday"]
// 
//
//Constraints:
//
//0 <= synonyms.length <= 10
//synonyms[i].length == 2
//synonyms[i][0] != synonyms[i][1]
//All words consist of at most 10 English letters only.
//text is a single space separated sentence of at most 10 words.
public class SynonymousSentences {
	class DSU {
        Map<String, String> parents ;
        public DSU() {
            parents = new HashMap<>(); // ！！！！这里不用像数组那样初始化，这个在遍历“边”的时候，
            				// 通过isConnect（）来初始化的同时并合并
        }
        
        public String findParent(String s) {
        	
        	if (parents.get(s).equals(s)) { // 这块的parents就是该class内的全局parents,不要要参数传入
                return s;
            }
            String parent = parents.get(s);
            parent = findParent(parent);
            
            return parent;
        	
        }
        
        public void union(String s, String d) {
            String sp = findParent(s);
            String dp = findParent(d);
            if (sp != dp) {
                parents.put(sp, dp);
            }
        }
        
        // 初始化并union  // 也可不用，但在遍历“边”-synonyms的时候要初始化 ！！！
        public boolean isConnect(String s, String d) {
            parents.putIfAbsent(s, s);
            parents.putIfAbsent(d, d);
            return findParent(s).equals(findParent(d)); //这里check的是其对应的parent,不是s本身！！！
        }
    }

	public List<String> generateSentences(List<List<String>> synonyms, String text) {
        if (synonyms == null || synonyms.size() == 0)
            return Arrays.asList(text);
        //Map<String, Set<String>> graph = new HashMap<>(); // 把属于synonyms归为1组， ==> UninoFind， 所以这里
        			// 用graph不合适，graph会把有关的合并为一个graph,但但是会分布在多个key中，需要遍历每个key, 所以这里没有union-find来的直接
        //buildGraph(graph, synonyms);
        DSU dsu = new DSU();
        Set<String> all = new HashSet<>();
        // 初始化并union， 同时确定所有的synonyms words
        for (List<String> synonym : synonyms) {
            String s1 = synonym.get(0);
            String s2 = synonym.get(1);
            if (!dsu.isConnect(s1, s2)) {
                dsu.union(s1, s2);
            }
            
            // 如果上面没有定义 isConnect()
//            dsu.parents.put(s, s);
//            dsu.parents.put(d, d); //初始化
//            dsu.union(s, d); // union
            
            all.add(s1);
            all.add(s2);
        }
        
        Map<String, Set<String>> group = new HashMap<>(); // 在text中的作为key
        for (String word : text.split("\\s+")) {
            if (all.contains(word)) {
                group.put(word, new HashSet<>());
                for (String syn : all) { // 找出跟其一个group的所有synonym
                    if (dsu.isConnect(word, syn))
                        group.get(word).add(syn);
                    // 不用isConnect(); //这里根据是否具有同样的parent，判断是否一个组
//                    if (dsu.findParent(syn).equals(dsu.findParent(word)))
//                        group.get(word).add(s);
                }
            }
        }
        
        //PriorityQueue<String> pq = new PriorityQueue<>(); // 下面的backtracking有可能重复，选用TreeSet
        //Set<String> visited = new HashSet<>(); // 这里不用visited,这不是graph, 按顺序backtrack所有group的元素就好
        TreeSet<String> pq = new TreeSet<>();
        replace(group, text.split("\\s+"), 0, pq);
        List<String> result = new LinkedList<>();
        for (String s : pq) {
            result.add(s);
        }
        return result;
    }
    
	public void replace(Map<String, Set<String>> group, 
            String[] words, int s, TreeSet<String> pq) {
		if (s == words.length) {
			pq.add(String.join(" ", words));
			return;
		}
		for (int i = s; i < words.length; i++) {
			if (!group.containsKey(words[i]))
			    continue;
			String curr = words[i];
			for (String adj : group.get(words[i])) {
			    words[i] = adj;
			    replace(group, words, i + 1, pq);
			    words[i] = curr;
			}			
		}
		pq.add(String.join(" ", words)); // 当最后一个word不是synonyms，不会递归调用，从而到 s==len，直接for选择结束跳出了,此时就要记录下这个text
	}
    
	// ------------------------------ 用 String转ID来处理 -----------------------//
//	class DSU {
//        int[] parents;
//        public DSU() {
//            parents = new int[20]; // 但需要给定数组的长度
//            for (int i = 0; i < 20; i++) { // 初始化在这里
//                parents[i] = i;
//            }
//        }
//        
//        public int findParent(int s) {
//            if (parents[s] == s)
//                return s;
//            parents[s] = findParent(parents[s]);
//            return parents[s];
//        }
//        
//        public void union(int s, int d) {
//            int sp = findParent(s);
//            int dp = findParent(d);
//            if (sp != dp) {
//                parents[sp] = dp;
//            }
//        }
//    }
//    public List<String> generateSentences(List<List<String>> synonyms, String text) {
//        if (synonyms == null || synonyms.size() == 0)
//            return Arrays.asList(text);
//        
//        Map<String, Integer> wordToId = new HashMap<>();
//        int id = 0;
//        DSU dsu = new DSU();
//        Set<String> set = new HashSet<>();
//        for (List<String> synonym : synonyms) {
//            String s = synonym.get(0);
//            String d = synonym.get(1);
//            wordToId.putIfAbsent(s, id++);
//            wordToId.putIfAbsent(d, id++);  // 把 word转为 ID
//            
//            set.add(s); // 同时记录所有synonym
//            set.add(d);
//            dsu.union(wordToId.get(s), wordToId.get(d)); // 按这个 “边” 合并
//        }
//        
//        Map<String, Set<String>> group = new HashMap<>();
//        for (String word : text.split("\\s+")) {
//            if (set.contains(word)) {
//                group.put(word, new HashSet<>());
//                for (String s : set) {
//                    if (dsu.findParent(wordToId.get(s)) 
//                            == dsu.findParent(wordToId.get(word)))  // 具有同样的parent的就是一个组的
//                        group.get(word).add(s);
//                }
//            }
//        }
//        
//        TreeSet<String> pq = new TreeSet<>();
//        replace(group, text.split("\\s+"), 0, pq);
//        List<String> result = new LinkedList<>();
//        for (String s : pq) {
//            result.add(s);
//        }
//        return result;
//        
//    }
    
    public void buildGraph(Map<String, Set<String>> graph, List<List<String>> synonyms ) {
        for (List<String> synonym : synonyms) {
            String w1 = synonym.get(0);
            String w2 = synonym.get(1);
            
            graph.computeIfAbsent(w1, k -> new HashSet<>()).add(w2);
            graph.computeIfAbsent(w2, k -> new HashSet<>()).add(w1);
        }
    }
    
    public static void main(String[] args) {
    	List<List<String>> synonyms = new LinkedList<>();
    	synonyms = Arrays.asList(Arrays.asList("happy","joy"), Arrays.asList("sad","sorrow"),
    			Arrays.asList("joy","cheerful"));
    	String text = "I am happy today but was sad";
    	new SynonymousSentences().generateSentences(synonyms, text);
    }
}
