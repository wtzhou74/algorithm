package math;

public class PowerOfThree {

	public boolean isPowerOfThree(int n) {
//        if (n == 1) return true; // 0 power of three
//        if (n < 3) return false;
//        while (n >= 3) {
//            if (n % 3 == 0) {
//                n = n / 3;
//            } else {
//                return false;
//            }
//        }
//        return n == 1;
		
		// recursive solution
		if (n == 0) return false;
		if (n == 1) return true;
		return n % 3 == 0 && isPowerOfThree(n / 3);
		
    }
	 public static void main(String[] args) {
		 System.out.println(new PowerOfThree().isPowerOfThree(45));
	 }
}
