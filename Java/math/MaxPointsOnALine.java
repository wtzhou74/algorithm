package math;

import java.util.HashMap;
import java.util.Map;

public class MaxPointsOnALine {

	// Better option ==> Point(x, y) class
	// ===> Refer to: https://leetcode.com/explore/interview/card/top-interview-questions-hard/123/math/873/discuss/471499/Java-solution-easy-to-understand-using-Pair-class
	
	// Note: 考虑全面
	// 1) same/repeated points; 2) 除数为0； 3） 计算总的点的个数
	public int maxPoints(int[][] points) {
        if (points.length == 0 || points == null) return 0;
        Map<Double, Integer> slopMap = new HashMap<>();
        int max = 1;
        for (int i = 0; i < points.length; i++) {
        	int sameCount = 0;// calculate same points [1,1], [1,1]; it allows
            for (int j = 0; j < points.length; j++) {
                if (i == j) {
                    continue;
                }
                if (points[i][0] == points[j][0]
                        && points[i][1] == points[j][1]) {
                         sameCount++;
                         continue;
                }
                //double slop = (points[j][0] - points[i][0]) / (double)(points[j][1] - points[i][1]);
                double slop = (points[j][1] - points[i][1]) / (double)(points[j][0] - points[i][0]); // it is not always correct !!!!!!!
                if (slopMap.containsKey(slop))
                    slopMap.put(slop, slopMap.get(slop) + 1);
                else {
                    slopMap.put(slop, 2);// by default, it is 2 since ANY TWO points can be on the same line
                }
            }
            int curMax = 1;
            for (Map.Entry<Double, Integer> entry : slopMap.entrySet()) {
                curMax = Math.max(curMax, entry.getValue());
            }
            max = Math.max(max, curMax + sameCount);
            slopMap.clear();
        }
        
        return max;
    }
	
	///////////////////////////////////////////////////////////////////
	// User-defined class and override equals function
	class Point {
        private int x;
        private int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof Point)) return false;
            Point p = (Point)o;
            return x == p.x && y == p.y;
        }
        @Override
        public int hashCode() {
            return x ^ y;
        }
    }
    public int maxPoints2(int[][] points) {
        if (points.length == 0 || points == null) return 0;
        //Map<Point, Integer> slopMap = new HashMap<>();
        int max = 1;
        for (int i = 0; i < points.length; i++) {
            int sameCount = 0;
            int localMax = 0;
            Map<Point, Integer> slopMap = new HashMap<>();
            for (int j = 0; j < points.length; j++) {
                if (i == j) {
                    continue;
                }
                if (points[i][0] == points[j][0]
                   && points[i][1] == points[j][1]) {
                    sameCount++;
                    continue;
                }
                // double slop = (points[j][1] - points[i][1]) / (double)(points[j][0] - points[i][0]); // for same case, the slop could be same even though there are not on the same line.
                int diffX = points[j][0] - points[i][0];
                int diffY = points[j][1] - points[i][1];
                
                int gcd = gcd(diffX, diffY); // based on the slop formula, all nodes on the same line will have same GCD
                int p = diffX/gcd;
                int q = diffY/gcd;
                
                Point point = new Point(p, q);
                slopMap.put(point, slopMap.getOrDefault(point, 0) + 1);
                localMax = Math.max(localMax, slopMap.get(point));
            }
            // int curMax = 1;
            // for (Map.Entry<Double, Integer> entry : slopMap.entrySet()) {
            //     curMax = Math.max(curMax, entry.getValue());
            // }
            max = Math.max(max, localMax + sameCount + 1);// at least 2 points
            //slopMap.clear();// check the map position
        }
        
        return max;
    }
    
    public int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
	
	public static void main(String[] args) {
		int[][] points = new int[][] {
//			{0, 0},
//			{94911151, 94911150},
//			{94911152, 94911151}
			{1, 1},
			{2, 2},
			{3, 3}
		};
		
		new MaxPointsOnALine().maxPoints2(points);
	}
}
