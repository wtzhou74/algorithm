import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


// refer to: https://blog.csdn.net/lanzijingshizi/article/details/88808175
public class TarjanAlgoTest {

	private int numOfNode;
	private List<ArrayList<Integer>> graph;// 图
	private List<ArrayList<Integer>> result;// 保存极大强连通图

	private boolean[] inStack;// 节点是否在栈内
	private Stack<Integer> stack;
	private int[] dfn;
	private int[] low;
	private int time;

	public TarjanAlgoTest(List<ArrayList<Integer>> graph, int numOfNode) {
		this.graph = graph;
		this.numOfNode = numOfNode;
		this.inStack = new boolean[numOfNode];
		this.stack = new Stack<Integer>();
		dfn = new int[numOfNode];
		low = new int[numOfNode];

		Arrays.fill(dfn, -1);// 将dfn所有元素都置为1，其中dfn[i]=-1代表这个点没有被访问过
		Arrays.fill(low, -1);

		result = new ArrayList<ArrayList<Integer>>();
	}

	public void tarjan(int current) {
		dfn[current] = low[current] = time++;
		inStack[current] = true;// 是否已在栈中的数组，不是是否已visited的数组!!!!!!!
								// 在确定强连通点的时候，是需要已访问过的是需要出栈的
		stack.push(current);

		for (int i = 0; i < graph.get(current).size(); i++) {
			int next = graph.get(current).get(i);
			if (dfn[next] == -1) {// 当前点没有被访问到； 不能用instack来判断，因为在出栈操作，visited过的是会被出栈的
				tarjan(next);
				//递归往回回溯的时候不断更新 lowTime
				// 在这里做 dfn[curr] < low[adj] 的操作可以找出 critical edges ==> CriticalConnectionsInANetwork.java
				low[current] = Math.min(low[current], low[next]);
				
			} else if (inStack[next]) { // 这个点已经在栈中了, 更新low要注意是跟 dfn的值比较
				low[current] = Math.min(low[current], dfn[next]);
			}
		}
		
		if (low[current] == dfn[current]) {
			// 这个current点是关键点
			ArrayList<Integer> temp = new ArrayList<Integer>();
			int j = -1;
			while (current != j) {
				j = stack.pop();
				inStack[j] = false;//这个点要移除栈，用以上面是否在栈中，更新low的操作
				temp.add(j);
			}
			result.add(temp);
		}
	}

	public List<ArrayList<Integer>> run() {
		for (int i = 0; i < numOfNode; i++) {
			if (dfn[i] == -1)
				tarjan(i);
		}
		return result;
	}

	public static void main(String[] args) {
		
		// 创建图
		//int numOfNode = 6;
		int numOfNode = 4;
		List<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < numOfNode; i++) {
			graph.add(new ArrayList<Integer>());
		}
//		graph.get(0).add(1);
//		graph.get(0).add(2);
//		graph.get(1).add(3);
//		graph.get(2).add(3);
//		graph.get(2).add(4);
//		graph.get(3).add(0);
//		graph.get(3).add(5);
//		graph.get(4).add(5);
		graph.get(1).add(2);
		graph.get(0).add(1);
		graph.get(1).add(3);
		graph.get(2).add(0);

		TarjanAlgoTest t = new TarjanAlgoTest(graph, numOfNode);
		List<ArrayList<Integer>> result = t.run();
		// 打印结果
		
		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < result.get(i).size(); j++) {
				System.out.print(result.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}
	

}
