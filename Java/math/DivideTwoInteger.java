package math;

/**
 * 
 * Given two integers dividend and divisor, divide two integers without using multiplication, division and mod operator.

Return the quotient after dividing dividend by divisor.

The integer division should truncate toward zero.

Example 1:

Input: dividend = 10, divisor = 3
Output: 3
Example 2:

Input: dividend = 7, divisor = -3
Output: -2
Note:

Both dividend and divisor will be 32-bit signed integers.
The divisor will never be 0.
Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. For the purpose of this problem, assume that your function returns 231 − 1 when the division result overflows.*/

public class DivideTwoInteger {
	//BINARY SEARCH ==> SIMILIAR TO SqrtX.java
	public int divide(int dividend, int divisor) {
        if (dividend == 0) return 0;
        if (divisor == 1) return dividend;
        if (divisor == -1) {
            if (dividend == Integer.MIN_VALUE) return Integer.MAX_VALUE;
            return -dividend;
        }
        // int dividendTemp = Math.abs(dividend); // Math.abs(Integer.MIN_VALUE) == Integer.MIN_VALUE
        // int divisorTemp = Math.abs(divisor); // OR using Math.abs(long(Integer.MIN_VALUE)); namely Convert it to LONG
        int i = 1, j = Math.abs(dividend / 2); // Division is only affected by SIGN
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (mid > Math.abs(dividend / divisor)) j = mid - 1;
            else i = mid + 1;
        }
        boolean isNegative = false;
        if ((dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0)) isNegative = true;
        return isNegative ? -(i - 1) : i - 1;
    }
}
