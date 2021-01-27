package amazon;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

//Design and implement a data structure for Least Frequently Used (LFU) cache. It should support the following operations: get and put.
//
//get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
//put(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity, it should invalidate the least frequently used item before inserting a new item. For the purpose of this problem, when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be evicted.
//
//Note that the number of times an item is used is the number of calls to the get and put functions for that item since it was inserted. This number is set to zero when the item is removed.
//
// 
//
//Follow up:
//Could you do both operations in O(1) time complexity?
//
// 
//
//Example:
//
//LFUCache cache = new LFUCache( 2 /* capacity */ );
//
//cache.put(1, 1);
//cache.put(2, 2);
//cache.get(1);       // returns 1
//cache.put(3, 3);    // evicts key 2
//cache.get(2);       // returns -1 (not found)
//cache.get(3);       // returns 3.
//cache.put(4, 4);    // evicts key 1.
//cache.get(1);       // returns -1 (not found)
//cache.get(3);       // returns 3
//cache.get(4);       // returns 4

public class LFUcache {

	class Node {
        int freq;
        int val;
        //int key;
        
        public Node(int freq, int val) {
            this.freq = freq;
            this.val = val;
            //this.key = key;
        }
    }
	
	// 这里的重点是先对 Freq 排序，==> TreeMap/PriorityQueue
	// 若 freq一样，按LRU取  ===> 对具有freq的cache按LRU排 ===> LinkedHashMap可以保证（插入/时间）顺序/ Double LinkedList
	//											===> 参考LRUcache
	// 这里如果我们总是是对 freq排序，就会有 O（nlgN)复杂度，这里我们只需弹出最小freq中的一个元素 
	//								===> 借助一个全局变量 minFreq记录当前最小的freq, 从这个LinkedHashMap中剔除一个
	//									===> 达到 O（1）的复杂度，因为不用排序了，只需 map.get(freq)就行
    Map<Integer, Node> map; // 用以方便取值
    //对某个freq,只要记录其对应的 key就可以了，不用带上node， 所以这里可以用LinkedList取代 LinkedHashMap,看下面commented的代码
    TreeMap<Integer, LinkedHashMap<Integer, Node>> freqMap;
    
    int minFreq = Integer.MAX_VALUE;//既然evict LFU的出去，那就用一个变量专门记录minFreq,这样需要evict
    								//的时候，从里面其对应的 LinkedList里面找队列最前面的就可以，不许要额外sort
    								// 看下面代码  ==> O(1)
    //Map<Integer, LinkedHashMap<Integer, Node>> freqMap; // 这里就不需要 TreeMap了
    
    int capacity;
    public LFUcache(int capacity) {
        this.map = new HashMap<>();
        this.freqMap = new TreeMap<>();
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if (map.containsKey(key)) {
//            Node node = map.get(key);
//            int freq = node.freq;
//            node.freq = node.freq + 1;
//            LinkedHashMap<Integer, Node> linkedMap = freqMap.get(freq);
//            linkedMap.remove(key);
//            if (linkedMap.size() == 0)
//            	freqMap.remove(freq);
//            freqMap.computeIfAbsent(node.freq, 
//                                    k -> new LinkedHashMap<>()).put(key, node);
        	Node node = map.get(key);
            updateFreq(node, freqMap, key);
            return node.val;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if (capacity == 0) return;///!!!!对 edge case判断，虽然cache的capacity=0无实际意义
        if (map.containsKey(key)) {
        	Node node = map.get(key);
            node.val = value;
            updateFreq(node, freqMap, key);
            //代码重复
//            Node node = map.get(key);
//            int freq = node.freq;
//            node.freq = node.freq + 1;
//            node.val = value;
//            LinkedHashMap<Integer, Node> linkedMap = freqMap.get(freq);
//            linkedMap.remove(key);
//            if (freqMap.get(freq).size() == 0)
//                freqMap.remove(freq);
//            freqMap.computeIfAbsent(node.freq, 
//                                    k -> new LinkedHashMap<>()).put(key, node);
        	
        } else if (map.size() < capacity) {
            Node node = new Node(1, value);
            map.put(key, node);
            freqMap.computeIfAbsent(node.freq, 
                                    k -> new LinkedHashMap<>()).put(key, node);
            
        } else if (map.size() == capacity) {
            //System.out.println(key + " " + map.size());
            int fkey = freqMap.firstKey();
            System.out.println(fkey);
            LinkedHashMap<Integer, Node> linkedMap = freqMap.get(fkey);
            //System.out.println(key + " " + fkey);
            int rKey = linkedMap.entrySet().iterator().next().getKey();
            System.out.println(key + " " + rKey);
            map.remove(rKey);
            linkedMap.remove(rKey);
            if (freqMap.get(fkey).size() == 0)
                freqMap.remove(fkey);
            Node node = new Node(1, value);
            map.put(key, node);
            freqMap.computeIfAbsent(node.freq, 
                                    k -> new LinkedHashMap<>()).put(key, node);
        }
    }
    
    public void updateFreq(Node node, TreeMap<Integer, LinkedHashMap<Integer, Node>> freqMap, int key) {
        int freq = node.freq;
        node.freq = node.freq + 1;
        //node.val = value;
        LinkedHashMap<Integer, Node> linkedMap = freqMap.get(freq);
        linkedMap.remove(key);
        if (freqMap.get(freq).size() == 0)
            freqMap.remove(freq);
        freqMap.computeIfAbsent(node.freq, 
                                k -> new LinkedHashMap<>()).put(key, node);
    }
    
    //----------------------------
    // 用 LinkedList 取代上面的 LinkedHashMap, 对某个freq,只要记录其对应的 key就可以了，不用带上node
//    class Node {
//        int freq;
//        int val;
//        //int key;
//        
//        public Node(int freq, int val) {
//            this.freq = freq;
//            this.val = val;
//            //this.key = key;
//        }
//    }
//    Map<Integer, Node> map;
//    TreeMap<Integer, LinkedList<Integer>> freqMap;
//    int capacity;
//    public LFUCache(int capacity) {
//        this.map = new HashMap<>();
//        this.freqMap = new TreeMap<>();
//        this.capacity = capacity;
//    }
//    
//    public int get(int key) {
//        if (map.containsKey(key)) {
//            Node node = map.get(key);
//            updateFreq(node, freqMap, key);
//            return node.val;
//        }
//        return -1;
//    }
////     ["LFUCache","put","put","get","put","get","get","put","get","get","get"]
//// [[2],[1,1],[2,2],[1],[3,3],[2],[3],[4,4],[1],[3],[4]]
//    public void put(int key, int value) {
//        if (capacity == 0) return;
//        if (map.containsKey(key)) {
//            Node node = map.get(key);
//            node.val = value;
//            updateFreq(node, freqMap, key);
//        } else if (map.size() < capacity) {
//            Node node = new Node(1, value);
//            map.put(key, node);
//            freqMap.computeIfAbsent(node.freq, 
//                                    k -> new LinkedList<>()).add(key);
//            
//        } else if (map.size() == capacity) {
//            //System.out.println(key + " " + map.size());
//            int fkey = freqMap.firstKey();
//            System.out.println(fkey);
//            LinkedList<Integer> linkedList = freqMap.get(fkey);
//            //System.out.println(key + " " + fkey);
//            int rKey = linkedList.get(0);
//            System.out.println(key + " " + rKey);
//            map.remove(rKey);
//            linkedList.remove(new Integer(rKey));
//            if (freqMap.get(fkey).size() == 0)
//                freqMap.remove(fkey);
//            Node node = new Node(1, value);
//            map.put(key, node);
//            freqMap.computeIfAbsent(node.freq, 
//                                    k -> new LinkedList<>()).add(key);
//        }
//    }
//    
//    public void updateFreq(Node node, TreeMap<Integer, LinkedList<Integer>> freqMap, int key) {
//        int freq = node.freq;
//        node.freq = node.freq + 1;
//        //node.val = value;
//        LinkedList<Integer> linkedList = freqMap.get(freq);
//        linkedList.remove(new Integer(key));
//        if (freqMap.get(freq).size() == 0)
//            freqMap.remove(freq);
//        freqMap.computeIfAbsent(node.freq, 
//                                k -> new LinkedList<>()).add(key);
//    }
    
    //-------------------------
    // 借助一个int minFreq变量实现 O（1）， 不需要额外基于freq排序  ==> 正确更新 minFreq即可
//    public void put(int key, int value) {
//        if (capacity == 0) return;
//        if (map.containsKey(key)) {
//            Node node = map.get(key);
//            node.val = value;
//            updateFreq(node, freqMap, key);
//        } else if (map.size() < capacity) {
//            Node node = new Node(1, value);
//            if (minFreq > 1)
//                minFreq = 1;
//            map.put(key, node);
//            freqMap.computeIfAbsent(node.freq, 
//                                    k -> new LinkedList<>()).add(key);
//            
//        } else if (map.size() == capacity) {
//            LinkedList<Integer> linkedList = freqMap.get(minFreq);
//            //System.out.println(key + " " + fkey);
//            int rKey = linkedList.get(0);
//            map.remove(rKey);
//            linkedList.remove(new Integer(rKey));
//            if (freqMap.get(minFreq).size() == 0)
//                freqMap.remove(minFreq);
//            Node node = new Node(1, value);
//            if (minFreq > 1)
//                minFreq = 1;
//            map.put(key, node);
//            freqMap.computeIfAbsent(node.freq, 
//                                    k -> new LinkedList<>()).add(key);
//        }
//    }
//    
//    public void updateFreq(Node node, Map<Integer, LinkedList<Integer>> freqMap, int key) {
//        int freq = node.freq;
//        node.freq = node.freq + 1;
//        //node.val = value;
//        LinkedList<Integer> linkedList = freqMap.get(freq);
//        linkedList.remove(new Integer(key));
//        if (freqMap.get(freq).size() == 0) {
//            // for current frequency, there is no corresponding cache
//            freqMap.remove(freq);
//            if (minFreq == freq)
//                minFreq++;
//        }
//            
//        freqMap.computeIfAbsent(node.freq, 
//                                k -> new LinkedList<>()).add(key);
//    }
    
    public static void main(String[] args) {
    	LFUcache cache = new LFUcache(2);
    	cache.put(1, 1);
    	cache.put(2, 2);
    	cache.get(1);
    	cache.put(3, 3);
    	cache.get(2);
    	cache.get(3);
    	cache.put(4, 4);
    	cache.get(1);
    }
}
