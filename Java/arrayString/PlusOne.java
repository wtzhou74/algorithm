package arrayString;

import java.util.ArrayList;
import java.util.List;

public class PlusOne {

	//Given a non-empty array of digits representing a non-negative integer, plus one to the integer.
	//The digits are stored such that the most significant digit is at the head of the list, 
	// and each element in the array contain a single digit.
	//You may assume the integer does not contain any leading zero, except the number 0 itself.
	//
	public static int[] plusOneSolution(int[] digits) {
		int leng = digits.length;
        
		// back forward the array
        for (int i = leng - 1; i >= 0; i--)
        {
        	// dynamic "LAST" digit
            if (digits[i] < 9){
                digits[i] = digits[i] + 1;
                return digits;// do not need to traverse ahead items since it is only added 1
            }
            // set the current digit as 0 since 9+1 = 10
            // And then the "LAST" digit will be added one if it is less than 9
            digits[i] = 0;
        }
        
        // special case: 999*
        int[] newDigits = new int[leng + 1];
        newDigits[0] = 1;
        for (int i = 1; i < leng + 1; i++)
        {
            newDigits[i] = 0;
        }
        
        return newDigits;
        
        //return arr;
    }
}
