package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class DepthOfGraph {
	
	// a由于Graph从某个点出发可以有多个分支，在DFS回来的时候，对应不同的分支，该点有不同的 depth值，要取其中最大，
	// a对树来说，只有左右两支，直接比较大小，而对Graph，分支不定，不好处理，所有用BFS更简单
	public void lengthOfGraph() {
		int[][] edges = new int[][] {
			{0, 1}, {1, 2}, {1, 3}, {2, 4}, {4, 6}, {3, 5}
		};
		Map<Integer, List<Integer>> graph = new HashMap<>();
        buildGraph(edges, graph);
        boolean[] visited = new boolean[7];
        Map<Integer, Integer> lens = new HashMap<>();
        // dfs(0, graph, visited, lens);
        int depth = bfs(0, graph, visited, lens);
        System.out.println(depth);
	}

	int max = Integer.MIN_VALUE;
	public int dfs(int s, Map<Integer, List<Integer>> graph, boolean[] visited,
			Map<Integer, Integer> lens) {
//        if (!graph.containsKey(s) || visited[s]) {
		//a这个if始终不会进来，因为 每个点都在这个 graph里面
		// a要么就用 graph.get(s).size() == 0; 也没必要，因为 undirected, 每个点都会有adj
//        	//max = Math.max(max, length);
//        	return 0;
//        }
        visited[s] = true;
        int length = 1;//a每个点本身初始化为1，出发后往回一个个 +1
        for (int adj : graph.getOrDefault(s, new ArrayList<>())) {
        	if (visited[adj]) continue;// 这里一定要判断，否则走重复路
            length = dfs(adj, graph, visited, lens) + 1;
//        	int temp = dfs(adj, graph, visited, lens); // adj的depth
//        	length = temp + 1; // s 的就是子节点adj + 1
            // a此时得到一个到叶子节点的距离，否则会一直 DFS下去，不会跳到这里
            // a到底往回的时候才会， 此时我们得到一个个（从叶子节点到其实点所过的所有点的）距离
            // a！！！！但这里会出现问题，当某个点的子树高度不一的时候，在return回来的时候，该点的depth会被别的子树更新掉
            //     a 这也就是在 MaximumDepthOfBinaryTree.java  return max(left, right);
            //      1
            //     / \
            //    2   4
            //    /
            //    3
            // a如上面的例子，先遍历 node1 的左子树得到depth = 3, 但当往右子树走的时候，node1的depth是2，原来的3会被更新掉
            max = Math.max(max, length);
            // - a但是 lens map 不能放在这里，否则 叶子节点不会被放到map，如果是 put(s,len);因为当是叶子节点时，adj是空，不会进来
            // - a但如果 put(adj, len)
            // lens.put(s, length); // 叶子节点不会放进来
            // lens.put(adj, length); // 这个是错误的match, return回来后， 是 dfs(adj,)的值，而length是 return 回来的值 + 1， 是adj父节点 s的值，即叶子节点depth会是2，而不是1
            // 						// 除非 int temp = dfs(adj); length = temp + 1; put(adj, temp); 如上
        }
        lens.put(s, length);
        //max = Math.max(max, length);
        return length; // return 用以得到当前点的depth的值 （4->3->2）; 
        			// a到2 return 1, 到3时=len(2) + 1,在往回return给4
    }
	
	
	// BFS
	public int bfs(int s, Map<Integer, List<Integer>> graph, boolean[] visited,
			Map<Integer, Integer> lens) {
		Queue<Integer> q = new LinkedList<>();
		q.offer(s);
		int depth = 0;
		int count = 0;
		//visited[s] = true;
		while (!q.isEmpty()) {
			int size = q.size();
			//depth++;
			while (size > 0) {
				int node = q.poll();
				count++;
				visited[node] = true;
				lens.put(node, depth);
				for (int adj : graph.get(node)) {
					if (visited[adj]) continue;
					q.offer(adj);
				}
				size--;
			}
			//if (count < 7) depth++; // 否则 depth会多加 1
			depth++; // 此时最后一个点还会被 +1, 实际不用了
		}
		return depth;
	}
	public void buildGraph(int[][] edges, Map<Integer, List<Integer>> graph){
        for (int i = 0; i < edges.length; i++) {
            int s = edges[i][0];
            int d = edges[i][1];
            
            graph.computeIfAbsent(s, k -> new ArrayList<Integer>()).add(d);
            graph.computeIfAbsent(d, k -> new ArrayList<Integer>()).add(s);
        }
    }
	
	public static void main(String[] args) {
		new DepthOfGraph().lengthOfGraph();
	}
}
