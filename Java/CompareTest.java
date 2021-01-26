import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class CompareTest {

	public static void main(String[] args) {
		
		////////////////////////Collections
//		List<Student> stus = new ArrayList<Student>(){
//			{
//				add(new Student("张三", 30));	
//				add(new Student("李四", 20));	
//				//add(new Student("王五", 60));	
//			}
//		};
//		//对users按年龄进行排序
//		Collections.sort(stus, new Comparator<Student>() {
//
//			@Override
//			public int compare(Student s1, Student s2) {
//				// 升序, 大于0， swap
//				return s1.getAge()-s2.getAge();
//				//return s1.getAge().compareTo(s2.getAge());
//				// 降序, 小于0， swap
//				// return s2.getAge()-s1.getAge();
//				// return s2.getAge().compareTo(s1.getAge());
//			}
//		});
//		
//		for (Student s : stus) {
//			System.out.println(s.name);
//		}
		
		// sort array
//		List<int[]> tst = new ArrayList<>();
//		tst.add(new int[] {1, 2});
//		tst.add(new int[] {3, -2});
//		tst.add(new int[] {2, 3});
//		tst.add(new int[] {5, -3});
//		tst.add(new int[] {1, 6});
//		tst.add(new int[] {4, -6});
//		
//		Collections.sort(tst, new Comparator<int[]>() {
//			@Override
//			public int compare(int[] a, int[] b) {
//				if (a[0] != b[0]) return a[0] - b[0];
//				return b[1] - a[1];
//			}
//		});
//		
//		for (int[] s : tst) {
//			System.out.println(s[0] + " " + s[1]);
//		}
		
		/////////////////////////////////////////List
		List<Integer> ls = new ArrayList<>();
		Collections.sort(ls);
		
		
		//////////////////////////////////Set
		Set<Integer> s = new HashSet<>();
		//Collections.sort(s);// WRONG
		// 1)
		List<Integer> l = new ArrayList<>(s);
		Collections.sort(l);
		// 2)  ==> TreeSet
		
		///////////////////////////////////////////Arrays
		String[] newNums = new String[5];
		Arrays.sort(newNums, new Comparator<String>() {
            public int compare(String a, String b) {
                String s1 = a + b;
                String s2 = b + a;
                
                return s2.compareTo(s1);
            }
        } );
		
		Integer[] start = new Integer[2]; // MUST BE INTEGER, not int[]
		Arrays.sort(start, (a, b) -> a - b);
		
		PriorityQueue<Integer> heap = new PriorityQueue<>(new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				return b - a;
			}
		});
		//PriorityQueue<Integer> heap = new PriorityQueue<>();
		
		heap.offer(3);
		heap.offer(6);
		heap.offer(0);
		heap.offer(1);
		heap.offer(9);
		heap.offer(5);
		
//		Iterator<Integer> it = heap.iterator();
//		while (it.hasNext()) {
//			System.out.println(it.next());
//		}
		while (!heap.isEmpty()) {
			System.out.println(heap.poll());
		}
		
//		List<Map.Entry<String, Integer>> list = new LinkedList<>(); 
//		// Sort the list 
//        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
//            public int compare(Map.Entry<String, Integer> o1,  
//                               Map.Entry<String, Integer> o2) 
//            { 
//                //return (o1.getValue()).compareTo(o2.getValue());
//            	return o1.getValue() - o2.getValue();
//            } 
//        }); 
	}
}

