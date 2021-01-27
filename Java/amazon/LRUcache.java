package amazon;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

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
public class LRUcache {

	// remove的点是  least recently used!!!!不是简单从一个list里面删除一个元素
	// 不管以下哪种方法，一定要明白 什么时候需要remove !!!!!
	
	// 下面的方法我们之间借助 Queue ！！！在put的时候要remove，再重新offer()
	//  但是 queue.remove(object) 是 O（N） 时间
	// 下面的O（1） approach跟这个思想是一样的，只是 O（1）那个需要自己构建 DLinkedList实现remove/add等操作
	
	// 还有一种更直接  extends LinkedHashMap<Integer, Integer>  ==> removeEldestEntry<Map.Entry<>> ==> O(1) ==> 如下
	int capacity;
    Map<Integer, Integer> map1;
    Queue<Integer> queue;
    // uncomment it if using this solution
//    public LRUcache(int capacity) {
//        this.capacity = capacity;
//        this.map = new HashMap<>();
//        this.queue = new LinkedList<>();
//    }
    
    public int get1(int key) {        
        int val = map1.getOrDefault(key, -1);
        if (val == -1) return -1;
        queue.remove(key); // 修改优先级
        queue.offer(key);
        return val;
    }
    
    public void put1(int key, int value) {
        //System.out.println(capacity);
        if (map1.containsKey(key)) { // !!每新访问（put/get），都要给它放到队尾，即当前最新访问的点
        	map1.put(key, value);
            queue.remove(key);
            queue.offer(key);
            return;
        }
        if (queue.size() == capacity) {
            int remove = queue.poll();
            map1.remove(remove);
        }
        queue.offer(key);
        map1.put(key, value);
    }
    
    
    ////////////////////////////////O（1） 时间， 自定义 双端队列
    class DLinkedNode {
        DLinkedNode pre;
        DLinkedNode next;
        int key;
        int val;
        public DLinkedNode(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
    private DLinkedNode head, tail; // 初始化 LRUcache的同时一定要初始化它，并head连上tail
    
    // 把新点加入到 head.next，代表latest used, 同时，head, tail 都用了一个dummy node方便处理
    public void addNode(DLinkedNode node) {
        node.pre = head;
        node.next = head.next;
        
        head.next.pre = node;
        head.next = node;
    } 
    // 从队中删掉一个元素 // ！！！！不需要查找，因为 传进来的是Node,且有前后指针，很容易删除并重新拼接list
    public void removeNode(DLinkedNode node) {
        DLinkedNode pre = node.pre;
        pre.next = node.next;
        node.next.pre = pre;
    }
    
    public DLinkedNode removeFromTail() { // 队尾放的总是 least used，所以 pop队尾的出去如果满的话
        DLinkedNode aux = tail.pre;
        aux.pre.next = tail;
        tail.pre = aux.pre;
        return aux;
    }
    
    // ！！！！以 Node为单位， 所以不用 map<Integer, Integer>; 包括DLinkedList的各种操作
    private Map<Integer, DLinkedNode> map = new HashMap<>();
    //private int capacity;
    private int size; // 没必要，看map size就可以
    public LRUcache(int capacity) {
        this.capacity = capacity;
        this.head = new DLinkedNode(-1, -1); // 新建dummy node方便处理
        this.tail = new DLinkedNode(-1, -1);
        
        // ！！！！一定要记得首尾连上，在初始化的时候
        head.next = tail;
        tail.pre = head;
    }
    
    public int get(int key) {        
        if (!map.containsKey(key)) return -1;
        DLinkedNode node = map.get(key);
        removeNode(node);
        addNode(node);
        return node.val;
    }
    
    public void put(int key, int value) {
    	if (capacity == 0) return; ///!!!!对 edge case判断，虽然cache的capacity=0无实际意义
        if (map.containsKey(key)) {
            DLinkedNode node = map.get(key);
            removeNode(node);
            node.val = value;
            addNode(node);
            return;
        }
        if (size == capacity) {
            DLinkedNode leastNode = removeFromTail();
            map.remove(leastNode.key);
            size--;
        }
        DLinkedNode n = new DLinkedNode(key, value);
        addNode(n);
        map.put(key, n);
        size++;
    }
    
//    class Node {
//        int key;
//        int val;
//        Node pre;
//        Node next;
//        
//        public Node(int key, int val) {
//            this.key = key;
//            this.val = val;
//        }
//    }
//    int capacity;
//    Node head;
//    Node tail;
//    Map<Integer, Node> map;
//    public LRUCache(int capacity) {
//        this.capacity = capacity;
//        this.head = new Node(-1, -1);
//        this.tail = new Node(-1, -1);
//        this.map = new HashMap<>();
//        
//        head.next = tail;
//        tail.pre = head;
//    }
//    
//    public void insertToHead(Node node) {
//        
//        node.next = head.next;
//        head.next.pre = node;
//        
//        node.pre = head;
//        head.next = node;
//    }
//    
//    public Node evictNode() {
//        Node node = tail.pre;
//        node.pre.next = tail;
//        tail.pre = node.pre;
//        
//        return node;
//    }
//    
//    public void removeNode (Node node) {
//        Node aux = node.pre;
//        Node nxt = node.next;
//        
//        aux.next = nxt;
//        nxt.pre = aux;
//    }
////     ["LRUCache","put","put","get","put","get","put","get","get","get"]
//// [[0],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]
//    public int get(int key) {
//        if (map.containsKey(key)) {
//            Node node = map.get(key);
//            removeNode(node);
//            insertToHead(node);
//            return node.val;
//        } else return -1;
//    }
//    
//    public void put(int key, int value) {
//        if (capacity == 0) return;
//        if (map.containsKey(key)) {
//            Node node = map.get(key);
//            node.val = value;
//            removeNode(node);
//            insertToHead(node);
//        } else if (map.size() < capacity) {
//            Node node = new Node(key, value);
//            map.put(key, node);
//            insertToHead(node);
//        } else if (map.size() == capacity) { // 这里我们不用额外的size来计数
//            Node aux = evictNode();
//            map.remove(aux.key);
//            Node node = new Node(key, value);
//            map.put(key, node);
//            insertToHead(node);
//        }
//    }
    
    
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
    
    public static void main(String[] args) {
    	LRUcache cache = new LRUcache( 2);

    	cache.put(1, 1);
    	cache.put(2, 2);
    	System.out.println(cache.get(1));       // returns 1
    	cache.put(3, 3);    // evicts key 2
    	System.out.println(cache.get(2));       // returns -1 (not found)
    	cache.put(4, 4);    // evicts key 1
    	System.out.println(cache.get(1));       // returns -1 (not found)
    	System.out.println(cache.get(3));       // returns 3
    	System.out.println(cache.get(4));       // returns 4
    }
}
