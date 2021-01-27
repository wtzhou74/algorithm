package amazon;

import java.util.LinkedList;
import java.util.List;

//Design a Snake game that is played on a device with screen size = width x height. Play the game online if you are not familiar with the game.
//
//The snake is initially positioned at the top left corner (0,0) with length = 1 unit.
//
//You are given a list of food's positions in row-column order. When a snake eats the food, its length and the game's score both increase by 1.
//
//Each food appears one by one on the screen. For example, the second food will not appear until the first food was eaten by the snake.
//
//When a food does appear on the screen, it is guaranteed that it will not appear on a block occupied by the snake.
//
//Example:
//
//Given width = 3, height = 2, and food = [[1,2],[0,1]].
//
//Snake snake = new Snake(width, height, food);
//
//Initially the snake appears at position (0,0) and the food at (1,2).
//
//|S| | |
//| | |F|
//
//snake.move("R"); -> Returns 0
//
//| |S| |
//| | |F|
//
//snake.move("D"); -> Returns 0
//
//| | | |
//| |S|F|
//
//snake.move("R"); -> Returns 1 (Snake eats the first food and right after that, the second food appears at (0,1) )
//
//| |F| |
//| |S|S|
//
//snake.move("U"); -> Returns 1
//
//| |F|S|
//| | |S|
//
//snake.move("L"); -> Returns 2 (Snake eats the second food)
//
//| |S|S|
//| | |S|
//
//snake.move("U"); -> Returns -1 (Game over because snake collides with border)


// !!!!!关键一点是在移动的过程中，需要记录snake所占的位置，但是这里需要意识到的一点是：
//  移动过程中，中间部分是不变的，只有头往要去的方向移动了一位，尾部没了 （其实就是后一个占到了前一个的位置）
// ====> 当吃到food的时候，只在head增加一个， 如果没吃到food,就不停地 addHead/removeTail
public class DesignSnakeGame {

	class Point {
        int r;
        int c;
        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    private int width;
    private int height;
    private int[][] food;
    int score = 0;
    List<Point> snake = new LinkedList<>();
    public DesignSnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;
        this.snake.add(new Point(0,0));
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {
        Point curr = new Point(snake.get(0).r, snake.get(0).c);
        switch(direction) {
            case "U": curr.r--; break;
            case "L": curr.c--; break;
            case "R": curr.c++; break;
            case "D": curr.r++; break;   
        }
        
        if (curr.r < 0 || curr.c < 0 || curr.r >= height
           || curr.c >= width)
            return -1;
        //System.out.println(snake.size());
        // ！！！！不要判断最后一个， 因为在移动了之后，尾部的那个已经移走了（remove掉了），位置空出来了，所以这里需要 -1
        for (int i = 1; i < snake.size() - 1; i++) {
            Point temp = snake.get(i);
            if (curr.r == temp.r && curr.c == temp.c)
                return -1;
        }
        snake.add(0, new Point(curr.r, curr.c));
        // 要看是否所有的food都已经吃了，吃了之后就是不停的移动而已，add/remove
        if (score < food.length) {
            int[] foodCell = food[score];
            if (curr.r == foodCell[0] && curr.c == foodCell[1]) {
                score++;
            } else {
                snake.remove(snake.size() - 1);
            }
            // snake.add(0, new Point(curr.r, curr.c));
        } 
        else {
            snake.remove(snake.size() - 1);
            //snake.add(0, new Point(curr.r, curr.c));
        }
        
        
        return score;
    }
}
