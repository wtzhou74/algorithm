package arrayString;

public class StrStr {

	public static int strStrSolution(String hayStack, String needle) {
		
		// needle is EMPTY then match any character
		if(needle == null && needle.equals("")) {
			return 0;
		}
		if (hayStack == null) {
			return -1;
		}
		
		int nLength = needle.length();
		int hLength = hayStack.length();
		if (nLength > hLength) {
			return -1;
		}
		
		// DONOT need to iterate the String to the end
		for (int i = 0; i < hLength - nLength; i++) {
			if (hayStack.substring(i, nLength).equals(needle)) {
				return i;
			}
		}
		
		return -1;
	}
}
