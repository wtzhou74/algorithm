package amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

//Given a list accounts, each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account.
//
//Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some email that is common to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.
//
//After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.
//
//Example 1:
//Input: 
//accounts = [["John", "johnsmith@mail.com", "john00@mail.com"], ["John", "johnnybravo@mail.com"], ["John", "johnsmith@mail.com", "john_newyork@mail.com"], ["Mary", "mary@mail.com"]]
//Output: [["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],  ["John", "johnnybravo@mail.com"], ["Mary", "mary@mail.com"]]
//Explanation: 
//The first and third John's are the same person as they have the common email "johnsmith@mail.com".
//The second John and Mary are different people as none of their email addresses are used by other accounts.
//We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'], 
//['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
//Note:
//
//The length of accounts will be in the range [1, 1000].
//The length of accounts[i] will be in the range [1, 10].
//The length of accounts[i][j] will be in the range [1, 30].

public class AccountsMerge {

	// 乍一看考虑按name 分组，在不同组中比较每个 accounts有没有相同，再合并。。。很复杂，不合理的算法
	// ===> 有关系的2D问题 ==> Graph  ==> 把同属于一个人的emails都连在一起, 形成一个Graph
	// ====> 这里要注意的是不是所有的emails都属于同一个人，所以会出现多个graph ===> 对每一个email都要判断
	
	// 或者类似于group，把分布在不同的地方的属于同一个account的email归为一组  ===> Union-Find ==> emailToID
	
	// Time: O(nlgn) n是所有account的长度之和
	public List<List<String>> accountsMerge(List<List<String>> accounts) {
        if (accounts == null || accounts.size() == 0)
            return new ArrayList<>();
        
        List<List<String>> result = new LinkedList<>();
        Map<String, String> emailName = new HashMap<>();
        Map<String, Set<String>> graph = new HashMap<>(); // 构建图
        for (List<String> account : accounts) {
            if (account.size() == 2) { // 自己跟自己也要连在一起（至少要把该点放到map中），
            			// 否则DFS的时候，这个account会被滤过
                emailName.put(account.get(1), account.get(0));
                graph.computeIfAbsent(account.get(1), 
                                      k -> new HashSet<>()).add(account.get(1));
                graph.computeIfAbsent(account.get(1),
                                      k -> new HashSet<>()).add(account.get(1));
                continue;
            }
            String name = "";
            for (int i = 0; i < account.size() - 1; i++) {
                if (i == 0) {
                    name = account.get(i);
                    continue;
                }
                graph.computeIfAbsent(account.get(i), 
                                      k -> new HashSet<>()).add(account.get(i + 1));
                graph.computeIfAbsent(account.get(i + 1),
                                      k -> new HashSet<>()).add(account.get(i));
                emailName.put(account.get(i), name);
            }
            emailName.put(account.get(account.size() - 1), name);
            
            // 上面的这一段也可以简写为把所有email属于同一个name的都连到第一个email上，包括自己
//            String name = "";
//            for (int i = 0; i <= account.size() - 1; i++) {
//                if (i == 0) {
//                    name = account.get(i);
//                    continue;
//                }
//                graph.computeIfAbsent(account.get(1), 
//                                      k -> new HashSet<>()).add(account.get(i));
//                graph.computeIfAbsent(account.get(i),
//                                      k -> new HashSet<>()).add(account.get(1));
//                emailName.put(account.get(i), name);
//            }
        }
        
        Set<String> visited = new HashSet<>();
        for (String key : graph.keySet()) { // 对每个key/email都要判断，因为这里面可以有多个graph
            if (visited.contains(key))
                continue;
            List<String> sol = new LinkedList<>();
            dfs(graph, key, sol, visited);
            Collections.sort(sol);
            sol.add(0, emailName.get(key)); // 把第一个name加回去
            
            result.add(sol);
        }
        
        return result;
    }
    
    public void dfs(Map<String, Set<String>> graph, String node, List<String> sol,
                   Set<String> visited) {
        visited.add(node);
        sol.add(node);
        for (String adj : graph.getOrDefault(node, new HashSet<>())) {
            if (visited.contains(adj))
                continue;
            dfs(graph, adj, sol, visited);
        }
    }
    
    public void dfsIterative(Map<String, Set<String>> graph, List<String> sol,
            Set<String> visited, Stack<String> stack) {
		 while (!stack.isEmpty()) {
		     String node = stack.pop();
		     sol.add(node);
		     //visited.add(node);
		     for (String adj : graph.getOrDefault(node, new HashSet<>())) {
		         if (visited.contains(adj))
		             continue;
		         visited.add(adj); // 注意顺序， 如果放在外面加，
		         		// 已经在stack的但是还没出栈还会被访问一遍，因为没出栈没被加到visited中，而实际上已经visited了
		         stack.push(adj);
		     }
		 }
	}
    
    
    public List<List<String>> accountsMergeUnionFind(List<List<String>> accounts) {
        Map<String, String> emailName = new HashMap<>();
        Map<String, Integer> emailId = new HashMap<>(); // 需要将email转换为id
        
        DSU dsu = new DSU(); // 新建一个Disjoint Set Union 类，处理 union-find， 包括： 初始化parents, findParents, union
        int id = 0;
        for (List<String> account : accounts) {
            String name = "";
            for (int i = 0; i < account.size(); i++) {
                if (i == 0) {
                    name = account.get(i);
                    continue;
                }
                emailName.put(account.get(i), name);
                if (!emailId.containsKey(account.get(i))) {
                    emailId.put(account.get(i), id++);
                }
                // 初始时，在同一个name下的这些email, 合并  ==> 需要并为一组
                dsu.union(emailId.get(account.get(1)), emailId.get(account.get(i)));
            }
        }
        
        Map<Integer, List<String>> ans = new HashMap<>();
        for (String email : emailName.keySet()) {// 接下来，找出每个email对应的parent, 
        			// 对具有同一个parent的归为一组，这就是同属于一个账号的emails
            int idx = dsu.findParent(emailId.get(email));
            ans.computeIfAbsent(idx, k -> new ArrayList<>()).add(email);
        }
        
        for (List<String> component : ans.values()) {// 取出每组的emails, sort, 再加上 name就好
            Collections.sort(component);
            component.add(0, emailName.get(component.get(0)));
        }
        
        return new ArrayList<>(ans.values());
    }
    
    class DSU {
        int[] parents;
        
        public DSU() {
            parents = new int[10001]; // 新建数组
            
            // 初始化parents
            for (int i = 0; i < 10001; i++) {
                parents[i] = i;
            }
        }
        
        // 给定一个val,找parent
        public int findParent(int node) {
            if (parents[node] == node)
                return node;
            int parent = parents[node]; // 开始找parent的parent
            parent = findParent(parents[node]);// compress path， 让所有属于同一个group的都指向最高的parent
            
            return parent;
        }
        
        public void union(int x, int y) { // 合并，！！！需要合并的x,y, 本来就是有关系的需要连在一起的（比如一条边的两个点），
        			//用union是看这两点的关系是不是已经在一组了（指向同一个parent了），不在就合并
            int px = findParent(x);
            int py = findParent(y);
            
            if (px != py)
                parents[px] = py; // 将 px, py 合并为父子关系， 不是 px=py; 这里合并是完成父子关系
        }
    }
    
    
    class DSU1 {
        Map<String, String> parents;
        public DSU1(){
            this.parents = new HashMap<>();
        }
        
        public String findParent(String val) {
            if (parents.get(val).equals(val))
                return val;
            String p = parents.get(val);
            p = findParent(p);
            return p;
        }
        
        public void union(String s1, String s2) {
            String p1 = findParent(s1);
            String p2 = findParent(s2);
            
            if (!p1.equals(p2)) {
                parents.put(p1, p2);
            }
        }
    }
    public List<List<String>> accountsMerge1(List<List<String>> accounts) {
        Map<String, String> nameEmail = new HashMap<>();
        
        DSU1 dsu = new DSU1();
        Set<String> emails = new HashSet<>();
        for (List<String> account : accounts) {
            String name = "";
            for (int i = 0; i < account.size(); i++) {
                if (i == 0) {
                    name = account.get(0);
                    continue;
                }
                dsu.parents.put(account.get(i), account.get(i));

                nameEmail.put(account.get(i), name);
                emails.add(account.get(i));
                if (i != account.size() - 1) {
                	dsu.parents.put(account.get(i + 1), account.get(i + 1));
                	dsu.union(account.get(i), account.get(i + 1));
                }
                
            }
//            nameEmail.put(account.get(account.size() - 1), name);
//            emails.add(account.get(account.size() - 1));
        }
        
        Map<String, List<String>> group = new HashMap<>();
        for (String email : nameEmail.keySet()) {
            String p = dsu.findParent(email);
            group.computeIfAbsent(p, k -> new LinkedList<>()).add(email);
        }
        
        List<List<String>> result = new LinkedList<>();
        for (List<String> aux : group.values()) {
            Collections.sort(aux);
            aux.add(0, nameEmail.get(aux.get(0)));
            result.add(aux);
        }
        return result;
        
    }
    
    public static void main(String[] args) {
    	List<List<String>> accounts = Arrays.asList(
    			Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com"), 
    			Arrays.asList("John", "johnnybravo@mail.com"),
    			Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"),
    			Arrays.asList("Mary", "mary@mail.com"));
    	
    	new AccountsMerge().accountsMerge1(accounts);//accountsMerge1, accountsMergeUnionFind
    }
    
}
