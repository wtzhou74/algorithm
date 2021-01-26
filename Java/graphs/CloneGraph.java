package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

//Given a reference of a node in a connected undirected graph.
//
//Return a deep copy (clone) of the graph.
//
//Each node in the graph contains a val (int) and a list (List[Node]) of its neighbors.
//
// class Node {
//    public int val;
//    public List<Node> neighbors;
//}
// 
//
//Test case format:
//
//For simplicity sake, each node's value is the same as the node's index (1-indexed). For example, the first node with val = 1, the second node with val = 2, and so on. The graph is represented in the test case using an adjacency list.
//
//Adjacency list is a collection of unordered lists used to represent a finite graph. Each list describes the set of neighbors of a node in the graph.
//
//The given node will always be the first node with val = 1. You must return the copy of the given node as a reference to the cloned graph.
//
// 
//
//Example 1:
//
//
//Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
//Output: [[2,4],[1,3],[2,4],[1,3]]
//Explanation: There are 4 nodes in the graph.
//1st node (val = 1)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
//2nd node (val = 2)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
//3rd node (val = 3)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
//4th node (val = 4)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
//Example 2:
//
//
//Input: adjList = [[]]
//Output: [[]]
//Explanation: Note that the input contains one empty list. The graph consists of only one node with val = 1 and it does not have any neighbors.
//Example 3:
//
//Input: adjList = []
//Output: []
//Explanation: This an empty graph, it does not have any nodes.
//Example 4:
//
//
//Input: adjList = [[2],[1]]
//Output: [[2],[1]]
// 
//
//Constraints:
//
//1 <= Node.val <= 100
//Node.val is unique for each node.
//Number of Nodes will not exceed 100.
//There is no repeated edges and no self-loops in the graph.
//The Graph is connected and all nodes can be visited starting from the given node.

class Node {
	int val;
	List<Node> neighbors;
	
	public Node() {
		val = 0;
		neighbors = new ArrayList<Node>();
	}
	
	public Node(int _val) {
		this.val = _val;
		this.neighbors = new ArrayList<Node>();
	}
	
	public Node(int _val, ArrayList<Node> _neighbors) {
		this.val = _val;
		this.neighbors = _neighbors;
	}
}
public class CloneGraph {
	
	public Node cloneGraph0(Node node) {
        if (node == null) return null;
        if (node.neighbors.size() == 0) return node;
        Set<Integer> visited = new HashSet<>();
        Node newNode = new Node(node.val, new ArrayList<>());
        visited.add(node.val);
        dfs(node, visited, newNode);
        return newNode;
    }
    
	// 错误的solution
    public void dfs(Node node, Set<Integer> visited, Node copyNode) {
        if (node == null || node.neighbors.size() == 0) return;
        //System.out.println(node.val);
        // if (visited.contains(node.val)) {
        //     copyNode.neighbors.add(node);
        //     return;
        // }
        for (Node adj : node.neighbors) {
            
            if (visited.contains(adj.val)) {
                copyNode.neighbors.add(adj);
                continue;
            }
            // 这里往里面加的的 node,在之前可能已经 new 过了
            // 比如： 1 [2, 3], 此时 2，3已经被new 过了，所以 真正 轮到2，3时候不用再 new 了
            // 需要一个 Map来记录已经 new 过的 node, 已经有的，就直接拿来用就好
            Node newNode = new Node(adj.val, new ArrayList<>()); 
            copyNode.neighbors.add(newNode);
            visited.add(adj.val);
            dfs(adj, visited, newNode);
        }
    }
    
    // Time: O(n)
    // DFS
    public Node cloneGraphDFS(Node node) {
        if (node == null) return null;
        if (node.neighbors.size() == 0) return new Node(node.val);
        
        Map<Integer, Node> map = new HashMap<>();
        Node newNode = new Node(node.val, new ArrayList<>());
        map.put(node.val, newNode);
        dfs(node, map, newNode);
        return newNode;
    }
    
    // 无return, 通过传入一个新的起始对象节点，开始复制过程
    public void dfs(Node start, Map<Integer, Node> map, Node newNode) {
        if (start == null || start.neighbors.size() == 0)
            return;
        for (Node adj : start.neighbors) {
            if (map.containsKey(adj.val)) { // 画图理清楚DFS的过程
                newNode.neighbors.add(map.get(adj.val));//加边就行，不用再DFS这个点了，否则infinite
                continue;
            }
            Node newAdj = new Node(adj.val, new ArrayList<>());
            map.put(adj.val, newAdj);
            newNode.neighbors.add(newAdj);
            dfs(adj, map, newAdj);
        }
    }
    
    // 带 return的，即每DFS时候clone一个
    public Node cloneGraphDFS1(Node node) {
        if (node == null) return null;
        if (node.neighbors.size() == 0) return new Node(node.val);
        
        Map<Integer, Node> map = new HashMap<>();
        return dfs2(node, map);
    }
    
    // 每次return 回一个clone的点
    public Node dfs2(Node start, Map<Integer, Node> map) {
        if (start == null)
            return null;
        //没必要
//        if (start.neighbors.size() == 0)
//            return new Node(start.val, new ArrayList<>());
        //复制的过程，如果已经 new，就直接return, 否则创建一个新的节点
        if (map.containsKey(start.val))
            return map.get(start.val);
        Node newNode = new Node(start.val, new ArrayList<>());
        map.put(start.val, newNode);
        // 处理 adj
        for (Node adj : start.neighbors) {
            Node newAdj = dfs2(adj, map);//复制adj
            newNode.neighbors.add(newAdj);//将得到的newAdj放入到对应的新节点的neighbors中
        }
        return newNode; //最后返回的是 递归回到最初的点
    }
    
    // BFS
    // Note: 1) 是否需要新建 Node
    public Node cloneGraphBFS(Node node) {
        if (node == null) return null;
        // 不能直接 return node, 因为是要 clone, 所以要 new 一个
        if (node.neighbors.size() == 0) return new Node(node.val);
        
        
        Queue<Node> queue = new LinkedList<>();
        Map<Integer, Node> nodeMap = new HashMap<>();// 记录当前需要的 Node已经新建了，不需要再 new了 ！！！！！！
        Node newNode = new Node(node.val, new ArrayList<Node>());//主要用于记录一个新的头节点用于return
        nodeMap.put(node.val, newNode);
        //Set<Integer> visited = new HashSet<>(); // 记录已访问的点， 入Queue时候很重要, 但这里用 nodeMap判断也可以
        queue.offer(node);
        //visited.add(node.val);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                Node curr = queue.poll();
                Node newCurr = null;
                if (!nodeMap.containsKey(curr.val)) {
                    newCurr = new Node(curr.val, new ArrayList<>());
                } else {
                    newCurr = nodeMap.get(curr.val);
                }
                //visited.add(curr.val);
                for (Node adj : curr.neighbors) {
                	// 取代下面的 visited
                	//不能放下面去，因为 接下来nodeMap会往里加node
                	if (!nodeMap.containsKey(adj.val))
                        queue.offer(adj);
                	
                	// 对新的graph, 不用管顺序，只管 new或者add就行，只需要注意 Node是否已经 new了
                	// 另外遍历的顺序追踪原 graph
                    if (nodeMap.containsKey(adj.val)) {
                        newCurr.neighbors.add(nodeMap.get(adj.val));
                    } else {
                        Node newAdj = new Node(adj.val, new ArrayList<>());
                        nodeMap.put(adj.val, newAdj);
                        newCurr.neighbors.add(newAdj);
                    }
                    // visited放这里无所谓，因为跟的是原graph，上面的步骤修改的是基于新graph
//                    if (!visited.contains(adj.val)) {
//                        queue.offer(adj);
//                        visited.add(adj.val);
//                    }
                        
                }
                size--;
            }
        }
        return newNode;
    }
    
    public static void main(String[] args) {
    	Node node1 = new Node(1, new ArrayList<Node>());
    	Node node2 = new Node(2, new ArrayList<Node>());
    	Node node3 = new Node(3, new ArrayList<Node>());
    	Node node4 = new Node(4, new ArrayList<Node>());
    	
    	node1.neighbors.add(node2);
    	node1.neighbors.add(node4);
    	
    	node2.neighbors.add(node1);
    	node2.neighbors.add(node3);
    	
    	node3.neighbors.add(node2);
    	node3.neighbors.add(node4);
    	
    	node4.neighbors.add(node1);
    	node4.neighbors.add(node3);
    	
    	new CloneGraph().cloneGraphDFS1(node1);
    }
	
}
