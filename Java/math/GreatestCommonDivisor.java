package math;

public class GreatestCommonDivisor {

	public int generalizedGCD(int num, int[] arr)
    {
        // WRITE YOUR CODE HERE
        int first = arr[0];
        for (int i = 1; i < num; i++) {
            int cd = gcd(first, arr[i]);
            first = cd;
        }
        return first;
    }
    
	
    public int gcd(int a, int b) {
//        if (a < b) { // 保证大的数字在前面
//            int temp = a;
//            a = b;
//            b = temp;
//        }
        // get remainder till it is 0
        while (a % b != 0) {
        	// switch b and a % b
            int temp = a % b;
            a = b;
            b = temp;
        }
        return b;
    }
    
    // recursive
    public int recursiveSol(int a, int b) 
    { 
      if (b == 0) 
        return a; 
      return recursiveSol(b, a % b);  
    } 
    
    public static void main(String[] args) {
    	GreatestCommonDivisor gcd = new GreatestCommonDivisor();
    	System.out.println(gcd.gcd(5, 10));
    	System.out.println(gcd.recursiveSol(5, 10));
    }
}
