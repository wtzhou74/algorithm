package triePrefixTree;

import java.util.ArrayList;
import java.util.List;

public class ReplaceWords {
//	public String replaceWords(List<String> dict, String sentence) {
//		List<String> result = new ArrayList<>();
//        String[] words = sentence.trim().split(" ");
//        for (String word : words) {
//            int temp = Integer.MAX_VALUE;
//            String foundedRoot = "";
//            for (String root : dict) {
//                if (root.length() > word.length()) continue;
//                if (word.startsWith(root) && root.length() < temp) {
//                    temp = root.length();
//                    foundedRoot = root;
//                }
//            }
//            String tempStr = foundedRoot.length() > 0 ? foundedRoot : word;
//            result.add(tempStr);
//        }
//        
//        StringBuilder str = new StringBuilder();
//        int i = 0;
//        for (String word : result) {
//            if (i < result.size() - 1) str.append(word + " ");
//            else str.append(word);
//            i++;
//        }
//        
//        return str.toString();
//    }
	
	//Trie Solution
	public static String replaceWords(List<String> dict, String sentence) {
		TrieNode3 root = buildTrie(dict);
		String[] words = sentence.trim().split(" ");
		StringBuilder str = new StringBuilder();
        int i = 0;
        for (String word : words) {
            String result = search(root, word);
            str.append(result + " ");
        }
        
        return str.toString().trim();
    }
    
    // search word against specified Trie
	// GET SHORTEST PREFIX
    public static String search(TrieNode3 root, String word) {
    	TrieNode3 curr = root;
        StringBuilder mappedRoot = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (curr.children[c-'a'] != null) {
                if (curr.children[c - 'a'].val != c) return word;// return original word
                if (curr.children[c -'a'].isWord) // THE PATH TO THE FIRST MET NODE WHOSE isWord=TRUE is the shortest one
                {
                	// ADD THE LAST CHAR
                	mappedRoot.append(c);
                	break;
                }
                if (curr.children[c - 'a'].val == c) {
                    curr = curr.children[c - 'a'];
                    mappedRoot.append(c);
                }
            }
            else return word;
        }
        return mappedRoot.toString();
    }
    
    // build Trie with dic
    public static TrieNode3 buildTrie(List<String> dict) {
    	TrieNode3 root = new TrieNode3(' ');
        for (String str : dict) {
            insert(str, root);
        }
        return root;
    }
    
    // insert word (FROM DIC, not sentence) to Trie
    public static void insert(String word, TrieNode3 root) {
    	TrieNode3 curr = root;
        if (word.length() == 0) return;
        for (char c : word.toCharArray()) {
        	if (curr.children[c - 'a'] == null) 
        		curr.children[c - 'a'] = new TrieNode3(c);// check null
        	// founded, check its children
            curr = curr.children[c - 'a'];
        }
        curr.isWord = true;// relative to element in Dic, not a real word
    }
    
    public static void main(String[] args) {
    	String a  = "cat";
    	String b = "bat";
    	String c = "rat";
    	List<String> dic = new ArrayList<>();
    	dic.add(a);
    	dic.add(b);
    	dic.add(c);
    	String sentence = "the cattle was rattled by the battery";
    	
    	replaceWords(dic, sentence);
    	
    }
}

//class TrieNode3 {
//	TrieNode3[] children;
//    char val;
//    boolean isWord;
//    
//    public TrieNode3(char val) {
//        this.children = new TrieNode3[26];
//        this.val = val;
//        this.isWord = false;
//    }
//}
