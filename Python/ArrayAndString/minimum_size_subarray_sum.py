# -*- coding: utf-8 -*-
# =============================================================================
# Given an array of n positive integers and a positive integer s, find the minimal length of a contiguous subarray of which the sum ≥ s. If there isn't one, return 0 instead.
# 
# Example: 
# 
# Input: s = 7, nums = [2,3,1,2,4,3]
# Output: 2
# Explanation: the subarray [4,3] has the minimal length under the problem constraint.
# Follow up:
# If you have figured out the O(n) solution, try coding another solution of which the time complexity is O(n log n). 
# =============================================================================

def minSubArrayLen(s, nums) -> int:
    """
    :type s:int
    :type nums:List
    :rtype int
    """
    # TIME LIMIT EXCEED
    if not nums: return 0
    if sum(nums) < s: return 0
    result = len(nums)
    for i in range(len(nums)):
        j = i
        length = 0
        temp_sum = 0
        while j < len(nums) and temp_sum < s:
            temp_sum += nums[j]
            length += 1
            j += 1
        if temp_sum >= s: # record VALID length
            result = min(result, length) # NOTICE sum(nums) < s !!!!!!
    return result

# Time: O(n)
# Space: O(1)
def minSubArrayLen0(s, nums) ->int:
    """
    :type s:int
    :type nums: List
    :rtype int
    """
    sum = 0
    start = 0
    min_size = float("inf") # useful for finding lowest value
    for i in range(len(nums)):
        sum += nums[i]
        while sum >= s:
            min_size = min(min_size, i - start + 1)
            sum -= nums[start]
            start += 1
            
    return min_size if min_size != float("inf") else 0

if __name__ == '__main__':
    print(minSubArrayLen0(3, [1,1]))
        
            
