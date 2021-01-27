package amazon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Design an in-memory file system to simulate the following functions:
//
//ls: Given a path in string format. If it is a file path, return a list that only contains this file's name. If it is a directory path, return the list of file and directory names in this directory. Your output (file and directory names together) should in lexicographic order.
//
//mkdir: Given a directory path that does not exist, you should make a new directory according to the path. If the middle directories in the path don't exist either, you should create them as well. This function has void return type.
//
//addContentToFile: Given a file path and file content in string format. If the file doesn't exist, you need to create that file containing given content. If the file already exists, you need to append given content to original content. This function has void return type.
//
//readContentFromFile: Given a file path, return its content in string format.
//
// 
//
//Example:
//
//Input: 
//["FileSystem","ls","mkdir","addContentToFile","ls","readContentFromFile"]
//[[],["/"],["/a/b/c"],["/a/b/c/d","hello"],["/"],["/a/b/c/d"]]
//
//Output:
//[null,[],null,null,["a"],"hello"]
//
//Explanation:
//filesystem
// 
//
//Note:
//
//You can assume all file or directory paths are absolute paths which begin with / and do not end with / except that the path is just "/".
//You can assume that all operations will be passed valid parameters and users will not attempt to retrieve file content or list a directory or file that does not exist.
//You can assume that all directory names and file names only contain lower-case letters, and same names won't exist in the same directory.
public class DesignInMemoryFileSystem {

	class Node {
        String val; //存放 fileName/directoryName； 也是方便后面取文件名
        Map<String, Node> children;
        boolean isFile;
        StringBuffer content;
        public Node(String val) {
            this.val = val;
            this.content = null;
            this.children = new HashMap<>();
            this.isFile = false;
        }
    }
    Node root = null;
    // 构建Trie的过程分两种，一种是针对file, 另一种就是纯 directory
    public void insert(Node root, String path, boolean isFile, String content) {
        if (root == null) return;
        Node curr = root;
        String[] ds = path.substring(1, path.length()).split("\\/"); //!!! /a/b ==> split的结果包括{“”， a, b},所以要把空串去掉
        int i = 0;
        while (i < ds.length - 1) {
            String p = ds[i];
            // 按照directory 构建Trie
            Map<String, Node> children = curr.children;
            if (children.containsKey(p)) {
                curr = children.get(p);
                i++;
            } else {
                children.put(p, new Node(p));
                curr = children.get(p);
                i++;
            }
                
        }
        Node temp = curr;
        String last = ds[i];
        // 对最后一个字符，看是否是 file path
        if (isFile) {
            if (temp.children.containsKey(last)) {
                Node fileNode = temp.children.get(last);
                fileNode.content.append(content);
                temp.children.put(last, fileNode);
            } else {
                Node node = new Node(last);
                node.content = new StringBuffer();
                node.content.append(content);
                node.isFile = true;
                temp.children.put(last, node);
            }
        } else {
            if (!curr.children.containsKey(last)) {
                curr.children.put(last, new Node(last));
            }
        }
    }
    public DesignInMemoryFileSystem() {
        root = new Node("");
    }
    
    public List<String> ls(String path) {
        Node curr = root;
        List<String> res = new ArrayList<>();
        if (path.length() == 1) {
            if (curr.children.size() == 0) return res;
            for (Map.Entry<String, Node> entry : curr.children.entrySet()) {// root的children，不管是directory还是file,下面的代码都会包括
                res.add(entry.getKey());
            }
            Collections.sort(res);
            return res;
        } else {
            String[] ds = path.substring(1, path.length()).split("\\/");
            int i = 0;
            while (i < ds.length) {
                String p = ds[i];
                Map<String, Node> children = curr.children;
                //System.out.println(children.size());
                if (children.containsKey(p)) {
                    curr = children.get(p);
                    i++;
                }
            }
            // 否则对directory, 按path往下走，走到最后，
                          // 这时候如果是个directory, loop through map, 如果是个file, 直接取当前node.val即可
            if (curr.isFile) {
                res.add(curr.val);
                return res;
            }
            for (Map.Entry<String, Node> entry : curr.children.entrySet()) {
                res.add(entry.getKey());
            }
            Collections.sort(res); // Time O(nlgn)
            return res;
        }
    }
    
    public void mkdir(String path) {
        insert(root, path, false, "");
    }
    
    public void addContentToFile(String filePath, String content) {
        insert(root, filePath, true, content);
    }
    
    public String readContentFromFile(String filePath) {
        Node curr = root;
        String[] ds = filePath.substring(1, filePath.length()).split("\\/");
        if (ds.length == 0) return "";
        int i = 0;
        // 往下一直沿着file path走，到最后
        // 这里while的判断，curr != null 对file总是有用的情况没必要
        while (curr != null && i < ds.length - 1) {
            String p = ds[i];
            Map<String, Node> children = curr.children;
            if (children.containsKey(p)) {
                curr = children.get(p);
                i++;
            } else {
                return "";
            }
                
        }
        return curr.children.get(ds[i]).content.toString();
    }
    
    public static void main(String[] args) {
    	DesignInMemoryFileSystem fileSystem = new DesignInMemoryFileSystem();
    	fileSystem.ls("/");
    	fileSystem.mkdir("/a/b/c");
    	fileSystem.mkdir("/a/e");
    	fileSystem.ls("/a");
    }
}
