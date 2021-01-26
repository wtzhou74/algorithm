package graphs;

import java.util.HashMap;
import java.util.Map;

//In a town, there are N people labelled from 1 to N.  There is a rumor that one of these people is secretly the town judge.
//
//If the town judge exists, then:
//
//The town judge trusts nobody.
//Everybody (except for the town judge) trusts the town judge.
//There is exactly one person that satisfies properties 1 and 2.
//You are given trust, an array of pairs trust[i] = [a, b] representing that the person labelled a trusts the person labelled b.
//
//If the town judge exists and can be identified, return the label of the town judge.  Otherwise, return -1.
//
// 
//
//Example 1:
//
//Input: N = 2, trust = [[1,2]]
//Output: 2
//Example 2:
//
//Input: N = 3, trust = [[1,3],[2,3]]
//Output: 3
//Example 3:
//
//Input: N = 3, trust = [[1,3],[2,3],[3,1]]
//Output: -1
//Example 4:
//
//Input: N = 3, trust = [[1,2],[2,3]]
//Output: -1
//Example 5:
//
//Input: N = 4, trust = [[1,3],[1,4],[2,3],[2,4],[4,3]]
//Output: 3
// 
//
//Note:
//
//1 <= N <= 1000
//trust.length <= 10000
//trust[i] are all different
//trust[i][0] != trust[i][1]
//1 <= trust[i][0], trust[i][1] <= N
public class FindTheTownJudge {

	public int findJudge(int N, int[][] trust) {
        if (N == 0 || N == 1) return N;
        Map<Integer, int[]> map = new HashMap<>();
        for (int i = 1; i <= N; i++) {
            map.put(i, new int[2]);
        }
        for (int i = 0; i < trust.length; i++) { // 这块trust的长度可以小于 N的， 这里面只是描述路径而已
            int s = trust[i][0];
            int d = trust[i][1];
            
            map.get(s)[0]++;// outdegree
            map.get(d)[1]++;// indegree
            
        }
        
        for (Map.Entry<Integer, int[]> entry : map.entrySet()) {
            int[] degree = entry.getValue();
            if (degree[0] == 0 && degree[1] == N - 1) // 是Judge的条件
                return entry.getKey();
        }
        return -1;
    }
	
	
	// Simplify the above solution
	public int findJudge2(int N, int[][] trust) {
		if (N == 0 || N == 1) return N;
		int[] degrees = new int[N + 1]; // 0 is useless, but the array index starting from 0
		for (int i = 0; i < trust.length; i++) {
			degrees[trust[i][0]]--;//出度
			degrees[trust[i][1]]++;//入度
		}
		
		for (int i = 0; i < degrees.length; i++) {
			if (degrees[i] == N - 1)
				return i;
		}
		return -1;
	}
}
