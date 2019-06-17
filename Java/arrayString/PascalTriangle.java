package arrayString;

import java.util.ArrayList;
import java.util.List;

public class PascalTriangle {

	public static List<List<Integer>> generateTriangle(int numRows) {
        List<List<Integer>> triangle = new ArrayList<List<Integer>>();
        // Empty Triangle
        if (numRows == 0)
        {
            return triangle;
        }
        
        
        for (int i = 0; i < numRows; i++)
        {
        	// handle the FIRST and SECOND layer
            if (i == 0 || i == 1)
            {
            	List<Integer> rows = new ArrayList<Integer>();
            	// add elements
                for (int j = 0; j <= i; j++){
                    rows.add(1);
                }
                triangle.add(rows);
                continue;
            }
            
            List<Integer> rows = new ArrayList<Integer>();
            rows.add(1);//first item
            List<Integer> lastRow = triangle.get(i-1);//last layer
            // the calculating rule of middle value
            //!!!!!!!!!!!!!!!!!!!!!!
            for (int item = 1; item < i; item++){
                int middleValue = lastRow.get(item - 1) + lastRow.get(item);
                rows.add(middleValue);
            }
            rows.add(1);//last item
            
            triangle.add(rows);
        }
        return triangle;
    }
}
