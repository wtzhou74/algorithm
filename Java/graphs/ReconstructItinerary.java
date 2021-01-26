package graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeSet;

//Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], reconstruct the itinerary in order. All of the tickets belong to a man who departs from JFK. Thus, the itinerary must begin with JFK.
//
//Note:
//
//If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string. For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
//All airports are represented by three capital letters (IATA code).
//You may assume all tickets form at least one valid itinerary.
//Example 1:
//
//Input: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
//Output: ["JFK", "MUC", "LHR", "SFO", "SJC"]
//Example 2:
//
//Input: [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
//Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
//Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"].
//             But it is larger in lexical order.

// Hierholzer's Algorithm ==> 欧拉路径
public class ReconstructItinerary {
	
	// 错误的solution
	public List<String> findItinerary0(List<List<String>> tickets) {
        if (tickets == null) return null;
        if (tickets.size() == 1) return tickets.get(0);
        Map<String, TreeSet<String>> graph = new HashMap<>();
        //buildGraph(tickets, graph);
        List<String> res = new ArrayList<>();
        res.add("JFK");
        dfs(graph, "JFK", res);
        return res;
    }
    
    public void dfs(Map<String, TreeSet<String>> graph, String start, List<String> res) {
        if (!graph.containsKey(start)) return;
        if (graph.get(start).size() == 0) return;        
        String next = graph.get(start).first(); // ！！！这块直接这么处理的后果是 找出来的路径不一定走过所有的route, 看 ReconstructItinerary.PNG
        System.out.println(next);  /// ===============> intuitive solution是 backtracking， 同时 Greedy，总是找 排最前面的 in lexical order
        graph.get(start).remove(next); // 修改写入顺序，找到最后一个站 再写入list ==> topological解法
        res.add(next);
        dfs(graph, next, res);
    }
    
    // backtracking + greedy
    // Time O(E^d)  E is the number of total flights (edges), d is the maximum number of flights from an airport
    // Space O(V + E)
    public List<String> findItinerary(List<List<String>> tickets) {
        if (tickets == null) return null;
        if (tickets.size() == 1) return tickets.get(0);
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, boolean[]> visited = new HashMap<>();
        buildGraph(tickets, graph);
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            Collections.sort(entry.getValue()); // 这里我们不直接用 PriorityQueue, 不方便按顺序遍历
            visited.put(entry.getKey(), new boolean[entry.getValue().size()]);
        }
        
        int routeSize = tickets.size() + 1;
        List<String> res = new ArrayList<>();
        res.add("JFK");
        dfs(graph, "JFK", res, visited, routeSize);
        return finalRes;
    }
    private List<String> finalRes = new ArrayList<>();
    public boolean dfs(Map<String, List<String>> graph, String start, List<String> sol, Map<String, boolean[]> visited, int size) {
        if (sol.size() == size) {
            finalRes = new ArrayList<String>(sol);
            return true; // 这里要return true 或者 false, 否则找出的结果不一定按 lexical order
        }
        if (!graph.containsKey(start)) return false; // 还没找完全部route, 就已经不能走了，即此路不通
        if (graph.get(start).size() == 0) return false;
        boolean[] nextVisited = visited.get(start);
        // backtrack 每种情况
        for (int i = 0; i < graph.get(start).size(); i++) {
            if (nextVisited[i]) continue;
            String next = graph.get(start).get(i);
            sol.add(next);
            nextVisited[i] = true;
            boolean res = dfs(graph, next, sol, visited, size);
            sol.remove(sol.size() - 1);
            nextVisited[i] = false;
            if (res) return true;// 之后的是true，那么返回 true
        }
        return false; // 所有都情况都遍历完了还是没找到，那么直接return false， 无结果
    }
    
    //////////////// 飞行路线本质是一个 按序的，   ==> 逆 topological order  ==>ReconstructItinerary2.PNG
    ////////////////==> topological思路， 但这块不能直接套用 topological的解法, 这是个逆向的topological
    public List<String> findItinerary2(List<List<String>> tickets) {
        if (tickets == null) return null;
        if (tickets.size() == 1) return tickets.get(0);
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        buildGraph1(tickets, graph);
        List<String> res = new ArrayList<>();
        //res.add("JFK");
        dfs1(graph, "JFK", res);
        Collections.reverse(res); // 逆向的 topological,所以要逆回去，可以用 list.add(0, itinerary)代替
        return res;
    }
    public void dfs1(Map<String, PriorityQueue<String>> graph, String start, List<String> res) {
        PriorityQueue<String> pq = graph.get(start);
        while (pq != null && pq.size() > 0) {
            String next = pq.poll(); //删掉边，相当于 visited, 但如果删掉会影响后面的遍历就不可以
            dfs1(graph, next, res);
        }
        // 找到 topological的 "outdegree" = 0的点， 记录下来，再返回，完成正常的 topological 顺序
        
        res.add(start);//需要reverse, 这里第一个写进去的就是最后一站
        //res.add(0, start);//始终写在 list的最前面，就不需要reverse了
    }
    
    public void buildGraph1(List<List<String>> tickets, Map<String, PriorityQueue<String>> graph) {
        for (List<String> ticket : tickets) {
            String s = ticket.get(0);
            String d = ticket.get(1);
            
            graph.computeIfAbsent(s, k -> new PriorityQueue<String>()).add(d);
        }
    }
    
    public void buildGraph(List<List<String>> tickets, Map<String, List<String>> graph) {
        for (List<String> ticket : tickets) {
            String s = ticket.get(0);
            String d = ticket.get(1);
            
            graph.computeIfAbsent(s, k -> new ArrayList<String>()).add(d);
        }
    }
    
    
    
    public static void main(String[] args) {
    	List<List<String>> tickets = new ArrayList<>();
    	List<String> ticket0 = new ArrayList<>();
    	ticket0.add("JFK");
    	ticket0.add("KUL");
    	List<String> ticket1 = new ArrayList<>();
    	ticket1.add("JFK");
    	ticket1.add("NRT");
    	List<String> ticket2 = new ArrayList<>();
    	ticket2.add("NRT");
    	ticket2.add("JFK");
    	tickets.add(ticket0);
    	tickets.add(ticket1);
    	tickets.add(ticket2);
    	
    	new ReconstructItinerary().findItinerary2(tickets);
    }
}
