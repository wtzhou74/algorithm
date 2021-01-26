package oa.amazon;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
        int[] lowTime = new int[n];
        
        // initial parents
        Arrays.fill(parent, -1);
        
        List<List<Integer>> ce = new LinkedList<>();
        // for each node, do Tarjan algo
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, parent, visitedTime, lowTime, visited, graph, ce);
            }
        }
        return ce;
    }
    
    private int time = 0;
    // Tarjan algorithm
    public void dfs (int currNode, int[] parent, int[] visitedTime, int[] lowTime,
                    boolean[] visited, LinkedList<Integer>[] graph, List<List<Integer>> ce) {
        visited[currNode] = true;
        visitedTime[currNode] = time;
        lowTime[currNode] = time;
        time++;
        
        // process its adjacent nodes
        for (int adj : graph[currNode]) {
            
            if (!visited[adj]) {
                // set parent-child relation
                parent[adj] = currNode;
                // dfs to process its child
                dfs(adj, parent, visitedTime, lowTime, visited, graph, ce);
                

                // no un-visited node or reach to end
                if (visitedTime[currNode] < lowTime[adj]) {
                    List<Integer> cuttingEdge = new LinkedList<>();
                    cuttingEdge.add(adj);
                    cuttingEdge.add(currNode);
                    ce.add(cuttingEdge);
                }

                lowTime[currNode] = Math.min(lowTime[currNode], lowTime[adj]); // will get TLE if move before of IF STATEMENT
                
            } else if (adj != parent[currNode]) {
                lowTime[currNode] = Math.min(lowTime[currNode], visitedTime[adj]);
            }
        }
    }
    
    public static void main(String[] args) {
    	List<List<Integer>> connections = Arrays.asList(Arrays.asList(0, 1),
    			Arrays.asList(1,2), Arrays.asList(2, 0), Arrays.asList(1, 3));
    	new CriticalConnectionsInANetwork().criticalConnections(4, connections);
    }
}
