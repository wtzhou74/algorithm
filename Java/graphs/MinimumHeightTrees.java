package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

//For an undirected graph with tree characteristics, we can choose any node as the root. The result graph is then a rooted tree. Among all possible rooted trees, those with minimum height are called minimum height trees (MHTs). Given such a graph, write a function to find all the MHTs and return a list of their root labels.
//
//Format
//The graph contains n nodes which are labeled from 0 to n - 1. You will be given the number n and a list of undirected edges (each edge is a pair of labels).
//
//You can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0, 1] is the same as [1, 0] and thus will not appear together in edges.
//
//Example 1 :
//
//Input: n = 4, edges = [[1, 0], [1, 2], [1, 3]]
//
//        0
//        |
//        1
//       / \
//      2   3 
//
//Output: [1]
//Example 2 :
//
//Input: n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]
//
//     0  1  2
//      \ | /
//        3
//        |
//        4
//        |
//        5 
//
//Output: [3, 4]
//Note:
//
//According to the definition of tree on Wikipedia: “a tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree.”
//The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
public class MinimumHeightTrees {

	// Intuitive solution, 从每个点出发，找出 depth最小的点
	// Time : O(n * h)  ===> TLE   ===> 分析depth最低的那些点存在的情况  ==> degree
	public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (edges.length == 0) return Arrays.asList(0);
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] degree = new int[n];//用于解法2
        buildGraph(edges, graph, degree);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int i = 0; i < n; i++) {
            boolean[] nodeVisited = new boolean[n];
            int depth = bfs(i, graph, nodeVisited);
            //System.out.println(max[0] + " " + i);
            pq.offer(new int[]{depth, i});
        }
        List<Integer> res = new ArrayList<>();
        int[] depth = pq.poll();
        res.add(depth[1]);
        int pre = depth[0];
        while (!pq.isEmpty()) {
            int[] temp = pq.poll();
            if (pre == temp[0])
                res.add(temp[1]);
            else break;
        }
        return res;
    }
    
    public int bfs(int s, Map<Integer, List<Integer>> graph, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        int depth = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                int node = queue.poll();
                visited[node] = true;
                for (int adj : graph.get(node)) {
                    if (visited[adj]) continue;
                    queue.offer(adj);
                }
                size--;
            }
            depth++;
        }
        
        return depth;
    }
    
    // 要找 depth最少的，那对图来说，最外层 （degree = 1）的点出发的肯定是最长的，所以从这些点开始， BFS， 
    // 处理完这些后，再找 degree = 1的点，直到没有这样的点为止，最后一次记录的这样的点就是要找的点
    public List<Integer> findMinHeightTreesOptimized(int n, int[][] edges) {
        if (edges.length == 0) return Arrays.asList(0);
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] degree = new int[n];
        buildGraph(edges, graph, degree);
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1)
                queue.add(i);
        }
        
        List<Integer> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>(); // 最后一轮degree=1的所有，每轮这个list会清空
            int size = queue.size();
            while (size > 0) {
                int node = queue.poll();
                list.add(node); // 记录每轮等于 1 的点，如果接下来queue中没有 = 1的点，list中的值就是目前值
                for (int adj : graph.get(node)) {
                    //if (degree[adj] == 0) continue; //没必要，因为这样的点本身就不会被加到 queue中
                    degree[node]--;
                    degree[adj]--;
                    if (degree[adj] == 1) // 假设 adj最后会变成0，但变成0之前 adj = 1会被加进去，list会捕捉到
                        queue.offer(adj);
                } 
                size--;
            }
            res = list;
            
            // !!!!! 如果不用上面的 每轮new List； 会比较麻烦， 注意edge cases;如下注释的部分
//	         boolean next = false;
//	         for (int i = 0; i < n; i++) {
//	             if (degree[i] > 1) {
//	                 next = true;
//	                 break;
//	             }
//	         }
//	         if (!next) break;
            
        }
        
//        for (int i = 0; i < n; i++) {
//            if (degree[i] >= 0) // 有可能会是0， 比如 [[1,0], [2,0], [3, 0]]; 处理完一圈后 degree[0] = 0,而0就是结果
//                res.add(i);
//        }
//        if (res.size() == 0) // 另外，也可能存在都小于 0； 比如 [0, 1]; 两个点互相减，其 degree就都小于0； 而该case的结论就是它们都是
//            for (int i = 0; i < n; i++)
//                res.add(i);
        
        return res;
    }
    public void buildGraph(int[][] edges, Map<Integer, List<Integer>> graph,
    		int[] degree){
        for (int i = 0; i < edges.length; i++) {
            int s = edges[i][0];
            int d = edges[i][1];
            
            graph.computeIfAbsent(s, k -> new ArrayList<Integer>()).add(d);
            graph.computeIfAbsent(d, k -> new ArrayList<Integer>()).add(s);
            
            // undirected graph
            degree[s]++;
            degree[d]++;
        }
    }
    
    public static void main(String[] args) {
    	int[][] edges = new int[][] {
			{1, 0}, {2, 0}, {3, 0}
		};
		new MinimumHeightTrees().findMinHeightTreesOptimized(4, edges);
    }
}
