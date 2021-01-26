package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Given a non-negative index k where k ≤ 33, return the kth index row of the Pascal's triangle.
//
//Note that the row index starts from 0.
//
//
//In Pascal's triangle, each number is the sum of the two numbers directly above it.
//
//Example:
//
//Input: 3
//Output: [1,3,3,1]
//Follow up:
//
//Could you optimize your algorithm to use only O(k) extra space?  =>  RESET VALUE IN PLACE
public class PascalTriangleII {

	// TLE!!!
	public List<Integer> getRow(int rowIndex) {
		List<Integer> res = new ArrayList<Integer>();
		// get values for the specific row
        for (int j = 0; j <= rowIndex; j++ ) {
                res.add(helper(rowIndex, j));
        }        
        return res;
	}
	
	public int helper(int rowIndex, int colIndex) {
        if (rowIndex == 0 || colIndex == 0 || rowIndex == colIndex) {
            return 1;
        }
        return helper(rowIndex - 1, colIndex - 1) + helper(rowIndex - 1, colIndex);
    }
	
	public List<Integer> getRow2(int rowIndex) {
		int k = rowIndex;
	    if (k == 0) return Arrays.asList(1);
	    else if (k == 1) return Arrays.asList(1, 1);
	    else if (k == 2) return Arrays.asList(1, 2, 1);
	    List<Integer> result = new ArrayList<>();
	    result.add(2);
	    k = k - 2;
	    int p, c;
	    while (k-- > 0) {
	      p = 1;
	      int i = 0;
	      for (int l = result.size(); i < l; i++) {
	        c = result.get(i);
	        result.set(i, p + c);
	        p = c;
	      }
	      result.add(p + 1);
	    }
	    result.add(0, 1);
	    result.add(1);
	    return result;
	}
	
	public static List<Integer> getRow3(int rowIndex) {
		List<Integer> res = new ArrayList<Integer>();
        // for (int j = 0; j <= rowIndex; j++ ) {
        //         res.add(helper(rowIndex, j));
        // }
        int i = 0;
        while (i <= rowIndex) {
            if (i == 0) {res.add(1); i++; continue;}
            if (i == 1) {res.set(i - 1, 1); res.add(1); i++; continue;}
            int temp = 0;
            int p = 1;
            for (int j = 0; j < res.size() - 1; j++) {
            	temp = res.get(j + 1);
                res.set(j + 1, p + temp);
                // remember to RECOVER previous value
                p = temp;
            }
            res.add(1);
            i++;
        }
        return res;
	}
	
	public static void main(String[] args) {
		getRow3(3);
	}
} 
