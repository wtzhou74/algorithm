package arrayString;

public class ReverseWords2 {

	public static String reverseWords2Sol(String s) {
		if (s.length() == 0) {
			return "";
		}
		String[] strs = s.split(" ");
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].equals("")) {
				strBuilder.append(" ");
				continue;
			}
			strBuilder.append(reverseWord(strs[i]));
			if (i != strs.length - 1) {
				strBuilder.append(" ");
			}
			
		}
		return strBuilder.toString();
	}
	
	public static String reverseWords2Sol2(String s) {
		StringBuilder strBuilder = new StringBuilder();
		int i = 0;
		while (i < s.length()) {
			int index = s.indexOf(" ", i);
			if (i == index) {
				strBuilder.append(" ");
				i++;
			} else {
				if (index == -1) {
					strBuilder.append(reverseWord(s.substring(i, s.length())));
					break;
				}
				strBuilder.append(reverseWord(s.substring(i, index)));
				i = index;
				
			}
		}
		return strBuilder.toString();
	}
	
	public static String reverseWord (String s) {
		StringBuilder strBuilder = new StringBuilder();
		for (int i = s.length() - 1; i >= 0; i--) {
			strBuilder.append(s.charAt(i));
		}
		return strBuilder.toString();
	}
	
	
}
