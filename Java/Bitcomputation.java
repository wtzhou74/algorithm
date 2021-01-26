
public class Bitcomputation {

	public static void main(String[] args) {
//		for (int  i = 31; i >= 0; i--) {
//			System.out.println(1 << i);
//		}
		System.out.println(Integer.parseInt("0011", 2));
		System.out.println(Integer.toBinaryString(3));//11
		System.out.println(Integer.toBinaryString(-3));//11111111111111111111111111111101
		System.out.println(3 ^ (-3));// -2; // 位运算的结果自动转回到int
		System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));//1111111111111111111111111111111
		System.out.println(Integer.toBinaryString(Integer.MIN_VALUE));//10000000000000000000000000000000
		System.out.println(Integer.toBinaryString(0));// 0
		System.out.println(Integer.toBinaryString(-1)); // 11111111111111111111111111111111 // reverse of 0X00000001 and then add 1
		System.out.println(Integer.toBinaryString(2)); // 10
		System.out.println(Integer.toBinaryString(3)); // 11
	}
	
	public int countBits(int num) {
        //int aux = num;
        int count = 0;
        while (num > 0) {
            count += num & 1; // 统计最后一个1，如果有的话 ； &1 只能使最低位还是1，其他全为0
            num = num >> 1; // 移位，把 1 往右边移动，直到最低位被统计入 count
        }
        return count;
    }
}
