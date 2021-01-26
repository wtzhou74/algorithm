package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.stream.IntStream;

//There are N cities numbered from 1 to N.
//
//You are given connections, where each connections[i] = [city1, city2, cost] represents the cost to connect city1 and city2 together.  (A connection is bidirectional: connecting city1 and city2 is the same as connecting city2 and city1.)
//
//Return the minimum cost so that for every pair of cities, there exists a path of connections (possibly of length 1) that connects those two cities together.  The cost is the sum of the connection costs used. If the task is impossible, return -1.
//
// 
//
//Example 1:
//
//
//
//Input: N = 3, connections = [[1,2,5],[1,3,6],[2,3,1]]
//Output: 6
//Explanation: 
//Choosing any 2 edges will connect all cities so we choose the minimum 2.
//Example 2:
//
//
//
//Input: N = 4, connections = [[1,2,3],[3,4,4]]
//Output: -1
//Explanation: 
//There is no way to connect all cities even if all edges are used.
// 
//
//Note:
//
//1 <= N <= 10000
//1 <= connections.length <= 10000
//1 <= connections[i][0], connections[i][1] <= N
//0 <= connections[i][2] <= 10^5
//connections[i][0] != connections[i][1]

// 经典的最小生成树（Minimum Spanning Tree）问题
public class ConnectingCitiesWithMinimumCost {

	// Step:
	// 		1) 初始化  有 N 棵孤立的数  （目标是合并所有的数成一棵树，cost是最少的）
	//		2）  初始化 parentss ==> unionFind  ==> 合并不在一棵树的树， 按 cost
	//		3) 把所有边按从小到大排序  ==> 每次总是取最小的    （从给定的边出发，所以不需要先 buildGraph）
	public int kruskal(int N, int[][] connections) {
		if (N == 0 || connections == null || connections.length == 0)
			return -1;
		int disconnected = N;//初始值有N个孤立棵树
		
		// for union-finder （用于做并查集算法）
		// 初始值，每个点各自都是一棵独立的树
		int[] parents = new int[N + 1];
		for (int i = 0; i < N + 1; i++) {  // Time: O(N)
			parents[i] = i; //初始化时每个点的根节点都是自己
		}
		
		// 对每条边按从value（cost）从小到大排序
		Arrays.sort(connections, (a, b) -> a[2] - b[2]); // Time: O(NlgN)
		
		int cost = 0;
		for (int[] edge : connections) { // Time: O(E)
			int s = edge[0];// 起点   // 实际这里是 undirected的，但我们从给的边出发，不会出现双向
			int d = edge[1];// 终点
			
			// 需要合并
			if (union(parents, s, d)) {
				cost += edge[2]; // 合并完了之后计算cost， 对 Kruskal算法， 每次增加边，但新增加的边的两个点必须不在一棵树上
				disconnected--;//并完之后就少了一个组
			}
		}
		
		return disconnected == 1 ? cost : -1; // 如果能找到，那最后只有一棵树（包含所有点）, 因为初始值各自为政，要最终把它们合并为一个，用最少的 cost
	}
	
	// 给定的两点是否在同一个组（共有一个树节点/根节点）
	public boolean union(int[] parents, int s, int d) {
		int sp = findParent(parents, s);
		int dp = findParent(parents, d);
		
		if (sp == dp) return false;// 两个点已经在同一棵树上， 不用管
		
		// 否则合并 (因为根据 connection， 他俩需要在一起)
		parents[sp] = dp;
		return true;
		
	} 
	
	// 查找给定点的根节点
	public int findParent(int[] parents, int val) {
		if (parents[val] == val)
			return val;
		// 路径压缩， 每次遇到的点，把其对应的根节点都更新为最终的根节点
		parents[val] = findParent(parents, parents[val]); // 递归到根节点，再返回，返回路上，这个语句将把所有路过的点的root都更新其共同的最上层节点
		//return findParent(parents, parents[val]); // 仅用于找到根/group值，并不会更新路过的值
		return parents[val];
		
		//！！！下面能找到parent，但每起到 路径压缩的作用，因为递归过程中，中间点的parents每被更新
//		int p = parents[val];
//		p = findParent(parents, parents[val]);
//		return p;
		
		// iterative solution
//		int son = val;
//        while (root[val] != val) {
//            val = root[val];
//        }
//        while (son != val) {
//            int temp = root[son];
//            root[son] = val;// update its root
//            son = temp;
//        }
//        return val;
	}
	
	// 加点法  ==> 先建图  ===> undirected ==> visited[]
	// Steps:
	//		1) 从任意点出发 ， 找相邻cost最小的点  ==> 边需要排序   ==> 把 adjs 放 priorityQueue
	//		2） 按图遍历，直到 PQ 为空
	//		3) 查看是否所有点都visited过
	public int minimumCostPrim(int N, int[][] connections) {
	    
        if (N == 0 || connections == null ||connections.length == 0)
            return -1;
        Map<Integer, List<int[]>> graph = new HashMap<>();
        buildGraph(connections, graph);
        boolean[] visited = new boolean[N + 1];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        visited[1] = true;
        if (graph.get(1) == null) return -1;
        for (int[] adj : graph.get(1)) {
            pq.offer(adj);
        }
        int cost = 0;
        while (!pq.isEmpty()) {
            int[] e = pq.poll();
            if (visited[e[0]]) continue;
            cost += e[1];
            visited[e[0]] = true;
            for (int[] adj : graph.get(e[0])) {
                if (!visited[adj[0]])
                    pq.offer(adj);
            }
        }
        
        for (int i = 1; i < visited.length; i++) {
            if (!visited[i])
                return -1;
        }
        return cost;
    }
    
    public void buildGraph(int[][] connections, Map<Integer, List<int[]>> map) {
        for (int i = 0; i < connections.length; i++) {
            int s = connections[i][0];
            int d = connections[i][1];
            int cost = connections[i][2];
            map.computeIfAbsent(s, k -> new ArrayList<>()).add(new int[]{d, cost});
            map.computeIfAbsent(d, k -> new ArrayList<>()).add(new int[]{s, cost});
        }
    }
    
    
	// 加点法 =》 直到所有点都被遍历到
	public int prim(int N, int[][] connections) {
        // prim algo
        if ( N == 0 || connections.length == 0) return -1;
        boolean[] visited = new boolean[N+1];
        Map<Integer, Set<int[]>> graph = new HashMap<>();
        
        // 初始化
        for (int[] connection : connections) {
            graph.computeIfAbsent(connection[0], y -> new HashSet<>()).add(connection);// 起点不用赋值
            graph.computeIfAbsent(connection[1], y -> new HashSet<>()).add(new int[] {connection[1], connection[0], connection[2]});//为后点赋值
        }
        
        Queue<int[]> pq = new PriorityQueue<>((int[] a, int[] b) -> a[2] - b[2]);// 先边排序，从小到大取
        
        int k = graph.keySet().iterator().next();
        visited[k] = true;
        for (int[] e : graph.get(k)) {
            pq.offer(e);
        }
        
        int r = 0;
        while (!pq.isEmpty()) {
            int[] e = pq.poll();
            if (visited[e[1]]) continue;
            r += e[2];
            visited[e[1]] = true;
            for (int[] ne : graph.get(e[1])) {
                if (!visited[ne[1]]) {
                    pq.offer(ne);
                }
            }
        }
        
        return IntStream.range(1, N + 1).allMatch(i -> visited[i] == true) ? r : -1;
    }
	
	
	public int minimumCost(int N, int[][] connections) {
        if ( N == 0 || connections.length == 0) return -1;
        Map<Integer, TreeMap<Integer, Integer>> graph = new HashMap<>();
        boolean[] visited = new boolean[N + 1];
        for (int i = 0; i < connections.length; i++) {
           
            if (graph.containsKey(connections[i][0])) {
                TreeMap<Integer, Integer> map = graph.get(connections[i][0]);
                map.put(connections[i][1], connections[i][2]);
            } else {
                TreeMap<Integer, Integer> map = new TreeMap<>((a, b) -> b - a);
                map.put(connections[i][1], connections[i][2]);
                graph.put(connections[i][0], map);
            }
            
        }
//        {1, 2, 3},
//		{1, 3, 4}  对这种Graph就不对
        Stack<Integer> stack = new Stack<>();
        stack.push(connections[0][0]);
        int cost = 0;
        int num = 0;
        while (!stack.isEmpty()) {
            int val = stack.pop();
            visited[val] = true;
            num++;
            if (num == N) return cost;
            if (graph.containsKey(val)) {
                TreeMap<Integer, Integer> map = graph.get(val);
                int path = Integer.MAX_VALUE;
                for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    if (!visited[entry.getKey()]) {
                        path = Math.min(path, entry.getValue());
                        stack.push(entry.getKey());
                    }
                }
                if (path < Integer.MAX_VALUE) cost += path;
            }
        }
        return -1;
    }
	
	public static void main(String[] args) {
		int[] temp = new int[] {
				0, 0, 1, 2, 3, 4, 5, 6, 7
		};
		int root = new ConnectingCitiesWithMinimumCost().findParent(temp, 6);
		new ConnectingCitiesWithMinimumCost().kruskal(3, new int[][] {
			{1, 2, 5},
			{1, 3, 6},
			{2, 3, 1}
		});
	}
}
