package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

//There are N courses, labelled from 1 to N.
//
//We are given relations[i] = [X, Y], representing a prerequisite relationship between course X and course Y: course X has to be studied before course Y.
//
//In one semester you can study any number of courses as long as you have studied all the prerequisites for the course you are studying.
//
//Return the minimum number of semesters needed to study all courses.  If there is no way to study all the courses, return -1.
//
// 
//
//Example 1:
//
//
//
//Input: N = 3, relations = [[1,3],[2,3]]
//Output: 2
//Explanation: 
//In the first semester, courses 1 and 2 are studied. In the second semester, course 3 is studied.
//Example 2:
//
//
//
//Input: N = 3, relations = [[1,2],[2,3],[3,1]]
//Output: -1
//Explanation: 
//No course can be studied because they depend on each other.
// 
//
//Note:
//
//1 <= N <= 5000
//1 <= relations.length <= 5000
//relations[i][0] != relations[i][1]
//There are no repeated relations in the input.
public class ParallelCourses {

	// topological 
	public int minimumSemesters(int N, int[][] relations) {
        if (N == 1 && relations.length == 0) return 1;
        int[] inDegree = new int[N + 1];
        Map<Integer, List<Integer>> graph = new HashMap<>();
        buildGraph(relations, graph, inDegree);
        Queue<Integer> queue = new LinkedList<>();
        int count = 0;
        int semester = 0;
        for (int i = 1; i <= N; i++) { 
        	// !!!!!a这里要注意relations没记录的是“孤立点”， 在这个indegree数组中，其值对应的是 indegree[i]==0
        	// a所以即便relations中没看到该点，但还是会被放入Queue,因为==0； relations只是记录边（edges）
        	// a所以 （3， [[1,2]])a成立
            if (inDegree[i] == 0) {
                queue.offer(i);
                //course++;
            }    
        }
        while (!queue.isEmpty()) {
            int size = queue.size();
            semester++;
            while (size > 0) {
                int course = queue.poll();
                count++;
                System.out.println(count);
                for (int adj : graph.getOrDefault(course, new ArrayList<>())) {
                    inDegree[adj]--;
                    if (inDegree[adj] == 0)
                        queue.offer(adj);
                }
                size--;
            }
        }
        
        return count == N ? semester : -1;
    }
    
    public void buildGraph(int[][] relations, Map<Integer, List<Integer>> graph,
                          int[] inDegree) {
        for (int[] relation : relations) {
            int s = relation[0];
            int d = relation[1];
            inDegree[d]++;
            graph.computeIfAbsent(s, k -> new ArrayList<>()).add(d);
        }
    }
}
