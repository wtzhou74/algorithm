package amazon;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

//On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a 2D coordinate on this grid.
//
//Our goal is to assign a bike to each worker. Among the available bikes and workers, we choose the (worker, bike) pair with the shortest Manhattan distance between each other, and assign the bike to that worker. (If there are multiple (worker, bike) pairs with the same shortest Manhattan distance, we choose the pair with the smallest worker index; if there are multiple ways to do that, we choose the pair with the smallest bike index). We repeat this process until there are no available workers.
//
//The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.
//
//Return a vector ans of length N, where ans[i] is the index (0-indexed) of the bike that the i-th worker is assigned to.
//
// 
//
//Example 1:
//
//
//
//Input: workers = [[0,0],[2,1]], bikes = [[1,2],[3,3]]
//Output: [1,0]
//Explanation: 
//Worker 1 grabs Bike 0 as they are closest (without ties), and Worker 0 is assigned Bike 1. So the output is [1, 0].
//Example 2:
//
//
//
//Input: workers = [[0,0],[1,1],[2,0]], bikes = [[1,0],[2,2],[2,1]]
//Output: [0,2,1]
//Explanation: 
//Worker 0 grabs Bike 0 at first. Worker 1 and Worker 2 share the same distance to Bike 2, thus Worker 1 is assigned to Bike 2, and Worker 2 will take Bike 1. So the output is [0,2,1].
// 
//
//Note:
//
//0 <= workers[i][j], bikes[i][j] < 1000
//All worker and bike locations are distinct.
//1 <= workers.length <= bikes.length <= 1000
public class CompusBikes {

	// Note: 1) 要双向比较，不能单独从worker/bike开始，找出其对应的最近的bike/worker， 
	//		      比如 w0离b1最近，此时不能pair(w0,b1),有可能 w2里b1更近		==> check所有的pair,
	//		 2) 这些pair里面，就会包括题设所说的， 相同的距离可以有 （ 1)不同workers对同一个 bike, 2) 同个worker对应多个bikes, 3) 不同workers对应不同的bikes）
	//			===> 在这里面，我们就可以PriorityQueue根据条件进行比较了
	//	     3） 要先处理距离近的pair， ==> Map ==> 按距离排序  ==> TreeMap
	//					===> 每个距离下会有多pairs  ===> map.value = PQ
	
	//       4) 在输出结果的时候，同样需要同时考虑worker, bikes
	//				已匹配的bike和已找到bike的worker就不需要处理，否则会被更新掉 (后面的pair只有距离符合条件而已，但顺序就不对，所以不能被后面的值更新)
	public int[] assignBikes(int[][] workers, int[][] bikes) {
        if (workers == null || bikes == null)
            return new int[0];
        TreeMap<Double, PriorityQueue<int[]>> map = new TreeMap<>();
        for (int i = 0; i < workers.length; i++) {
            for (int j = 0; j < bikes.length; j++) {
                double dis = distance(workers[i], bikes[j]);
                map.computeIfAbsent(dis, k -> new PriorityQueue<>(new Comparator<int[]>(){
                    
                    public int compare(int[] a1, int[] a2) {
                    	// 对所有具有同样（worker, bike）距离的排序
                        if (a1[0] == a2[0])
                            return a1[1] - a2[1];
                        else
                            return a1[0] - a2[0];
                    }
                })).offer(new int[]{i, j});
            }
        }
        int[] result = new int[workers.length];
        Arrays.fill(result, -1); // 不能被更新，所以取到bike后后面就不能再被更新了，因为已经PQ排好顺序了
        int k = 0;
        Set<Integer> taken = new HashSet<>();
        for (Map.Entry<Double, PriorityQueue<int[]>> entry : map.entrySet()) {
            PriorityQueue<int[]> aux = entry.getValue();
            int size = aux.size();
            while (size > 0) {
                int[] cell = aux.poll();
                //System.out.println(cell[0] + " " + cell[1]);
                // 没有找到bike且这个bike没有被取走
                if (result[cell[0]] == -1 && !taken.contains(cell[1])) {
                    result[cell[0]] = cell[1]; // 保存这个pair
                    taken.add(cell[1]);
                }
                size--;    
            }
        }
        
        return result;
    }
    
    public double distance (int[] x1, int[] x2) {
        return (double)Math.abs(x1[0] - x2[0]) + (double)Math.abs(x1[1] - x2[1]);
    }
}
