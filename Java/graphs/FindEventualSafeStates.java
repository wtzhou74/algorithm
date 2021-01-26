package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

//In a directed graph, we start at some node and every turn, walk along a directed edge of the graph.  If we reach a node that is terminal (that is, it has no outgoing directed edges), we stop.
//
//Now, say our starting node is eventually safe if and only if we must eventually walk to a terminal node.  More specifically, there exists a natural number K so that for any choice of where to walk, we must have stopped at a terminal node in less than K steps.
//
//Which nodes are eventually safe?  Return them as an array in sorted order.
//
//The directed graph has N nodes with labels 0, 1, ..., N-1, where N is the length of graph.  The graph is given in the following form: graph[i] is a list of labels j such that (i, j) is a directed edge of the graph.
//
//Example:
//Input: graph = [[1,2],[2,3],[5],[0],[5],[],[]]
//Output: [2,4,5,6]
//Here is a diagram of the above graph.
//
//Illustration of graph
//
//Note:
//
//graph will have length at most 10000.
//The number of edges in the graph will not exceed 32000.
//Each graph[i] will be a sorted list of different integers, chosen within the range [0, graph.length - 1].
public class FindEventualSafeStates {

	// Time: O(N + E)
	// a反着看，是在找 outdegree == 0 的点，但这个outdegree是需要做改变的  ===> Topological Algo
	public List<Integer> eventualSafeNodes(int[][] graph) {
        if (graph == null || graph.length == 0) return new ArrayList<>();
        //Map<Integer, Integer> outDegree = new HashMap<>();
        int[] outDegree = new int[graph.length]; //a最后的点需要排序，这里我们借助数组的下标识Node，而值则是对应的outdegree, 之后我们只需O（N）取值就行
        Queue<Integer> q = new LinkedList<>();
        Map<Integer, Set<Integer>> reverseGraph= new HashMap<>(); // 需要反着来，这里我们reverse下，方便后面取值
        for (int i = 0; i < graph.length; i++) {
            if (graph[i].length == 0) {
                q.offer(i);
                continue;
            }
            outDegree[i] = graph[i].length;
            for (int j = 0; j < graph[i].length; j++) {
                reverseGraph.computeIfAbsent(graph[i][j], k -> new HashSet<>()).add(i);
            }

        }
        List<Integer> safe = new ArrayList<>();
        while (!q.isEmpty()) {
            int node = q.poll();
            //safe.add(node);
            if (reverseGraph.containsKey(node)) {
                for (int k : reverseGraph.get(node)) {
                    // if (outDegree.containsKey(k)) {
                    //     int newDeg = outDegree.get(k) - 1;
                    //     if (newDeg == 0) q.offer(k);
                    //     outDegree.put(k, newDeg);
                    // }
                    if (outDegree[k] > 0) {
                        outDegree[k] = outDegree[k] - 1;
                        if (outDegree[k] == 0) q.offer(k);
                    }
                }
            }

        }

        //Collections.sort(safe); // 用数组就不需要再对 list 排序了
        for (int i = 0; i < outDegree.length; i++) {
            if (outDegree[i] == 0)
                safe.add(i);
        }
        return safe;
    }
	
	// DFS ==> 每个点其“所有”出去的路径都能到达terminal点  ==> 不能有环 ==> 否则又回到“已访问的”点
	//     ===> a这块 Mark点的时候，除了 0-visited, 2-terminal, 还需要一个中间状态的点（会导致环的）  1-Being visited
	public List<Integer> eventualSafeNodesDFS(int[][] graph) {
        if (graph == null || graph.length == 0) return new ArrayList<>();
        // 0 : not visited; 1 - being visited; 2 - visited/terminal status
        int[] status = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            if (graph[i].length == 0) status[i] = 2;// terminal nodes
        }
        for (int i = 0; i < graph.length; i++) {
            //if (status[i] != 0) { // 比如例子中 2 对0时会是 being visited（1），但2作为起点时还需要再check的
                //isSafe(i, graph, status);
//                int result = isSafe(i, graph, status);
//                status[i] = result; // 2 会被忽略
            //}
        }
        List<Integer> safe = new ArrayList<>();
        for (int i = 0; i < status.length; i++) {
            
            if (status[i] == 2) safe.add(i);
        }
        return safe;
    }
	// DFS
    public int isSafe(int node, int[][] graph, int[] status) {
        if (status[node] != 0) {
            return status[node]; // 点2的status会在traceback 时候被写成2
        }
        status[node] = 1; // now process node i
        //a所有出去的路径
        for (int i = 0; i < graph[node].length; i++) {
            int s = isSafe(graph[node][i], graph, status);
            if (s == 1) {// 如果其中有一条不行，那就返回，说明不是Safe点 （什么时候return，有一个行不能return，要check所有，反之可以）
                return status[node] = 1; // 返回时候 同时更新 status
                //return 1;
            }
        }
        // a这里更新完后，之后再碰到这个点时直接就可以返回值而不需要再递归一次，相当于 DP中的Memo
        return status[node] = 2; // 递归返回时候同时更新status值，如果不是，最开始if(!=0)会skip掉第一个值2，对题目中的例子
        //return 2; // a当所有的 都能到达 terminal， 则这个点时 safe的，设置值为 2
    }
    
    public List<Integer> eventualSafeNodes3(int[][] graph) {
        if (graph == null || graph.length == 0) return new ArrayList<>();
        // 0 : not visited; 1 - being visited; 2 - visited/terminal status
        int[] status = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            if (graph[i].length == 0) status[i] = 2;// terminal nodes
        }
        List<Integer> safe = new ArrayList<>();
        for (int i = 0; i < graph.length; i++) {
            if(isSafe2(i, graph, status)) safe.add(i);
            //status[i] = result;
        }
        // List<Integer> safe = new ArrayList<>();
        // for (int i = 0; i < status.length; i++) {                
        //     if (status[i] == 2) safe.add(i);
        // }
        return safe;
    }
    
    public boolean isSafe2(int node, int[][] graph, int[] status) {
        
        if (status[node] == 2) return true;
        if (status[node] == 1) return false;
        status[node] = 1; // now process node i
        for (int i = 0; i < graph[node].length; i++) {
            if (!isSafe2(graph[node][i], graph, status)) {
                status[node] = 1;//a真正设置为 1,说明此点出发有环
                return false;
            }
            // } else if (status[graph[node][i]] != 2)
            //     return 1;
            // int s = isSafe(graph[node][i], graph, status);
            // if (s == 1) {
            //     return status[node] = 1;
            //     //return 1;
            // }
        }
        status[node] = 2;
        return true;
    }
    
    public static void main(String[] args) {
    	int[][] graph = new int[][] {
    		{1, 2},
    		{2, 3},
    		{5},
    		{0},
    		{5},{},{}
    	};
    	new FindEventualSafeStates().eventualSafeNodes3(graph);
    }
}
