package arrayString;

import java.util.ArrayList;
import java.util.List;

public class ActiveAndInActiveCellsAfterKdays {
//
//	Given a binary array of size n where n > 3. A true (or 1) value in the array means active and false (or 0) means inactive.
//	Given a number k, the task is to find count of active and inactive cells after k days. After every day, status of i’th cell becomes active if left and right cells are not same and inactive if left and right cell are same (both 0 or both 1).
//	Since there are no cells before leftmost and after rightmost cells, the value cells before leftmost and after rightmost cells 
//	is always considered as 0 (or inactive).
	public List<Integer> cellCompete(int[] states, int days)
    {
    // WRITE YOUR CODE HERE
        List<Integer> res = new ArrayList<>();
        if (states.length == 1){
            res.add(states[0]);
            return res;
        }
        
        // At same day, we cannot use new value to update   ==> temp array, then we can still get original value from states
        int[] temp = new int[states.length];
        for (int i = 0; i < states.length; i++) {
            temp[i] = states[i];
        }
        while (states.length >= 2 && days-- > 0) {
            // check the left/right-most cells
            temp[0] = 0 ^ states[1]; // inactive if two states are same; active if two states are different
            temp[states.length - 1] = 0 ^ states[states.length - 2];
            
            for (int i = 1; i <= states.length - 2; i++) {
                temp[i] = states[i - 1] ^ states[i + 1];
            }
            
            // copy temp back to states array 
            for (int i = 0; i < states.length; i++) {
                states[i] = temp[i];
            }
        }
        for (int i = 0; i < states.length; i++) {
        	res.add(states[i]);
        }
        return res;
    }
	
	public static void main(String[] args) {
		int[] states = new int[] {0, 1, 0, 1, 0, 1, 0, 1};
		new ActiveAndInActiveCellsAfterKdays().cellCompete(states, 3);
	}
}
