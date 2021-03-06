# -*- coding: utf-8 -*-
# =============================================================================
# Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
# 
# (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).
# 
# Find the minimum element.
# 
# You may assume NO DUPLICATEE exists in the array.
# 
# Example 1:
# 
# Input: [3,4,5,1,2] 
# Output: 1
# Example 2:
# 
# Input: [4,5,6,7,0,1,2]
# Output: 0
# =============================================================================

def findMin(nums) -> int:
    if not nums:
        return -1
    left, right = 0, len(nums) - 1
    while left < right:
        mid = left + (right - left) // 2
        # left side sorted and the pivot is on the right side
        if nums[mid] > nums[right]:
            left = mid + 1
        else:
            right = mid # mid might be pivot
    return nums[left]
            
def findMin0(nums) -> int:
    if not nums:
        return -1
    return min(nums)
            