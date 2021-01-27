package amazon;

import java.util.Arrays;

//We have a list of points on the plane.  Find the K closest points to the origin (0, 0).
//
//(Here, the distance between two points on a plane is the Euclidean distance.)
//
//You may return the answer in any order.  The answer is guaranteed to be unique (except for the order that it is in.)
//
// 
//
//Example 1:
//
//Input: points = [[1,3],[-2,2]], K = 1
//Output: [[-2,2]]
//Explanation: 
//The distance between (1, 3) and the origin is sqrt(10).
//The distance between (-2, 2) and the origin is sqrt(8).
//Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
//We only want the closest K = 1 points from the origin, so the answer is just [[-2,2]].
//Example 2:
//
//Input: points = [[3,3],[5,-1],[-2,4]], K = 2
//Output: [[3,3],[-2,4]]
//(The answer [[-2,4],[3,3]] would also be accepted.)
// 
//
//Note:
//
//1 <= K <= points.length <= 10000
//-10000 < points[i][0] < 10000
//-10000 < points[i][1] < 10000
public class KClosestPointsToOrigin {

	// Time: O(NlgN)
	public int[][] kClosest(int[][] points, int K) {
        if (points.length < K) return new int[0][0];
        Arrays.sort(points, 
                    (a, b) -> (a[0] * a[0] + a[1] * a[1])
                        - (b[0] * b[0] + b[1] * b[1]));
        return Arrays.copyOfRange(points, 0, K);
    }
	
	
	// 自己实现排序算法， 比如 quickSort （Divide and Conquer 思路）
	public int[][] kClosest1(int[][] points, int K) {
        if (points.length < K)
            return new int[0][0];
        quickSort(points, 0, points.length - 1);
        
        // int[][] sortedPoints = mergeSort(points);
        return Arrays.copyOfRange(points, 0, K);
        
    }
    
	// 参考: https://blog.csdn.net/adusts/article/details/80882649
    public void quickSort(int[][] points, int low, int high) {
        if (low > high)
            return;
        int[] pivot = points[low];
        int i = low;
        int j = high;
      //最后会一样，不会出现 i > j, 因为 一边一边移动的，不是同时
        while (i != j) {
        	// 一定要先从右边开始移动，
        	// 停在 元素值比 Pivot小的点
            while (dis(points[j]) >= dis(pivot) && i < j) {
                j--;
            }
            // 然后从左边开始移动，并停在元素值比Pivot大的点位置
            while (dis(points[i]) <= dis(pivot) && i < j) {
                i++;
            }
            // 如果此时 i < j， 把对应的两个元素互换，while重新开始上述过程直到 i >= j 完成一次快排
            if (i < j) { // 此时 j的元素是小于pivot, i 的元素是大于pivot, 所以要换下，把大的放右边去
                int[] temp = points[j];
                points[j] = points[i];
                points[i] = temp;
            }
        }
        // 完成一轮排序后，此时 i 对应的是pivot,即左边都比pivot小，右边都比pivot大，
        // 但此时 i 对应的值不是 pivot 本身，所以，我们需要 交换，把 pivot 放到 i 的位置
        points[low] = points[i];
        points[i] = pivot;
        // 递归执行左边部分
        quickSort(points, low, i - 1);
        // 递归执行右边部分
        quickSort(points, i + 1, high);
        
    }
    
    // mergeSort 排序
    public int[][] mergeSort(int[][] points) {
        // if (low >= high)
        //     return new int[0][0];
        if (points.length == 1)
            return points;
        int mid = points.length / 2;
        int[][] left = mergeSort(Arrays.copyOfRange(points, 0, mid));
        int[][] right = mergeSort(Arrays.copyOfRange(points, mid, points.length));
        return merge(left, right);
    }
    
    public int[][] merge(int[][] left, int[][] right) {
        int[][] res = new int[left.length + right.length][2];
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (dis(left[i]) > dis(right[j])) {
                res[k++] = right[j];
                j++;
            } else {
                res[k++] = left[i];
                i++;
            }
        }
        while (i < left.length)
            res[k++] = left[i++];
        while (j < right.length)
            res[k++] = right[j++];
        
        return res;
        
    }
    
    public int dis(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }
}
