package hashTable;

public class MyHashMap {

//	class Node {
//        int key;
//        int val;
//        Node next;
//        public Node(int key, int val) {
//            this.key = key;
//            this.val = val;
//        }
//    }
//    
//    Node[] bucket;
//    double loadfactor;
//    int count;
//
//    /** Initialize your data structure here. */
//    public MyHashMap() {
//        bucket = new Node[10000];
//        loadfactor = 0.75;
//        count = 0;
//    }
//    
//    /** value will always be non-negative. */
//    public void put(int key, int value) {
//        int hashcode = key % bucket.length;// HASH FUNCTION
//        Node node = bucket[hashcode];
//        while (node != null) {
//            if (node.key == key) {
//                node.val = value;
//                return;
//            }
//            node = node.next;
//        }
//        Node tail = getTail(bucket[hashcode]);
//        if (tail == null) {
//            bucket[hashcode] = new Node(key, value);
//        } else {
//            tail.next = new Node(key, value);
//        }
//        count++;
//        if (count > bucket.length * loadfactor) {
//            bucket = rehashing(bucket);
//        }
//    }
//    
//    public Node getTail(Node node) {
//        if (node == null) {
//            return node;
//        }
//        while (node.next != null) {
//            node = node.next;
//        }
//        return node;
//    }
//    
//    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
//    public int get(int key) {
//        int hashcode = key % bucket.length;
//        Node node = bucket[hashcode];
//        while (node != null) {
//            if (node.key == key) {
//                return node.val;
//            }
//            node = node.next;
//        }
//        return -1;
//    }
//    
//    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
//    public void remove(int key) {
//        int hashcode = key % bucket.length;
//        Node node = bucket[hashcode];
//        if (node != null && node.key == key) {
//            bucket[hashcode] = node.next;
//            return;
//        }
//        while (node != null && node.next != null) {
//            if (node.next.key == key) {
//                node.next = node.next.next;
//            }
//            node = node.next;
//        }
//    }
//    
//    public Node[] rehashing(Node[] bucket) {
//        Node[] newbucket = new Node[bucket.length * 2];
//        for (Node node : bucket) {
//            while (node != null) {
//                int hashcode = node.key % newbucket.length;
//                Node tail = getTail(newbucket[hashcode]);
//                if (tail == null) {
//                    newbucket[hashcode] = node;
//                } else {
//                    tail.next = node;
//                }
//                node = node.next;
//            }
//        }
//        return newbucket;
//    }
	
	
	ListNode[] listNode;
    /** Initialize your data structure here. */
    public MyHashMap() {
        listNode = new ListNode[10000];// For each bucket, it contains a LinkedList
    }
    
    /** value will always be non-negative. */
    public void put(int key, int value) {
        int i = plx(key);// decide which LinkedList (bucket)
        if (listNode[i] == null) {
            listNode[i] = new ListNode(-1, -1);// initialize a ListNode
        }
        // find the last element of LinkedList - listNode[hashCode]
        ListNode pre = find(listNode[i], key);
        if (pre.next == null) 
        {
            ListNode next = new ListNode(key, value);// initialize a node of LinkedList;
            pre.next = next;
        } else {
            // element with given key found
            pre.next.val = value;
        }
        
    }
    
    // find the last element or the element with given key within a speicific LinkedList
    private ListNode find(ListNode listNode, int key) {
        ListNode pre = null;
        while (listNode != null && listNode.key != key) {
            pre = listNode;
            listNode = listNode.next;
        }
        
        return pre;
    }
    
    // Hash function
    private int plx(int key) {
        return Integer.hashCode(key) % listNode.length;
    }
    
    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        int i = plx(key);
        // corresponding bucket is not exisited
        if (listNode[i] == null) return -1;
        // bucket exisited, find the node with the key
        ListNode node = find(listNode[i], key);
        if (node.next != null) {
            return node.next.val;
        } else return -1;
        
    }
    
    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        int i = plx(key);
        if (listNode[i] == null) return;
        ListNode node = find(listNode[i], key);
        if (node.next == null) return;
        else node.next = node.next.next;
    }
}

class ListNode {
    int key;
    int val;
    ListNode next;
    ListNode (int key, int val) {
        this.key = key;
        this.val = val;
    }
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */
