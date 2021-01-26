package arrayString;

public class GasStation {

	// 1） a转一圈总的total剩下的oil 必须>=0才能找到要求的点， 这里转一圈是指不管从那个点开始
	// 2） 找开始点  ==> 走一圈，看哪个点合适   ===> 合适的开始点， 对之后每个点， 他的剩余oil 肯定大于等于当前需要的
	// a [0, k, s]验证k能到达S 当total >=0 时候（反证法）的过程： https://leetcode.com/problems/gas-station/solution/
	public int optimizedSol(int[] gas, int[] cost) {
        if (gas.length == 0) {
            return -1;
        }
        int total = 0;
        int curr = 0;
        int starter = 0;
        for (int i = 0; i < gas.length; i++) {
            total += (gas[i] - cost[i]);
            curr += (gas[i] - cost[i]);
            if (curr < 0) {
                curr = 0;
                starter = i + 1;
                continue;
            }
        }
        return total >= 0 ? starter : -1;
    }
	
	 public int canCompleteCircuit(int[] gas, int[] cost) {
	        if (gas.length == 0) {
	            return -1;
	        }
	        for (int i = 0; i < gas.length; i++) {
	            int oil = 0;
	            int count = 0;
	            int j = i;
	            while (count < gas.length) {
	                int idx = j % gas.length;
	                if (oil + gas[idx] >= cost[idx]) {
	                    oil = oil + gas[idx] - cost[idx];
	                    j++;
	                    count++;
	                } else 
	                    break;
	            }
	            if (count == gas.length)
	                return i;
	            
	        }
	        return -1;
	    }
}
