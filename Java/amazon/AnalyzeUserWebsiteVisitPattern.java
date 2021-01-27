package amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

//We are given some website visits: the user with name username[i] visited the website website[i] at time timestamp[i].
//
//A 3-sequence is a list of websites of length 3 sorted in ascending order by the time of their visits.  (The websites in a 3-sequence are not necessarily distinct.)
//
//Find the 3-sequence visited by the largest number of users. If there is more than one solution, return the lexicographically smallest such 3-sequence.
//
// 
//
//Example 1:
//
//Input: username = ["joe","joe","joe","james","james","james","james","mary","mary","mary"], timestamp = [1,2,3,4,5,6,7,8,9,10], website = ["home","about","career","home","cart","maps","home","home","about","career"]
//Output: ["home","about","career"]
//Explanation: 
//The tuples in this example are:
//["joe", 1, "home"]
//["joe", 2, "about"]
//["joe", 3, "career"]
//["james", 4, "home"]
//["james", 5, "cart"]
//["james", 6, "maps"]
//["james", 7, "home"]
//["mary", 8, "home"]
//["mary", 9, "about"]
//["mary", 10, "career"]
//The 3-sequence ("home", "about", "career") was visited at least once by 2 users.
//The 3-sequence ("home", "cart", "maps") was visited at least once by 1 user.
//The 3-sequence ("home", "cart", "home") was visited at least once by 1 user.
//The 3-sequence ("home", "maps", "home") was visited at least once by 1 user.
//The 3-sequence ("cart", "maps", "home") was visited at least once by 1 user.
// 
//
//Note:
//
//3 <= N = username.length = timestamp.length = website.length <= 50
//1 <= username[i].length <= 10
//0 <= timestamp[i] <= 10^9
//1 <= website[i].length <= 10
//Both username[i] and website[i] contain only lowercase characters.
//It is guaranteed that there is at least one user who visited at least 3 websites.
//No user visits two websites at the same time.


// Note： 1） 要找的是一个 3-sequece, 而这个 sequence是 a list of sites，而且必须同属于同一个user， 
//				==> 所以这里要对每个 user, 列出其所有的 seq; 最后对这些 seq排序，   ！！！而不是简单对sites出现的个数排序
//		2） 需要先把pairs 按 时间 ascending 排序， ==> 题目中列出的是sorted，但实际不一定，所以要先按time sort, 
//				==> 否则出来的结果，内容正确，但顺序就不一定了
//		3） 处理的对象是 3-sequence (以user位单位，不是所有sites中的组合)， 不是某个具体的site
public class AnalyzeUserWebsiteVisitPattern {

	class Pair{  // 每个field的 data type不一样，这里数组就不能用了，所以新定义个object
        String name;
        int time;
        String site;
        public Pair(String name, int time, String site) {
            this.name = name;
            this.time = time;
            this.site = site;
        }
    }
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        int i = 0;
        List<Pair> pairs = new ArrayList<>();
        while (i < username.length) {
            Pair pair = new Pair(username[i], timestamp[i], website[i]);
            pairs.add(pair);
            i++;
        }
        // 先按时间排序， 不按要求排序，下面出来的sequence结论正确，但顺序就不一定对了
        Collections.sort(pairs, (a,b) -> a.time - b.time);
        
        Map<String, List<String>> map = new HashMap<>(); 
        // 每个user的所有site, 之后要列出其所有的 3-sequence sites的序列
        for (Pair pair : pairs) {
            map.computeIfAbsent(pair.name, k -> new ArrayList<>()).add(pair.site);
        }
        
        Map<String, Integer> seqCount = new HashMap<>();
        int max = Integer.MIN_VALUE;
        String s = "";
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
        	// 对每个user， 列出其所有的 3-sequences sites, 最后统计这些 sequence的数目 ！！！
        	//									不是某个具体的site
            Set<String> seqs = findSeq4User(entry.getValue());
            for (String seq : seqs) {
                seqCount.put(seq, seqCount.getOrDefault(seq, 0) + 1);// 当前的这些seq都是同一个user的，所以 +1
                // 接下来的操作保证我们 map里面只放一个（两个）有用的
                if (seqCount.get(seq) > max) {
                    max = seqCount.get(seq);
                    s = seq;
                } else if (seqCount.get(seq) == max && seq.compareTo(s) < 0) {
                					// 对一样个数的，取 lexicographically smallest (留下ASCII小的)
                    s = seq;
                }
            }
        }
        
        String[] sites = s.split("\\,");
        
        return Arrays.asList(sites);
    }
    
    // 对每个用户，取出其所有的 3-sequence seqs
    public Set<String> findSeq4User(List<String> sites) {
        Set<String> set = new HashSet<>();
        int n = sites.size();
     // 三个组合，不是直接 for(i = 0; i < size - 2; i++)就能覆盖全部的
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    set.add(sites.get(i) + "," + sites.get(j) + "," + sites.get(k));
                }
            }
        }
        return set;
    }
    
    
    class Pattern {
        //String user;
        int time;
        String site;
        
        public Pattern(int time, String site) {
            //this.user = user;
            this.time = time;
            this.site = site;
        }
    }
    public List<String> mostVisitedPattern1(String[] username, int[] timestamp, String[] website) {
        Map<String, List<Pattern>> map = new HashMap<>();
        for (int i = 0; i < username.length; i++) {
            map.computeIfAbsent(username[i], 
                                k -> new LinkedList<>())
                            .add(new Pattern(timestamp[i], website[i]));
        }
        Map<String, Set<String>> siteMap = new HashMap<>();
        for (Map.Entry<String, List<Pattern>> entry : map.entrySet()) {
            String name = entry.getKey();
            List<Pattern> ps = entry.getValue();
            Collections.sort(ps, (a, b) -> a.time - b.time);
            siteMap.put(name, new HashSet<>());
            
            // 三个组合，不是直接 for(i = 0; i < size - 2; i++)就能覆盖全部的
            for (int i = 0; i < ps.size() - 2; i++) {
                for (int j = i + 1; j < ps.size() - 1; j++) {
                    for (int k = j + 1; k < ps.size(); k++) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(ps.get(i).site + " ");
                        sb.append(ps.get(j).site + " ");
                        sb.append(ps.get(k).site);
                        //System.out.println(sb.toString());
                        siteMap.get(name).add(sb.toString());
                    }
                }
                
            }
        }
        
        Map<String, Integer> patternMap = new HashMap<>();
        for (Map.Entry<String, Set<String>> sites : siteMap.entrySet()) {
           // Map<String, Integer> aux = new HashMap<>();
            for (String pattern : sites.getValue()) {
                //System.out.println(pattern);
                patternMap.put(pattern, patternMap.getOrDefault(pattern, 0) + 1);
            }
        }
        PriorityQueue<String> pq = 
            new PriorityQueue<>((a, b) ->
                                {if(patternMap.get(a) == patternMap.get(b))
                                                       return b.compareTo(a);
                                                       else
                                                       return patternMap.get(a) - patternMap.get(b);});
        for (Map.Entry<String, Integer> entry : patternMap.entrySet()) {
            //System.out.println(entry.getKey());
            pq.offer(entry.getKey());
            if (pq.size() > 1)
                pq.poll();
        }
        String result = pq.poll();
        //System.out.println(result);
        return Arrays.asList(result.split(" "));
        
    }
}
