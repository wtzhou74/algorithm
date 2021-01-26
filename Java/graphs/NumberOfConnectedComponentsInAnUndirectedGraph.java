package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

//Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes), write a function to find the number of connected components in an undirected graph.
//
//Example 1:
//
//Input: n = 5 and edges = [[0, 1], [1, 2], [3, 4]]
//
//     0          3
//     |          |
//     1 --- 2    4 
//
//Output: 2
//Example 2:
//
//Input: n = 5 and edges = [[0, 1], [1, 2], [2, 3], [3, 4]]
//
//     0           4
//     |           |
//     1 --- 2 --- 3
//
//Output:  1
//Note:
//You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.
public class NumberOfConnectedComponentsInAnUndirectedGraph {

	// Time: O(n + elgN)
	// Space: O(n)
	public int countComponents(int n, int[][] edges) {
        if (edges.length == 0) return n;
        
        int[] parents = new int[n];
        // int[] size = new int[n]; //a记录每组/每个子树/子图 下点的个数， 用于合并小树到大树
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }
        
        int total = n; // 初始值总共有 n 个联通分量
        for (int[] edge : edges) {
            int sp = findParent(parents, edge[0]);
            int dp = findParent(parents, edge[1]);
            // union
            if (sp != dp) {
            	parents[dp] = sp; // 合并的时候可以选择 - 合并小树到大树  =》 Time: O(n + e) 约等于
            	// a当前的根被更新了，在这个例子中，其下的所有根都要更新下， 注意区别下面路径压缩的代码
            	// a但这里没必要做，因为可以统计 total来计算结果，而不是看 list中有多少个不同的
//            	for (int i = 0; i < n; i++) {
//            		if (parents[i] == dp)
//            			parents[i] = sp;
//            	}
            	total--; //a合并完一个，连通分量少 1
            }
            
        }
        
//        Set<Integer> set = new HashSet<>();
//        for (int parent : parents) {
//            set.add(parent);
//        }
//        return set.size();
        return total;
    }
    
    public int findParent(int[] parents, int node) {
        if (parents[node] == node)
            return node;
        parents[node] = findParent(parents, parents[node]); //a用于路径压缩， 使其 “下”的所有点都指向同一个根， 注意与 union 里面要做的区分
        
        return parents[node];
    }
    
    
    // Time: O(n + e)
    public int dfsSol(int n, int[][] edges) {
    	if (edges.length == 0) return n;
    	
    	List<List<Integer>> graph = new ArrayList<>();
    	for (int i = 0; i < n; i++) {
    		graph.add(new ArrayList<>());
    	}
    	
    	// TIME： O(e)
    	for (int[] edge : edges) {
    		// undirected graph
    		graph.get(edge[0]).add(edge[1]);
    		graph.get(edge[1]).add(edge[0]);
    	}
    	
    	int componets = 0;
    	
    	boolean[] visited = new boolean[n];
    	// a类似找 NumOfIslands, 因为这不一定只有一个连通分量，所以要从任何一个未访问过的点，去看当前是不是有一个连通分量
    	// Time： O(N); 需要访问每个点
    	for (int i = 0; i < n; i++) {
    		componets += dfs(graph, visited, i);
    	}
    	return componets;
    }
    
    public int dfs (List<List<Integer>> graph, boolean[] visited, int i) {
    	if (visited[i]) {
    		return 0;
    	}
    	visited[i] = true;
    	for (int v : graph.get(i)) {
    		dfs(graph, visited, v);
    	}
    	
    	return 1;
    }
    
    // Time: O(n + e)
    public int bfsSol(int n, int[][] edges) {
    	if (edges.length == 0) return n;
    	
    	List<List<Integer>> graph = new ArrayList<>();
    	for (int i = 0; i < n; i++) {
    		graph.add(new ArrayList<>());
    	}
    	
    	// TIME： O(e)
    	for (int[] edge : edges) {
    		// undirected graph
    		graph.get(edge[0]).add(edge[1]);
    		graph.get(edge[1]).add(edge[0]);
    	}
    	
    	boolean[] visited = new boolean[n];
    	
    	int components = 0;
    	Queue<Integer> q = new LinkedList<>();
    	for (int i = 0; i < n; i++) {
    		if (!visited[i]) {
    			q.offer(i);
    			while (!q.isEmpty()) {
                    int node = q.poll();
                    visited[node] = true;
                    for (int adj : graph.get(node)) {
                        if (!visited[adj])
                            q.offer(adj);
                    }
                }
        		components++;
    		}
    	}
    	return components;
    }
    
    public static void main(String[] args) {
    	new NumberOfConnectedComponentsInAnUndirectedGraph().countComponents(4, new int[][] {
    		{0, 1},
    		{2, 3},
    		{1, 2}
    	});
    }
}
