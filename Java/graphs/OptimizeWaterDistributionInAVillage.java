package graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

//There are n houses in a village. We want to supply water for all the houses by building wells and laying pipes.
//
//For each house i, we can either build a well inside it directly with cost wells[i], or pipe in water from another well to it. The costs to lay pipes between houses are given by the array pipes, where each pipes[i] = [house1, house2, cost] represents the cost to connect house1 and house2 together using a pipe. Connections are bidirectional.
//
//Find the minimum total cost to supply water to all houses.
//
// 
//
//Example 1:
//
//
//
//Input: n = 3, wells = [1,2,2], pipes = [[1,2,1],[2,3,1]]
//Output: 3
//Explanation: 
//The image shows the costs of connecting houses using pipes.
//The best strategy is to build a well in the first house with cost 1 and connect the other houses to it with cost 2 so the total cost is 3.
// 
//
//Constraints:
//
//1 <= n <= 10000
//wells.length == n
//0 <= wells[i] <= 10^5
//1 <= pipes.length <= 10000
//1 <= pipes[i][0], pipes[i][1] <= n
//0 <= pipes[i][2] <= 10^5
//pipes[i][0] != pipes[i][1]

// 本质是 无向图， 带权值  ==> MST 的问题
public class OptimizeWaterDistributionInAVillage {

	// Kruskal 算法  ===> 把wells 转换成 edges !!!
	public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        if (n == 1) return wells[0];
        int[] parents = new int[n + 1];
        // int disConnected = n;
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            parents[i] = i;
        }
        for (int i = 0; i < pipes.length; i++) {
            edges.add(pipes[i]);
        }
        // Trick的地方， 把wells看成 （0， i）边的 cost, 点0是虚拟的
        for (int i = 0; i < wells.length; i++) {
            edges.add(new int[] {0, i + 1, wells[i]}); // wells是数组，而点是从1开始的
        }
        Collections.sort(edges, (a, b) -> a[2] - b[2]);
        int total = 0;
        for (int i = 0; i < edges.size(); i++) {
            int s = edges.get(i)[0];
            int d = edges.get(i)[1];
            int cost = edges.get(i)[2];
            
            int sp = findParent(parents, s);
            int dp = findParent(parents, d);
            
            if (sp == dp) continue;
            parents[sp] = dp;
            
            total += cost;
        }
        
        //这里我们不对disconnected值计算，(允许孤立点存在) 因为这里不需要connected,只是借助MST算法求最小cost
        // 而实际由于虚拟点0的存在，会把所有的点连在一起，形成connected
        return total;
    }
    
    public int findParent(int[] parents, int s) {
        if (parents[s] == s)
            return s;
        parents[s] = findParent(parents, parents[s]);
        return parents[s];
    }
    
    // Prim 算法
    public int minCostToSupplyWaterPrim(int n, int[] wells, int[][] pipes) {
        if (n == 1) return wells[0];
        Map<Integer, List<int[]>> graph = new HashMap<>();
        buildGraph(graph, pipes, wells);
        boolean[] visited = new boolean[n + 1];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        visited[1] = true;
        for (int[] adj : graph.get(1)) {
            pq.offer(adj);
        }
        int total = 0;
        while (!pq.isEmpty()) {
            int[] node = pq.poll();
            if (visited[node[0]])
                continue;
            visited[node[0]] = true;
            total += node[1];
            for (int[] adj : graph.getOrDefault(node[0], new ArrayList<>()) ) {
                if (visited[adj[0]]) continue;
                pq.offer(adj);
            }
        }
        // 不用判断是否连通，故不校验visited, (允许孤立点存在) 不是真正要构造树，只是借助MST算法求最小cost
        // 而实际由于虚拟点0的存在，会把所有的点连在一起，形成connected
        return total;
        
    }
    
    public void buildGraph(Map<Integer, List<int[]>> graph, int[][] pipes, int[] wells) {
        for (int[] pipe : pipes) {
            int s = pipe[0];
            int d = pipe[1];
            int cost = pipe[2];
            
            graph.computeIfAbsent(s, k -> new ArrayList<>()).add(new int[] {d, cost});
            graph.computeIfAbsent(d, k -> new ArrayList<>()).add(new int[]{s, cost});
        }
        // ！！！将 wells看成特殊边处理
        for (int i = 0; i < wells.length; i++) {
            graph.computeIfAbsent(0, k -> new ArrayList<>()).add(new int[]{i + 1, wells[i]});
            graph.computeIfAbsent(i + 1, k -> new ArrayList<>()).add(new int[]{0, wells[i]});
        }
    }
    
    public static void main(String[] args) {
    	int n = 3;
    	int[] wells = new int[]{1, 2, 2};
    	int[][] pipes = new int[][] {
    		{1, 2, 1}
    	};
    	new OptimizeWaterDistributionInAVillage().minCostToSupplyWaterPrim(n, wells, pipes);
    	
    }
}
