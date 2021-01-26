package recursion;

import java.util.ArrayList;
import java.util.List;

/*Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.


In Pascal's triangle, each number is the sum of the two numbers directly above it.

Example:

Input: 5
Output:
[
     [1],
    [1,1],
   [1,2,1],
  [1,3,3,1],
 [1,4,6,4,1]
]
*/

public class PascalTriangle {

	public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<List<Integer>>();
        if (numRows == 0)
        {
            return triangle;
        }
        
        for (int i = 0; i < numRows; i++)
        {
            if (i == 0 || i == 1)
            {
            	List<Integer> rows = new ArrayList<Integer>();
                for (int j = 0; j <= i; j++){
                    rows.add(1);
                }
                triangle.add(rows);
                continue;
            }
            
            List<Integer> rows = new ArrayList<Integer>();
            rows.add(1);//first item
            List<Integer> lastRow = triangle.get(i-1);//last layer
            for (int item = 1; item < i; item++){
                int middleValue = lastRow.get(item - 1) + lastRow.get(item);
                rows.add(middleValue);
            }
            rows.add(1);//last item
            
            triangle.add(rows);
        }
        return triangle;
    }
	
	// TLE!!!
	public List<List<Integer>> iterativeSol(int numRows) {
		List<List<Integer>> triangle = new ArrayList<List<Integer>>();
		if (numRows == 0) return triangle;
		for (int i = 1; i <= numRows; i++) {
			List<Integer> row = new ArrayList<>();
			for (int j = 1; j <= numRows; j++) {
				// get the each cell value using iterative solution
				row.add(helper(i, j));
			}
			triangle.add(row);
		}
		return triangle;
	}
	
	public int helper(int rowNum, int colNum) {
		if (rowNum == 1) return 1;
		if (colNum == 1 || colNum == rowNum) return 1;
		return helper (rowNum - 1, colNum - 1) + helper(rowNum - 1, colNum);
	}
}
