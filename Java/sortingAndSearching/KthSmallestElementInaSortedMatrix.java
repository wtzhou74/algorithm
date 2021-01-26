package sortingAndSearching;

import java.util.PriorityQueue;


public class KthSmallestElementInaSortedMatrix {

	// Time: O(n^2logN)
	public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                q.offer(matrix[i][j]);
            }
        }
//        Iterator<Integer> iter = q.iterator();
        int count = 0;
        int res = 0;
//        while (iter.hasNext()) { // !!!!! priorityQueue.iterator() does not guarantee the order 
//            if (count == k) {
//                break;
//            }
//            count++;    
//            res = iter.next();
//        }
        
        while (count <= k) {
            if (count == k)
                return res;
            res = q.poll();
            count++;
        }
        return res;
    }
	
	// Time: O(n * logN * log(max -min))
	// Space: O(1)
	public int binarySearch(int[][] matrix, int k) {
		int left = matrix[0][0];
        int right = matrix[matrix.length - 1][matrix[0].length - 1];
        while (left < right) {
            int count = 0;
            int mid = left + (right - left) / 2;  // 对mid值向下取整  （用以寻找  larggerOrEqual 目标值的情况）
            // int mid = (left + right) >>> 1;
            // calculate the number of cells in matrix by looping through each row
            for (int i = 0; i < matrix.length; i++) {
                int col = binarySearchRow(matrix[i], mid);
                count += col + 1;// the returned col is index
            }
            if (count >= k) {// the mid is larger than the target value   （寻找 大于等于 目标值， 所以 等号 放在这里）
                right = mid; //  同时 设置 当前 索引从 mid 开始，以免 漏掉  == 目标值的情况
            } else {
                left = mid + 1;
            }
        }
        return left; // 或者 right
	}
	
	// binary search for each row to find the target value
    public int binarySearchRow(int[] row, int target) {
    	if (target < row[0]) return -1; // [[1,2],[3,3]]
        int left = 0, right = row.length - 1;
        while (left < right) {
            int mid = left + (right - left + 1) / 2;// 对mid值向上取整  （用以寻找  smallerOrEqual 目标值的情况）
            //int mid = (left + right) >>> 1; // it is more efficient than left + (right - left + 1) / 2
            //if (row[mid] == target) return mid;
            if (row[mid] <= target) { // 目标是找 <= 目标值的索引， 因为（count = col + 1）， 所以 等号“=” 放在left这边
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;// 因为 判定条件 while(left < right) ， 出循环的时候 left 就等于 right 了， 所以此处， left 或者 right 都可以， 否则要判断
    }
	
	public static void main(String[] args) {
		
		int res = new KthSmallestElementInaSortedMatrix().binarySearch(new int[][] {{1, 2}, {1, 3}}, 4);
		System.out.println(res);
	}
}
