package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

//You are given an undirected connected graph. An articulation point (or cut vertex) is defined as a vertex which, when removed along with associated edges, makes the graph disconnected (or more precisely, increases the number of connected components in the graph). The task is to find all articulation points in the given graph.
//
//Input:
//The input to the function/method consists of three arguments:
//
//numNodes, an integer representing the number of nodes in the graph.
//numEdges, an integer representing the number of edges in the graph.
//edges, the list of pair of integers - A, B representing an edge between the nodes A and B.
//Output:
//Return a list of integers representing the critical nodes.
//
//Example:
//
//Input: numNodes = 7, numEdges = 7, edges = [[0, 1], [0, 2], [1, 3], [2, 3], [2, 5], [5, 6], [3, 4]]

public class CriticalRouters {
	
	public List<Integer> articulationPoints(int[][] edges, int numNodes, int numEdges) {
		// build graph
		LinkedList<Integer>[] graph = new LinkedList[numNodes];
		for (int i = 0; i < numNodes; i++) {
			graph[i] = new LinkedList<>();
		}
		// adding edges
		for (int[] edge : edges) {
			graph[edge[0]].add(edge[1]);
			// undirected graph
			graph[edge[1]].add(edge[0]);
		}
		
		List<Integer> ap = new LinkedList<>();
		
		// initial required storage
		// node is INTEGER, so, we can using Array, and its INDEX can be treated as Node number !!!!!!
		// So, we wont use Map
		int[] parent = new int[numNodes];
		boolean[] visited = new boolean[numNodes];
		int[] visitedTime = new int[numNodes];
		int[] lowTime = new int[numNodes];//数组中的值表示DFS中该顶点不通过父顶点能访问到的祖先顶点中最小的顺序值（或者说时间戳）。
		
		Arrays.fill(parent, -1);// set the parents of all nodes to -1, -1 means root
		
		// starting from non-visited node, for each of them, applying Tarjan algo
		for (int i = 0; i < numNodes; i++) {
			if (!visited[i]) {
				dfsTarjan(i, parent, visitedTime, lowTime, visited, graph, ap);
			}
		}
		// new ArrayList<>(set);
//		List<Integer> rs = new LinkedList<>();
//		for (int node : ap) {
//			rs.add(node);
//		}
		return ap;
	}
	
	private int time = 0;
	public void dfsTarjan(int currNode, int[] parent, int[] visitedTime, 
			int[] lowTime, boolean[] visited, LinkedList<Integer>[] graph, List<Integer> ap) {
		
		visited[currNode] = true;
		visitedTime[currNode] = lowTime[currNode] = ++time;
//		lowTime[currNode] = time; // by default, visitedTime == lowTime == time
//		time++;
		
		int children = 0; // used to check whether the ROOT is an articulation point
		
		// check its adjacent nodes
		for (int adj : graph[currNode]) {
			// for non-visited adj node
			if (!visited[adj]) {
				children++;
				parent[adj] = currNode;// set parent-child relation
				
				// for adjacent node, do Tarjan algorithm
				dfsTarjan(adj, parent, visitedTime, lowTime, visited, graph, ap);
				
				// once non-adj or reach the end
				// update lowTime if needed
				lowTime[currNode] = Math.min(lowTime[currNode], lowTime[adj]);
				
				// CONDITIONS OF AP node
				// 1) if it is a ROOT, AND NUM of Children >= 2
				if (parent[currNode] == - 1 && children >= 2) {
					ap.add(currNode);
				}
				// 2) non-root, if visitedTime(currNode) <= lowTime(adj)
				if (parent[currNode] != -1 && visitedTime[currNode] <= lowTime[adj]) {
					ap.add(currNode);
				}
				
			} else if (adj != parent[currNode]) {// no back edge
				// for visited and not currNode's parent node, update lowTime if needed, but we do not need to do Tarjan algo for them
				lowTime[currNode] = Math.min(visitedTime[adj], lowTime[currNode]);
			}
		}
		
		
	}

	public List<Integer> criticalRouters(int[][] edges, int numNodes, int numEdges) {
		
		// Articulation/Critical nodes
		List<Integer> ap = new ArrayList<>();
		// Parent of each node
		Map<Integer, Integer> parent = new HashMap<>();
		// visited nodes
		Set<Integer> visited = new HashSet<>();
		
		// visited time
		Map<Integer, Integer> visitedTime = new HashMap<>(); // increased by 1; CANNOT BE UPDATED
		// low time
		Map<Integer, Integer> lowTime = new HashMap<>();// can be updated
		
		// build a graph by 2D array (adjacency list )
		// List<Integer>[] adj = new ArrayList[numCourses];
		Map<Integer, Set<Integer>> graph = new HashMap<>();
		for (int i = 0; i < numNodes; i++) {
			graph.put(i, new HashSet<>());// initialize each node
		}
		
		// add edges
		for (int[] edge : edges) {
			graph.get(edge[0]).add(edge[1]); // connect the vertex edge[1] to vertex edge[0]
			// for UNDIRECTED graph
			graph.get(edge[1]).add(edge[0]);
		}
		
		// starting from a certain node to do Tarjan algorithm
		for (int i = 0; i < numNodes; i++) {
			if (!visited.contains(i)) {
				tarjan(visited, parent, ap, visitedTime, lowTime, i, graph);
			}
		}
		
		return ap;
	}
	
	//private int time  = 0;
	// Tarjan Algorithm / DFS
	
	// parent-child relation
	public void tarjan(Set<Integer> visited, Map<Integer, Integer> parent, List<Integer> ap,
			Map<Integer, Integer> visitedTime, Map<Integer, Integer> lowTime, int currNode, Map<Integer, Set<Integer>> graph) {
		
		visited.add(currNode);
		visitedTime.put(currNode, time);
		lowTime.put(currNode, time); // by default, visitedTime == lowTime == time
		time++;
		
		boolean isAP = false;
		int children = 0; // used to check if the node is root, then whether it is an AP node
		// process its adjacent nodes / children
		for (int adj : graph.get(currNode)) {
//			// for undirected graph, if the adj node is the parent node of curr node, skip
			if (parent.get(currNode) != null && parent.get(currNode) == adj) {
				continue;
			}

			// if the adjacent/child was not visited, start to process it
			if (!visited.contains(adj)) {
				
//				if (parent.get(currNode) == adj) {
//					continue;
//				}
				children++;
				// else. record its parent-child relation
				parent.put(adj, currNode);
				// process the node
				tarjan(visited, parent, ap, visitedTime, lowTime, adj, graph);
				
				// When node reaches the end/ no adjacent non-visited node, update lowTime
				int newLowTime = Math.min(lowTime.get(currNode), lowTime.get(adj));
				lowTime.put(currNode, newLowTime);
				
				// TWO CONDITIONS
				// 1) if it is ROOT, then there are more than 1 child
				// 2) If the currNode's visited Time is LESS THAN or EQUAL to the new coming visit node adj
//				if (parent.get(currNode) == null && children > 1
//						|| visitedTime.get(currNode) <= lowTime.get(adj))
//					ap.add(currNode);
				
				if (visitedTime.get(currNode) <= lowTime.get(adj))
					isAP = true;
			} else {
				// if the node has been visited, then see if the lowTime can be updated
				int newLowTime = Math.min(lowTime.get(currNode), visitedTime.get(adj));
				lowTime.put(currNode, newLowTime);
			}
		}
		if (parent.get(currNode) == null && children > 1
				|| parent.get(currNode) != null && isAP)
			ap.add(currNode);
		
	}

	public static void main(String[] args) {
		// int[][] edges = new int[][] {{0, 1}, {0, 2}, {1, 3}, {2, 3}, {2, 5}, {5, 6}, {3, 4}};
		// int[][] edges = new int[][] {{1, 0}, {0, 2}, {2, 1}, {0, 3}, {3, 4}};
		// int[][] edges = new int[][] {{0, 1}, {2, 3}, {1, 2}};
		int[][] edges = new int[][] {{0, 1}, {1, 2}, {1, 3}, {2, 0}, {1, 4}, {1, 6}, {3, 5}, {4, 5}};// [1, 1]???
		new CriticalRouters().articulationPoints(edges, 7, 7);
	}
}
