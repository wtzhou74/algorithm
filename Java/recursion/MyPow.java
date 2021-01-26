package recursion;

public class MyPow {

	// TLE
	public double power(double x, int n) {
		int counter = 1;
        double result = 1d;
        int temp = n < 0 ? -n : n;
        while (counter <= temp) {
            result *= x;
            counter++;
        }
        return x < 0 ? 1 / result : result;
	}
	
	public double iterative(double x, int n) {
		if (n == 0) return 1;
		long N = n; // because n is [−2**31, 2**31 − 1], n can equal −2**31, it will overthrows once -n
		return n < 0 ? helper(1/x, -N) : helper(x, N);
	}
	public double helper(double x, long n) {
		if (n == 1) return x;
		double res = helper(x, n / 2);
		return res * res * ((x % 2) == 0 ? 1 : x);
	}
}
