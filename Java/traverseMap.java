import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class traverseMap {

	public static void main(String[] args) {
		Map<String, Integer> hm =  
                new HashMap<String, Integer>(); 

	   // Adding mappings to HashMap 
	   hm.put("GeeksforGeeks", 54); 
	   hm.put("A computer portal", 80); 
	   hm.put("For geeks", 82); 
	   hm.containsKey("");
	   
	   hm.keySet().stream().findFirst().get();
	   hm.values().stream().findFirst().get();
	   
	   Iterator<Map.Entry<String, Integer>> iter = hm.entrySet().iterator();
	   while (iter.hasNext()) {
		   iter.next().getKey();
		   iter.next().getValue();
	   }
	   
	   for (Map.Entry<String, Integer> map : hm.entrySet()) {
		   map.getKey();
		   map.getValue();
	   }
	}
}
