package math;

public class SqrtX {

	public int mySqrt(int x) {
        if (x < 2) return x;
        int left = 1, right = x / 2; // the sqrt(x) cannot be larger than x / 2 + 1
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (mid <= x / mid) left = mid + 1; // WRONG if using mid * mid <= x here !!!!!
            else right = mid - 1;
        }
        return left - 1; // Since mid * mid == x ==> left = mid + 1;
		
        
    }
}
