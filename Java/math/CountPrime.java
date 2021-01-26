package math;

public class CountPrime {

	public int countPrimes(int n) {
		int count = 0;
		for(int i = 1; i< n; i++) {
			if (isPrime(i)) {
				count++;
				System.out.println(i);
			}
		}
		return count;
	}
	
	public boolean isPrime(int num) {
        if (num == 1) return false;
//        int i = 2;
        // a * a = num  => check 2 ~ sqrt(num), rather than 2 ~ n - 1
//        while (i <= Math.sqrt(num)) { // i * i <= num since sqrt() is expensive
//            if (num % i == 0) return false;
//            i++;
//        }
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
         }
        return true;
    }
	
	// mark off non-prime
	// reference: https://leetcode.com/explore/interview/card/top-interview-questions-easy/102/math/744/
	public int sol3(int n) {
		if (n == 1) return 0;
        boolean[] aux = new boolean[n];
        for (int i = 2; i * i < n; i++) {
            for (int j = i; i * j < n; j++) {
                aux[i * j] = true;
            }
        }
        int count = 0;
        for (int i = 2; i < n; i++) {
            if (!aux[i]) count++;
        }
        return count;
	}
	
	//public boolean 
	
	public static void main(String[] args) {
		new CountPrime().countPrimes(10);
	}
}
