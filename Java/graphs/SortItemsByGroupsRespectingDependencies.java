package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

//There are n items each belonging to zero or one of m groups where group[i] is the group that the i-th item belongs to and it's equal to -1 if the i-th item belongs to no group. The items and the groups are zero indexed. A group can have no item belonging to it.
//
//Return a sorted list of the items such that:
//
//The items that belong to the same group are next to each other in the sorted list.
//There are some relations between these items where beforeItems[i] is a list containing all the items that should come before the i-th item in the sorted array (to the left of the i-th item).
//Return any solution if there is more than one solution and return an empty list if there is no solution.
//
// 
//
//Example 1:
//
//
//
//Input: n = 8, m = 2, group = [-1,-1,1,0,0,1,0,-1], beforeItems = [[],[6],[5],[6],[3,6],[],[],[]]
//Output: [6,3,4,1,5,2,0,7]
//Example 2:
//
//Input: n = 8, m = 2, group = [-1,-1,1,0,0,1,0,-1], beforeItems = [[],[6],[5],[6],[3],[],[4],[]]
//Output: []
//Explanation: This is the same as example 1 except that 4 needs to be before 6 in the sorted list.
// 
//
//Constraints:
//
//1 <= m <= n <= 3*10^4
//group.length == beforeItems.length == n
//-1 <= group[i] <= m-1
//0 <= beforeItems[i].length <= n-1
//0 <= beforeItems[i][j] <= n-1
//i != beforeItems[i][j]
//beforeItems[i] does not contain duplicates elements.

// 该题的本质是先 看 GROUP的优先顺序（group 的items要连续）； 再看 group内部元素的先后顺序 ===> 需要做两次 topological排序
// 根据 元素 beforeItems的顺序可以得出Group的顺序，有冲突 （比如，组在前， 而组内元素又是在另一个组（后组）元素的后面） 那肯定就没结果了   

// 1） 初始化组号，并初始化组元素   2） 根据 beforeItems来确定组顺序，元素顺序  2） 对组，组内元素 做topological排序

public class SortItemsByGroupsRespectingDependencies {

	public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
		if (n == 1) return new int[]{0};
        Map<Integer, List<Integer>> groupList = new HashMap<>();
        
        // 这里我们为那些没组的分别assign一个（虚）组号，方便后面对group排序
        for(int i = 0; i < n; i++) {
            if(group[i] < 0) group[i] = m++;
        }
        // initialize group list
        for (int i = 0; i < m; i++) {
            groupList.put(i, new ArrayList<>());
        }
        // build group list
        for (int i = 0; i < group.length; i++) {
            groupList.get(group[i]).add(i);
            
        }
        //for (int i = 0; i < m; i++) {//不能用 m 了，这个值已经在前面被改了
//        for (int i = 0; i < group.length; i++) { //！！！！ 不能直接取代上面的， 因为此时的 group[i]不是连续的
        									//所以我们前面要先 initialize group一遍，这样不管该group有没有元素，都在graph有一个值，用以在toplogical比较数目
//        	groupList.computeIfAbsent(group[i], k -> new ArrayList<>()).add(i);
//        }
        
        Map<Integer, List<Integer>> groupGraph = new HashMap<>();
        Map<Integer, List<Integer>> itemGraph = new HashMap<>();
        int[] groupInDegree = new int[groupList.size()];
        int[] itemInDegree = new int[n];
        // build group order
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < beforeItems.get(i).size(); j++) {
                int s = beforeItems.get(i).get(j);
                int d = i;
//                int groupS = group[s];
//                int groupD = group[d];
                // 同时构建 组的先后顺序，用以后面针对 Group的 topological排序
                if (group[s] != group[d]) {
                    groupGraph.computeIfAbsent(group[s], k -> new ArrayList<>()).add(group[d]);
                    groupInDegree[group[d]]++;
                }
                itemGraph.computeIfAbsent(s, k -> new ArrayList<>()).add(d);
                itemInDegree[d]++;
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < groupInDegree.length; i++) {
            if (groupInDegree[i] == 0)
                queue.offer(i);
        }
        if (queue.size() == 0) return new int[]{};
        List<Integer> list = new ArrayList<>();
        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                int groupNum = queue.poll();
                count++;
                // ！！！对每个组都做一次 全items的排序，会导致 TLE， 而实际这里只需要对Group内的元素排序就可以
                // ===>优化：  组，组内元素 分别排好序；  得到组后再取组内元素按序输出即可
                // ===> 代码简化：  分别组内，组 排好，再根据组，组内元素的顺序按序输出
                boolean isConflict = innerItemToplogical(groupList.get(groupNum), list, itemGraph, itemInDegree);
                if (!isConflict) return new int[]{};
                for (int adj : groupGraph.getOrDefault(groupNum, new ArrayList<>())) {
                    groupInDegree[adj]--;
                    if (groupInDegree[adj] == 0)
                        queue.offer(adj);
                }
                size--;
            }
        }
        if (count != m) return new int[]{};
        else {
            return list.stream().mapToInt(x -> x).toArray();
        }
        
    }
	
	public int[] test(int n, int m, int[] group, List<List<Integer>> beforeItems) {
		if (n == 1) return new int[]{0};
        Map<Integer, List<Integer>> groupMap = new HashMap<>();
        
        // 这里我们为那些没组的分别assign一个（虚）组号，方便后面对group排序
        for(int i = 0; i < n; i++) {
            if(group[i] < 0) group[i] = m++; //此时 m才是真正的Group的数目，所以后面初始化对象跟Group数目有关的用 m
        }
        // initialize group list
        for (int i = 0; i < m; i++) {
        	groupMap.put(i, new ArrayList<>());
        }
        // build group list
        for (int i = 0; i < group.length; i++) {
        	groupMap.get(group[i]).add(i);
            
        }
        
        Map<Integer, List<Integer>> groupGraph = new HashMap<>();
        Map<Integer, List<Integer>> itemGraph = new HashMap<>();
        int[] groupInDegree = new int[groupMap.size()];
        int[] itemInDegree = new int[n];
        // build group order
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < beforeItems.get(i).size(); j++) {
                int s = beforeItems.get(i).get(j);
                int d = i;
//                int groupS = group[s];
//                int groupD = group[d];
                // 同时构建 组的先后顺序，用以后面针对 Group的 topological排序
                if (group[s] != group[d]) {
                    groupGraph.computeIfAbsent(group[s], k -> new ArrayList<>()).add(group[d]);
                    groupInDegree[group[d]]++;
                } else {
                	itemGraph.computeIfAbsent(s, k -> new ArrayList<>()).add(d);
                    itemInDegree[d]++;
                }
                
            }
        }
        
        // 对组内元素进行 topological sort
        Map<Integer, List<Integer>> sortedGroupInnerItem = new HashMap<>();
        // 有 M 个组，循环对每个组都sort一遍，并把它放回 group-sortedItems map中， 之后根据 Group的先后顺序，输出合并组内的元素即可
        for (int i = 0; i < m; i++) {
        	List<Integer> sortedItem = toplogical(itemGraph, groupMap.get(i), itemInDegree);
        	sortedGroupInnerItem.put(i, sortedItem);
        }
        
        // 对组本身进行 topological sort
        List<Integer> groups = new ArrayList<>();
        for (int i = 0; i < m; i++) {
        	groups.add(i);
        }
        // 一遍即可
        List<Integer> sortedGroup = toplogical(groupGraph, groups, groupInDegree);
        
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < sortedGroup.size(); i++) {
        	List<Integer> innerItems  // i 不是组号， 是sortedGroup.get（i） 才是
        		= sortedGroupInnerItem.get(sortedGroup.get(i));// 内部元素已排序，直接输出
        	list.addAll(innerItems);
        }
        
        return list.size() == n ? list.stream().mapToInt(k -> k).toArray() : new int[] {};
        
    }
	
	public List<Integer> toplogical(Map<Integer, List<Integer>> graph, List<Integer> items, int[] itemInDegree) {
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 0; i < items.size(); i++) {
			if (itemInDegree[items.get(i)] == 0) {
				queue.offer(items.get(i));
			}
		}
		
		int count = 0;
		List<Integer> list = new ArrayList<>();
		while (!queue.isEmpty()) {
			int size = queue.size();
			while (size > 0) {
				int node = queue.poll();
				count++;
				list.add(node);
				for (int adj : graph.getOrDefault(node, new ArrayList<>())) {
					itemInDegree[adj]--;
					if (itemInDegree[adj] == 0)
						queue.offer(adj);
				}
				size--;
			}
		}
		
		return count == items.size() ? list : new ArrayList<>();
	}
    
    public boolean innerItemToplogical(List<Integer> items, List<Integer> list,
        Map<Integer, List<Integer>> itemGraph, int[] itemInDegree) {
        Queue<Integer> queue = new LinkedList<>();
        // 这里要check的仅是 组内的元素，不需要遍历全部元素，所以下面的循环对象是 items, 不是itemInDegree
//        for (int i = 0; i < itemInDegree.length; i++) {
//            if (itemInDegree[i] == 0)
//                queue.offer(i);
//        }
        for (int i = 0; i < items.size(); i++) {
            if (itemInDegree[items.get(i)] == 0) {
                //count++;
                queue.offer(items.get(i));
            }   
        }
        //if (queue.size() == 0) return false; //不要， 否则对 Group本来没元素的 返回 false了
        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                int item = queue.poll();
                //if (items.contains(item)) { // 不用再判断了， 因为此时只处理 组内的元素
                list.add(item);
                count++;
                for (int adj : itemGraph.getOrDefault(item, new ArrayList<>())) {
                    itemInDegree[adj]--;
                    if (itemInDegree[adj] == 0 && items.contains(adj)) // 同时还必须是组内元素才放进去，我们不考虑非组内元素
                        queue.offer(adj);
                }
                //}
                size--;
            }
            
        }
        return items.size() == count ? true : false;
    }
    
    public static void main(String[] args) {
    	int[] group = new int[] {2,0,-1,3,0};
    	List<List<Integer>> beforeItems = new ArrayList<>();
    	List<Integer> b1 = new ArrayList<>();
    	b1.add(2); b1.add(1); b1.add(3);
    	List<Integer> b2 = new ArrayList<>();
    	b2.add(2); b2.add(4);
    	List<Integer> b3 = new ArrayList<>();
    	List<Integer> b4 = new ArrayList<>();
    	List<Integer> b5 = new ArrayList<>();
    	beforeItems.add(b1);
    	beforeItems.add(b2);
    	beforeItems.add(b3);
    	beforeItems.add(b4);
    	beforeItems.add(b5);
    	new SortItemsByGroupsRespectingDependencies().test(5, 5, group, beforeItems);
    }
}
