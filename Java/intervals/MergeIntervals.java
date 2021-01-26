package intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

//Given a collection of intervals, merge all overlapping intervals.
//
//Example 1:
//
//Input: [[1,3],[2,6],[8,10],[15,18]]
//Output: [[1,6],[8,10],[15,18]]
//Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
//Example 2:
//
//Input: [[1,4],[4,5]]
//Output: [[1,5]]
//Explanation: Intervals [1,4] and [4,5] are considered overlapping.
//NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.
public class MergeIntervals {

	// 画图理解
	public int[][] merge(int[][] intervals) {
        if (intervals.length == 0)
            return new int[0][0];
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int[] pre = intervals[0];
        List<int[]> list = new ArrayList<>();
        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            if (interval[0] <= pre[1]) {
                pre[1] = Math.max(pre[1], interval[1]);
            } else {
                //list.add(pre); // 不能这样直接加，加的是pre的索引，之后pre修改，这里的值也被修改了 ==> 需要新建
                list.add(new int[]{pre[0], pre[1]});
                pre[0] = interval[0];
                pre[1] = interval[1];
            }
                
        }
        list.add(new int[]{pre[0], pre[1]});
        return list.toArray(new int[list.size()][2]);
        //return 
    }
	
	public int[][] merge1(int[][] intervals) {
        if (intervals.length == 0 || intervals.length == 1) return intervals;
        //int curr = 0;
        PriorityQueue<int[]> queue = new PriorityQueue<>(intervals.length, new Comparator<int[]>(){
            public int compare(int[]     a, int[] b) {
                if (a[0] == b[0]) return a[1] - b[1];
                else return a[0] - b[0];
            }
        });
        for (int i = 0; i < intervals.length; i++) {
            queue.offer(intervals[i]);
        }
        List<int[]> resList = new ArrayList<>();
        int[] curr = queue.poll();
        //queue.offer(curr);
        resList.add(curr);
        int j = 0;
        while(!queue.isEmpty()) {
            curr = resList.get(j);
            int[] temp = queue.poll();
            if (curr[1] >= temp[1]) {
                continue;                
            } else if (curr[1] >= temp[0]) {
                int[] interval = new int[]{curr[0], temp[1]};
                resList.set(j, interval);
            } else if (curr[1] < temp[0]) {
                resList.add(temp);
                j++;
            }
        }
        int[][] res = new int[resList.size()][2];
        int i = 0;
        for (int[] item : resList) {
            res[i] = item;
            i++;
        }
        return res;
    }
}
