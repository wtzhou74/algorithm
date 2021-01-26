package arrayString;

//Write a function that given an integer N, returns the maximum possible value obtained by inserting one '5' digit inside the decimal representation of integer N.
//
//Example
//Example1:
//Input: N = 268
//Output: 5268
//Example2:
//Input: N = 670
//Output: 6750
//Example3:
//Input: N = 0
//Output: 50
//Example4:
//Input: N = -999
//Output: -5999
//Notice
//N is an integer within the range [-8000, 8000]
public class MaximumPossibleValue {

	public int maximumPossibleValue(int N) {
        // write your code here
        if (N == 0) {
            return 50;
        }
        String s = String.valueOf(N);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        // 边界值是否能等于 5
        if (N > 0) {
            while (i < s.length() && s.charAt(i) - '5' >= 0) {
                sb.append(s.charAt(i));
                i++;
            }
        } else {
            i = 1;
            sb.append("-");
            while (i < s.length() && s.charAt(i) - '5' <= 0) {
                sb.append(s.charAt(i));
                i++;
            }
        }
        sb.append(5);
        while (i < s.length()) {
            sb.append(s.charAt(i));
            i++;
        }
        return Integer.parseInt(sb.toString());
    }
	
	public static void main(String[] args) {
		System.out.println(new MaximumPossibleValue().maximumPossibleValue(9564));
	}
}
