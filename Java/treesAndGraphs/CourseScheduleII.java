package treesAndGraphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

//There are a total of n courses you have to take, labeled from 0 to n-1.
//
//Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
//
//Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to finish all courses.
//
//There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses, return an empty array.
//
//Example 1:
//
//Input: 2, [[1,0]] 
//Output: [0,1]
//Explanation: There are a total of 2 courses to take. To take course 1 you should have finished   
//             course 0. So the correct course order is [0,1] .
//Example 2:
//
//Input: 4, [[1,0],[2,0],[3,1],[3,2]]
//Output: [0,1,2,3] or [0,2,1,3]
//Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both     
//             courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0. 
//             So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3] .
//Note:
//
//The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
//You may assume that there are no duplicate edges in the input prerequisites.

public class CourseScheduleII {

	public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        int[][] graph = new int[numCourses][numCourses]; // or apply Adjacent List
        int[] res = new int[numCourses];
        // calculate indegree for each course
        for (int[] edge : prerequisites) {
            indegree[edge[0]]++;
            graph[edge[1]][edge[0]] = 1;
        }
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) queue.offer(i);
        }
        int count = 0;// used to check circle
        while (!queue.isEmpty()) {
            int course = queue.poll();
            res[count] = course;
            count++;
            for (int i = 0; i < numCourses; i++) {
                if (graph[course][i] == 1) {
                    indegree[i]--;
                    if (indegree[i] == 0)
                        queue.offer(i);
                }
            }
        }
        return count == numCourses ? res : new int[0];
    }
	
	public int[] findOrder1(int numCourses, int[][] prerequisites) {
        if (prerequisites == null) return new int[0];
        // 这个就多此一举了， prerequisites为空， 说明这些课没关系，随便怎么输出， ！！！！！！！
        //	即便graph为空， 但 indegree[] 默认值都是 0， 这样 会被加到queue中最后输出
        //if (prerequisites.length == 0) return new int[]{0};
        Map<Integer, List<Integer>> graph = new HashMap<>();
        buildGraph(prerequisites, graph);
        int[] inDegree = new int[numCourses]; //用一个 indegree数组更方便，否则直接在graph里删，还是要一个个判断，不方便
        //int[] outDegree = new int[numCourses];
        for (int[] edge : prerequisites) {
            //int s = edge[1];
            int d = edge[0];
            inDegree[d]++;
            //outDegree[s]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
                
            
        }
        int[] res = new int[numCourses];
        //Arrays.fill(res, -1);
        int i = 0;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            res[i] = node;
            i++;
            for (int adj : graph.getOrDefault(node, new ArrayList<>())) {
                //System.out.println(inDegree[node]);
                inDegree[adj]--;
                if (inDegree[adj] == 0)
                    queue.offer(adj);
            }
            
        }
        //return res[res.length - 1] == -1 ? new int[0] : res;
        return i == numCourses ? res : new int[0];
        
        
    }
    public void buildGraph(int[][] prerequisites, Map<Integer, List<Integer>> graph) {
        for (int[] edge : prerequisites) {
            int s = edge[1];
            int d = edge[0];
            graph.computeIfAbsent(s, k -> new ArrayList<>()).add(d);
        }
    }
}
