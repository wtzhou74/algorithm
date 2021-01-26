package graphs;

import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

//Equations are given in the format A / B = k, where A and B are variables represented as strings, and k is a real number (floating point number). Given some queries, return the answers. If the answer does not exist, return -1.0.
//
//Example:
//Given a / b = 2.0, b / c = 3.0.
//queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
//return [6.0, 0.5, -1.0, 1.0, -1.0 ].
//
//The input is: vector<pair<string, string>> equations, vector<double>& values, vector<pair<string, string>> queries , where equations.size() == values.size(), and the values are positive. This represents the equations. Return vector<double>.
//
//According to the example above:
//
//equations = [ ["a", "b"], ["b", "c"] ],
//values = [2.0, 3.0],
//queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ]. 
// 
//
//The input is always valid. You may assume that evaluating the queries will result in no division by zero and there is no contradiction.

public class EvaluateDivision {

	public class Node {
		String t;
		double w;
		
		public Node(String t, double w) {
			this.t = t;
			this.w = w;
		}
	}
	
	Map<String, Set<Node>> graph = new HashMap<>();
	public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
		//Map<String, List<Node>> graph = new HashMap<>();
		if (equations == null || equations.size() == 0
		           || values.length == 0 || queries == null ) return null;
		buildGraph(equations, values);
		double[] res = new double[queries.size()];
		for (int i = 0; i < queries.size(); i++) {
			String s = queries.get(i).get(0);
			String d = queries.get(i).get(1);
			if (!graph.containsKey(s)
					|| !graph.containsKey(d)) {
				res[i] = -1;
				continue;
			}
			if (s.equals(d)) {
				res[i] = 1;
				continue;
			}
			//Queue<String> queue = new LinkedList<>();
			Queue<Node> queue = new LinkedList<>(); // 选择数据结构的重要性 ！！！！看下面用Map的例子-> 复杂
			Set<String> visited = new HashSet<>();
			
			queue.offer(new Node(s, 1.0)); // 从query的第一个元素入手， 新建Node入队列
			while (!queue.isEmpty()) {
				Node node = queue.poll();
				visited.add(node.t);
				if (node.t.equals(d)) {
					res[i] = node.w;
					break;
				}
				for (Node adj : graph.get(node.t)) {
					if (!visited.contains(adj.t)) {
						queue.offer(new Node(adj.t, node.w * adj.w)); // offer 进queue的node新建， 因为需要修改 weight， 如果直接改原来的node, 则修改了graph
					}
				}
			}
			if (res[i] == 0.0) { // 比如 [a, b], [c, d], 求 [a, c] 结果应该是  -1 
                res[i] = -1;
            }
		}
		
		return res;
		
	}
	
	// 不是更好的数据结构
	public double bfs(String s, String d, Map<String, Map<String, Double>> graph, Set<String> visited) {
        if (!graph.containsKey(s)) {
            return -1.0;
        }
        if (s.equals(d)) {
            return 1.0;
        }
        //Queue<String> q = new LinkedList<>();
        Queue<Map<String, Double>> q = new LinkedList<>(); ///！！！这个数据结构不理想，可考虑自定义的 Node,如上
        Map<String, Double> f = new HashMap<>();
        f.put(s, 1.0);
        q.offer(f);
        //visited.add(s);        
        while (!q.isEmpty()) {
            // check adjacencies
            Map<String, Double> node = q.poll(); 
            String key = node.keySet().stream().findFirst().get();
            visited.add(key);
            for (Map.Entry<String, Double> adj : graph.get(key).entrySet()) {             
                if (!visited.contains(adj.getKey())) {
                    
                    if (adj.getKey().equals(d)) {
                        return node.values().stream().findFirst().get() * adj.getValue(); 
                    }
                    Map<String, Double> next = new HashMap<>();
                    next.put(adj.getKey(), node.values().stream().findFirst().get() * adj.getValue());
                    q.offer(next);
                }
            }
        }
        return -1.0;
    }
	
	// a 图的构建是重点！！！ ==》 新创建 node， 包含 [val, weight]， 而不是 [s, t, weight]
	// 但Union-Find不需要，因为 equations给出了graph的结构，此处主要判断 parents; 而对 BFS，DFS 需要构造图
	public void buildGraph(List<List<String>> equations, double[] values) {
		for (int i = 0; i < equations.size(); i++) {
			List<String> equation = equations.get(i);
			graph.computeIfAbsent(equation.get(0), k -> new HashSet<>()).add(new Node(equation.get(1), values[i]));
			graph.computeIfAbsent(equation.get(1), k -> new HashSet<>()).add(new Node(equation.get(0), 1 / values[i])); // [a, b] = 2; 则 [b, a] = 1 /2
//			if (graph.containsKey(equation.get(0))) {
//				graph.get(equation.get(0)).add(new Node(equation.get(1), values[i]));
//			} else {
//				Set<Node> adjs = new HashSet<>();
//				adjs.add(new Node(equation.get(1), values[i]) );
//				graph.put(equation.get(0), adjs);
//			}
//			
//			if (graph.containsKey(equation.get(1))) {
//				graph.get(equation.get(1)).add(new Node(equation.get(0), 1 / values[i]));
//			} else {
//				Set<Node> adjs = new HashSet<>();
//				adjs.add(new Node(equation.get(0), 1/ values[i]) );
//				graph.put(equation.get(1), adjs);
//			}
		}
	}
	
	
	// a该题还可以用  union-find的方法， 只有 在一组的 才有值
	//a  重点是 用Map记录 每个parent的weight !!!!
	
	// Time: O(e + q)
	// Space: O(e)
	public double[] unionFinder(List<List<String>> equations, double[] values, List<List<String>> queries) {
		if (equations == null || equations.size() == 0
		           || values.length == 0 || queries == null ) return null;
		        Map<String, Node> parents = new HashMap<>();
		        //initialization
		        // for (List<String> equation : equations) {
		        //     parents.put(equation.get(0), new Node(equation.get(0), 1.0));
		        //     parents.put(equation.get(1), new Node(equation.get(1), 1.0));
		        // }
		        
		        for (int i = 0; i < equations.size(); i++) {
		            String s = equations.get(i).get(0);
		            String p = equations.get(i).get(1);
		            boolean hassp = parents.containsKey(s);
		            boolean hasdp = parents.containsKey(p);
		            // a根据 equation来完成初始化工作
		            // a注意，如果之前有初始化赋值了（注释掉的部分），以下代码要改
		            if (!hassp && !hasdp) {
		                parents.put(s, new Node(p, values[i]));
		                parents.put(p, new Node(p, 1.0));//“根”节点, equations描述出的是一个“森林”
		            } else if (!hasdp) {
		                parents.put(p, new Node(s, 1.0 / values[i]));
		            } else if (!hassp) {
		                parents.put(s, new Node(p, values[i]));
		            } else {
		                Node sp = findParent(parents, s);
		                Node dp = findParent(parents, p);
		            
		                if (!sp.t.equals(dp.t)) {
		                    // union
		                    sp.t = dp.t;
		                    // 已知 s->root的关系 s/root1, 且 d -> root2的关系  d/root， 求 root1/root2
		                    // s1/root1 = w1（sp.weight）, d1/root2 = w2, s1/d1 = w ===> root1/root2 = w * (1/w1) * w2
		                    sp.w = values[i] / sp.w * dp.w; // 该表达式由上关系得
		                }
		            }
		            
		        }
		        
		        double[] res = new double[queries.size()];
		        for (int i = 0; i < queries.size(); i++) {
		            
		            Node s = findParent(parents, queries.get(i).get(0));
		            Node p = findParent(parents, queries.get(i).get(1));
		            
		            if (s == null || p == null || !s.t.equals(p.t)) {
		                res[i] = -1;
		                continue;
		            }
		            
		            if (queries.get(i).get(0).equals(queries.get(i).get(1))) {
		                res[i] = 1.0;
		                continue;
		            }
		            res[i] = s.w / p.w;
		        }
		        
		        return res;
		
	}
	
	public Node findParent(Map<String, Node> parents, String s) {
		if (!parents.containsKey(s)) {
			return null;
		}
		if (parents.get(s).t.equals(s)) {
			return parents.get(s);
		}
		Node n = parents.get(s);
		Node p = findParent(parents, parents.get(s).t); // path compression
//		parents.get(s).t = p.t;
//		parents.get(s).w *= p.w;
		n.t = p.t;
		n.w *= p.w; // 在返回的过程中， 不但更新 parent, 还更新 weight
		
		return n;
		
	}
	
	// DFS  ==> 构建图
	Map<String, Map<String, Double>> dfsGraph = new HashMap<>();
    public double[] calcEquationDFS(List<List<String>> equations, double[] values, List<List<String>> queries) {
        if (equations == null || equations.size() == 0
           || values == null || queries == null || queries.size() == 0)
            return null;
        buildDfsGraph(equations, values);
        double[] res = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            Set<String> visited = new HashSet<>();
            res[i] = dfs(queries.get(i).get(0),
                        queries.get(i).get(1), dfsGraph, 1.0, visited);
        }
        return res;
    }
    
    public double dfs (String s, String d, Map<String, Map<String, Double>> graph, double v, Set<String> visited) {
        if (!graph.containsKey(s)) {
            return -1.0;
        }
        if (graph.get(s).containsKey(d)) {
            return v * graph.get(s).get(d);
        }
        visited.add(s);
        for (Map.Entry<String, Double> adj : graph.get(s).entrySet()) {
            
            if (!visited.contains(adj.getKey())) {
                double w = dfs(adj.getKey(), d, graph, v * adj.getValue(), visited);
                if (w != -1.0)
                    return w;
                // else 
                //     return -1.0;  //!!!! 这里不要return, 否则找到一条不适合的路径就return结束了，而这里实际是接着要处理其他路径，通过 adj
                //return dfs(node.t, d, graph, v * adj.getValue());
            }
        }
        return -1.0;
    }
    
    public void buildDfsGraph(List<List<String>> equations, double[] values) {
        for (int i = 0; i < equations.size(); i++) {
            String s = equations.get(i).get(0);
            String d = equations.get(i).get(1);
            dfsGraph.computeIfAbsent(s, k -> new HashMap<>()).put(d, values[i]);
            dfsGraph.computeIfAbsent(d, k -> new HashMap<>()).put(s, 1 / values[i]);
        }
    }
	
	public static void main(String[] args) {
//		[["x1","x2"],["x2","x3"],["x1","x4"],["x2","x5"]]
//				[3.0,0.5,3.4,5.6]
//				[["x2","x4"],["x1","x5"],["x1","x3"],["x5","x5"],["x5","x1"],["x3","x4"],["x4","x3"],["x6","x6"],["x0","x0"]]
		List<List<String>> equations = new ArrayList<>();
		List<String> equation = new ArrayList<>();
		equation.add("x1");
		equation.add("x2");
		equations.add(equation);
		List<String> equation1 = new ArrayList<>();
		equation1.add("x2");
		equation1.add("x3");
		equations.add(equation1);
		List<String> equation2 = new ArrayList<>();
		equation2.add("x3");
		equation2.add("x4");
		equations.add(equation2);
		List<String> equation3 = new ArrayList<>();
		equation3.add("x4");
		equation3.add("x5");
		equations.add(equation3);
//		List<String> equation4 = new ArrayList<>();
//		equation4.add("x1");
//		equation4.add("x4");
//		equations.add(equation4);
		double[] values = new double[] {3.0, 4.0, 5.0, 6.0};
		
		List<List<String>> queries = new ArrayList<>();
		List<String> query = new ArrayList<>();
		query.add("x2");
		query.add("x4");
		queries.add(query);
//		List<String> query1 = new ArrayList<>();
//		query1.add("x1");
//		query1.add("x4");
//		queries.add(query1);
		
		String[][] eqs = new String[][] {
			{"x1", "x2"},
			{"x2", "x3"},
			{"x3", "x4"},
			{"x4", "x5"}
		};
		String[][] qs = new String[][] {
			{"x1", "x5"},
			{"x5", "x2"}
		};
		new EvaluateDivision().calcEquationDFS(equations, values, queries);
	}
	
}
