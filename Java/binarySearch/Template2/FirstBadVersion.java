package binarySearch.Template2;

import java.util.ArrayList;
import java.util.List;

public class FirstBadVersion {

	public int firstBadVersionSol (int n) {
		
		if (n == 0) return -1;
        int start = 1, end = n;
        
        while (start <= end) 
        {
            int mid = (start + end) >>> 1;
//            if (isBadVersion(mid)) end = mid - 1;
//            else start = mid + 1;
        }
        
        return start;// end condition:  START == END (The target is existed)
		  //return findHelper(1, n);
	}
	
	// recursively
//	public int findHelper(int start, int end) {
//        if (start >= end) return start;  // STACKOVERFLOW if set the end condition with START == END
//        int mid = (start + end) >>> 1;
////        if (isBadVersion(mid)) return findHelper(start, mid - 1);
////        else return findHelper(mid + 1, end);
//    }
}
