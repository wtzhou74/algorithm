package queueAndStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Given the head of a graph, return a deep copy (clone) of the graph. Each node in the graph contains
 * a label (int) and a list (List[UndirectedGraphNode]) 
 * of its neighbors. There is an edge between the given node and each of the nodes in its neighbors.*/

// Recursion -> DFS
public class UndirectedGraphClone {

	private static HashMap<Integer, UndirectedGraphNode> map = new HashMap<>();
    public static UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        return clone(node);
    }

    private static UndirectedGraphNode clone(UndirectedGraphNode node) {
        if (node == null) return null;
        
        // CHECK CYCLE !!!
        if (map.containsKey(node.label)) {
            return map.get(node.label);
        }
        // clone node, reference to definition of UndirectedGraphNode
        UndirectedGraphNode clone = new UndirectedGraphNode(node.label);//still need to clone NEIGHBORS
        map.put(clone.label, clone);// put node into map
        // loop through neighbors / For each neighbor, REMEMBER to do the same CLONE action => recursion
        for (UndirectedGraphNode neighbor : node.neighbors) {
            clone.neighbors.add(clone(neighbor));
        }
        return clone;
    }
}

class UndirectedGraphNode {
	int label;
	List<UndirectedGraphNode> neighbors;
	UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
}
