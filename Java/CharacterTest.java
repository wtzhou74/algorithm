
public class CharacterTest {

	public static void main(String[] args) {
		
		char defaultC = 0; //默认是0，但不是‘’
		System.out.println(defaultC);
		char inte = '4';
		System.out.println(inte - '0');
		
		char c = 'a';
		Character.toUpperCase('a');
		Character.toLowerCase('A');
		
		int[] aux = new int[256];
		aux[c] = 1; // aux[c] = 1 means the value of index = 96 is 1 since (int)a = 97
		
		System.out.println((int)c);//97
		System.out.println(String.valueOf((char)(c + 1)));
		
		System.out.println('A' - 'A' + 1);
		
		System.out.println("abc" == "abc");//true
		String a = "abc";
		String b = a;
		System.out.println(a == b);//true
		System.out.println('a' == 'a');//true
	}
}
