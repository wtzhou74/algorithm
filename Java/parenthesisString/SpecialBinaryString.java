package parenthesisString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Special binary strings are binary strings with the following two properties:
//
//The number of 0's is equal to the number of 1's.
//Every prefix of the binary string has at least as many 1's as 0's.
//Given a special string S, a move consists of choosing two consecutive, non-empty, special substrings of S, and swapping them. (Two strings are consecutive if the last character of the first string is exactly one index before the first character of the second string.)
//
//At the end of any number of moves, what is the lexicographically largest resulting string possible?
//
//Example 1:
//Input: S = "11011000"
//Output: "11100100"
//Explanation:
//The strings "10" [occuring at S[1]] and "1100" [at S[3]] are swapped.
//This is the lexicographically largest string possible after some number of swaps.
//Note:
//
//S has length at most 50.
//S is guaranteed to be a special binary string as defined above.
public class SpecialBinaryString {

	// ！！！将问题转换成我们熟悉的 ==> "11011000" ==>根据问题描述 => 这本质就是 Valid Parentheses 问题
	//						  ==> "(()(()))  ==> +1 / -1 balanced
	// 要求数越大越好  ==> 1越靠前，即 （ 越靠前  ==> balanced Parentheses范围越大越好
	// ==> 对要求相邻的互换 ==> 将相邻的 balanced的一个个 放 list
	// ====>每一个 balanced的里面还可能包含balanced  ==> balanced里面的balanced可能需要互换 ===> 递归问题！！！
	public String makeLargestSpecial(String S) {
        if (S.length() == 0) {
            return S;
        }
        List<String> list = new ArrayList<>();//局部变量放当前balanced的substring及其相邻的，如果有的话
        int count = 0, nxt = 0;
        for (int i = 0; i < S.length(); i++) {// S是变化的
            count += S.charAt(i) == '1' ? 1 : -1; //找balanced
            if (count == 0) {
            	//这里前后加1-0是因为要递归找里面的所有balanced，并看是否需要交换，而1-0对balanced来说
            	// 第一个跟最后一个肯定是1-0
                list.add("1" + makeLargestSpecial(S.substring(nxt + 1, i)) + "0");//一直递归到最里面，再一层层往回
                nxt = i + 1;//相邻balanced的开始索引； 递归完里面之后开始往外走的时候，这个相邻balanced的点就求作用了
                			//所以这里有相邻的会被放到同一个局部变量list中，到for外面排序，继续往外扩
                // 对没有相邻的 “1100”，当回到这里的时候，nxt索引肯定大于当前 S.length(),跳出循环，局部list也就只有一个元素
            }
        }
        // 对当前的S，找完所有的balanced及相邻的，递归回去对List里面相邻的balanced进行lexicographical互换
        Collections.sort(list, Collections.reverseOrder());
        StringBuffer sb = new StringBuffer();
        for (String s : list) {
            sb.append(s);
        }
        return sb.toString();
    }
	
	public static void main(String[] args) {
		new SpecialBinaryString().makeLargestSpecial("1100");
	}
}
