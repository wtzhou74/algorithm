package math;

//Calculate the sum of two integers a and b, but you are not allowed to use the operator + and -.
//
//Example 1:
//
//Input: a = 1, b = 2
//Output: 3
//Example 2:
//
//Input: a = -2, b = 3
//Output: 1
public class SumOfTwoIntegers {

	// Sum of TWO integers = Sum WITHOUT considering CARRY + Sum of CARRY
	public int getSum(int a, int b) {
        int sum = a ^ b;// EQUALs the sum of integers WITHOUT considering CARRY; 1^1=0,0^0=0,1^0=1;
        int carry = (a & b & 0x7fffffff) << 1;// EQUALs the sum of integers ONLY considering CARRY; 1&1=1
        // Since this is carry, so we should LEFT SHIFT ONE bit
        // Since MINUS does not support SHIFT Operation, so &0x7fffffff will MASK the HIGHEST digit.
        while (carry != 0) {
        	int temp = sum ^ carry;//Doing SUM WITHOUT CONSIDERING carry
            carry = (sum & carry & 0x7fffffff) << 1;
            sum = temp;
        }
        return sum;
	}
	
	// Concise iterative solution without declaring new variables
	public int iterative(int a, int b) {
		while (b != 0) {
			int carry = (a & b & 0x7fffffff) << 1; //Current Sum of CARRY
			a = a ^ b;
			b = carry;// Starting from next loop, CURRENT SUM a will add Carry
		}
		return a;
	}
	
	// Recursive Solution
	public int recursive(int a, int b) {
		if (b == 0) return a;
		int carry = (a & b & 0x7fffffff) << 1; // Get ALL carries, Since ADDING CARRY, we need to SHIFT LEFT 1 digit
		a = a ^ b;
		// do sum of SumWithoutCarry + SumWithCarry
		return recursive(a, carry);
		
	}
	public static void main(String[] args) {
		new SumOfTwoIntegers().getSum(2, 3); // 10; 11
	}
}
