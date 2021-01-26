package graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/*           
Given a DAG property graph as follows with A as the root:
 
             A
         /1  |2  \3
        B    C    D
       /4    |5 
      E      |
    /6  \7  /
   F      G
           \8
            H
           
The integers are edge weights/costs.      
 
1) Model the graph with some data structure
2) Create an instance of the graph in memory
3) Iterate the graph and print
Example result:
A->B:1
A->C:2
A->D:3
B->E:4
C->G:5
E->F:6
E->G:7
4) Sum the weights per node and print
Example result:
A:0
B:1
C:2
D:3
E:5
F:11
G:12
G:7
 
*/ 
public class GraphTraverse {

	// Create a graph with 2D matrix
//	public class Graph {
//		int[][] adjMatrix;
//		int numVertices;
//		
//		public Graph(int num) {
//			this.adjMatrix = new int[num][num];
//			this.numVertices = num;
//		}
//		
//		public void addEdge(int i, int j, int weight) {
//			adjMatrix[i][j] = weight; 
//		}
//	}
//	
//	public void printGraph() {
//		Map<Integer, Integer> map = new HashMap<>();
//		Graph graph = new Graph(8);
//		graph.addEdge(0,  1,  1);
//		graph.addEdge(0,  2,  2);
//		graph.addEdge(0,  3,  3);
//		graph.addEdge(1,  4, 4);
//		graph.addEdge(2,  6, 5);
//		graph.addEdge(4,  5,  6);
//		graph.addEdge(4,  6,  7);
//		graph.addEdge(6,  7, 8);
//		
//		int[][] matrix = graph.adjMatrix;
//		for (int i = 0; i < 8; i++) {
//			for (int j = 0; j < 8; j++) {
//				if (matrix[i][j] != 0) {
//					if (map.containsKey(i)) {
//						map.put(j, matrix[i][j] + map.get(i));
//					} else {
//						map.put(j, matrix[i][j]);
//					}
//					System.out.println(convertNumToLetter(i) + "->" + convertNumToLetter(j) + ":" + matrix[i][j]);
//				}
//			}
//			
//		}
//		
//		// print sum weights
//		for (int i = 0; i < 8; i++) {
//			if (map.containsKey(i)) System.out.println(convertNumToLetter(i) + ":" + map.get(i));
//			else System.out.println(convertNumToLetter(i) + ":0");
//		}
////		for (Map.Entry<Integer, Integer> item : map.entrySet()) {
////			if (item.getValue()) {
////				
////			}
////		}
//	}
	
	public class Edge {
		int source;
		int destination;
		int weight;
		
		public Edge(int source, int destination, int weight) {
			this.source = source;
			this.destination = destination;
			this.weight = weight;
		}
	}
	public class Graph {
		int numOfVertices;
		LinkedList<Edge> adjListArray[];
		
		public Graph(int num) {
			this.numOfVertices = num;
			this.adjListArray = new LinkedList[num];
			
			// initialize adjacency lists for all the vertices
			for (int i = 0; i < num; i++) {
				adjListArray[i] = new LinkedList<>();
			}
		}
		
		public void addEdge(int source, int destination, int weight) {
			// Edge is the element of linked list
			Edge edge = new Edge(source, destination, weight);
			adjListArray[source].add(edge);
		}
	}
	
	//
	public void printGraph() {
		Graph graph = new Graph(8);
		LinkedList<Edge>[] listArray = graph.adjListArray;
		Map<Integer, Integer> map = new HashMap<>();
		
		// add edges
		graph.addEdge(0,  1,  1);
		graph.addEdge(0,  2,  2);
		graph.addEdge(0,  3,  3);
		graph.addEdge(1,  4, 4);
		graph.addEdge(2,  6, 5);
		graph.addEdge(4,  5,  6);
		graph.addEdge(4,  6,  7);
		//graph.addEdge(6,  7, 8);
		
		for (int i = 0; i < listArray.length; i++) {
			LinkedList<Edge> list = listArray[i];
			for (int j = 0; j < list.size(); j++) { // j is the index of "A", "B" etc. it is the destination of i !!!!!!!!!!
				// each of them represents a EDGE
				System.out.println(convertNumToLetter(i) + "->" + convertNumToLetter(list.get(j).destination) + ":" + list.get(j).weight);
				// check whether current source is also a destination before
				if (map.containsKey(i)) {
					map.put(list.get(j).destination, map.get(i) + list.get(j).weight);
				} else {
					map.put(list.get(j).destination, list.get(j).weight);
				}
			}
		}
		
		// print the sum weights
		for (int i = 0; i < 8; i++) {
			if (map.containsKey(i)) {
				System.out.println(convertNumToLetter(i) + ":" + map.get(i));				
			} else {
				System.out.println(convertNumToLetter(i) + ":" + 0);
			}
		}
	}
	public String convertNumToLetter(int i) {
		switch(i) {
		case 0:
			return "A";
		case 1: 
			return "B";
		case 2:
			return "C";
		case 3:
			return "D";
		case 4:
			return "E";
		case 5:
			return "F";
		case 6:
			return "G";
		case 7:
			return "H";
		default:
			return "";
		}
	}
	
	public static void main(String[] args) {
		new GraphTraverse().printGraph();
	}
}
