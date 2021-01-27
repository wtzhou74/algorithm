package amazon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

//Implement FreqStack, a class which simulates the operation of a stack-like data structure.
//
//FreqStack has two functions:
//
//push(int x), which pushes an integer x onto the stack.
//pop(), which removes and returns the most frequent element in the stack.
//If there is a tie for most frequent element, the element closest to the top of the stack is removed and returned.
// 
//
//Example 1:
//
//Input: 
//["FreqStack","push","push","push","push","push","push","pop","pop","pop","pop"],
//[[],[5],[7],[5],[7],[4],[5],[],[],[],[]]
//Output: [null,null,null,null,null,null,null,5,7,5,4]
//Explanation:
//After making six .push operations, the stack is [5,7,5,7,4,5] from bottom to top.  Then:
//
//pop() -> returns 5, as 5 is the most frequent.
//The stack becomes [5,7,5,7,4].
//
//pop() -> returns 7, as 5 and 7 is the most frequent, but 7 is closest to the top.
//The stack becomes [5,7,5,4].
//
//pop() -> returns 5.
//The stack becomes [5,7,4].
//
//pop() -> returns 4.
//The stack becomes [5,7].
// 
//
//Note:
//
//Calls to FreqStack.push(int x) will be such that 0 <= x <= 10^9.
//It is guaranteed that FreqStack.pop() won't be called if the stack has zero elements.
//The total number of FreqStack.push calls will not exceed 10000 in a single test case.
//The total number of FreqStack.pop calls will not exceed 10000 in a single test case.
//The total number of FreqStack.push and FreqStack.pop calls will not exceed 150000 across all test cases.
public class MaximumFrequencyStack {

	// 记录 frequency  ==> Map; 同时 同样的 frequency可以对应多个不同的对象
	//  又要保证Push进来的顺序 ==> stack, 
	//			====> !!!!但这里要注意的是 对同一个值，其从 freq = 1, 2, 3, 到当前fre都要保存一份， 
	//				===>  如果通过 remove/再store到freq-1对应的stack中，这样原来的顺序就被打乱了，所以，这里pop，只需删除其当前freq对应的值就可以了
	//					===> 对freq-1，其对应的stack中在push的时候已经记录了
	//    ===> 高freq先出  ===> TreeMap / 单独用一个 maxFreq变量来记录(就不用TreeMap排序了)
	TreeMap<Integer, Stack<Integer>> map = null; // freq - stack  
					// （按push进来的顺序按freq排好了，对freq=3的pop后，freq=2中的stack在push的时候就记录了，所以不用担心freq-1，该值在哪里）
					// 而且对freq相同的，保持了push进来的顺序了， pop后再取改stack，就破坏原有顺序了
    Map<Integer, Integer> freqMap = null; // key-freq
    
    int maxFreq = 0; // 这样上面用个普通 Map就可以
    public MaximumFrequencyStack() {
        map = new TreeMap<>((a, b) -> b - a);
        freqMap = new HashMap<>();
    }
    
    public void push(int x) {
        freqMap.put(x, freqMap.getOrDefault(x, 0) + 1);
        map.computeIfAbsent(freqMap.get(x), k -> new Stack<>()).push(x);
        
//        freqMap.put(x, freqMap.getOrDefault(x, 0) + 1);
//        map.computeIfAbsent(freqMap.get(x), k -> new Stack<>()).push(x);
//        if (freqMap.get(x) > maxFreq) // 更新下当前最大的Freq
//            maxFreq = freqMap.get(x);
    }
    
    public int pop() {
        int first = map.firstKey();
        int x = map.get(first).pop();
        if (map.get(first).size() == 0)
            map.remove(first);
        if (first == 1)
            freqMap.remove(x);
        else
            freqMap.put(x, first - 1);
        
//        int x = map.get(maxFreq).pop();
//        if (maxFreq == 1)
//            freqMap.remove(x); // 也可以不remove，freq是0不会用到，但会浪费space
//        else
//            freqMap.put(x, maxFreq - 1);
//        if (map.get(maxFreq).size() == 0) {
//            //map.remove(maxFreq);
//            maxFreq--;
//        }       
        
        return x;
    }
}
