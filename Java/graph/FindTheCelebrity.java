package graph;

import java.util.HashMap;
import java.util.Map;

//Suppose you are at a party with n people (labeled from 0 to n - 1) and among them, 
//there may exist one celebrity. The definition of a celebrity is that all the other n - 1 people know him/her but he/she does not know any of them.
//
//Now you want to find out who the celebrity is or verify that there is not one.
//The only thing you are allowed to do is to ask questions like: "Hi, A. Do you know B?" to get information of whether A knows B. You need to find out the celebrity (or verify there is not one) by asking as few questions as possible (in the asymptotic sense).
//
//You are given a helper function bool knows(a, b) which tells you whether A knows B. 
//Implement a function int findCelebrity(n). There will be exactly one celebrity if he/she is in the party. Return the celebrity's label if there is a celebrity in the party. If there is no celebrity, return -1.

// Note:
//	The directed graph is represented as an adjacency matrix, which is an n x n matrix where a[i][j] = 1 means person i knows person j while a[i][j] = 0 means the contrary.
//	Remember that you won't have direct access to the adjacency matrix.
public class FindTheCelebrity {

	// Treate it as a Directed GRAPH problem
	// ====> INDEGREE(i) == n - 1 && OUTDEGREE(i) = 0
	public int findCelebrity(int n) {
		if (n <= 1)
			return -1;
		Map<Integer, Integer> outDegree = new HashMap<>();
		// int[][] graph = new int[n][n];
		for (int i = 0; i < n; i++) {
			int degree = 0;
			for (int j = 0; j < n; j++) {
				if (i == j)
					continue;
				if (knows(i, j)) {
					degree++;
				}
			}
			outDegree.put(i, degree);
		}
		int zero = -1;
		int count = 0;
		for (Map.Entry<Integer, Integer> entry : outDegree.entrySet()) {
			if (entry.getValue() == 0) {
				count++;
				if (count > 1)
					return -1;
				zero = entry.getKey();
			}
		}
		if (zero == -1)
			return -1;
		// for the node whose outdegree is 0
		// check REQ 2: ALL other people know him
		// ALTERNATIVE: record its INDEGREE, and its indegree == n -1
		for (int i = 0; i < n; i++) {
			if (!knows(i, zero))
				return -1;
		}
		return zero;
	}

	// API
	public boolean knows(int a, int b) {
		return true;
	}
}
