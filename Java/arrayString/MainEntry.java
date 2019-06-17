package arrayString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainEntry {

	public static void main(String[] args)
	{
		//int[] nums = typeArray();
		
		/*
		 * ARRAY
		 * 
		 * MOVING PATTERN, CALCULATING RULE (up-down layer, left-right, etc.)
		 * ******************************************************************/
		//largest number at least twice of others
		//System.out.println(DominantIndex.optimizedSolution(nums));
		
		// PLUS ONE
		// printArray(PlusOne.plusOneSolution(nums));
		
		// DIAGONAL TRAVERSE  ==> FIND MOVING PATTERNS
		// (r + c) % 2 == 0 MOVE UP , OR MOVE DOWN
		//printArray(DiagonalTraverse.diagonalTraverseSolution(new int[][] {{1,2,3}, {5,4,6}}));
		
		//SPIRAL ORDER ==> FIND MOVING PATTERNS
		//System.out.println(SpiralOrder.spiralMatrixSolution(new int[][] {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}}));
		
		// PASCAL'S TRIANGLE => find the calculating RULE for middle value
		// System.out.println(PascalTriangle.generateTriangle(nums[0]));
		
		
		
		/*
		 * STRING
		 * 
		 * rules of CHAR, SUBSTRING
		 * ********/
		// ADD BINARY STRING ==> set CARRY correctly, and the remaining digits (longLen - shortLen - 1);;;string.toCharArray();stringBuilder.reverse()
		// optimize ==> sum = aChar? + bChar? + CARRY;  carry = sum / 2;;; string.charAt()
		// System.out.println(AddBinary.optimizedSolution(typeString(), typeString()));
		
		// HayStack-Needle;  ;; string.substring(i, needleLength)
		// NOTE: needle is empty, then match any character
		// StrStr.strStrSolution(typeString(), typeString());
		
		// String[] arrays = new String[3];
		// arrays[0] = typeString();
		// arrays[1] = typeString();
		// arrays[2] = typeString(); 
		// Start with the String which has the shortest length
		// System.out.println(LongestCommonPrefix.longestCommonPrefixSol(arrays));
		
		/*
		 * * Reverse String - two-pointer technique, one from the beginning and the other one starts from the end
		 * */
		// toCharArray; while(startPointer > endPointer); String.valueOf(charArray);;;
		// System.out.println(ReverseString.reverseStringSolution(typeString()));
		
		// !!!!!!!!!!!!!!!!!!!!!!!!!!!
		// !!!!!Replace SORT operation with treating original elements as INDEXES of EMPTY ARRAY, then the elements will be sorted automatically
		// Be careful of OFFSET and DUPLICATE elements (++/--)
		// ArrayPartition.arrayPartitionOptimizedSolution(new int[] {1,4,3,2});
		
		//TWO POINTERS move at the same time in response to SUM RESULT
		//printArray(TwoSumWithTarget.twoSumOptimizedSol(typeArray(), 9));
		
		// Moving Strategies
		// RemoveElement.removeElementOptimizedSol(typeArray(), 3);
		
		// !!!!!!!!!1? TODO, try coding another solution with O(nlogn)
		// System.out.println(MinSubArrayLen.minSubArrayLenSol(typeInt(), typeArray()));
		
		// ROTATION => % operator
		// REVERSE EACH BLOCK
		// RotateArray.rotateArraySolOPtimized(typeInt(), typeArray());
		
		// reverse words => Regular Expression \\s+
		// TODO s.lastIndexOf(String s, int fromIndex) (searching backforword starting at the specified index)- low time complexity
		// ReverseWords1.reverseWords1Sol(typeString());
		
		// The String might contain CONTIGUOUS WHITESPACEs
		// s.indexOf(String s, int fromIndex)
		// treated it as CHAR ARRAY
		// System.out.println(ReverseWords2.reverseWords2Sol2(typeString()));
		
		// Remove (sorted) duplicates - two-pointer technique
		// System.out.println(RemoveDuplicates.removeDuplicatesSol(typeArray()));
		// System.out.println(RemoveDuplicates.removeDuplicatesWithSortedOptimized(typeArray()));
		
		// Remove Zeros
		RemoveZeros.removeZerosSol(typeArray());
	}
	
	public static void printArray(int[] result)
	{
		for(int i : result) {
			System.out.println(i);
		}
	}
	
	// Type array
	public static int[] typeArray() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter an array: ");
		String line = scan.nextLine();
		String[] items = line.split(",");
		int[] nums = new int[items.length];
		for (int i = 0; i < items.length; i++)
		{
			nums[i] = Integer.parseInt(items[i].trim());
		}
		
		return nums;
	}
	
	// Type String
	public static String typeString() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter an String: ");
		String line = scan.nextLine();
		return line;
	}
	
	// Type int
	public static int typeInt() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter an Int: ");
		int input = scan.nextInt();
		return input;
	}
	
}
