# -*- coding: utf-8 -*-
# =============================================================================
# Given an array of integers nums sorted in ascending order, find the starting and ending position of a given target value.
# 
# Your algorithm's runtime complexity must be in the order of O(log n).
# 
# If the target is not found in the array, return [-1, -1].
# 
# Example 1:
# 
# Input: nums = [5,7,7,8,8,10], target = 8
# Output: [3,4]
# Example 2:
# 
# Input: nums = [5,7,7,8,8,10], target = 6
# Output: [-1,-1]
# =============================================================================

def searchRange(nums, target) -> list:
    if not nums:
        return [-1, -1]
    left, right = 0, len(nums) - 1
    # binary search the left-most index
    left_most = helper(nums, left, right, target)
    # target is not available in nums
    # in ascending order => left = mid + 1
    if left_most >= len(nums) or nums[left_most] != target:
        return [-1, -1]
    
    # binary search the right-most index
    right_most = helper1(nums, left, right, target)
    return [left_most, right_most]
            
def helper(nums, left, right, target):
    while left <= right:
        mid = left + (right - left) // 2
        if nums[mid] == target:
            # DONOT RETURN
            # move left-side
            right = mid - 1 # the return is LEFT instead of MID
        elif nums[mid] > target:
            right = mid - 1
        else:
            left = mid + 1
            
    return left

def helper1(nums, left, right, target):
    while left <= right:
        mid = left + (right - left) // 2
        if nums[mid] == target:
            # DONOT RETURN
            # move right-side
            left = mid + 1
        elif nums[mid] > target:
            right = mid - 1
        else:
            left = mid + 1
    return left - 1

if __name__ == '__main__':
    rst = searchRange([5,7,7,8,8,10], 8)
    for i in rst:
        print(i)