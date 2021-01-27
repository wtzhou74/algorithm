package amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class DesignSearchAutocompleteSystem {

	// 在search的过程中，一定要注意 ！！！ {“a”, "abc"}， 即句子"a" 是句子 “abc” 的子串，所以这里要同时记录这两个句子，
	//			而不是只有 “abc”
	//	另外，在判断是否是符合的sentence需要加入的时候，一定要遍历完给定String的所有字符后，	
	//		接下来所对应的sentences才是要找的 （即 找 以给定s为 PREFIX的sentences）
	//		比如 {“a”, "abc" } 给定/输入 “ab”, 只有“abc”符合，"a"不符合， 
	public class TrieNode{
        String val = "";
        //int time = 0; // sentence的times是可变的，在 input()之后，碰到新的sentence要加到system中
        				// 跟着 TrieNode走，更新times就比较麻烦了
        boolean isSentence = false;
        Map<Character, TrieNode> children; // 当前child的key（字符值）以及对应的TrieNode（child自己的点）
        
        public TrieNode () {
            this.children = new HashMap<>();
        }
    }
    
	public class Item {
        String val;
        int times;
        public Item(String val, int times) {
            this.val = val;
            this.times = times;
        }
    }
	
	StringBuilder sb = new StringBuilder();
    TrieNode root;
    List<String> pre = null;
    Map<String, Integer> map; // 记录sentence以及出现的次数
    public DesignSearchAutocompleteSystem(String[] sentences, int[] times) {
        root = new TrieNode();
        map = new HashMap<>();
        for (int i = 0; i < sentences.length; i++) {
            map.put(sentences[i], times[i]);
        }
        buildTrie(sentences, root);
    }
    
    public void buildTrie(String[] sentences, TrieNode root) {        
        for (int i = 0; i < sentences.length; i++) {
            TrieNode curr = root;
            for (char c : sentences[i].toCharArray()) {
                Map<Character, TrieNode> children = curr.children;
                if (!children.containsKey(c)) {
                    children.put(c, new TrieNode());
                }
                curr = children.get(c);
            }
            curr.val = sentences[i];
            //curr.time = times[i];
            curr.isSentence = true;
        }
    }
    
    public List<String> searchTrie(TrieNode root, String s) {
        if (root == null || root.children.size() == 0)
            return new ArrayList<>();
        TrieNode curr = root;
        int i = 0;
        List<String> result = new ArrayList<>();
        while (curr != null && i < s.length()) {
            Map<Character, TrieNode> children = curr.children;
            if (children.containsKey(s.charAt(i))) {
                curr = children.get(s.charAt(i));
//                if (curr.isSentence) // 不能在这里看到一个是sentence的就加进去，要check着整个字符串
//                    result.add(curr.val);
            } else
                break;
            i++;
        }
        //if (i <= s.length()) return 
        
        if (i < s.length() || curr == root) return new ArrayList<>();
        // iterative solution
        Stack<TrieNode> stack = new Stack<>();
        stack.push(curr);
        while (!stack.isEmpty()) {
            TrieNode node = stack.pop();
            if (node.isSentence)
                result.add(node.val);
            for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                stack.push(entry.getValue());
            }
        }
        // 递归
        //dfs(curr, result);
        return result;
    }
    
    //  dfs的过程是判断完给定字符串S的最后一个字符后，在这个字符后如果连着1个或多个分支，需要记录所有分支下的sentence
    //		===> !!!所以这里在判断的时候，在 node.isSentence ==true的时候还得继续下去，直到node==null
    public void dfs(TrieNode node, List<String> result) {
        if (node ==null)
            return;
        if (node.isSentence)
            result.add(node.val); // 碰到一个sentence，加进去，但不能return,因为这个sentence可能是另外一个sentence
        							// 的字串，比如  sentences ={ a, abc }
        Map<Character, TrieNode> children = node.children;
        for (Map.Entry<Character, TrieNode> entry : children.entrySet()) {
            dfs(entry.getValue(), result);
        }
    }
    
    
    public List<String> input(char c) {
        if (c == '#') {
            if (!map.containsKey(sb.toString())) { 
            	// 得到一个新的sentence了，要加入到原sentences库！！！，所以原来的sentence,times数组是变化的
                buildTrie(new String[]{sb.toString()}, root);
            }         
            map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + 1);
            sb.delete(0, sb.length());
        } else
            sb.append(c);
        List<String> strs = searchTrie(root, sb.toString());
        List<String> result = new LinkedList<>();
        if (strs.size() == 0)
            return result;
        PriorityQueue<Item> pq = new PriorityQueue<>((a, b) -> {
            if (a.times == b.times)
                return b.val.compareTo(a.val);
            else
                return a.times - b.times;
        });
        for (String str : strs) {
            pq.offer(new Item(str, map.get(str)));
            if (pq.size() > 3)
                pq.poll();                
        }
        System.out.println(pq.size());
        String[] aux = new String[pq.size() < 3 ? pq.size() : 3]; // ！！！不能直接用3，因为返回的结果有可能没到3个
        for (int i = 2; i >= 0; i--) {
            aux[i] = pq.poll().val;
        }
        //pre = result;
        return Arrays.asList(aux);
    }
    
    public static void main(String[] args) {
    	String[] sentences = new String[] {"abc","abbc","a"};
    	int[] times = new int[]{3,3,3};
    	DesignSearchAutocompleteSystem auto 
    		= new DesignSearchAutocompleteSystem(sentences, times);
    	auto.input('a');
    	auto.input('b');
    	
    }
}
