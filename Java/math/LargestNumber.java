package math;

import java.util.Arrays;
import java.util.Comparator;

//Given a list of non negative integers, arrange them such that they form the largest number.
//
//Example 1:
//
//Input: [10,2]
//Output: "210"
//Example 2:
//
//Input: [3,30,34,5,9]
//Output: "9534330"
//Note: The result may be very large, so you need to return a string instead of an integer.

public class LargestNumber {

	// Customize COMPARATOR to realize specific comparing purpose
	public String largestNumber(int[] nums) {
        if (nums.length == 0) {
            return "";
        }
        String[] newNums = new String[nums.length];
        // convert all nums to string
        for (int i = 0; i < nums.length; i++) {
            newNums[i] = String.valueOf(nums[i]);
        }
        
        // The problem's essence is to do String comparison !!!!!
        Arrays.sort(newNums, new Comparator<String>() { // for some case, you should code it raw MergeSort algorithm
            public int compare(String a, String b) {
                String s1 = a + b;
                String s2 = b + a;
                
                return s2.compareTo(s1);
            }
        } );
        
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < nums.length; i++) {
            sb.append(newNums[i]);
        }
        
        return sb.charAt(0) == '0' ? "0" : sb.toString();
    }
}
