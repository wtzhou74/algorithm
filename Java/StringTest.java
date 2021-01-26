
public class StringTest {

	public static void main(String[] args) {
		
		String[] sa = new String[] {"a", "b", "c"};
		String join = String.join(" ", sa);
		
		String lzero = "06";
		int zero = Integer.parseInt(lzero); // 6
		
		String equation = "x+6-3x";
		String[] es = equation.split("(?=\\+)|(?=\\-)"); //[x, +6, -3x] 匹配前面的，但不包括？=后面的
		//equation.indexOf(str, fromIndex);
		
		String e1 = "";
		String subs = e1.substring(0, e1.length());
		StringBuffer sb = new StringBuffer(e1);
		String a = "cbacbc";
		String b = "ccbcba";
		a.toCharArray();
		System.out.println(a.compareTo(b) < 0);
		
		a.indexOf('c', 2); //指定从idx=2处开始找第一个出现’c'的地方
		String endStr = "a~^~bcd~^~";
		endStr = endStr.substring(0, endStr.lastIndexOf("~^~"));
		System.out.println();
		
		endStr.lastIndexOf('A');
		endStr.toLowerCase();
		String n1 = null;
		String n2 = null;
		//System.out.println(n1.equals(n2));
		String[] st = "ABCD.EF".split("\\."); // '.' does not work
		String[] st1 = "ABC-DE".split("-");
		System.out.println(st);
		String s1 = "456";
		System.out.println(s1.charAt(2));
		
		System.out.println(s1.indexOf(""));
		
		int i = 1;
		System.out.println(i + 1 + "abc");// 2abc
		//System.out.println(i + 1 + "abc" + i - 1);//WRONG
		System.out.println(i + 1 + "abc" + (i - 1));//2abc0
		
		String log = "dig1 art can";
		System.out.println(log.indexOf(" "));// 4
		
		String s = "abcdef";
		System.out.println(s.substring(0, 0));
		System.out.println(s.substring(0, 1));
		System.out.println(s.substring(6));// subString(len) == ""
		System.out.println(s.substring(7));// >length ==> IndexOutOfRange
		
		String s0 = "";
		System.out.println(s0 + 'a');
		System.out.println(s0 + 'b');
		System.out.println(s0 + 'c');
		System.out.println(s0);
		
		System.out.println( 123 > 10);
		
		System.out.println(Integer.parseInt("001"));
		

		
		String ss = "A man, a plan, a canal: Panama";
		ss=ss.replaceAll("[^0-9A-Za-z]", "");
		System.out.println(ss.toLowerCase());
		
		System.out.println('a' ^ 'A');
		
		String sns = "null";
		System.out.println(sns == "null"); //true, BUT using equals instead
		sns = sns.toUpperCase();
		sns.toCharArray();
		System.out.println(sns);
		
		String sts = "test";
		String ns = null;
		System.out.println(ns.isEmpty());// NullPointerException
		
	}
}
