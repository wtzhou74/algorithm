
public class IntegerTest {

	public static void main(String[] args) {
		String i2s = String.valueOf(-50); // "-50"
		
		int mod = (int)Math.pow(10, 9) + 7; // 10^9 + 7
		
		System.out.println(11 / 10);
		System.out.println(12 % 10);
		System.out.println(10 % 10);
		System.out.println(1 / 10);//0
		System.out.println(1 % 10);//1
		System.out.println(-1 % 10);//-1
		
		System.out.println((char)('a' + 3)); // 'd'
		// For Integer
		// == performs REFERENCE identity comparison
		Integer t1 = -129;
		Integer t2 = -129;
		
		// t1 == t2 ==> False
		// Integer.compare(t1, t2) == 0  // > 0 if t1 > t2;  < 0 if t1 < t2
		// t1.compareTo(t2);  == 0 or > 0 or < 0
		Integer t = 10;
		System.out.println(t == 10);
		int a = 12;
		System.out.println(Integer.bitCount(a));
		
		System.out.println(Integer.MIN_VALUE);//-2147483648
		System.out.println(Integer.MAX_VALUE);//2147483647
		System.out.println(1 / 10);//0
		System.out.println(1 % 10);//1
		System.out.println(-1 % 10);//-1
		System.out.println(Math.abs(Integer.MIN_VALUE));
		System.out.println(Math.abs((long)(Integer.MIN_VALUE)));
		System.out.println(-(Integer.MIN_VALUE));
		System.out.println(Math.abs(Integer.MAX_VALUE));
		System.out.println(Math.abs(-1000));
		System.out.println();
		
		Integer.parseInt("9");
		
		System.out.println((char)(9 + '0'));
		
		System.out.println(Character.getNumericValue('5'));
	}
}
