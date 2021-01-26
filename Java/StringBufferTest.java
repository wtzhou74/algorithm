
public class StringBufferTest {

	// StringBuilder is fast but is not thread safe
	public static void main(String[] args) {
//		StringBuffer sb = new StringBuffer("abc");
//		sb.indexOf("a");// the parameter is String
//		System.out.println("abc1".indexOf(1));
//		System.out.println("abc".indexOf("abc".charAt(0)));
		
		String str = "abcde";
		StringBuffer sb = new StringBuffer(str);
		//sb.delete(start, end)
		sb.append(1);
		System.out.println(sb.charAt(2));// c
		sb.deleteCharAt(2);
		sb.reverse();
		System.out.println(sb.charAt(2));// d
		System.out.println(sb.replace(2, 3, "z"));//abze
		System.out.println(sb.toString());// abde ==> abze
		sb.setCharAt(0, 'z');
		System.out.println(str); // abcde
	}
}
