package intervals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

//
//Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.
//
//Example 1:
//
//Input: [[0, 30],[5, 10],[15, 20]]
//Output: 2
//Example 2:
//
//Input: [[7,10],[2,4]]
//Output: 1
//NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.
public class MeetingRoomsII {

//	public int minMeetingRooms(int[][] intervals) {
//        if (intervals == null || intervals.length ==  0) return 0;
//        Arrays.sort(intervals, new Comparator<int[]>(){
//            public int compare(int[] a, int[] b){
//                return a[0] - b[0];
//            }
//        });
//        int min = 1;
//        int end = intervals[0][1]; //a错误的！！！ 因为end 有可能是之前的，不一定是相邻的  ====> PriorityQueue 用来记录最小的
//        for(int i = 1; i < intervals.length; i++) {
//            if (intervals[i][0] == intervals[i - 1][0] && intervals[i][1] == intervals[i - 1][1]) {
//                min++;
//            } else if (intervals[i][0] < end) {
//                min++;
//                end = Math.min(end, intervals[i][1]);
//            } else {
//                end = intervals[i][1];
//            }
//        }
//        return min;
//    }
	
	// TIME： O（nLgN）
	// Space: O(N)
	// 只有结束了，新的meeting就可以用这个房间，否则新加一个，==> 需要记录最小的结束时间 ==> pq
	public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length ==  0) return 0;
        
        // a先按开始时间排序
        Arrays.sort(intervals, new Comparator<int[]>(){
            public int compare(int[] a, int[] b){
                return a[0] - b[0];
            }
        });
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        int min = 1;
        // a校验结束时间  ===> 如果比当前的最小结束时间小，说明这个meeting room可以共用了，不用new
        //						==> 同时要更新这个最小结束时间，因为这个room被用了
        minHeap.offer(intervals[0][1]);
        for(int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= minHeap.peek()) { // 两个会议在共用一个房间，所以去掉之前最早结束的时间， 记录当前会议的结束时间
                minHeap.poll();
//                minHeap.offer(intervals[i][1]);
            } else {
//                min++;
//                minHeap.offer(intervals[i][1]); //a需要一个房间，并记录当前会议的结束时间
            }
            minHeap.offer(intervals[i][1]);
        }
        //return min;
        return minHeap.size();
    }
	
	// a按时间顺序  Chronological Sorting ==> 拆分开始结束时间
	public int minMeetingRooms1(int[][] intervals) {
        if (intervals == null || intervals.length ==  0) return 0;
       
        // 每次比较开始跟结束时间，只有 start > end了，说明这个meeting 不需要额外的room了，因为有空出来的了
        Integer[] start = new Integer[intervals.length];
        Integer[] end = new Integer[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            start[i] = intervals[i][0];
            end[i] = intervals[i][1];
        }
        Arrays.sort(start, (a, b) -> a - b);
        Arrays.sort(end, (a, b) -> a - b);
        int min = 0;
        int j = 0;
        for (int i = 0; i < start.length; i++) {
            if (start[i] < end[j]) {
                min++;
            } else {
                j++;
            }
        }
        return min;
    }
	
}
