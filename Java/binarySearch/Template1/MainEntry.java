package binarySearch.Template1;

import java.util.Scanner;

public class MainEntry {

	public static void main(String[] args) {
		SearchInRotatedSortedArray.searchRotatedSol(createIntArray(), 3);
	}
	
	public static int[] createIntArray()
	{
		Scanner scan = new Scanner(System.in);
		String line = scan.nextLine();
		String[] strs = line.split(" ");
		int[] nums = new int[strs.length];
		for (int i = 0; i < strs.length; i ++) {
			nums[i] = Integer.parseInt(strs[i]);
		}
		
		return nums;
	}
}
