package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

//There are n servers numbered from 0 to n-1 connected by undirected server-to-server connections forming a network where connections[i] = [a, b] represents a connection between servers a and b. Any server can reach any other server directly or indirectly through the network.
//
//A critical connection is a connection that, if removed, will make some server unable to reach some other server.
//
//Return all critical connections in the network in any order.


//Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
//Output: [[1,3]]
//Explanation: [[3,1]] is also accepted.
// 
//
//Constraints:
//
//1 <= n <= 10^5
//n-1 <= connections.length <= 10^5
//connections[i][0] != connections[i][1]
//There are no repeated connections.

class CriticalConnectionsInANetwork {

	// Cutting Edges / Bridge
	// In comparison with Articulation/Critical Point
	// 1) NO NEED TO CONSIDER THE ROOT  ==> NO RECORD CHILDREN NUMBER
	// 2) visitedTime(curr) < lowTime(adj)  ===> NO EQUAL
	public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        // build graph by creating adjacency list
        LinkedList<Integer>[] graph = new LinkedList[n];
        // initial each list
        for (int i = 0; i < n; i++) {
            graph[i] = new LinkedList<>();
        }
        // add edges
        for (List<Integer> edge : connections) {
            graph[edge.get(0)].add(edge.get(1));
            // for undirected graph
            graph[edge.get(1)].add(edge.get(0));
        }
        
        // declare required storage
        boolean[] visited = new boolean[n];
        int[] parent = new int[n];
        int[] visitedTime = new int[n];
        int[] lowTime = new int[n];//数组中的值表示DFS中该顶点不通过父顶点能访问到的祖先顶点中最小的顺序值（或者说时间戳）。
               // low（x）为 x 或 x 的子树能够追溯到的最早的祖先的次序号
        
        // initial parents
        Arrays.fill(parent, -1); //或者这里按parent[i] = i也可以
        
        List<List<Integer>> ce = new LinkedList<>();
        
        // for each node, do Tarjan algo
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                //dfs(i, parent, visitedTime, lowTime, visited, graph, ce);
                tarjan4CuttingPoints(i, parent, visitedTime, lowTime, visited, graph, ce);//但这里我们可以不用for 
            }
        }
        return ce;
    }
    
	int root = 0; // 用以 割点的判断
    private int time = 0;
    private Set<Integer> set = new HashSet<>();// cutting points //用以放割点
    // Tarjan algorithm   ===> https://www.cnblogs.com/yanyiming10243247/p/9294160.html
    public void dfs (int currNode, int[] parent, int[] visitedTime, int[] lowTime,
                    boolean[] visited, LinkedList<Integer>[] graph, List<List<Integer>> ce) {
        visited[currNode] = true;
        visitedTime[currNode] = time; // 第几个被访问到，固定的
        lowTime[currNode] = time; // 在该子树中已经在栈中的最小的time， 可能会被更新; 是能到visited点的最小值
        time++; // time是个全局变量
        
        // process its adjacent nodes
        for (int adj : graph[currNode]) {
            
            if (!visited[adj]) { // 对没有访问过的， DFS往下
                // set parent-child relation
                parent[adj] = currNode; // 因为是无向图，设定直接的父子关系，防止下面else走回去
                // dfs to process its child
                dfs(adj, parent, visitedTime, lowTime, visited, graph, ce);
                

                // no un-visited node or reach to end
                // 相等说明是强连通分量子图中的点，（任意两个点可以相互到达）， 所以不是 Critical edge
                // 回溯过程中 visitedd过程中比它小，说明这两个点都是critical points, 那么边就是critical edge
                if (visitedTime[currNode] < lowTime[adj]) {
                    List<Integer> cuttingEdge = new LinkedList<>();
                    cuttingEdge.add(adj);
                    cuttingEdge.add(currNode);
                    ce.add(cuttingEdge);
                }
                
                // 出栈回溯过程中不断更新 lowTime
                lowTime[currNode] = Math.min(lowTime[currNode], lowTime[adj]); // will get TLE if move before of IF STATEMENT
                
                // 这里要注意 - ！！！adj != parent[currNode], 即要比较的对象不是当前节点的直接父节点
            } else if (adj != parent[currNode]) { // 这里对已访问过的，但不是curr的直接父节点，比较以下time
            		// （针对Directed graph, 找critical points）对已经在栈中了，（回溯到某个它有另一支连到之前访问的点） 且该点不是curr的直接父节点（undirected graph）,需要更新lowTime
            									// 不在栈中的就不用管了
            										// 这里比较的是 visitedTime, 不是lowTime[adj]; 因为这个adj可能已经在别的strongly connected中，
            										// 而其low可能达不到 （lowTime这里指的是到visited[adj]点最小的time），但是 visitedTime是可以达到的，这是个固定值，所以这里跟 visitedTime[adj]比较
                lowTime[currNode] = Math.min(lowTime[currNode], visitedTime[adj]);
            }
        }
    }
    
    
    public void tarjan4CuttingPoints (int currNode, int[] parent, int[] visitedTime, int[] lowTime,
            boolean[] visited, LinkedList<Integer>[] graph, List<List<Integer>> ce) {
		visited[currNode] = true;
		visitedTime[currNode] = time; // 第几个被访问到，固定的
		lowTime[currNode] = time; // 在该子树中已经在栈中的最小的time， 可能会被更新; 是能到visited点的最小值
		time++; // time是个全局变量
		
		int child = 0;  // 统计child 的个数，用以对是 root的点判断是否是割点
		
		// process its adjacent nodes
		for (int adj : graph[currNode]) {
		    
		    if (!visited[adj]) { // 对没有访问过的， DFS往下
		        child++;
		        parent[adj] = currNode; 
		        // dfs to process its child
		        tarjan4CuttingPoints(adj, parent, visitedTime, lowTime, visited, graph, ce);
		        
		        // 判断割点 条件 1  - 不是root
		        // 判断是否是root的时候，我们用parent【】， 初始化是 -1， 所以 如果是 -1，就说明其为root
		        //if (currNode != root && visitedTime[currNode] <= lowTime[adj]) { // 与求 割边的不同，这里可以等于，同时该点不能是 root,即遍历开始的第一个点，
		        if (parent[currNode] != -1 && visitedTime[currNode] <= lowTime[adj]) { // 对 root,我们要另作处理， 即至少要有两个child, 所以这里要统计 child的个数
		            set.add(currNode);
		        }
		        
		        // 判断割点 条件 2
		        // 如果是 root， 看有几个child,至少2个才符合
		        //if (currNode == root && child >= 2) // ！！！这里在算root-0的child的时候，得按DFS方向，比如 0-1-2-0的图， 虽然 0-2有边,
		        if (parent[currNode] == -1 && child >= 2)	// 			但由于无向边，2-0，2已被访问，从0开始不会再走2去，child也就不会+1，所以0的child就1一个
		        								//   所以无向图再DFS走的时候，不能直接从图上看有几条边与其相连，因为已访问的点，
		        								// 即使有边连着，也不会再走了，所以这个child就不会被算上了，否则infinite了，
		        	set.add(currNode);
		        
		        // 出栈回溯过程中不断更新 lowTime
		        lowTime[currNode] = Math.min(lowTime[currNode], lowTime[adj]); // will get TLE if move before of IF STATEMENT
		        
		        // 这里要注意 - ！！！adj != parent[currNode], 即要比较的对象不是当前节点的直接父节点
		    } else if (adj != parent[currNode]) { // 这里对已访问过的，但不是curr的直接父节点，比较以下time
		    		// （针对Directed graph, 找critical points）对已经在栈中了，（回溯到某个它有另一支连到之前访问的点） 且该点不是curr的直接父节点（undirected graph）,需要更新lowTime
		    									// 不在栈中的就不用管了
		    										// 这里比较的是 visitedTime, 不是lowTime[adj]; 因为这个adj可能已经在别的strongly connected中，
		    										// 而其low可能达不到 （lowTime这里指的是到visited[adj]点最小的time），但是 visitedTime是可以达到的，这是个固定值，所以这里跟 visitedTime[adj]比较
		        lowTime[currNode] = Math.min(lowTime[currNode], visitedTime[adj]);
		    }
		}
}
    
    public List<List<Integer>> criticalConnections2(int n, List<List<Integer>> connections) {
        if (n == 1) return new ArrayList<>();
        Map<Integer, List<Integer>> graph = new HashMap<>();
        buildGraph(connections, graph);
        int[] visitedTime = new int[n];
        int[] lowTime = new int[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        List<List<Integer>> res = new ArrayList<>();
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                tarjan(graph, visited, res, n, visitedTime, lowTime, i, parent);
            }
        }
        return res;
        
    }
    
    //private int time = 0;
    public void tarjan(Map<Integer, List<Integer>> graph, boolean[] visited,
                      List<List<Integer>> res, int n, int[] visitedTime, int[] lowTime, int curr, int[] parent) {
        visitedTime[curr] = time;
        lowTime[curr] = time;
        time++;
        visited[curr] = true;
        for (int adj : graph.getOrDefault(curr, new ArrayList<>())) {
            if (!visited[adj]) {
                parent[adj] = curr;
                tarjan(graph, visited, res, n, visitedTime, lowTime, adj, parent);
                if (visitedTime[curr] < lowTime[adj]) {
                    List<Integer> sol = new ArrayList<>();
                    sol.add(curr);
                    sol.add(adj);
                    res.add(sol);
                }
                lowTime[curr] = Math.min(lowTime[curr], lowTime[adj]);
            } else if (parent[curr] != adj) {
                lowTime[curr] = Math.min(lowTime[curr], visitedTime[adj]);
            }
        }
    }
    
    public void buildGraph(List<List<Integer>> connections, Map<Integer, List<Integer>> graph) {
        for (List<Integer> edge : connections) {
            int s = edge.get(0);
            int d = edge.get(1);
            graph.computeIfAbsent(s, k -> new ArrayList<>()).add(d);
            graph.computeIfAbsent(d, k -> new ArrayList<>()).add(s);
        }
    }
    
    public static void main(String[] args) {
    	List<List<Integer>> connections = new ArrayList<>();
    	List<Integer> connection = new ArrayList<>();
    	connection.add(0); connection.add(1);
    	List<Integer> connection1 = Arrays.asList(1, 2);
    	List<Integer> connection2 = Arrays.asList(2, 0);
    	List<Integer> connection3 = Arrays.asList(1, 3);
    	
    	connections.add(connection);
    	connections.add(connection3);
    	connections.add(connection2);
    	connections.add(connection1);
    	
    	new CriticalConnectionsInANetwork().criticalConnections(4, connections);
    }
}
