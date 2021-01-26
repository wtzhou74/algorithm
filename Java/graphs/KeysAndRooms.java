package graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

//There are N rooms and you start in room 0.  Each room has a distinct number in 0, 1, 2, ..., N-1, and each room may have some keys to access the next room. 
//
//Formally, each room i has a list of keys rooms[i], and each key rooms[i][j] is an integer in [0, 1, ..., N-1] where N = rooms.length.  A key rooms[i][j] = v opens the room with number v.
//
//Initially, all the rooms start locked (except for room 0). 
//
//You can walk back and forth between rooms freely.
//
//Return true if and only if you can enter every room.
//
//Example 1:
//
//Input: [[1],[2],[3],[]]
//Output: true
//Explanation:  
//We start in room 0, and pick up key 1.
//We then go to room 1, and pick up key 2.
//We then go to room 2, and pick up key 3.
//We then go to room 3.  Since we were able to go to every room, we return true.
//Example 2:
//
//Input: [[1,3],[3,0,1],[2],[0]]
//Output: false
//Explanation: We can't enter the room with number 2.
//Note:
//
//1 <= rooms.length <= 1000
//0 <= rooms[i].length <= 1000
//The number of keys in all rooms combined is at most 3000.
public class KeysAndRooms {

	public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        if (rooms.size() == 0) {
            return true;
        }
        boolean[] visited = new boolean[rooms.size()];
        visited[0] = true;
        List<Integer> keys = new ArrayList<>();
        keys.addAll(rooms.get(0)); // 从第一个房间开始，并要拿出那的锁，否则无法开始下一个
        helper(rooms, 1, keys, visited);
        for (boolean room : visited) {
            if (!room) return false;
        }
        return true;
    }
	
	// Graph 的DFS ==> 找到一条路可以走过所有的点
    public void helper(List<List<Integer>> rooms, int room, List<Integer> keys,  boolean[] visited) {
        if (keys.size() == 0 || room >= rooms.size()) // 如果满了，或者当前room没有钥匙，就回到前面的房间拿下一把锁
            return ;
        for (int i = 0; i < keys.size(); i++) { // 遍历每个房间的每个钥匙
        	if (visited[keys.get(i)]) { // 如果这个房间已经visited过了，拿下一把锁
        		continue;
        	}
        	visited[keys.get(i)] = true;//该房间标注为已 visited
            helper(rooms, room + 1, rooms.get(keys.get(i)), visited); // 拿着这把锁去开下一个对应的房间，并拿到那的锁
        }
    }
    
    // Iterative solution with Stack
    // Time: O(N + E)  // N- number of rooms, E: Number of keys
    // Space: O(N)
    public boolean iterativeSol(List<List<Integer>> rooms) {
        if (rooms.size() == 0) {
            return true;
        }
        //boolean[] visited = new boolean[rooms.size()];
        Set<Integer> set = new HashSet<>();
        set.add(0);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < rooms.get(0).size(); i++) {
            stack.push(rooms.get(0).get(i));
        }
        while (!stack.isEmpty()) {
            int key = stack.pop();
            if (set.contains(key)) {
                continue;
            }
            set.add(key);
            for (int i = 0; i < rooms.get(key).size(); i++) {
                stack.push(rooms.get(key).get(i));
            }
        }
        
        return set.size() == rooms.size();
    }
    
    public static void main(String[] args) {
    	List<List<Integer>> rooms = new ArrayList<>();

    	List<Integer> room = new ArrayList<>();
    	room.add(1); room.add(3);
    	List<Integer> room0 = new ArrayList<>();
    	room0.add(1); room0.add(3); room0.add(0);
    	List<Integer> room1 = new ArrayList<>();
    	room1.add(2);
    	List<Integer> room2 = new ArrayList<>();
    	room2.add(0);
    	rooms.add(room); rooms.add(room0); rooms.add(room1); rooms.add(room2);
    	new KeysAndRooms().canVisitAllRooms(rooms);
    }
}
