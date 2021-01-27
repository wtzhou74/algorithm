package amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

//We have a list of bus routes. Each routes[i] is a bus route that the i-th bus repeats forever. For example if routes[0] = [1, 5, 7], this means that the first bus (0-th indexed) travels in the sequence 1->5->7->1->5->7->1->... forever.
//
//We start at bus stop S (initially not on a bus), and we want to go to bus stop T. Travelling by buses only, what is the least number of buses we must take to reach our destination? Return -1 if it is not possible.
//
//Example:
//Input: 
//routes = [[1, 2, 7], [3, 6, 7]]
//S = 1
//T = 6
//Output: 2
//Explanation: 
//The best strategy is take the first bus to the bus stop 7, then take the second bus to the bus stop 6.
// 
//
//Constraints:
//
//1 <= routes.length <= 500.
//1 <= routes[i].length <= 10^5.
//0 <= routes[i][j] < 10 ^ 6.

public class BusRoutes {

	// 根据题意 ==> 图的问题  ==> 基于 Buses 还是 基于 Routes ? ==> 由于找最少Buses ==>尝试基于 Buses构图
	//  ===> BFS 从S到T， ===> 也是个类似Matrix里多源的遍历
	//  ===> 构图的时候，buses间相互连续，是因为它们的routes有共同的 至少一个stop
	//  ===> 之后就是一个典型的基于多源的 BFS遍历图找最短路径问题
	public int numBusesToDestination(int[][] routes, int S, int T) {
        if (routes == null || routes.length == 0)
            return 0;
        if (S == T) return 0;
        Map<Integer, List<Integer>> graph = new HashMap<>();
        buildGraph(routes, graph);
        // 无向的，所以需要一个 visited数组
        boolean[] visited = new boolean[routes.length];
        Queue<Integer> queue = new LinkedList<>();
        // 找到其实源，就是看哪些Bus的route还有S， 有的话就意味着可以乘这辆bus出发
        for (int i = 0; i < routes.length; i++) {
            int idx = Arrays.binarySearch(routes[i], S);
            if (idx >= 0) {
                queue.offer(i);
                visited[i] = true;
            }
        }
        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            count++;
            while (size > 0) {
                int route = queue.poll();
                //int idx = Arrays.binarySearch(routes[route], T);
                // 看当前route有没有T的，有的话就可以直接乘这辆bus到达T了
                if (binarySearch(routes[route], T)) {
                    return count;
                }
                for (int adj : graph.getOrDefault(route, 
                                                  new ArrayList<>())) {
                    if (visited[adj]) continue;
                    visited[adj] = true;
                    queue.offer(adj);
                }
                size--;
            }
        }
        return -1;
    }
    
    public void buildGraph(int[][] routes, Map<Integer, List<Integer>> graph) {
    	// routes间比较stops，看有没有一样，有一样的，这两个buses就可以连接，且是个无向的，因为buses可以来回开
        for (int i = 0; i < routes.length; i++) {
            for (int j = i + 1; j < routes.length; j++) {
                boolean inter = isIntersection(routes[i], routes[j]);
                if (inter) {
                	// 之后get的时候，用的 getOrDefault，所以不用担心有的key没有初始化
                    graph.computeIfAbsent(i, 
                                          k -> new ArrayList<>()).add(j);
                    graph.computeIfAbsent(j,
                                         k -> new ArrayList<>()).add(i);
                }
            }
        }
    }
    
    public boolean isIntersection(int[] s1, int[] s2) {
        Arrays.sort(s1);
        Arrays.sort(s2);
        int i = 0, j = 0;
        while (i < s1.length && j < s2.length) {
            if (s1[i] == s2[j])
                return true;
            if (s1[i] < s2[j])
                i++;
            else
                j++;
        }
        return false;
    }
    
    public boolean binarySearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return nums[left] == target;
    }
}
