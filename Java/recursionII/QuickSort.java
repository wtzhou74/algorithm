package recursionII;

import java.util.ArrayList;
import java.util.List;

public class QuickSort {

	// TIME: O(NlogN)
	// The worst case is O(N-square) if the pivot value happens to be the extreme value of the list; so the PARTITION would then occur N times, 
	//		each time, the partitioning scans at most N elements.
	// SPACE: O(logN), the worst case is O(N), since the deepth of stack is N
	public void quickSort(int[] nums) {
		//helper(nums, 0, nums.length - 1);
		quickSort2(nums, 0, nums.length - 1);
		
	}
	
	// 参考: https://blog.csdn.net/adusts/article/details/80882649
	public void quickSort2(int[] nums, int low, int high) {
		if (low >= high)
			return;
		int pivot = nums[low];
		int i = low;
		int j = high;
		//最后会一样，不会出现 i > j, 因为 一边一边移动的，不是同时
		while (i != j) {
			// 一定要先从右边开始移动，
        	// 停在 元素值比 Pivot小的点
			while (nums[j] >= pivot && i < j) {
				j--;
			}
			// 然后从左边开始移动，并停在元素值比Pivot大的点位置
			while (nums[i] <= pivot && i < j) {
				i++;
			}
			// 如果此时 i < j， 把对应的两个元素互换，while重新开始上述过程直到 i >= j 完成一次快排
			if ( i < j) {
				int temp = nums[j];
				nums[j] = nums[i];
				nums[i] = temp;
			}
		}
		// 完成一轮排序后，此时 i 对应的是pivot,即左边都比pivot小，右边都比pivot大，
        // 但此时 i 对应的值不是 pivot 本身，所以，我们需要 交换，把 pivot 放到 i 的位置
		nums[low] = nums[i];
		nums[i] = pivot;
		// 递归开始左半部分
		quickSort2(nums, low, i - 1);
		// 递归开始右半部分
		quickSort2(nums, i + 1, high);
	}
	
	public void helper(int[] nums, int lo, int hi) {
		if (lo >= hi) return;
		//  partition array recursively (in-place)
		int pivot = partition(nums, lo, hi);
		helper(nums, lo, pivot - 1);
		helper(nums, pivot + 1, hi);
	}
	
	// Process Array IN-PLACE
	public int partition(int[] nums, int lo, int hi) {
		//int pivot = nums[lo];
		// pick the last element hi as pivot
		int pivot = nums[hi];
		int j = lo;
		for (int i = lo; i < hi; i++) {
			if (nums[i] < pivot) {
				int temp = nums[j];
				nums[j] = nums[i];
				nums[i] = temp;
				j++;
			}
		}
		int temp = nums[j];
		nums[j] = nums[hi];
		nums[hi] = temp;
		return j; // j is the index of pivot
	}
	
	public List<Integer> sol2(int[] nums) {
		if (nums.length < 2) return new ArrayList<>();
		List<Integer> s = new ArrayList<>();
		for (int i = 0; i < nums.length; i++) {
			s.add(nums[i]);
		}
		return helper2(s);
	}
	public List<Integer> helper2(List<Integer> s) {
		if (s.size() < 2) return s;
		// WASTE OF SPACE
		List<Integer> left = new ArrayList<>();
		List<Integer> right = new ArrayList<>();
		int pivot = s.get(s.size() / 2);
		s.remove(s.size() / 2); // remove pivot from ori list
		for (int i = 0; i < s.size(); i++) {
			if (s.get(i) < pivot) {
				left.add(s.get(i));
			} else {
				right.add(s.get(i));
			}
		}
		List<Integer> r1 = helper2(left);
		r1.add(pivot);
		r1.addAll(helper2(right));
		return r1;
	}
	
	public static void main(String[] args) {
		int[] nums = new int[] {6, 1, 2, 5, 9, 4, 7, 10, 8};
//		new QuickSort().quickSort(nums);
//		for (int i = 0; i < nums.length; i++) {
//			System.out.println(nums[i]);
//		}
		
		new QuickSort().quickSort(nums);
//		List<Integer> res = new QuickSort().sol2(nums);
//		for (int i = 0; i < res.size(); i++) {
//			System.out.println(res.get(i));
//		}
	}
}
