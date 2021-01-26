package bitOperation;

public class MissingNumber {
	public int missingNumber(int[] nums) {
        // int temp = 0;
        // for (int i = 0; i < nums.length; i++) {
        //     temp += nums[i];
        // }
        // int total = ((1 + nums.length) * nums.length ) / 2;
        // return total - temp;
		
		
		// (0^0) ^ (1^1) ^ 2 ^ (3 ^ 3)
		// a^b = b^a
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
        	// With the help of existing array (Indices), rather than creating a new one
            res = res ^ i ^ nums[i];
        }
        return res ^ nums.length;
    }
}
