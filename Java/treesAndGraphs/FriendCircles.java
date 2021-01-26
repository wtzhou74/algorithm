package treesAndGraphs;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

//There are N students in a class. Some of them are friends, while some are not. Their friendship is transitive in nature. For example, if A is a direct friend of B, and B is a direct friend of C, then A is an indirect friend of C. And we defined a friend circle is a group of students who are direct or indirect friends.
//
//Given a N*N matrix M representing the friend relationship between students in the class. If M[i][j] = 1, then the ith and jth students are direct friends with each other, otherwise not. And you have to output the total number of friend circles among all the students.
//
//Example 1:
//Input: 
//[[1,1,0],
// [1,1,0],
// [0,0,1]]
//Output: 2
//Explanation:The 0th and 1st students are direct friends, so they are in a friend circle. 
//The 2nd student himself is in a friend circle. So return 2.
//Example 2:
//Input: 
//[[1,1,0],
// [1,1,1],
// [0,1,1]]
//Output: 1
//Explanation:The 0th and 1st students are direct friends, the 1st and 2nd students are direct friends, 
//so the 0th and 2nd students are indirect friends. All of them are in the same friend circle, so return 1.
//Note:
//N is in range [1,200].
//M[i][i] = 1 for all students.
//If M[i][j] = 1, then M[j][i] = 1.

public class FriendCircles {
	
	// NOTE: FriendCircles' traversal DOES NOT need to be CONTINUOUS ([0, 4] [4, 0]),
	// 		 that is why we CANNOT APPLY the same algo for NumOfIslands which traveral MUST BE SUCCESSIVE.
	
	// DFS
	// Time: O(n^2)
	// Space: O(n): visited array
	public int findCircleNum(int[][] M) {
        if (M.length == 1 && M[0].length == 1) {
            return 1;
        }
        boolean[] visited = new boolean[M.length];
        int circles = 0;
        for (int i = 0; i < M.length; i++) {
            if (!visited[i]) {
                dfs(M, i, visited);
                circles++;
            }
        }
        return circles;
    }
    
    public void dfs(int[][] M, int i, boolean[] visited) {
        visited[i] = true;
        for (int j = 0; j < M[0].length; j++) {
        	// i == j, an individual is his own friend.
            if (i != j && M[i][j] != 0 && !visited[j]) {
                dfs(M, j, visited);
            }
        }
    }
    
    // BFS
    // Time: O(N * N)
    // Space: O(N)

    public int bfs(int[][] M) {
        if (M.length == 1 && M[0].length == 1) {
            return 1;
        }
        boolean[] visited = new boolean[M.length];
        int circles = 0;
        // for (int i = 0; i < M.length; i++) {
        //     if (!visited[i]) {
        //         dfs(M, i, visited);
        //         circles++;
        //     }
        // }
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < M.length; i++) { // total number of nodes/individuals
            if (visited[i]) continue;
            queue.offer(i);
            while (!queue.isEmpty()) {
                int s = queue.poll();// current node and their children
                visited[s] = true;
                for (int j = 0; j < M[0].length; j++) {
                    if (s != j && M[s][j] == 1 && !visited[j]) {
                        queue.offer(j); // add each child, and then process again for INDIRECT friends
                    }
                }
            }
            circles++;
        }
        return circles;
    }
    
    // Union-Find solution  ==> https://blog.csdn.net/qq_41593380/article/details/81146850
    
    // Time: O(n^3), since Union and Find operations take O(n) in worst case
    // Space: O(n), parent array of size n
    public int unionFind(int[][] M) {
    	int[] parent = new int[M.length]; // there are total M.length TOP-MOST PARIENTs/ROOTs/GROUPs/SETs, parent[5] = -1 means node 5's parent is -1
    	Arrays.fill(parent, -1); // initializing the groups by assigning -1 to each parent/root/group
    	
    	// for each pair, find their parents and union
    	for (int i = 0; i < M.length; i++) {
    		for (int j = 0; j < M[0].length; j++) {
    			if (i != j && M[i][j] == 1) {
    				// find parents of i, j separately, if there are same, done, if there are different, union, meaning they can be assigned to the SAME GROUP
    				union(parent, i, j); 
    			}
    		}
    	}
    	int circles = 0;
    	for (int i = 0; i < parent.length; i++) {
    		if (parent[i] == -1) // each -1 means a single group  // not -1 means their direct parent is not root, parent[5] = 3 , parent[3] = -1, 5->3->-1 one group
    			circles++;
    	}
    	return circles;
    }
    
    // Union parents/groups of two elems (i, j) which have a relation if there are different
    public void union(int[] parent, int i, int j) {
    	int iP = findParent(parent, i);
    	int jP = findParent(parent, j);
    	
    	// their parents are different
    	if (iP != jP) {
    		parent[iP] = jP; // union parents, set one parent to another parent's child
    		// iP is the child of jP by running the above code
    	}
    }
   
    // find the parent/root of a given elem/node
    public int findParent(int[] parent, int i) {
    	// recursive solution
//    	if (parent[i] == -1) return i; // current i is the root and it is the TOP-MOST parent of the given i
//    	//if (parent[i] != -1) { // if the parent's value is -1, which is the root
//    	return	findParent(parent, parent[i]); // check its direct parent
    	
    	// Alternative => Iterative and Path Compression(路径压缩)  => Flattening TREE structure to reduce time complexity (low height)
    	int temp = i;
        while (parent[i] != -1) {
            i = parent[i];
        } // Got the ROOT i
        
        // Path Compression by setting all children to their root // NOT NECESSARY
        while (temp != i) {
            int dirParent = parent[temp]; // get its direct parent
            parent[temp] = i; // set current child to the root
            temp = dirParent; // check its direct parent
        }
        return i;
    	
    }
    
    
//	public int findCircleNum(int[][] M) {
//        if (M.length == 1 && M[0].length == 1) {
//            return 1;
//        }
//        boolean[][] visited = new boolean[M.length][M[0].length];
//        int circles = 0;
//        for (int i = 0; i < M.length; i++) {
//            for (int j = 0; j < M[0].length; j++) {
//                if (!visited[i][j] && M[i][j] == 1) {
//                    helper(M, i, j, visited);
//                    circles++;
//                }
//            }
//        }
//        return circles;
//    }
    
	// WRONG !!!! 不能采用这种类似迷宫遍历， because of DEFINITION of Friend Circle, For instance
//	[1,0,0,1],
//	[0,1,1,0],
//	[0,1,1,1],
//	[1,0,1,1]   // Expected answer is 1, while the result is 4 by running the following code
	
	// =====> DFS/BFS, == Check each individual for each row
	
//    public void helper(int[][] M, int row, int col, boolean[][] visited) {
//        if (row < 0 || row >= M.length || col < 0 || col >= M[0].length
//                || visited[row][col]) {
//            return;
//        }
//        if (M[row][col] != 1) return;
//        visited[row][col] = true;
//        helper(M, row + 1, col, visited);
//        helper(M, row - 1, col, visited);
//        helper(M, row, col + 1, visited);
//        helper(M, row, col - 1, visited);
//        //visited[row][col] = false;
//    }
}
