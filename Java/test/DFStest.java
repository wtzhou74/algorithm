package test;

import java.util.ArrayList;
import java.util.List;

public class DFStest {

	public void backtracking(char[] chars, int start, List<List<Character>> subs,
			List<Character> list) {
        if (start == chars.length)
            return;
        //List<Character> list = new ArrayList<>();
        
        for (int i = start; i < chars.length; i++) {
        	list.add(chars[start]);
        	backtracking(chars, start + 1, subs, list);
        	List<Character> newList = new ArrayList<>();
        	subs.add(new ArrayList<>(list));
        }
        
        //subs.add(list);
    }
	
	// a用以跟dfs在for循环中做比较 （仅用于理解DFS用）  ==> refer to DFSTest.jpg
	// a不在for循环中，局部变量的变化
	int total = 0;
    public int dfs(String s, int idx) {
        if (idx == 0) {
        	return 1; //a这里return回去的1，会被 + unqiue，然后DFS往回走
        }
        String sub = s.substring(0, idx + 1);
        int unqiue = countUniqueChars(sub);
        int count = 0;// 这里初始化0， 是DFS时候每一层（节点）初始化值（临时/局部变量），再返回的时候不会回到这里，所以不会再初始化一遍清0； 如果下面有 for (),dfs在for里面就会
        count = dfs(s, idx - 1) + unqiue; // DFS返回的时候，计算各个节点的对应的 count值，累加上
        
        // total += count; //a 不用total, count就是一个累加的过程，最后一个 count出来就是累加起来的结果
        return count; // DFS开始返回，从 idx = 1，2，3, ...a并将对应的值 + unqiue得到更大idx的值，比如 idx=1 ==> idx=2 的值等
    }
    
    public int countUniqueChars(String s) {
        int count = 0;
        int[] unique = new int[26];
        for (int i = 0; i < s.length(); i++) {
            unique[s.charAt(i) - 'A']++;
        }
        for (int i = 0; i < 26; i++) {
            if (unique[i] == 1)
                count++;
        }
        return count;
    }
	public static void main(String[] args) {
		String s = "ABCD";
		List<List<Character>> subs = new ArrayList<>();
		new DFStest().backtracking(s.toCharArray(), 0, subs, new ArrayList<>());
	}
}
