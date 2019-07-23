# -*- coding: utf-8 -*-
# =============================================================================
# Given two arrays, write a function to compute their intersection.
# 
# Example 1:
# 
# Input: nums1 = [1,2,2,1], nums2 = [2,2]
# Output: [2,2]
# Example 2:
# 
# Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
# Output: [4,9]
# Note:
# 
# Each element in the result should appear as many times as it shows in both arrays.
# The result can be in any order.
# Follow up:
# 
# What if the given array is already sorted? How would you optimize your algorithm?
# What if nums1's size is small compared to nums2's size? Which algorithm is better?
# What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
# =============================================================================

# =============================================================================
# # If the given array is not sorted and the memory is unlimited:
# #   - Time:  O(m + n)
# #   - Space: O(min(m, n))
# # elif the given array is already sorted:
# #   if m << n or m >> n:
# #     - Time:  O(min(m, n) * log(max(m, n)))
# #     - Space: O(1)
# #   else:
# #     - Time:  O(m + n)
# #     - Soace: O(1)
# # else: (the given array is not sorted and the memory is limited)
# #     - Time:  O(max(m, n) * log(max(m, n)))
# #     - Space: O(1)
# =============================================================================

import bisect
import collections

# Time: O(m + n)
# Space: O(1)
def intersect(nums1:list, nums2:list) -> list:
    if len(nums1) > len(nums2):
        return intersect(nums2, nums1)
    nums1.sort() # if it is sorted, then it does not count in time
    nums2.sort()
    it1, it2 = 0, 0
    res = []
    while it1 < len(nums1) and it2 < len(nums2):
        if nums1[it1] < nums2[it2]:
            it1 += 1
        elif nums1[it1] > nums2[it2]:
            it2 += 1
        else:
            res += nums1[it1],
            it1 += 1
            it2 += 1
    return res

def sol2(nums1:list, nums2:list) -> list:
    if len(nums1) > len(nums2):
        return sol2(nums2, nums1)
    res = []
    nums1.sort()
    for v in nums2:
        i = bisect.bisect_left(nums1, v) # v is not required to be in list
        if i < len(nums1) and nums1[i] == v:
            res += v,
            del nums1[i] # remove the item in index i
            
    return res

def sol3(nums1: list, nums2: list) -> list:
    c = collections.Counter(nums1) & collections.Counter(nums2) # intersection min(c1[x], c2[x])
    res = []
    for i in c:
        res.extend([i] * c[i])
    return res

def sol4(nums1: list, nums2: list) -> list:
    lookup = collections.defaultdict(int)  # returns a DICTIONARY-like object, the value type is INT
    for i in nums1:
        lookup[i] += 1
    res = []
    for i in nums2:
        if lookup[i] > 0:
            res += i,
            lookup[i] -= 1
    return res

if __name__ == '__main__':
    result = sol4([1,2,2,1], [2,2])
    for i in result:
        print(i)