package math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Shuffle a set of numbers without duplicates.
//
//Example:
//
//// Init an array with set 1, 2, and 3.
//int[] nums = {1,2,3};
//Solution solution = new Solution(nums);
//
//// Shuffle the array [1,2,3] and return its result. Any permutation of [1,2,3] must equally likely to be returned.
//solution.shuffle();
//
//// Resets the array back to its original configuration [1,2,3].
//solution.reset();
//
//// Returns the random shuffling of array [1,2,3].
//solution.shuffle();

public class ShuffleAnArray {

//	private int[] array;
//	private int[] original;
//	
//	public ShuffleAnArray(int[] nums) {
//		this.array = nums;
//		this.original = nums.clone();
//	}
//	
//	public int[] reset() {
//		array = original.clone();
//		return original;
//	}
//	
//	public int[] shuffle() {
//		//int[] temp = new int[array.length];
//        List<Integer> aux = new ArrayList<>();
//        int j = 0;
//        while(j < array.length) aux.add(array[j++]);
//        Random rand = new Random();
//        for (int i = 0; i < array.length; i++) {
//            int randInd = rand.nextInt(aux.size());
//            array[i] = aux.get(randInd);
//            aux.remove(randInd);
//        }
//        return array;
//	}
	
	int[] nums;
	Random random;
	public ShuffleAnArray(int[] nums) {
		this.nums = nums;
		this.random = new Random();
	}
	
	public int[] reset() {
		return nums;
	}
	
//	public int[] shuffle() {
//		int[] aux = new int[nums.length];
//        for (int j = 0; j < nums.length; j++) {aux[j] = nums[j];}
//		for (int i = 0; i < nums.length; i++) {
//			int rand = random.nextInt(nums.length);
//			// in case duplicated / get the same value
//			int temp = nums[i];
//			nums[i] = nums[rand];
//			nums[rand] = temp;
//		}
//		return nums;
//	}
	
	public int[] shuffle() {
        int[] aux = nums.clone();
        for (int i = 0; i < nums.length; i++) {
            int j = random.nextInt(i + 1);//  (1-1/(1+i)) * (1/i) = 1/(1+i). 
            					//任何（0， i）的值都有同样的概率放到任何位置上
            if (i != j) {
                int temp = aux[i];
                aux[i] = aux[j];
                aux[j] = temp;
            }
            // i == j 即还在原来位置上，概率还是 1/ (1 + i) ,因为是在 （0， i）中选一个位置
        }
        return aux;
    }
	
	public static void main(String[] args) {
		ShuffleAnArray s = new ShuffleAnArray(new int[] {1, 2, 3,4,5});
		s.shuffle();
	}
}
