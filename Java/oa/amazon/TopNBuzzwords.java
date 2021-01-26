package oa.amazon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

//You work on a team whose job is to understand the most sought after toys for the holiday season. A teammate of yours has built a webcrawler that extracts a list of quotes about toys from different articles. You need to take these quotes and identify which toys are mentioned most frequently. Write an algorithm that identifies the top N toys out of a list of quotes and list of toys.
//
//Your algorithm should output the top N toys mentioned most frequently in the quotes.
//
//Input:
//The input to the function/method consists of five arguments:
//
//numToys, an integer representing the number of toys
//topToys, an integer representing the number of top toys your algorithm needs to return;
//toys, a list of strings representing the toys,
//numQuotes, an integer representing the number of quotes about toys;
//quotes, a list of strings that consists of space-sperated words representing articles about toys
//
//Output:
//Return a list of strings of the most popular N toys in order of most to least frequently mentioned
//
//Note:
//The comparison of strings is case-insensitive. If the value of topToys is more than the number of toys, return the names of only the toys mentioned in the quotes. If toys are mentioned an equal number of times in quotes, sort alphabetically.
//
//Example 1:
//
//Input:
//numToys = 6
//topToys = 2
//toys = ["elmo", "elsa", "legos", "drone", "tablet", "warcraft"]
//numQuotes = 6
//quotes = [
//"Elmo is the hottest of the season! Elmo will be on every kid's wishlist!",
//"The new Elmo dolls are super high quality",
//"Expect the Elsa dolls to be very popular this year, Elsa!",
//"Elsa and Elmo are the toys I'll be buying for my kids, Elsa is good",
//"For parents of older kids, look into buying them a drone",
//"Warcraft is slowly rising in popularity ahead of the holiday season"
//];
//
//Output:
//["elmo", "elsa"]
//
//Explanation:
//elmo - 4
//elsa - 4
//"elmo" should be placed before "elsa" in the result because "elmo" appears in 3 different quotes and "elsa" appears in 2 different quotes.

public class TopNBuzzwords {

	public List<String> topNBuzzwords(int numToys, int topToys, String[] toys, int numQuotes, String[] quotes ) {
		List<String> res = new ArrayList<>();
		// here we need to consider both name's occurrence and quote's occurrences of that name === > Map<String, int[]>
		Map<String, int[]> map = new HashMap<>(); // int[2]; 1- total occurrence of name; 2 - total occurrence of quote
		// we need to sort the toys   ===> initialize the map with toys's name
		for (String toy : toys) {
			//！！！ 除了需要记录toy出现的频率，还得同时记下这个 toy在哪几个 quotes中出现
			map.put(toy, new int[2]); //int[] t = new int[2];
		}
		
		// parsing quotes
		for (String quote : quotes) {
			// for each quote, dealing both name's occurrence and the occurrence of name in quote
			String[] words = quote.split("\\W+"); // split the quote by non-alphanumeric character
			Set<String> set = new HashSet<>();
			for (String word : words) {
				// case sensitive
				String lowcase = word.toLowerCase();
				if (map.containsKey(lowcase)) {
					int[] freq = map.get(lowcase);
					freq[0]++;// toy name occurrence
					if (!set.contains(lowcase)) {
						freq[1]++;
						set.add(lowcase);
					}
				}
			}
		}
		
		// Fetch the top N toys name
		// we need to sort the toys name by two kinds of occurrences
		// construct MIN HEAP
		PriorityQueue<String> heap = new PriorityQueue<>(new Comparator<String>() {
			public int compare(String str1, String str2) {
				int[] freq1 = map.get(str1);
				int[] freq2 = map.get(str2);
				if (freq1[0] != freq2[0]) {// min heap  ==> poll when size > K, the smallest will pop first
					return freq1[0] - freq2[0];
				}
				// now freq1[0] == freq2[0]
				if (freq1[1] != freq2[1]) {
					return freq1[1] - freq2[1];
				}
				return str2.compareTo(str1);// if the occurrences are same, we should keep the toys in alphabetical order, so, we should sort it in REVERSED ORDER.
			}
		});
		
		if (topToys > numToys) {
			// return the toys mentioned in quotes
			for (String toy : map.keySet()) {
				if (map.get(toy)[0] > 0)
					heap.offer(toy); // we only add the toys mentioned in quotes
			}
		} else {
			for (String toy : toys) {
				heap.add(toy);
				
				// we just add topN instead of ALL
				if (heap.size() > topToys) // This is why we need to construct a MIN HEAP  
					heap.poll();  // poll if the size larger than K   ===> Time: O(NlgK) instead of O(NlgN)
			}
		}
		while (!heap.isEmpty()) {
			res.add(heap.poll());
		}
		
		// reverse the list since the result should be sorted in order of most to least frequently mentioned
		Collections.reverse(res);
		
		return res;
	}
	
	public static void main(String[] args) {
		String[] quotes = new String[] {
				"Elmo is the hottest of the season! Elmo will be on every kid's wishlist!",
				"The new Elmo dolls are super high quality",
				"Expect the Elsa dolls to be very popular this year, Elsa!",
				"Elsa and Elmo are the toys I'll be buying for my kids, Elsa is good",
				"For parents of older kids, look into buying them a drone",
				"Warcraft is slowly rising in popularity ahead of the holiday season"	
		};
		String[] toys = new String[] {
				"elmo", "elsa", "legos", "drone", "tablet", "warcraft"	
		};
		new TopNBuzzwords().topNBuzzwords(6, 2, toys, 6, quotes);
	}
}
