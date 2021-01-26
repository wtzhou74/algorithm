package math;

public class PowerOfTwo {

	public boolean isPowerOfTwo(int n) {
        if (n == 1) return true;
        if (n == 0) return false;
        //return n % 2 == 0 && isPowerOfTwo(n / 2);
        
        return n > 0 && (n & (n - 1)) == 0; // The highest digit is 1
        
        // TLE
//        int i = 1;
//        while (i < n) {
//            i = i * 2;
//        }
//        return i == n;
    }
	
	public static void main(String[] args ) {
		System.out.println(new PowerOfTwo().isPowerOfTwo(16));
	}
}
