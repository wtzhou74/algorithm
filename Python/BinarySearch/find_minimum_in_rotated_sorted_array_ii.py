# -*- coding: utf-8 -*-
# =============================================================================
# Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
# 
# (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).
# 
# Find the minimum element.
# 
# The array MAY CONTAIN DUPLICATES.
# 
# Example 1:
# 
# Input: [1,3,5]
# Output: 1
# Example 2:
# 
# Input: [2,2,2,0,1]
# Output: 0
# Note:
# 
# This is a follow up problem to Find Minimum in Rotated Sorted Array.
# Would allow duplicates affect the run-time complexity? How and why?
# =============================================================================

def findMin(nums: list) ->int:
    return min(nums)

# Time: O(logn) ~ O(n)
# Space: O(1)
def sol2(nums) -> int:
    left, right = 0, len(nums) - 1
    while left < right:
        mid = left + (right - left) // 2
# =============================================================================
#         if nums[mid] == nums[left]:
#             left += 1
#         if nums[mid] > nums[left]:
#             # left side is sorted and pivot is on right side
#         ==> WRONG CONCLUSION !!!!!! [4,5,6,7,0,1,2] 1 > 0
# =============================================================================
        if nums[mid] == nums[right]:
             right -= 1 # for duplicates
        elif nums[mid] > nums[right]:
            left = mid + 1
        else:
            right = mid
       
    return nums[left]

def sol3(nums) -> int:
    left, right = 0, len(nums) - 1
    while left < right and nums[left] >= nums[right]: # pivot should be on right since it is sorted in ascending order
        mid = left + (right - left) // 2
        if nums[mid] == nums[left]:
            left += 1
        elif nums[mid] > nums[left]:
            left = mid + 1
        else:
            right = mid
            
    return nums[left]

if __name__ == '__main__':
    print(sol3([2,2,2,0,1]))