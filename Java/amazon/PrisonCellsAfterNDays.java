package amazon;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//There are 8 prison cells in a row, and each cell is either occupied or vacant.
//
//Each day, whether the cell is occupied or vacant changes according to the following rules:
//
//If a cell has two adjacent neighbors that are both occupied or both vacant, then the cell becomes occupied.
//Otherwise, it becomes vacant.
//(Note that because the prison is a row, the first and the last cells in the row can't have two adjacent neighbors.)
//
//We describe the current state of the prison in the following way: cells[i] == 1 if the i-th cell is occupied, else cells[i] == 0.
//
//Given the initial state of the prison, return the state of the prison after N days (and N such changes described above.)
//
// 
//
//Example 1:
//
//Input: cells = [0,1,0,1,1,0,0,1], N = 7
//Output: [0,0,1,1,0,0,0,0]
//Explanation: 
//The following table summarizes the state of the prison on each day:
//Day 0: [0, 1, 0, 1, 1, 0, 0, 1]
//Day 1: [0, 1, 1, 0, 0, 0, 0, 0]
//Day 2: [0, 0, 0, 0, 1, 1, 1, 0]
//Day 3: [0, 1, 1, 0, 0, 1, 0, 0]
//Day 4: [0, 0, 0, 0, 0, 1, 0, 0]
//Day 5: [0, 1, 1, 1, 0, 1, 0, 0]
//Day 6: [0, 0, 1, 0, 1, 1, 0, 0]
//Day 7: [0, 0, 1, 1, 0, 0, 0, 0]
//
//Example 2:
//
//Input: cells = [1,0,0,1,0,0,1,0], N = 1000000000
//Output: [0,0,1,1,1,1,1,0]
// 
//
//Note:
//
//cells.length == 8
//cells[i] is in {0, 1}
//1 <= N <= 10^9
public class PrisonCellsAfterNDays {

	// 对N很大的时候会TLE  ===> 根据题目描述考虑会有 repeat, 先看cycle的大小 ， 然后 N%cycle
	// 另外对cells,其状态都是 0， 1 ==> 可考虑位运算
	public int[] prisonAfterNDays(int[] cells, int N) {
        int[] aux = new int[8];
        
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j < 7; j++) {
                if (cells[j - 1] == cells[j + 1]) {
                    aux[j] = 1;
                } else {
                    aux[j] = 0;
                }
            }
            aux[0] = 0;
            aux[7] = 0;
            for (int k = 0; k < 8; k++) {
                cells[k] = aux[k];
            }
        }
        return cells;
    }
	
	// 先看 cycle 并求 cycle的大小  ==> 不同输入对应的 cycle size是不一样的
	// Time (O(K * min(N, 2^k))) // 2^k possible states
	public int[] prisonAfterNDays1(int[] cells, int N) {
        int count = 0;
        Set<String> set = new HashSet<>(); // !!!这里不能直接跟初始cells比较，这里循环开始不一定从这里
        boolean isCycle = false;
        for (int i = 1; i <= N; i++) {
            //cells = nextDayState(cells); // 这个错误的，会导致下面N%=cycle的开始cells是循环的开始值
        									// 而真正要的开始值是上一个循环的结束，其作为初始值
        									// 这样我们即找到了cycle开始的地方以及cycle的size
        									// !!!这里第0天，是上一循环的结束点，要从这里开始
            int[] next = nextDayState(cells);
            String s = Arrays.toString(next);
            if (set.contains(s)) { // 找到重复了
                isCycle = true;
                break;
            }
            count++;
            set.add(s);
            cells = next;
        }
        if (isCycle) {
            N %= count;
            for (int i = 1; i <= N; i++) {
                cells = nextDayState(cells);
            }
        }
        
        return cells;
    }
    
	// Time O(N)
    public int[] nextDayState(int[] cells) {
        int[] aux = new int[8];
        for (int i = 1; i < 7; i++) {
            if (cells[i - 1] == cells[i + 1])
                aux[i] = 1;
            else aux[i] = 0;
        }
        return aux;
    }
    
    
    // 借助位运算求解next day的状态 ==> 只需一次位运算 O（1）
    // 总的 O（min(N, 2^k)）
    public int[] prisonAfterNDays2(int[] cells, int N) {
        int count = 0;
        Set<Integer> set = new HashSet<>();
        boolean isCycle = false;
        int stateBitmap = 0x0;
        // 把cells 转换成 bitMap，这里只能一位一位处理， 因为 cells里面的值是INT的0，1，不是 bit
        for (int cell : cells) {
            stateBitmap <<= 1; // 保证最后一位是0， 这样 “或” 运算后，最后一位是什么就是什么
            stateBitmap = (stateBitmap | cell);
        }
        
        for (int i = 1; i <= N; i++) {
            //cells = nextDayState(cells);
            int next = nextDayState(stateBitmap);
            //String s = Arrays.toString(next);
            if (set.contains(next)) {
                isCycle = true;
                break;
            }
            count++;
            set.add(next);
            stateBitmap = next;
        }
        if (isCycle) {
            System.out.println(count);
            N %= count;
            for (int i = 1; i <= N; i++) {
                stateBitmap = nextDayState(stateBitmap);
            }
        }
        
        // conver stateBitmap back to cells  ==> 也是得一位一位处理
        for (int i = cells.length - 1; i >= 0; i--) {
            cells[i] = (stateBitmap & 0x1);
            stateBitmap >>= 1;
        }
        
        return cells;
    }
    
    // Time: O(1)
    public int nextDayState(int stateBitmap) {
    	// 根据rule, 得出下面的位运算表达式 ==> 参考 PrisonCellsAfterNDays.png
    	// !!!取反异或结果的
        stateBitmap = ~(stateBitmap << 1) ^ (stateBitmap >> 1);
        // set the head and tail to 0
        stateBitmap = stateBitmap & 0x7e; // 01111110
        return stateBitmap;
    }
    
    public static void main(String[] args) {
    	new PrisonCellsAfterNDays().prisonAfterNDays1(new int[] {1,1,0,1,1,0,1,1}, 10);
    }
}
