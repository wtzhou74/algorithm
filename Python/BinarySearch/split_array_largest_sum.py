# -*- coding: utf-8 -*-
# =============================================================================
# Given an array which consists of non-negative integers and an integer m, you can split the array into m non-empty continuous subarrays.
# Write an algorithm to minimize the largest sum among these m subarrays.
# 
# Note:
# If n is the length of array, assume the following constraints are satisfied:
# 
# 1 ≤ n ≤ 1000
# 1 ≤ m ≤ min(50, n)
# Examples:
# 
# Input:
# nums = [7,2,5,10,8]
# m = 2
# 
# Output:
# 18
# 
# Explanation:
# There are four ways to split nums into two subarrays.
# The best way is to split it into [7,2,5] and [10,8],
# where the largest sum among the two subarrays is only 18.
# =============================================================================

# Time: O(nlogs): s is the sum of nums
# Space: O(1)
def splitArray(nums: list, m: int) -> int:
    
    def isLargestSum(nums, target, m):
        cnt, curr_sum = 1, 0 # default group is 1 since the orignal array is 1 group 
        for num in nums:
            curr_sum += num
            if curr_sum > target:
                curr_sum = num
                cnt += 1
        return cnt <= m # sum is too big

    left, right = max(nums), sum(nums)
    while left <= right:
        mid = left + (right - left) // 2
        # check if mid is the larest sum among the subarrays
        if isLargestSum(nums, mid, m):
            right = mid - 1
        else:
            left = mid + 1
    return left


if __name__ == '__main__':
    print(splitArray([7,2,5,10,8], 2))