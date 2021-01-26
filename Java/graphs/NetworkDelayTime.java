package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

//There are N network nodes, labelled 1 to N.
//
//Given times, a list of travel times as directed edges times[i] = (u, v, w), where u is the source node, v is the target node, and w is the time it takes for a signal to travel from source to target.
//
//Now, we send a signal from a certain node K. How long will it take for all nodes to receive the signal? If it is impossible, return -1.
public class NetworkDelayTime {

	class Node {
        int d;
        int w;
        public Node(int d, int w) {
            this.d = d;
            this.w = w;	
        }
    }
	Map<Integer, List<Node>> graph = new HashMap<>();
	
	// Time: O(N^N + ElogE)  ==> 每个点出去都有可能需要访问剩下所有的 node
	// Space: O(N + E)
	// DFS ==> 从K点出发， 记录到达每个点所需的“最少”cost （用Map记录）， 从中找出最大的即是最终结果
	// 这里判断是否visit该点有点特殊，要看 cost
	// 贪心策略 (Greedy),每次总是选最小cost的那条边走
	public int networkDelayTimeDFS(int[][] times, int N, int K) {
        if (times == null || times.length == 0)
            return 0;
        buildGraph(times);
        Map<Integer, Integer> cost = new HashMap<>();
        for (int i = 1; i <= N; i++) {
            cost.put(i, Integer.MAX_VALUE);
        }
        // 对每点，对其出发到邻近边的cost排序，这样每到达一个点时，始终从消耗最少的出发
        // 如果碰到已访问过的点，我们看看新的cost跟旧的cost那个小，如果新的比大的大，没必要再去访问这个点了
        // O(ElgE)
        for (int node : graph.keySet()) {
            Collections.sort(graph.get(node), (a, b) -> a.w - b.w);
        }
        int total = 0;
        // DFS
        dfs(graph, new Node(K, 0), 0, cost);
        for (int i = 1; i <= N; i++) {
            if (cost.get(i) == Integer.MAX_VALUE) {
                return -1;
            }
            total = Math.max(total, cost.get(i));
        }
            
        return total;
        
    }
    
    public void dfs(Map<Integer, List<Node>> graph, Node node, int elapsed, Map<Integer, Integer> cost) {
        // 这块判断是否还要访问有点特殊  ==> 看cost , 而不是简单看有没有 visited过
    	if (elapsed >= cost.get(node.d))
            return;
        cost.put(node.d, elapsed); // 记录或更新每路过的点的cost
        if (graph.containsKey(node.d)) {
            for (Node adj : graph.get(node.d)) {
                dfs(graph, adj, elapsed + adj.w, cost); //累加路过的cost
            }
        }
    }
    
    
	// 由于该图虽然是有向图，但图中的两点可以同时是SOURCE, DESTINATION，  3->4, 4->3
    // 这种情况下该方法对某些用例就不正确了   ===> DFS ==> 按cost最小开始一条条路径开始check
    //									===> 应用 Dijkstra算法
    // ====> 基于BFS, Dynamic Programming， ==> Bellman Ford算法
	public int networkDelayTimeBFS(int[][] times, int N, int K) {
        if (times == null || times.length == 0)
            return 0;
        buildGraph(times);
        Map<Integer, Integer> map = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node(K, 0));
        map.put(K, 0);
        int total = 0;
        //int nodeNum = 0;
        boolean[] visited = new boolean[N + 1];
        while (!queue.isEmpty()) {
            int size = queue.size();
            int levelMax = 0;
            while (size > 0) {
                Node n = queue.poll();
                visited[n.d] = true;                
                if (graph.containsKey(n.d)) {
                    for (Node node : graph.get(n.d)) {
                        if (!visited[node.d]) {
                            queue.offer(new Node(node.d, n.w + node.w));
                            //levelMax = Math.max(levelMax, node.w);
                            map.put(node.d, n.w + node.w); // 记录到达当前点的COST          
                        }
                        if (visited[node.d]) {
                        	//如果该点已经被访问到了，那肯定已经有一个COST了，此时我们选到达该点更小的COST
                            map.put(node.d, Math.min(map.get(node.d), n.w + node.w));
                            // 看是合理， 但是由于该图虽然是有向图，但图中的两点可以同时是SOURCE, DESTINATION，  3->4, 4->3
                            // 这种情况下该方法对某些用例就不正确了
                        }
                    }
                }                
                size--;
            }
            //total += levelMax;
        }
        for (int i = 1; i <= N; i++) {
            if (!visited[i]) return -1;
            total = Math.max(total, map.get(i));
        }
            
        return total;
        
    }
	
	// Dijkstra 最基础算法 - 同时记录最小cost的路径
	// https://leetcode.com/problems/network-delay-time/solution/
	// TIME： O（N^2  + E）
	// Space: O(N + E)
	Map<Integer, Integer> dist;
    public int networkDelayTimeTest(int[][] times, int N, int K) {
        Map<Integer, List<int[]>> graph = new HashMap();
        for (int[] edge: times) {
            if (!graph.containsKey(edge[0]))
                graph.put(edge[0], new ArrayList<int[]>());
            graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }
        dist = new HashMap(); // 记录都是从指定起始点到其他点的最短距离，初始化时都为无限大
        for (int node = 1; node <= N; ++node)
            dist.put(node, Integer.MAX_VALUE);
        // 记录最短的路径
        int[] prev = new int[N + 1];

        dist.put(K, 0);
        boolean[] seen = new boolean[N+1];

        while (true) {
            int next = -1;//找下一个访问点
            int currMin = Integer.MAX_VALUE;//当前最小距离的点
            // 取最小的这个过程，可以用PriorityQueue来优化
            for (int i = 1; i <= N; ++i) {
            	// dist 记录都是从指定起始点到其他点的最短距离，初始化时都为无限大
            	// 确认下一开始的点，这里要找的是 1） 未访问； 2） 出去的点 COST最小 
            	// （这里不仅包括当前点出去，也包括从起始点出去的，其实就是已访问过的点） ！！！！， 已访问过的点就会有一个从源点到其他点的“当前”最短距离，之后根据大小再更新
                if (!seen[i] && dist.get(i) < currMin) {
                	currMin = dist.get(i);
                    next = i;
                }
            }

            if (next < 0) break;
            seen[next] = true;
            if (graph.containsKey(next))
                for (int[] adj: graph.get(next)) {
                	// 同时赋予邻接点的距离或更新假如当前路径出去cost更新
                	// 比较1) 当前点已记录的cost; 2)按该路径走的cost
                    dist.put(adj[0],
                             Math.min(dist.get(adj[0]), dist.get(next) + adj[1]));
            		// 其原始值(题中给定的weight值，或者MAX_VALUE)是 adj[0]
                    
                    
                    if (dist.get(adj[0]) > adj[1] + dist.get(next)) {
                        prev[adj[0]] = next; // 更新父节点
                        dist.put(adj[0], dist.get(next) + adj[1]);
                    }
                }
            
        }

        int ans = 0;
        for (int cand: dist.values()) {
            if (cand == Integer.MAX_VALUE) return -1;
            ans = Math.max(ans, cand);
        }
        return ans;
    }
    
	// Dijkstra改进算法  （用PriorityQueue 去获取最小cost）
    // Time: O(NlgN)
	// 需要记录:  1) Visited[]   2) Cost[]
	//			==> 基于Greedy 策略,每次从(指定原点到未访问的)COST最小的点出发  ===> PriorityQueue<>(a.w - b.w)
	//		根据距离大小,更新每个点的最小 Cost
	public int networkDelayTime(int[][] times, int N, int K) {
        if (times == null || times.length == 0)
            return 0;
        buildGraph(times);
        int[] cost = new int[N + 1];
        Arrays.fill(cost, Integer.MAX_VALUE);
        cost[K] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.w - b.w); // 建堆 - lgN
        pq.offer(new Node(K, 0));
        boolean[] visited = new boolean[N + 1];
        int total = 0;
        while (!pq.isEmpty()) {
            Node node = pq.poll(); // 每次出来的都是 Cost最小的
            if (visited[node.d]) continue;
            visited[node.d] = true;
            cost[node.d] = node.w;
            //if (graph.containsKey())
            for (Node adj : graph.getOrDefault(node.d, new LinkedList<>())) {
                // if (adj.w + node.w < cost[d])
                //     cost[d] = adj.w + node.w;
                pq.offer(new Node(adj.d, adj.w + node.w)); // lgN
            }
        }
        for (int i = 1; i <= N; i++) {
            if (cost[i] == Integer.MAX_VALUE) return -1;
            total = Math.max(total, cost[i]);
        }
            
        return total;
        
    }
    
    public void buildGraph(int[][] times) {
        for (int i = 0; i < times.length; i++) {
            int s = times[i][0];
            int d = times[i][1];
            int w = times[i][2];
            graph.computeIfAbsent(s, k -> new LinkedList<>()).add(new Node(d, w));
        }
    }
    
    
    // Time: O(NE)
    // 就是对每条边做 N - 1 次 Relaxation
    public int networkDelayTimeBellmanFord(int[][] times, int N, int K) {
        if (times == null || times.length == 0)
            return 0;
        //buildGraph(times);
        int[] cost = new int[N + 1];
        Arrays.fill(cost, Integer.MAX_VALUE);
        cost[K] = 0;
        //boolean[] visited = new boolean[N + 1];
        int total = 0;
        //int[] prev = new int[N + 1];
        //Arrays.fill(prev, -1);
        for (int i = 0; i < N - 1; i++) {
            for (int[] edge : times) {
                int s = edge[0];
                int d = edge[1];
                int w = edge[2];
                
                // 这里由于加了数，本身有可能是MAX_VALUE,所以加完溢出，所以对S已经是MAX的不用管
                // 对Dijkstra，它是从指定Single Source的，而这个指定的single source 是0， 所以不会
                // 重复做 Relaxation就好
                if (cost[s] != Integer.MAX_VALUE && cost[s] + w < cost[d]) {
                    cost[d] = cost[s] + w;
                    //prev[d] = s;
                }
                //cost[d] = Math.min(cost[d], cost[s] + w);
            }
        }
        // check negative cycle
        for (int[] edge : times) {
            if (cost[edge[0]] + edge[2] < cost[edge[1]])
                return -1;
        }
        for (int i = 1; i <= N; i++) {
            if (cost[i] == Integer.MAX_VALUE) return -1;
            total = Math.max(total, cost[i]);
        }
        // for (int i = 0; i < prev.length; i++) {
        //     System.out.println(prev[i]);
        // }
            
        return total;
        
    }
    
    public static void main(String[] args) {
    	int[][] times = new int[][] {
    		{1, 2, 1},
    		{1, 4, 2},
    		{2, 3, 3},
    		{3, 4, 1},
    		{3, 5, 6},
    		{4, 5, 3}
    	};
    	new NetworkDelayTime().networkDelayTimeTest(times, 5, 1);
    }
}
