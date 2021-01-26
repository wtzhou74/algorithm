package recursion;

import java.util.List;

//On the first row, we write a 0. Now in every subsequent row, we look at the previous row and replace each occurrence 
//of 0 with 01, and each occurrence of 1 with 10.
//
//Given row N and index K, return the K-th indexed symbol in row N. (The values of K are 1-indexed.) (1 indexed).
//
//Examples:
//Input: N = 1, K = 1
//Output: 0
//
//Input: N = 2, K = 1
//Output: 0
//
//Input: N = 2, K = 2
//Output: 1
//
//Input: N = 4, K = 5
//Output: 1
//
//Explanation:
//row 1: 0
//row 2: 01
//row 3: 0110
//row 4: 01101001
//Note:
//
//N will be an integer in the range [1, 30].
//K will be an integer in the range [1, 2^(N-1)].

//					0
//	  	  /      		   \
//	     0               	1	
//	  /       \         /     	 \
//   0          1        1          0
// /    \      / \     /  \      /    \
//0    1      1    0    1    0    0     1 


public class KthSymbolInGrammar {

	public int kthGrammar(int N, int K) {
		String s = "0";

        StringBuffer temp = new StringBuffer();
        for (int i = 1; i < N; i++) {
        	temp.setLength(0);
            char[] c = s.toCharArray();
            for (int j = 0; j < c.length; j++) {
                if (c[j] == '0') {
                    temp.append("01");
                } else {
                    temp.append("10");
                }
            }
            s = temp.toString();
        }
        Character result = s.charAt(K - 1);
        
        return Character.getNumericValue(result);
	}
	
	public int iterative_binaryTree(int N, int K) {
		if (N == 0) return 0;
		// if K is even, then the Kth value is the right child, whose father's value is different from its
		// if K is odd, then the Kth value is the left child, whose father's value is the same as its.
		if (K % 2 == 0) {
			// last/father's layer
			return iterative_binaryTree(N - 1, K/2) == 0 ? 1 : 0; // K/2 is the position of K located in parent's row
		} else {
			// RECURRENCE RELATION -> HOW TO GET ITS PARENT's VALUE
			return iterative_binaryTree(N - 1, (K + 1) / 2 == 0 ? 0 : 1);
		}
		
//		if (K % 2 == 0) {
//            int parent = kthGrammar(N - 1, K / 2);
//            return parent == 0 ? 1 : 0;
//        } else {
//            int parent = kthGrammar(N - 1, (K + 1) / 2);
//            return parent == 0 ? 0 : 1;
//        }
	}
	
	// TLE
	public int kthGrammar2(int N, int K) {
        String aux = "0";
        for (int i = 2; i <= N; i++) {
            String temp = "";
            for (int j = 0; j < aux.length(); j++) {
                 if (aux.charAt(j) == '0')
                     temp += "01";
                else
                    temp += "10";
            }
            aux = temp;
        }
        return Integer.valueOf(aux.charAt(K - 1) + "");
        
    }
	// 递归到N=1，再返回， 但 TLE
	public int kthGrammarBottomup(int N, int K) {
        String value = nthRowValue(N);
        //System.out.println(value);
        return Integer.valueOf(value.substring(K - 1, K));
    }
    
    public String nthRowValue(int N) {
        String str = "";
        if (N == 1)
            return "0";
        String val = nthRowValue(N - 1);
        for (char c : val.toCharArray()) {
            if (c == '0')
                str += "01";
            else
                str += "10";
        }
        return str;
    }
	
	
	public static void main(String[] args) {
		int res = new KthSymbolInGrammar().iterative_binaryTree(4, 5);
		System.out.println(res);
	}
}
