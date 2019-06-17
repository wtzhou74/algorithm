package binarySearch.Practices;

/**
 * Given an array of integers that is already sorted 
 * in ascending order, find two numbers such that they add up to a specific target number.
 * The function twoSum should return indices of the two numbers such that they add up to 
 * the target, where index1 must be less than index2.
 * */
public class TwoSum2 {

	// two pointers, LEFT RIGHT
	public int[] twoSum(int[] numbers, int target) {
        if (numbers.length <= 1) return new int[0];
        int i = 0, j = numbers.length - 1;
        int[] result = new int[2];
        while (i < j) {
            if (numbers[i] + numbers[j] > target) {
                j--;
            } else if (numbers[i] + numbers[j] < target) {
                i++;
            } else {
                result[0] = i + 1;
                result[1] = j + 1;
                
                break;
            }
        }
        
        return result;
    } 
	// Binary search
	// for each item, find taget - item through binary search
}
