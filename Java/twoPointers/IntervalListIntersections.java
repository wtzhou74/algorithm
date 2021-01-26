package twoPointers;

import java.util.ArrayList;
import java.util.List;

//Given two lists of closed intervals, each list of intervals is pairwise disjoint and in sorted order.
//
//Return the intersection of these two interval lists.
//
//(Formally, a closed interval [a, b] (with a <= b) denotes the set of real numbers x with a <= x <= b.  The intersection of two closed intervals is a set of real numbers that is either empty, or can be represented as a closed interval.  For example, the intersection of [1, 3] and [2, 4] is [2, 3].)
//
// 
//
//Example 1:
//
//
//
//Input: A = [[0,2],[5,10],[13,23],[24,25]], B = [[1,5],[8,12],[15,24],[25,26]]
//Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
//Reminder: The inputs and the desired output are lists of Interval objects, and not arrays or lists.
// 
//
//Note:
//
//0 <= A.length < 1000
//0 <= B.length < 1000
//0 <= A[i].start, A[i].end, B[i].start, B[i].end < 10^9

public class IntervalListIntersections {

	public int[][] intervalIntersection(int[][] A, int[][] B) {
        if (A == null || B == null || A.length == 0 || B.length == 0) {
            return new int[0][];
        }
        int i = 0, j = 0;
        List<int[]> aux = new ArrayList<>();
        while (i < A.length && j < B.length) {
            int[] a = A[i];
            int[] b = B[j];
            
            int[] temp = new int[2];
            
            if (a[0] < b[0]) {
                if (a[1] < b[0]) {
                    i++;
                    continue;
                } else if (a[1] == b[0]) {
                    temp[0] = b[0];
                    temp[1] = b[0];
                    i++;
                } else if (a[1] <= b[1]) {
                    temp[0] = b[0];
                    temp[1] = a[1];
                    if (a[1] == b[1]) {
                        i++;
                        j++;
                    } else {
                        i++;
                    }
                } else if (a[1] > b[1]) {
                    temp[0] = b[0];
                    temp[1] = b[1];
                    j++;
                }
            } else if (a[0] > b[0]) {
                if (a[0] > b[1]) {
                    j++;
                    continue;
                } else if (a[0] == b[1]) {
                    temp[0] = a[0];
                    temp[1] = a[0];
                    j++;
                } else if (a[1] > b[1]) {
                    temp[0] = a[0];
                    temp[1] = b[1];
                    j++;
                } else if (a[1] <= b[1]) {
                    temp[0] = a[0];
                    temp[1] = a[1];
                    if (a[1] == b[1]) {
                        i++;
                        j++;
                    } else {
                        i++;
                    }
                }
            } else {
                if (a[1] > b[1]) {
                    temp[0] = a[0];
                    temp[1] = b[1];
                    j++;
                } else if (a[1] < b[1]) {
                    temp[0] = a[0];
                    temp[1] = a[1];
                    i++;
                } else {
                    temp[0] = a[0];
                    temp[1] = a[1];
                    i++;
                    j++;
                }
            }
            aux.add(temp);
        }
        return aux.toArray(new int[aux.size()][2]);
    }
	
	// Simplify the above solution
	public int[][] intervalIntersection1(int[][] A, int[][] B) {
        if (A == null || B == null || A.length == 0 || B.length == 0) {
            return new int[0][];
        }
        int i = 0, j = 0;
        List<int[]> aux = new ArrayList<>();
        while (i < A.length && j < B.length) {
            int[] a = A[i];
            int[] b = B[j];
            
            int left = Math.max(a[0], b[0]); //a画出图可发现 left 始终取左边的最大值，而 right 始终取右边的最小值，如果有交集的话
            int right = Math.min(a[1], b[1]);
            
            if (left <= right) {
                aux.add(new int[]{left, right});
            }
            
            // decide how to remove point
            if (a[1] > b[1]) {
                j++;
            } else if (a[1] < b[1]) {
                i++;
            } else {
                i++;
                j++;
            }
        }
        
        return aux.toArray(new int[aux.size()][2]);
        
    }
}
