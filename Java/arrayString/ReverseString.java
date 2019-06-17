package arrayString;

/**
 * Write a function that takes a string as input and returns the string reversed.
 * */
public class ReverseString {

	public static String reverseStringSolution(String s) {
		
		if(s.length() == 0) {
            return "";
        }
        int i = 0;
        int j = s.length() - 1;
        char[] strArray = s.toCharArray();
        
        // One pointer starts from the beginning while the other pointer starts from the end
        while (i < j) {
            char temp = strArray[i];
            strArray[i] = strArray[j];
            strArray[j] = temp;
            i++;
            j--;
        }
        return String.valueOf(strArray);
	}
}
