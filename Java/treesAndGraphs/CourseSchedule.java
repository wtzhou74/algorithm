package treesAndGraphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

//There are a total of n courses you have to take, labeled from 0 to n-1.
//
//Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
//
//Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?
//
//Example 1:
//
//Input: 2, [[1,0]] 
//Output: true
//Explanation: There are a total of 2 courses to take. 
//             To take course 1 you should have finished course 0. So it is possible.
//Example 2:
//
//Input: 2, [[1,0],[0,1]]
//Output: false
//Explanation: There are a total of 2 courses to take. 
//             To take course 1 you should have finished course 0, and to take course 0 you should
//             also have finished course 1. So it is impossible.
//Note:
//
//The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
//You may assume that there are no duplicate edges in the input prerequisites.
	

// DIRECT GRAPH
public class CourseSchedule {

	// Toplogical Sort
	public boolean canFinish(int numCourses, int[][] prerequisites) {
		int[] indegree = new int[numCourses]; // keep track of the indegree for each node since TOPOLOGICAL SORT will start from indegree == 0
		int[][] graph = new int[numCourses][numCourses]; // MATRIX to keep record of the graph
		
		for (int[] edge : prerequisites) {
			indegree[edge[0]]++; // [0, 1] node 0 has a incoming edge
			graph[edge[1]][edge[0]] = 1; // Creating an edge between the two nodes;  // coverting the edge list to a graph matrix
		}
		
		// offer ALL indegree = 0 to Queue
		Queue<Integer> queue = new ArrayDeque<>(); // can be replaced with Stack
		int count = 0;
		// starting from indegree = 0
		while (!queue.isEmpty()) {
			int node = queue.poll();
			count++;
			for (int i = 0; i < numCourses; i++) {
				if (graph[node][i] == 1) { // there is a edge starting from the node whose indegree == 0
					graph[node][i] = 0;// removing the edge, NOT NECESSARY since Queue will poll the node, so it wont be visited again.
					indegree[i]--;
					if (indegree[i] == 0) { // If there is a cycle, there is AT LEAST ONE node whose indegree > 1
						queue.offer(i); // if it is 0, starting from here next
					}
				}
			}
		}
		
		return count == numCourses;
	}
	
	
	// Converting edge list to adjacency list
	public boolean adjacencyList(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        //int[][] graph = new int[numCourses][numCourses];
        List<Integer>[] adj = new ArrayList[numCourses]; // an array of adjacency list, the default value of list is NULL
        for (int[] edge : prerequisites) {
            indegree[edge[0]]++;
            //graph[edge[1]][edge[0]] = 1; // there is a edge between the two nodes
            if (adj[edge[1]] == null) adj[edge[1]] = new ArrayList<>();
            adj[edge[1]].add(edge[0]); // add the edge
        }
        
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0)
                stack.push(i);
        }
        int count = 0;
        while (!stack.isEmpty()) {
            int course = stack.pop();
            count++;
            // for (int i = 0; i < numCourses; i++) {
            //     if (graph[course][i] == 1) {
            //         //graph[course][i] = 0; 
            //         indegree[i]--;
            //         if (indegree[i] == 0)
            //             stack.push(i);
            //     }
            // }
            for (int i = 0; i < numCourses; i++) {
                if (adj[course].contains(i)) {
                    if (--indegree[i] == 0)
                        stack.push(i);
                }
            }
        }
        return count == numCourses;
    }
	
	
	public static void main(String[] args) {
		int[][] prerequisites = new int[][]{{1, 0}};
		new CourseSchedule().adjacencyList(2, prerequisites);
	}
}
