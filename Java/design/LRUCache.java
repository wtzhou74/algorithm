package design;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

//Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put.
//
//get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
//put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
//
//The cache is initialized with a positive capacity.
//
//Follow up:
//Could you do both operations in O(1) time complexity?
//
//Example:
//
//LRUCache cache = new LRUCache( 2 /* capacity */ );
//
//cache.put(1, 1);
//cache.put(2, 2);
//cache.get(1);       // returns 1
//cache.put(3, 3);    // evicts key 2
//cache.get(2);       // returns -1 (not found)
//cache.put(4, 4);    // evicts key 1
//cache.get(1);       // returns -1 (not found)
//cache.get(3);       // returns 3
//cache.get(4);       // returns 4


// HashMap + Doubly Linked List
// map<Key, DLinkedNode> ==> so once we knew the KEY, we can get the NODE in O(1), then KNOWN the node, we can do remove/remove/delete in O(1)
// With HashMap, we do not need to spend O(n) time to find the specific node
// Time: O(1)
// Space: O(capacity)
public class LRUCache {
	
	class DLinkedNode {
		DLinkedNode next;
		DLinkedNode pre;
		int key;
		int value;
		
		public DLinkedNode() {
			
		}
	}
	
	private DLinkedNode head, tail;
	
	private void addNode(DLinkedNode node) {
		// add the new node to the right after head
		node.pre = head;
		node.next = head.next;
		
		// DOUBLY
		head.next.pre = node;
		head.next = node;
	}
	
	private void removeNode(DLinkedNode node) {
//		node.pre.next = tail;
//		tail.next = node.pre;
		
		// removing an existing node
		DLinkedNode prev = node.pre;
		DLinkedNode nxt = node.next;
		
		prev.next = nxt;
		nxt.pre = prev;
		
	}
	
	private void moveToHead(DLinkedNode node) {
		// move certain node to the head
		removeNode(node); // remove current node
		addNode(node); // add the node to head as a new node
	}
	
	private DLinkedNode popTail() {
		// pop the current tail
		DLinkedNode res = tail.pre;
		removeNode(res);
		return res;
	}
	
	private Map<Integer, DLinkedNode> cache = new HashMap<>();
	private int size;
	private int capacity;
	
	public LRUCache(int capacity) {
	
		this.capacity = capacity;
		
		// initialize a DLinkedList
		this.size = 0;
		this.head = new DLinkedNode();
		this.tail = new DLinkedNode();
		
		head.next = tail;
		tail.pre = head;
	}
	
	public int get(int key) {
		DLinkedNode node = cache.get(key);
		if (node == null) return -1;
		
		// move the accessed node to the head
		// most accessed node
		// Always keep the LEAST accessed node at the end of list
		moveToHead(node);
		return node.value;
	}
	
	public void put(int key, int value) {
		// for map, check if the key exists or not first
		DLinkedNode node = cache.get(key);
		if (node == null) {
			DLinkedNode newNode = new DLinkedNode();
			newNode.key = key;
			newNode.value = value;
			
			cache.put(key, newNode);
			addNode(newNode);
			size++;
			
			if (size > capacity) {
				// pop the tail since the tail is always the LEAST accessed ndoe
				DLinkedNode tail = popTail();
				cache.remove(tail.key);
				size--;
			}
		} else {
			// update the value;
			cache.get(key).value = value;
			moveToHead(node);
		}
	}
	
}



// Applying LinkedHashMap since it has features from both LinkedList and HashMap, and it allows to support SORTED BY ACCESS
class LRUCache1 extends LinkedHashMap<Integer, Integer>{

    private int capacity;
    public LRUCache1(int capacity) {
        super(capacity, 0.75F, true); // True for ACCESS ORDER (LEAST to MOST order);  fill ration-0.75F is a empirically chosen value for hash collisions
        this.capacity = capacity;
    }
    
    public int get(int key) {
        return super.getOrDefault(key, -1);
    }
    
    public void put(int key, int value) {
        super.put(key, value);
    }
    
    @Override
    public boolean removeEldestEntry(Map.Entry<Integer, Integer> map) {
        return size() > capacity; // when to remove eldest entry/ least accessed entry
    }
}

class LRUCacheTLE {


    //////////////////////// TLE
	private int capacity;
    private Queue<Integer> queue;
    private Map<Integer, Integer> map;
    
    public LRUCacheTLE(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
        this.map = new HashMap<>(capacity);
    }
    
    public int get(int key) {
        if (map.containsKey(key)) {
            updateOrder(queue, key);
            return map.get(key);
        } else {
            return -1;
        }
    }
    
    public void updateOrder(Queue<Integer> queue, int target) {
        if (queue.size() == 0) return;
        Queue<Integer> temp = new LinkedList<>();
        while (queue.size() > 0) {
            if (queue.peek() == target) {
            	queue.poll();
            	continue;
            }
            temp.offer(queue.poll());
        }
        queue.offer(target);
        while (temp.size() > 0)
            queue.offer(temp.poll());
        return;
    }
    
    public void put(int key, int value) {
    	if (map.containsKey(key)) { // existed, then update
            map.put(key, value);
            updateOrder(queue, key);
        } else if (queue.size() < capacity) {
            queue.offer(key);
            map.put(key, value);
        } else {
            int evictKey = queue.poll();
            map.remove(evictKey);
            queue.offer(key);
            map.put(key, value);
        }
        
    }
    /////////////////////////////////////// TLE END
    
    public static void main(String[] args) {
    	LRUCache obj = new LRUCache(2);
    	System.out.println(obj.get(2));
    	obj.put(2, 6);
    	System.out.println(obj.get(1));
    	obj.put(1, 5);
    	obj.put(1, 2);
    	System.out.println(obj.get(1));
    	System.out.println(obj.get(2));
    }
}
