package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

//
//In this problem, a tree is an undirected graph that is connected and has no cycles.
//
//The given input is a graph that started as a tree with N nodes (with distinct values 1, 2, ..., N), with one additional edge added. The added edge has two different vertices chosen from 1 to N, and was not an edge that already existed.
//
//The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] with u < v, that represents an undirected edge connecting nodes u and v.
//
//Return an edge that can be removed so that the resulting graph is a tree of N nodes. If there are multiple answers, return the answer that occurs last in the given 2D-array. The answer edge [u, v] should be in the same format, with u < v.
//
//Example 1:
//Input: [[1,2], [1,3], [2,3]]
//Output: [2,3]
//Explanation: The given undirected graph will be like this:
//  1
// / \
//2 - 3

//Example 2:
//Input: [[1,2], [2,3], [3,4], [1,4], [1,5]]
//Output: [1,4]
//Explanation: The given undirected graph will be like this:
//5 - 1 - 2
//    |   |
//    4 - 3
//Note:
//The size of the input 2D-array will be between 3 and 1000.
//Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.

public class RedundantConnection {

	// z这个问题的本质是 - 判断边的两个点是否有同一个根/组  ==> Union-find
	// z这个边是多余的， 那么从一定有其他路可以从这个点到另一点，那么问题就变成了看是否这个路径存在，存在的前提是这两个点肯定在同一个组 ,
	// z对每条边，如果这两个点当前不在一个组/指向同一个根， 就合并， 因为一条边上的，那这两点本该在一组的，所以合并
	public int[] findRedundantConnection(int[][] edges) {
        int[] roots = new int[edges.length + 1];
        for (int i = 0; i <= edges.length; i++) {
            roots[i] = i;
        }
        int[] res = new int[2];
        for (int[] edge : edges) {
            if (haveSameRoot(edge[0], edge[1], roots)) {
                res[0] = edge[0];
                res[1] = edge[1];
            }
        }
        
        return res;
    }
    
    public boolean haveSameRoot (int s, int d, int[] roots) {
        int rs = findRoot(s, roots);
        int rd = findRoot(d, roots);
        
        if (rs == rd) {
            return true;
        } else {
            roots[rd] = rs;
            //roots[s] = d; 是合并不同的根/最上层 节点， 不是当前点！！！！
            return false;
        }
    }
    
    public int findRoot(int n, int[] roots) {
        if (roots[n] == n) return n;
        roots[n] = findRoot(roots[n], roots);
        
        return roots[n];
    }
    
    
    /// Topological Sort 算法   ===> 判断环  （对无向图， 从indegree = 1开始） ==> 环里面删除要求的边
    public int[] topological(int[][] edges) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] indegree = new int[edges.length + 1];
        for (int i = 1; i <= edges.length; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
            
            indegree[edge[0]]++;
            indegree[edge[1]]++;
        }
        
        Queue<Integer> q = new LinkedList<>();
        for (int i = 1; i < indegree.length; i++) {
            if (indegree[i] == 1)
                q.offer(i);
        }
        
        if (q.isEmpty()) return edges[edges.length - 1];
        
        while (!q.isEmpty()) {
            int node = q.poll();
            for (int i : graph.get(node)) {
                indegree[i]--;
                if (indegree[i] == 1) // 无向图， 所以此处从 indegree = 1 开始删， 所有的点的出入度都 >= 1
                    q.offer(i);
            }
            graph.remove(node); // 删除掉这个点， 同时也删掉了与这个点相连的边
        }
        
        // a剩下的边肯定是个 环
        for (int i = edges.length - 1; i >= 0; i--) { //a因为要找最后一个可删除的边， 所以从后往前
            if (graph.containsKey(edges[i][0]) && graph.containsKey(edges[i][1])) { // 判断该边是否已删除， 因为是  undirected， 没被删除的边，其两个点都是graph的key
                return new int[] {edges[i][0], edges[i][1]};
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
    	new RedundantConnection().topological(new int[][] {
    		{1, 2},
    		{2, 3},
    		
    		{3, 4},
    		{1, 4},
    		{1, 5}
    	});
    }
}
