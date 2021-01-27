package amazon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//You want to schedule a list of jobs in d days. Jobs are dependent (i.e To work on the i-th job, you have to finish all the jobs j where 0 <= j < i).
//
//You have to finish at least one task every day. The difficulty of a job schedule is the sum of difficulties of each day of the d days. The difficulty of a day is the maximum difficulty of a job done in that day.
//
//Given an array of integers jobDifficulty and an integer d. The difficulty of the i-th job is jobDifficulty[i].
//
//Return the minimum difficulty of a job schedule. If you cannot find a schedule for the jobs return -1.
//
// 
//
//Example 1:
//
//
//Input: jobDifficulty = [6,5,4,3,2,1], d = 2
//Output: 7
//Explanation: First day you can finish the first 5 jobs, total difficulty = 6.
//Second day you can finish the last job, total difficulty = 1.
//The difficulty of the schedule = 6 + 1 = 7 
//Example 2:
//
//Input: jobDifficulty = [9,9,9], d = 4
//Output: -1
//Explanation: If you finish a job per day you will still have a free day. you cannot find a schedule for the given jobs.
//Example 3:
//
//Input: jobDifficulty = [1,1,1], d = 3
//Output: 3
//Explanation: The schedule is one job per day. total difficulty will be 3.
//Example 4:
//
//Input: jobDifficulty = [7,1,7,1,7,1], d = 3
//Output: 15
//Example 5:
//
//Input: jobDifficulty = [11,111,22,222,33,333,44,444], d = 6
//Output: 843
// 
//
//Constraints:
//
//1 <= jobDifficulty.length <= 300
//0 <= jobDifficulty[i] <= 1000
//1 <= d <= 10

public class MinimumDifficultyOfAJobSchedule {

	public int minDifficulty(int[] jobDifficulty, int d) {
        if (jobDifficulty.length < d)
            return -1;
        List<Integer> list = new ArrayList<>();
        for (int jobD : jobDifficulty) {
            list.add(jobD);
        }
        dp(0, d, list, 0);
        
        return min;
    }
    
    int min = Integer.MAX_VALUE;
    // backtracking ===> TLE  ==> + memorization 如下
    public void dp(int end, int d, List<Integer> jobDifficulty, int difficulty) {
        if (end >= jobDifficulty.size())
            return;
        if (jobDifficulty.size() - end < d) // 递归过来的 end是 i+1的结果，所以end是新group的起点
        						// end改为 start更好
            return;
        if (end <= jobDifficulty.size() - 1 && d == 1) {
            min = Math.min(min, difficulty + getMax(jobDifficulty.subList(end, jobDifficulty.size())));
            System.out.println(min);
            return;
        }
        int current = 0;        
        for (int i = end; i < jobDifficulty.size(); i++) {
        	current = getMax(jobDifficulty.subList(end, i + 1));
            difficulty += current;
            dp(i + 1, d - 1, jobDifficulty, difficulty);
            difficulty -= current; // backtrack, 递归往回到上一个group， difficulty的
            		//a也得回去，所以要减去，这也是backtracking的核心，一个个判断
        }
    }
    
    public int minDifficultyMemo(int[] jobDifficulty, int d) {
        if (jobDifficulty.length < d)
            return -1;
        List<Integer> list = new ArrayList<>();
        Map<String, Integer> memo = new HashMap<>(); // key = start +"/" + d
        //int[][] memo = new int[jobDifficulty.length + 1][d + 1]; // 这里我们用二维数组更直观
        for (int jobD : jobDifficulty) {
            list.add(jobD);
        }
        dp(0, d, list, memo);
        
        return min;
    }
    
    public int dp(int end, int d, List<Integer> jobDifficulty, Map<String, Integer> memo) {
    	if (end >= jobDifficulty.size() 
                || jobDifficulty.size() - end < d)
                return 300 * 1000;
            if (memo.containsKey(end + "/" + d))
                return memo.get(end + "/" + d);
            if (end <= jobDifficulty.size() - 1 && d == 1) {
                int max = getMax(jobDifficulty.subList(end, jobDifficulty.size()));
                memo.put(end + "/" + d, max);
                return max;
            }
            int answer = Integer.MAX_VALUE;
            for (int i = end; i < jobDifficulty.size(); i++) {
            	int current = getMax(jobDifficulty.subList(end, i + 1));
                answer = Math.min(answer, current + dp(i + 1, d - 1, jobDifficulty, memo)); //a当开始算最终answer时候，是前面已经return
                		//a回来了， 所以answer在没到最后一组的时候，其都是MAX_VALUE, [6|5|4,3,2,1], a当走最后一组得出最大值时候返回（4，这也是当前的
                		//a一个局部answer），接下来+ current=5, 此时我们又得到一个局部的answer（后两组），之后开【6|5，4|3，2，1】校验直到全部结束，退出for,
                		//a得到一个6为第一组的的 answer （key=1/2, value= 6），然后return, +current=6, 最终结果answer=12, 接下来 【6，5| 】为第一组，以此类推
                //difficulty -= current;
            }
            
            memo.put(end + "/" + d, answer);
            
            return answer;
    }
    
    public int getMax(List<Integer> num) {
        int max = num.get(0);
        for (int i = 1; i < num.size(); i++) {
            if (max < num.get(i))
                max = num.get(i);
        }
        return max;
    }
    
    public static void main(String[] args) {
    	int[] jobDifficulty = new int[] {6, 5, 4, 3, 2, 1};
    	int d = 3;
    	new MinimumDifficultyOfAJobSchedule().minDifficultyMemo(jobDifficulty, d);
    }
}
