# -*- coding: utf-8 -*-
# =============================================================================
# Given an integer array, return the k-th smallest distance among all the pairs. The distance of a pair (A, B) is defined as the absolute difference between A and B.
# 
# Example 1:
# Input:
# nums = [1,3,1]
# k = 1
# Output: 0 
# Explanation:
# Here are all the pairs:
# (1,3) -> 2
# (1,1) -> 0
# (3,1) -> 2
# Then the 1st smallest distance pair is (1,1), and its distance is 0.
# Note:
# 2 <= len(nums) <= 10000.
# 0 <= nums[i] < 1000000.
# 1 <= k <= len(nums) * (len(nums) - 1) / 2.
# =============================================================================
import itertools

# Time: O(nlogn + nlogw), n = len(nums), w = max(nums) - min(nums)
# Space: O(1)
def smallestDistancePair(nums: list, k: int) -> int:
    """
    :type nums:list
    :type k: int
    :rtype int
    """
    nums.sort()
    left, right = 0, nums[-1] - nums[0]
    while left < right:
        mid = left + (right - left) // 2
        # check the mid is the k-th smallest value
        if possibleValue(nums, mid, k):
            right = mid
        else:
            left = mid - 1
    return left    
        
        

# slide window solution; window size is two
def possibleValue(nums, target, k):
    """
    check if the target is the k-th smallest distance        
    """
    count, left = 0, 0
    for key, value in enumerate(nums):
        while value - nums[left] > target:
            left += 1
        # number of distance less than or equals target
        count += key - left # [1,2], [1,3], [1,1]
    return count >= k # k here is not index
            

def sol2(nums: list, k: int) -> int:
    nums.sort()
    pairs = list(itertools.combinations(nums, 2))
    result = abs(pairs[k - 1][0] - pairs[k - 1][1]) # the distance WAS NOT SORTED!!!!
    return result # WRONG RESULT
        
if __name__ == '__main__':
    print(sol2([62,100,4], 2))