package binarySearch.Template1;

/* The guess API is defined in the parent class GuessGame.
@param num, your guess
@return -1 if my number is lower, 1 if my number is higher, otherwise return 0
   int guess(int num); */
public class GuessNumber {

	public int guessNumber(int n) {
        int left = 1, right = n, result = 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
//            if (guess(mid) == -1) {
//                right = mid - 1;// MY number is lower than what you guess
//            } else if (guess(mid) == 1) {
//                left = mid + 1;
//            } else
//            {result = mid; break;}
        }
        
        return result;
    }
}
