package math;

public class PowerOfFour {

	public boolean isPowerOfFour(int num) {
        if (num == 1) return true;
        if (num == 0) return false;
        //return num % 4 == 0 && isPowerOfFour(num / 4);
        
        while (num >= 4) {
            if (num % 4 != 0) {
                return false;
            } else {
                num = num / 4;
            }
        }
        return num == 1;
    }
	
	public static void main(String[] args) {
		System.out.println(new PowerOfFour().isPowerOfFour(16));
	}
}
