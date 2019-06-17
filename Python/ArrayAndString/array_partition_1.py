# -*- coding: utf-8 -*-
# =============================================================================
# Given an array of 2n integers, your task is to group these integers into n pairs of integer, say (a1, b1), (a2, b2), ..., (an, bn) which makes sum of min(ai, bi) for all i from 1 to n as large as possible.
# 
# Example 1:
# Input: [1,4,3,2]
# 
# Output: 4
# Explanation: n is 2, and the maximum sum of pairs is 4 = min(1, 2) + min(3, 4).
# Note:
# 1. n is a positive integer, which is in the range of [1, 10000].
# 2. All the integers in the array will be in the range of [-10000, 10000].
# =============================================================================

# Time: O(nlogn)
# Space: O(n)
def arrayPairSum(nums) -> int:
    """
    :type nums:List[int]
    :rtype int
    """
# =============================================================================
#     nums.sort() # sort orignal list in-place  # Space O(1)
#     return sum([nums[i] for i in range(0, len(nums), 2)])
# =============================================================================
    return sum(sorted(nums)[::2])

# Time: O(nlogn)
# Space: O(n)
def arrayPairSum0(nums) -> int:
    nums = sorted(nums) # return a sorted list without affecting original one
    result = 0
    for i in range(0, len(nums), 2):
        result += nums[i]
    return result

# Time: O(r), r is the range size of the integers
# Space: O(r)
def arrayPairSum1(nums) -> int:
    
    num_list = [0] * 2001
    for num in nums:
        num_list[num + 1000] = num
    odd = True
    total = 0
    for i in range(len(num_list)):
        if num_list[i] != 0 and odd:
            total += num_list[i]
            odd = False
        else:
            odd = True
    return total
            
    
if __name__ == '__main__':
    print(arrayPairSum([1,4,3,2]))
    