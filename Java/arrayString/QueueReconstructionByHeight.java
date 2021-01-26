package arrayString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

//Suppose you have a random list of people standing in a queue. Each person is described by a pair of integers (h, k), where h is the height of the person and k is the number of people in front of this person who have a height greater than or equal to h. Write an algorithm to reconstruct the queue.
//
//Note:
//The number of people is less than 1,100.
//
// 
//Example
//
//Input:
//[[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
//
//Output:
//[[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
public class QueueReconstructionByHeight {

	public int[][] reconstructQueue(int[][] people) {
        
        if (people == null || people.length == 0) {
            return new int[0][0];
        }
        // Time: O(nlgN)
        Arrays.sort(people, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                if (a[0] != b[0]) {
                    return b[0] - a[0];
                }
                return a[1] - b[1];                
            }
        });
        
        List<int[]> output = new LinkedList<>();
        // Time: O(K), where k is a current number of elements in the list; for insert operations.
        for (int[] person : people) {
        	//Shifts the element currently at that position
            // (if any) and any subsequent elements to the right
        	output.add(person[1], person); }
        
        // Space: O(N)
        int[][] newQueue = new int[people.length][2];
        for (int i = 0; i < people.length; i++) {
        	newQueue[i][0] = output.get(i)[0];
        	newQueue[i][1] = output.get(i)[1];
        }
        //output.toArray(new int[people.length][2]);
        return newQueue;
    }
	
	public static void main(String[] args) {
		int[][] people = new int[][] {
			{7, 0},
			{4, 4},
			{7, 1},
			{5, 0}, {6, 1}, {5, 2}
//			{0, 0},
//			{6, 2},
//			{5, 5},
//			{4, 3},
//			{5, 2},
//			{1, 1},
//			{6, 0},
//			{6, 3},
//			{7, 0},
//			{5, 1}
		};
		new QueueReconstructionByHeight().reconstructQueue(people);
	}
	
}
