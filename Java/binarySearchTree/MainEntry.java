package binarySearchTree;

public class MainEntry {

	public static void main(String[] args) {
		// The kth largest element
		int[] nums = {4, 5, 8, 2};
		KthLargest obj = new KthLargest(3, nums);
		int[] test = {3, 5, 10, 9, 4};
		for (int i = 0; i < 5; i++) {
			System.out.println(obj.add(test[i]));
		}
		
	}
}
