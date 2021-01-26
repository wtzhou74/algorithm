
public class BinarySearchTest {

	public int binarySearch(int[] nums, int target, int left, int right) {
        int i = 0, j = nums.length - 1;
        while (i < j) {
            int mid = i + (j - i) / 2;
            // int mid = i + (j - i + 1) / 2; //取中位数的时候向上取整
            if (nums[mid] >= target) { // 向上取值，即比当前值大，比如找之前值大的插入位置
                j = mid;// mid值太大了，所以先左移
            } else {
                i = mid + 1;
            }
        }
        System.out.println(i + " " + nums[i]); // 4， 7
        return i;
    }
	
	public int binarySearch1(int[] nums, int target, int left, int right) {
        int i = 0, j = nums.length - 1;
        while (i < j) {
            int mid = i + (j - i) / 2;
            // int mid = i + (j - i + 1) / 2; //取中位数的时候向上取整
            if (nums[mid] <= target) { // 向下取值
                i = mid; //mid值太小，先右移
            } else {
                j = mid - 1;
            }
        }
        System.out.println(i + " " + nums[i]); // 3， 5
        return i;
    }
	
	public static void main(String[] args) {
		int[] nums = new int[] {1,2,3,5,7,10};
		new BinarySearchTest().binarySearch1(nums, 6, 0, nums.length - 1);
	}
}
