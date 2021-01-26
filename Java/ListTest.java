import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListTest {

	public static void main(String[] args) {
		
		List<Integer> t1 = Arrays.asList(1, 2, 3, 4, 5, 6);
		List<Integer> subT1 = t1.subList(1, 4);
		t1.set(1, 2);// 修改指定索引处的值
		t1.add(0, 1);// 把元素加入到指定索引的位置
	
		
		// List<Integer> ini = List.of(1, 2, 3); // available in Java 9 or higher
		List<Integer> arrList = Arrays.asList(1, 2);
		List<Integer> arrList2 = Arrays.asList(1, 2);
		Set<List<Integer>> setTest = new HashSet<>();
		setTest.add(arrList2);
		setTest.add(arrList);
		System.out.println(setTest.size()); // 1; 但 new ArrayList<>();两个就不一样了，即便含的元素都一样
		
//		List<List<Integer>> test = new ArrayList<>();
		Set<Integer> set = new HashSet<>();
		List<Integer> ls = new ArrayList<>(set);
		
		ls.remove(1);//这是删掉索引 1处的值
		ls.remove(Integer.valueOf(1));//删掉对象1
		
		List<Integer> l = new ArrayList<>();
		l.add(-129);
		l.add(-129);
		Integer a = -129;
		Integer b = -129;
		System.out.println(l.get(0).intValue() == l.get(1).intValue());//true
		System.out.println(l.get(0) == l.get(1));//false
		System.out.println(a == b);
		
//		list0.add(3);
//		List<Integer> list1 = new ArrayList<>();
//		list1.add(4);
//		list1.add(5);
//		list1.add(6);
//		List<Integer> list2 = new ArrayList<>();
//		list2.add(7);
//		list2.add(8);
//		list2.add(9);
//		test.add(list0);
//		test.add(list1);
//		test.add(list2);
//		System.out.println(test.get(1).get(2));
//		test.get(1).set(2, 10);
//		System.out.println(test.get(1).get(2));
		
		
		List<String> list = new ArrayList<>();
		list.add(0, "1");
		list.add(1, "3");
		list.set(0, "2"); // 如果要set, 这个索引下必须要有初始值，如果 new ArrayList<>()，还没赋值,set(0)是错误的，
							// 但 Array.asList(-1,-1)这样的初始化可以，idx=0的位置有值 -1 了，可以重新 set
		list.remove(0);//index // ！！！对删除元素是int的，要remove(new Integer(1))才是按元素值删除，否则按索引
		list.remove("1");// object
		Collections.copy(new ArrayList<>(), list);
		Collections.reverse(list);
		
		list.removeIf(i -> i.equals("1"));
		
		List<int[]> list1 = new ArrayList<>();
		list1.toArray(new int[list1.size()][2]);// int[] 是 Object， 所以这里可以
		
		// new int[] cannot be used here, primary type is not allowed, should be Object[]; 
		list1.toArray(new Integer[0]);// Space is not enough, a new Array will be created and size is the value of list.size()
		List<Integer> list0 = new ArrayList<>();
		list0.stream().mapToInt(i -> i).toArray();
		
	}
}
