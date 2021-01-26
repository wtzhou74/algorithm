package graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//You have N gardens, labelled 1 to N.  In each garden, you want to plant one of 4 types of flowers.
//
//paths[i] = [x, y] describes the existence of a bidirectional path from garden x to garden y.
//
//Also, there is no garden that has more than 3 paths coming into or leaving it.
//
//Your task is to choose a flower type for each garden such that, for any two gardens connected by a path, they have different types of flowers.
//
//Return any such a choice as an array answer, where answer[i] is the type of flower planted in the (i+1)-th garden.  The flower types are denoted 1, 2, 3, or 4.  It is guaranteed an answer exists.
//
// 
//
//Example 1:
//
//Input: N = 3, paths = [[1,2],[2,3],[3,1]]
//Output: [1,2,3]
//Example 2:
//
//Input: N = 4, paths = [[1,2],[3,4]]
//Output: [1,2,1,2]
//Example 3:
//
//Input: N = 4, paths = [[1,2],[2,3],[3,4],[4,1],[1,3],[2,4]]
//Output: [1,2,3,4]
// 
//
//Note:
//
//1 <= N <= 10000
//0 <= paths.size <= 20000
//No garden has 4 or more paths coming into or leaving it.
//It is guaranteed an answer exists.
public class FlowerPlantingWithNoAdjacent {

	// a每个点最多只有3个相邻点， + 自己一个刚好等于4个， 所以4种花刚好够分  ===> 直接贪心算法（greedy）
	public int[] gardenNoAdj(int N, int[][] paths) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int i = 1; i <= N; i++) {
            map.put(i, new HashSet<>());
        }
        
        for (int i = 0; i < paths.length; i++) {
            map.get(paths[i][0]).add(paths[i][1]);
            map.get(paths[i][1]).add(paths[i][0]);
        }
        
        int[] res = new int[N];
        for (int i = 1; i <= N; i++) {
            Set<Integer> adjs = map.get(i);
            int[] colors = new int[5];
            for (int adj : adjs) {
                colors[res[adj - 1]] = 1;
            }
            for (int c = 1; c <= 4; c++) {
                if (colors[c] == 0)
                    res[i - 1] = c;
            }
        }
        
        // a另一种方式 上色
//        int[] res = new int[N];
//        for (int i = 1; i <= N; i++) {
//            Set<Integer> adjs = map.get(i);
//            Set<Integer> colors = new HashSet<>(); // 在处理每个地时， 只需确保上的颜色跟它相邻点的颜色不一样即可
//            for (int adj : adjs) {                
//                colors.add(res[adj - 1]); //a收集相邻点上的颜色
//            }
//            for (int c = 1; c <= 4; c++) {
//                if (!colors.contains(c)) { // 上一个相邻点没用过的颜色
//                    res[i - 1] = c;
//                    break;
//                }
//            }
//        }
        return res;
    }
}
