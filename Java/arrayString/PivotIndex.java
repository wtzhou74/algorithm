package arrayString;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PivotIndex {

	public static void main(String args[]) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter an array: ");
		List<Integer> nums = new ArrayList<Integer>();
		String input = scan.nextLine();
		for (String item : input.split(",")) {
			nums.add(Integer.parseInt(item.trim()));
		}
		int[] numArray = new int[nums.size()];
		for (int i = 0; i <  nums.size(); i++) {
			numArray[i] = nums.get(i);
		}
		int i = pivotIndexSolution(numArray);
		System.out.println(i);
	}
	
	public static int pivotIndexSolution (int[] nums)
	{
		if (nums.length > 10000 || nums.length == 0) {
			return -1;
		}
		int total = 0;
		for (int item : nums) {
			total += item;
		}
		// Pivot:  LEFT + RIGHT + PIVOT == TOTAL => left == right => pivot = total - 2*left
		int left = 0;
		for (int i = 0; i < nums.length; i++) {
			total -= nums[i];// get right
			if(left == total) {
				return i;
			}
			left += nums[i];// or cannot pass the case which the first item is pivot index
		}
		
		//-----------------------------
		for (int i = 0; i < nums.length; i++)
		{
			if (total - left * 2 == nums[i])
			{
				return i;
			}
			left += nums[i];
		}
		
		return -1;

	}
}
