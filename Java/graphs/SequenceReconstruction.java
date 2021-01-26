package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

//Check whether the original sequence org can be uniquely reconstructed from the sequences in seqs. The org sequence is a permutation of the integers from 1 to n, with 1 ≤ n ≤ 104. Reconstruction means building a shortest common supersequence of the sequences in seqs (i.e., a shortest sequence so that all sequences in seqs are subsequences of it). Determine whether there is only one sequence that can be reconstructed from seqs and it is the org sequence.
//
//
//
//Example 1:
//
//Input: org = [1,2,3], seqs = [[1,2],[1,3]]
//Output: false
//Explanation: [1,2,3] is not the only one sequence that can be reconstructed, because [1,3,2] is also a valid sequence that can be reconstructed.
//Example 2:
//
//Input: org = [1,2,3], seqs = [[1,2]]
//Output: false
//Explanation: The reconstructed sequence can only be [1,2].
//Example 3:
//
//Input: org = [1,2,3], seqs = [[1,2],[1,3],[2,3]]
//Output: true
//Explanation: The sequences [1,2], [1,3], and [2,3] can uniquely reconstruct the original sequence [1,2,3].
//Example 4:
//
//Input: org = [4,1,5,2,6,3], seqs = [[5,2,6,3],[4,1,5,2]]
//Output: true
// 
//
//Constraints:
//
//1 <= n <= 10^4
//org is a permutation of {1,2,...,n}.
//seqs[i][j] fits in a 32-bit signed integer.

public class SequenceReconstruction {

	// a 本质是 topological的问题，因为其也要求有序 （跟org一样）
	// Note:
	//		1) 二维数组  ==> Graph ； 但本题并不指定给定的条件就是符合Graph的，即 二维数组元素“不是Edge”,所以其 size()可以是任意数 ==> BuildGraph要注意
	//		2） 由于1的问题 ==> 很多 corner case
	//		3） [[1,2,3], [1], [2]] a对于org=[1,2,3]是成立的，所以在buildGraph并初始化 indegree时一定要注意
	//		4） [[1],[1], [1]] => a对 org=[1]是成立的
	//		5） seqs中的元素可以任意 ==> 正数/负数/不是[1,n]的都可以
	public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        if (seqs.size() == 0 && org.length == 1) return false;
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] indegree = new int[org.length + 1];
        Set<Integer> set = new HashSet<>();// 用于放 seq.size() == 1 的
        boolean[] visited = new boolean[org.length + 1]; // 处理对于 [[1,2,3], [1], [2]]这样的case, 此时 [1],[2]不用再放入set
        boolean isValid = buildGraph(graph, seqs, indegree, set, visited, org.length);
        //if (graph.size() == 0) return false;
        if (!isValid) return false;
        Queue<Integer> queue = new LinkedList<>();
        for (int i : set)
            queue.offer(i);
        for (int i = 1; i < indegree.length && graph.size() > 0; i++) {
            if (indegree[i] == 0)
                queue.offer(i);
        }
        // a此时queue中只能有一个（任何时候都只能有一个），否则会出现多个选择
        if (queue.size() == 0 || queue.size() > 1) return false;
        int j = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            if (size > 1) return false;//只能有一个
            int num = queue.poll();
            if (num != org[j]) // 同时判断这个值是否跟org对应的值相等
                return false;
            j++;
            for (int adj : graph.getOrDefault(num, new ArrayList<>())) {
                indegree[adj]--;
                if (indegree[adj] == 0)
                    queue.offer(adj);
            } 
        }
        
        return j == org.length ? true : false;
        
    }
    
	// build的过程中，同时校验 1）元素值是否在 [1, n]之间；  2) “正确”初始化 indegree	
    public boolean buildGraph(Map<Integer, List<Integer>> graph, List<List<Integer>> seqs, int[] indegree, Set<Integer> set, boolean[] visited, int len) {
        for (int i = 0; i < seqs.size(); i++) {
            for (int j = 0; j < seqs.get(i).size() - 1; j++) {
                int s = seqs.get(i).get(j);
                int d = seqs.get(i).get(j + 1);
                if (s > len || d > len || s <= 0 || d <= 0) return false;
                visited[s] = true;
                visited[d] = true;
                if (set.contains(s)) set.remove(s); // graph中有，set就不要再有了
                if (set.contains(d)) set.remove(d);
                indegree[d]++;
                graph.computeIfAbsent(s, k -> new ArrayList<>()).add(d);
            }
            if (seqs.get(i).size() == 1) { // size() == 1 看是否需要放入 set,作为 indegree == 0来处理
                if (seqs.get(i).get(0) > len || seqs.get(i).get(0) <= 0) return false;
                if (!visited[seqs.get(i).get(0)]) {
                    set.add(seqs.get(i).get(0));
                }
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
    	List<List<Integer>> seqs = new ArrayList<>();
    	List<Integer> seq0 = new ArrayList<>();seq0.add(1);
    	List<Integer> seq1 = new ArrayList<>();seq1.add(1);
    	List<Integer> seq2 = new ArrayList<>();seq2.add(1);
    	seqs.add(seq2);
    	seqs.add(seq1);
    	seqs.add(seq0);
    	new SequenceReconstruction().sequenceReconstruction(new int[] {1}, seqs);
    }
}
