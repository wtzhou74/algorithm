import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetTest {

	public static void main(String[] args) {
		List<Integer> l1 = new ArrayList<>();
		l1.add(1);
		List<Integer> l11 = Arrays.asList(1);
		List<Integer> l2 = new ArrayList<>();
		l2.add(2);
		List<Integer> l22 = Arrays.asList(1);
		Set<List<Integer>> s = new HashSet<>();
//		s.add(l1);
//		s.add(l2);
//		System.out.println(s.size());// size = 2
		s.add(l11);
		s.add(l22);
		System.out.println(s.size()); // size = 1
		
		Set<Integer> set = new HashSet<>();
//		set.add(1);
//		set.remove(1);
		set.stream().mapToInt(i -> i).toArray();
		
		//Collections.addAll(set, array);
		Set<Integer> set1 = new HashSet<>(l22);// list to set
		
		// sort set
		// 1)
		List<Integer> l = new ArrayList<>(set); // set to list
		Collections.sort(l);
		// 2)  ==> TreeSet
		
		
	}
}
