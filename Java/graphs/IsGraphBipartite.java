package graphs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

//Given an undirected graph, return true if and only if it is bipartite.
//
//Recall that a graph is bipartite if we can split it's set of nodes into two independent subsets A and B such that every edge in the graph has one node in A and another node in B.
//
//The graph is given in the following form: graph[i] is a list of indexes j for which the edge between nodes i and j exists.  Each node is an integer between 0 and graph.length - 1.  There are no self edges or parallel edges: graph[i] does not contain i, and it doesn't contain any element twice.
//
//Example 1:
//Input: [[1,3], [0,2], [1,3], [0,2]]
//Output: true
//Explanation: 
//The graph looks like this:
//0----1
//|    |
//|    |
//3----2
//We can divide the vertices into two groups: {0, 2} and {1, 3}.
//Example 2:
//Input: [[1,2,3], [0,2], [0,1,3], [0,2]]
//Output: false
//Explanation: 
//The graph looks like this:
//0----1
//| \  |
//|  \ |
//3----2
//We cannot find a way to divide the set of nodes into two independent subsets.
// 
//
//Note:
//
//graph will have length in range [1, 100].
//graph[i] will contain integers in range [0, graph.length - 1].
//graph[i] will not contain i or duplicate values.
//The graph is undirected: if any element j is in graph[i], then i will be in graph[j].
public class IsGraphBipartite {

	public boolean isBipartite(int[][] graph) {
        if (graph == null || graph.length == 0) return true;
        boolean[] visited = new boolean[graph.length];
        // a对图，其有可能是“森林”, 所以需要check以每个点作为起点的情况
        for (int i = 0; i < graph.length; i++) {
            //visited[i] = true;
            if (!visited[i] && graph[i].length > 0) {
                if (!checkIsBipartite(graph, i, visited))
                    return false;
            } else {
                visited[i] = true;
            }
        }
        return true;
    }
    
	// a处理边 ===> BFS, 对每条边，两个点分别处在不同的集合，如果某个点同时在两个集合，则不是
    public boolean checkIsBipartite(int[][] graph, int start, boolean[] visited) {
        Set<Integer> s1 = new HashSet<>();
        // a 用一个数组就行， 每个点可以有两种选择/颜色， 如果某个点被写入两种颜色，说明它们在同一个集合了，不符合
        // a 如 checkIsBipartite2（）方法
        Set<Integer> s2 = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        
        s1.add(start);
        queue.offer(start);
        boolean isSet2 = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            if (isSet2) {
                while (size > 0) {
                    int node = queue.poll();
                    //s2.add(node);
                    visited[node] = true;
                    // a这个点不能同时在两个集合上
                    if (s2.contains(node) && s1.contains(node)) {
                        return false;
                    }
                    for (int adj : graph[node]) {
                        //if (!s2.contains(adj) && !s1.contains(adj))
                        if (!visited[adj]) {
                            queue.offer(adj);
                            s2.add(adj);
                        }
                            //queue.offer(adj);
                    }
                    size--;
                }
                isSet2 = false;
            } else {
               while (size > 0) {
                    int node = queue.poll();                    
                    //s1.add(node);
                    visited[node] = true;
                    if (s2.contains(node) && s1.contains(node)) {
                        return false;
                    } 
                    for (int adj : graph[node]) {
                        if (!visited[adj]) {
                            queue.offer(adj);
                            s1.add(adj);
                        }
                    }
                    size--;            
                }
                isSet2 = true;
            }
            
        }
        return true;
    }
    
    
    // BFS ==> 优化
    public boolean checkIsBipartite2(int[][] graph, int start, boolean[] visited, int[] colors) {
        
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        //colors[start] = 1;
        int color = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                int node = queue.poll();
                visited[node] = true;
                //colors[node] = color;
                if (colors[node] > 0 && colors[node] != color) {
                    return false;
                } else {
                    colors[node] = color;
                }                
                for (int adj : graph[node]) {
                    if (!visited[adj]) {
                        queue.offer(adj);
                    }
                }
                size--;
            }
            color = color == 1 ? 2 : 1;
        }
        return true;
    }
    
    
    // DFS
    public boolean isBipartiteDFS(int[][] graph) {
        if (graph == null || graph.length == 0) return true;
        int[] visited = new int[graph.length]; // 一个数组就能兼具 VISITED，black/white或者positive/negative的表示功能
        // a不用额外再declar两个队列去做 black/white的判断
        //int[] colors = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            //visited[i] = true;
            if (visited[i] == 0 && graph[i].length > 0) {
                if (!checkIsBipartiteDFS(graph, i, visited, 1))
                    return false;
            } else {
                visited[i] = 1;
            }
        }
        return true;
    }
    
    // Set<Integer> s1 = new HashSet<>();
    // Set<Integer> s2 = new HashSet<>();
    public boolean checkIsBipartiteDFS(int[][] graph, int start, int[] visited, int color) {
        if (visited[start] != 0) {
            if (visited[start] != color)
                return false;
            return true;
        }
        visited[start] = color;
//         if (color == 1) {
//                 if (s2.contains(start))
//                     return false;
//                 else 
//                     s1.add(start);
//             } 

//         if (color == 2 ) {
//             if (s1.contains(start)) 
//                 return false;
//             else 
//                 s2.add(start);
//         }
//         color = color == 1 ? 2 : 1;
        for (int i = 0; i < graph[start].length; i++) {            
            //if (visited[graph[start][i]]) continue;
            if (!checkIsBipartiteDFS(graph, graph[start][i], visited, -color)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isBipartiteTest(int[][] graph) {
        if (graph == null || graph.length == 0) return true;
        boolean[] visited = new boolean[graph.length];
        int[] colors = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            //visited[i] = true;
            if (!visited[i] && graph[i].length > 0) {
                if (!checkIsBipartiteTest(graph, i, visited, 1))
                    return false;
            } else {
                visited[i] = true;
            }
        }
        return true;
    }
    
    Set<Integer> s1 = new HashSet<>();
    Set<Integer> s2 = new HashSet<>();
    public boolean checkIsBipartiteTest(int[][] graph, int start, boolean[] visited, int color) {
        //if (visited[start]) return true;
        if (visited[start]) {
            if (color == 1 && s2.contains(start)) 
                return false; 
            else if (color == 2 && s1.contains(start)) 
                return false;
            else return true; //a对已visited的点不能直接return true, 要先判断是否false
            // a这块return true 会在 for循环中，接着判断另一个adjacent
        }
        if (color == 1) s1.add(start);
        if (color == 2) s2.add(start);
        
        visited[start] = true;
        color = color == 1 ? 2 : 1; // 这里是深度的，每一层对应一个不同的color,相当于边的两个不同点对应不同颜色，
        // a或者像上一种方法中放在参数中，保证每递归一次（深度+1）color变一次，不能放在for中，for中是同一层次的
        for (int i = 0; i < graph[start].length; i++) {                               
            //if (visited[graph[start][i]]) continue; // 这块已经在最开始那块判断过了
        	// a或者放到这里也可以，如下
//        	if (visited[start]) {
//                if (color == 1 && s2.contains(graph[start][i])) 
//                    return false; 
//                else if (color == 2 && s1.contains(graph[start][i])) 
//                    return false;
//                else continue; // continue下一个adj,不是return true, 而上面的return true达到同样的效果
//            }
            
            if (!checkIsBipartiteTest(graph, graph[start][i], visited, color)) {
                return false; // 这块的color还可以像上一种方法那样，直接在参数里面取反
            }
        }
        return true;
    }
    
    
    public static void main(String[] rgs) {
    	int[][] graph = new int[][] {
    		{1, 3},
    		{0, 2},
    		{1, 3},
    		{0, 2}
    	};
    	new IsGraphBipartite().isBipartiteTest(graph);
    }
}
