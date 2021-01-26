package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

//Given n nodes labeled from 0 to n-1 and a list of undirected edges (each edge is a pair of nodes), write a function to check whether these edges make up a valid tree.
//
//Example 1:
//
//Input: n = 5, and edges = [[0,1], [0,2], [0,3], [1,4]]
//Output: true
//Example 2:
//
//Input: n = 5, and edges = [[0,1], [1,2], [2,3], [1,3], [1,4]]
//Output: false
//Note: you can assume that no duplicate edges will appear in edges. Since all edges are undirected, [0,1] is the same as [1,0] and thus will not appear together in edges.
public class GraphValidTree {

	// 根据Valid Tree的条件： 1） no cycle 2) fully connected
	// // 先判断完本来undirected graph就可以存在的“环”,再根据parent判断是否存在违反条件的环
	//  剔除无效“环” ==> 1) contains()，continue, e.g. validTree0()  2) 遍历过程中remove 掉反向的边
	// Time: O(N + E)
	// Space: O(N + E)
	public boolean validTree0(int n, int[][] edges) {
        if (n == 1 && edges.length == 0) return true;
        Map<Integer, List<Integer>> graph = new HashMap<>();
        buildGraph(edges, graph);
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        Map<Integer, Integer> parent = new HashMap<>(); // 其两个作用 1） visited 2)记录parents
        parent.put(0, -1);
        //Set<Integer> visited = new HashSet<>();
        while (!stack.isEmpty()) {
            int node = stack.pop();
            //visited.add(node);
            for (int adj : graph.getOrDefault(node, new ArrayList<>())) {
            	// 先判断完本来undirected graph就可以存在的“环”,再根据parent判断是否存在违反条件的环
                if (parent.get(node) == adj) { // 同一条边的 反方向 （a->b; b->a），所以没必要回去 (两点在同一条边上，走一遍就好)
                    continue; // 对directed图，我们可以直接 listVisited.contains(adj)来判断环
                    // 去掉反向边形成的“环” ==> 2) 从graph中直接删除  
                }
                if (parent.containsKey(adj)) {//这里判断是否有环
                	// ！！！！由于是 undirected, 所以这里判断已访问的点是否再被访问时，要去掉上面（a->b,b->a）的情况
                	//  这种“环”对undirected graph的本来就是存在的，所以这里在我们剔除掉这种环后
                	// 看该点已经被作为parent用过了，如果用过了，说明这个点即是孩子点也是父节点，有环，参考GraphValidTree.PNG
                    return false;
                }
                parent.put(adj, node); // 这样先进parent的都是当前点的父节点，所以不需要parent(int, list)
                stack.push(adj);
            }
        }
        return parent.size() == n;
    }
	
	/// DFS 实现上面的算法
	public boolean validTree1(int n, int[][] edges) {
        if (n == 1 && edges.length == 0) return true;
        Map<Integer, List<Integer>> graph = new HashMap<>();
        buildGraph(edges, graph);
        //Stack<Integer> stack = new Stack<>();
        //stack.push(0);
        Map<Integer, Integer> parent = new HashMap<>();
        parent.put(0, -1);
        return dfs(parent, graph, 0) && parent.size() == n; // 两个条件 1) no cycle 2) fully connected
        
    }
    public boolean dfs(Map<Integer, Integer> parent, Map<Integer, List<Integer>> graph, int s) {
        if (!graph.containsKey(s)) return true;// 可删除，因为下面有 getOrDefault()
        for (int adj : graph.getOrDefault(s, new ArrayList<>())) {
            if (parent.get(s) == adj)
                continue;
            if (parent.containsKey(adj)) return false;
            parent.put(adj, s);
            if (!dfs(parent, graph, adj))
                return false;
        }
        return true;
    }
    
    public boolean validTreeBFS(int n, int[][] edges) {
        if (n == 1 && edges.length == 0) return true;
        Map<Integer, List<Integer>> graph = new HashMap<>();
        buildGraph(edges, graph);
        //Stack<Integer> stack = new Stack<>();
        //stack.push(0);
        Map<Integer, Integer> parent = new HashMap<>();
        parent.put(0, -1);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int adj : graph.getOrDefault(node, new ArrayList<>())) {
                if (parent.get(node) == adj)
                    continue;
                if (parent.containsKey(adj))
                    return false;
                queue.offer(adj);
                parent.put(adj, node);
            }
        }
        return parent.size() == n;
        
    }
    
    // 从graph中直接删除反向边
    public boolean validTreeBFS2(int n, int[][] edges) {
        if (n == 1 && edges.length == 0) return true;
        Map<Integer, List<Integer>> graph = new HashMap<>();
        buildGraph(edges, graph);
        //Stack<Integer> stack = new Stack<>();
        //stack.push(0);
        //Map<Integer, Integer> parent = new HashMap<>();
        //parent.put(0, -1);
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int node = queue.poll();
            visited.add(node);
            for (int adj : graph.getOrDefault(node, new ArrayList<>())) {
                if (visited.contains(adj)) // 这样就可以直接用 contains()判断有无环 （剔除干扰“环”）
                    return false;
                queue.offer(adj);
                visited.add(adj);
                //System.out.println(adj);
                graph.get(adj).remove(Integer.valueOf(node)); //剔除假“环” ； remove（OBJECT），否则是取索引处的值
            }
        }
        return visited.size() == n;
        
    }
	
	// 利用图的特性， fully connected的图只有 n-1 条边，
	//			======>  如果大于 ==> 肯定有cycle； 如果小于  ===> 不是 fully connected，所以不是valid tree
	// ===> 如果等于，那么接下来就是普通的BFS/DFS， 判断是否所有的点 都可  reachable
	public boolean validTree(int n, int[][] edges) {
        if (edges.length != n - 1) return false; // 根据边的个数就可判断是否有环！！！！！！
        if (edges.length == 0) return true; // 注意对只有一个点的情况
        Map<Integer, List<Integer>> graph = new HashMap<>();
        
        // undirected graph ===> 在构建graph的时候，一定要 “双向”， 在遍历的时候用 visited[]
        // 否则，对该题给定的edges, 只给“一个方向的边”，但不是所有的都按（directed）顺序的， 比如 [1,0],[2,0],这是个valid的树
        for (int i = 0; i < edges.length; i++) {
            graph.computeIfAbsent(edges[i][0], k -> new LinkedList<>()).add(edges[i][1]);
            graph.computeIfAbsent(edges[i][1], k -> new LinkedList<>()).add(edges[i][0]);
        }
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(edges[0][0]);
        visited[edges[0][0]] = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                int node = queue.poll();
                //visited[node] = true;
                for (int adj : graph.getOrDefault(node, new LinkedList<>())) {
                    if (!visited[adj]) {
                        queue.offer(adj);
                        visited[adj] = true;
                    }               
                }
                size--;
            }
        }
        for (int i = 0; i < n; i++) {
            if (!visited[i]) return false;
        }
        return true;
    }
	
	// 利用fully-connected特性  ==> 边数必须 n - 1,  之后 DFS判断是否 reachable
	public boolean validTreeDFS(int n, int[][] edges) {
        if (edges.length != n - 1) return false;
        if (edges.length == 0) return true;
        Map<Integer, List<Integer>> graph = new HashMap<>();
        buildGraph(edges, graph);
        boolean[] visited = new boolean[n];
        // boolean hasCycle = isCycle(graph, new ArrayList<>(), 0, visited);
        // if (hasCycle) return false;
        dfs(graph, 0, visited);
        for (int i = 0; i < n; i++) {
            if (!visited[i]) return false;
        }
        return true;
    }
	
	public void buildGraph(int[][] edges, Map<Integer, List<Integer>> map) {
        for (int i = 0; i < edges.length; i++) {
            int s = edges[i][0];
            int d = edges[i][1];
            
            map.computeIfAbsent(s, k -> new ArrayList<>()).add(d);
            map.computeIfAbsent(d, k -> new ArrayList<>()).add(s);
        }
    }
	
    public void dfs(Map<Integer, List<Integer>> graph, int s, boolean[] visited) {
        if (visited[s]) return;
        visited[s] = true;
        for (int adj : graph.getOrDefault(s, new ArrayList<>())) {
            if (!visited[adj])
                dfs(graph, adj, visited);
        }
    }
    
	// Union-find   ===> 正常的 边上的点都可以归到一个root, 但是在此过程中，如果一条边的两个点指向同一个parent ==> 有环
	// Time: O(N * α(N))
    public boolean validTreeUnionFind(int n, int[][] edges) {
        if (edges.length != n - 1) return false; // 一定要
        //if (edges.length == 0) return true; //此处不用，因为不需要像上面buildGraph先
        int[] parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }
        for (int i = 0; i < edges.length; i++) {
            int s = edges[i][0];
            int d = edges[i][1];
            
            int sp = findParent(parents, s);
            int dp = findParent(parents, d);
            if (sp == dp) return false; // 同一条的两个点指向同一个parent (比如一个三角形)
            
            parents[dp] = sp;
            
        }
        return true;
    }
    
    public int findParent(int[] parents, int node) {
        if (node == parents[node])
            return node;
        int p = parents[node]; // keeps tree very shallow , so, find(...) amortizes to O(α(N)),
        // where α is the Inverse Ackermann Function. The incredible thing about this function is that 
        // it grows so slowly that N will never go higher than 4 in the universe as we know it! 
        // So while in "practice" it is effectively O(1)O(1), in "theory" it is not.
        p = findParent(parents, p);
        return p;
    }
    public static void main(String[] args) {
    	int n = 3;
    	int[][] edges = new int[][] {
    		{1,0}, {2,0}
    	};
    	System.out.println(new GraphValidTree().validTree(n, edges));
    }
}
