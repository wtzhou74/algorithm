import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArrayTest {

	public static void main(String[] args) {
		
		int[] a1 = new int[] {1, 2, 3};
		Arrays.toString(a1);
		
		Arrays.copyOfRange(a1, 0, 2); // to is exclusive
		//List<Integer> a1List = Arrays.asList(a1);// 错误； List的元素是 INTEGER， 而asList的结果元素是 int[];
		List<int[]> a1List = Arrays.asList(a1); // 结果是 list有一个元素，即 a1
		
		Integer[] a2 = new Integer[] {Integer.valueOf(1), Integer.valueOf(2)};
		List<Integer> a2List = Arrays.asList(a2);// [1, 2] //可以，所以说 Array元素必须是 Object， primary type不行
		
		String[] a3 = new String[] {"abc"};
		List<String> a3List = Arrays.asList(a3); // ["abc"] 可以，数组元素是String对象
		
		List<Integer> l1 = Arrays.asList(1, 2);
		//int[] l1Array = l1.toArray(new int[2]);// 不可以， list的元素都是Object, 而且也只能是 Object
		int[] l1Array = l1.stream().mapToInt(i -> i).toArray();// [1,2]; 这个可以，把list对象的元素一个个mapToInt再转
		
		List<String> l2 = Arrays.asList("ABC", "bcd");
		String[] l2Array = l2.toArray(new String[1]); //["ABC", "bcd"]数组不够长，可以自增
		
		///////
		
		Set<Integer> set = new HashSet<>();
		new ArrayList<>(set); // 用set来初始化一个 list
		
		int[][] ts = new int[][] {{1,2}, {}};
		for (int i = 0; i < ts[1].length; i++) {
			System.out.println(ts[1][i]);
		}
		
		
		String[] s = new String[] {"A", "B"};
		//new ArrayList<>(s); //不能用数组来初始化
		List<String> l = Arrays.asList(s);
		
		int[] a = new int[] {1 , 9 , 8 ,5, 4};
		List<int[]> l0 = Arrays.asList(a);
		
		Arrays.sort(a);// sort it in-place
		
		char[] c = new char[] {'a' , 'A' , 'a' , 'A', 'a'};
		Arrays.sort(c);// it was not sorted by the number of letter  (Arrays.sort(nums, Comparator))
		
		int[] n = new int[5];
		Arrays.fill(n, 0, 5, 1 );
		
		System.out.println();
		
//		List/Set <==> Array (but no Arrays.asSet())
//		- list.toArray(new Object[0]), 参数必须是对象数组，primary type，比如 int 不行， String, Integer, int[] 可以
//			e.g. list1.toArray(new int[list1.size()][2]); // the result here is int[][2];  list.toArray(new String[0])
//		- list.stream().mapToInt(i -> i).toArray(); // 这里 list的元素是 Integer， 转换成 int[]
//		- Arrays.asList(array);  参数必须是 Object[], 比如 String[], Integer[]
//			e.g. List<String> l = Arrays.asList(s); // s是String[]； 如果是 char[], 那asList的结果是 List<char[]>
//		Arrays.asList(1, 2, 3);
		// ====>  Array, Set, List 用法 ====> FourSum.java
	}
}
