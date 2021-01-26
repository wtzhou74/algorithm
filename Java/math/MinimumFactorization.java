package math;

//Given a positive integer a, find the smallest positive integer b whose multiplication of each digit equals to a.
//
//If there is no answer or the answer is not fit in 32-bit signed integer, then return 0.
//
//Example 1
//Input:
//
//48 
//Output:
//68
//Example 2
//Input:
//
//15
//Output:
//35
public class MinimumFactorization {

	// 根据题意，要找的数各位数是 a的 factor(因子) ===> 问题就成为了 找factors !!!!!
	// 另外要求数最小，那么从最低位开始，并从9开始，先找大的因子，这样能找到的数才会越小（因为高位值小）
	public int smallestFactorization(int a) {
        if (a < 2) {
            return a;
        }
        long res = 0, temp = 1; //res值设置为 long， 因为再求结果的时候值可能会大与Integer.max
        for (int i = 9; i >= 2; i--) {// 对乘法 1没意义，所以1不管 
        					//（这里最低位补1，如果后面找到符合的数，那这个值肯定大，因为多了1位了，比如 68, 681）
            while (a % i == 0) {
                a = a / i;//剩余值，去高位
                res = temp * i + res;// 得出当前值
                temp *= 10; // temp每往前1位，*10
            }
        }
        
        // 对大于Integer.MAX, 按照题意不符合
        return a < 2 && res <= Integer.MAX_VALUE ? (int)res : 0;
    }
}
