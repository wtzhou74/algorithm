package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//Two strings X and Y are similar if we can swap two letters (in different positions) of X, so that it equals Y. Also two strings X and Y are similar if they are equal.
//
//For example, "tars" and "rats" are similar (swapping at positions 0 and 2), and "rats" and "arts" are similar, but "star" is not similar to "tars", "rats", or "arts".
//
//Together, these form two connected groups by similarity: {"tars", "rats", "arts"} and {"star"}.  Notice that "tars" and "arts" are in the same group even though they are not similar.  Formally, each group is such that a word is in the group if and only if it is similar to at least one other word in the group.
//
//We are given a list A of strings.  Every string in A is an anagram of every other string in A.  How many groups are there?
//
// 
//
//Example 1:
//
//Input: A = ["tars","rats","arts","star"]
//Output: 2
// 
//
//Constraints:
//
//1 <= A.length <= 2000
//1 <= A[i].length <= 1000
//A.length * A[i].length <= 20000
//All words in A consist of lowercase letters only.
//All words in A have the same length and are anagrams of each other.
//The judging time limit has been increased for this question.
public class SimiliarStringGroups {

	// 1） 判断 similiar ==> 看 diff的个数
	// 2） 看Groups, ==> 找有几个连通图  ===> 先构造图 （根据similiar的string; brute force 把一个个相似的连起来）
	//								===> 接下来就类似 NumOfIslands类似的问题 
	//								==> 从任意一个点出发，遍历所有未遍历过的点； 看一共需要几个出发点可能就是几个连通图
	public int numSimilarGroups(String[] A) {
        if (A.length == 1) return 1;
        Map<String, List<String>> graph = new HashMap<>();
        buildGraph(A, graph);
        Set<String> visited = new HashSet<>();
        int count = 0;
        // 从任意一个未访问的点出发，找到一个连通图（组）， 再处理下一个未访问过的点
        for (int i = 0; i < A.length; i++) {
            if (!visited.contains(A[i])) {
                count++;
                dfs(graph, visited, A[i]);
            }
        }
        return count;
    }
    
    public void dfs(Map<String, List<String>> graph, Set<String> visited, String s) {
        if (visited.contains(s) ) {
            return;
        }
        visited.add(s);
        for (String adj : graph.getOrDefault(s, new ArrayList<>())) {
            // if (isSimiliar(s, adj)) {//把所有点连成一个图，在这里判断，会丢失group,
        						// 比如 {"tars", "rats", "arts"}， {"star"}； 结果会得到1，因为star先走的时候，
        						// 会被设置成visited,之后由于所有点连通，剩下三个是similiar的，最终导致Group只有1
        						// 所以要先根据similiar构造图
            //     visited.add(adj);
            // }
            dfs(graph, visited, adj);
        }
    }
    
    public void buildGraph(String[] A, Map<String,List<String>> graph) {
    	// brute force查看所有组合
        for (int i = 0; i < A.length - 1; i++) {
            for (int j = i + 1; j < A.length; j++) {
                String s = A[i];
                String d = A[j];
                // ！！！这里我们只连接相似的 （brute force，查看所有的组合）；所有similiar的点会相互连起来形成一个连通图（group）
                // 否则会把所有点连在一起，形成仅有的一个图
                if (isSimiliar(s, d)) { //无向图 ！！！， 一个Str跟另一个Str相似，反之也是
                    graph.computeIfAbsent(s, k -> new ArrayList<>()).add(d);
                    graph.computeIfAbsent(d, k -> new ArrayList<>()).add(s);
                }
            }
        }
    }
    
    public boolean isSimiliar(String s1, String s2) {
        if (s1.equals(s2)) return true;
        int diff = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i))
                diff++;
        }
        return diff == 2;
    }
}
